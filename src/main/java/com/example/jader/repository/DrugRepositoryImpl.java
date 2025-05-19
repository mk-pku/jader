package com.example.jader.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.DrugEntry;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.QDrugEntry;
import com.example.jader.model.QReacEntry;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class DrugRepositoryImpl 
        extends QuerydslRepositorySupport 
        implements DrugRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    
    public DrugRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DrugEntry.class);
        this.queryFactory = queryFactory;
    }

    // 1) 動的ページング＋集計メソッド
    @Override
    public Page<CountResultDto> countByFieldLike(
            String fieldName, String keyword, Pageable pageable) {

        if (!StringUtils.hasText(fieldName) 
         || !StringUtils.hasText(keyword)) {
            return Page.empty(pageable);
        }
        // ホワイトリスト確認
        String path = Arrays.stream(FilterField.values())
            .filter(f -> f.getPath().equals(fieldName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown field: " + fieldName))
            .getPath();

        QDrugEntry d = QDrugEntry.drugEntry;
        // 動的パス
        PathBuilder<DrugEntry> pb = new PathBuilder<>(DrugEntry.class, d.getMetadata().getName());
        StringPath strPath = pb.getString(path);

        // ベースクエリ
        JPQLQuery<CountResultDto> base = queryFactory
            .select(Projections.constructor(
                CountResultDto.class,
                strPath,
                d.count()                        // ← インスタンスメソッドで COUNT(d)
            ))
            .from(d)
            .where(strPath.contains(keyword))
            .groupBy(strPath)
            .orderBy(d.count().desc(), strPath.asc());

        // ページング＆フェッチ
        List<CountResultDto> content = base
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 全件数クエリ
        long total = queryFactory
            .select(strPath.countDistinct())
            .from(d)
            .where(strPath.contains(keyword))
            .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<NameStatsDto> statsBy(
            FilterField filterField,
            GroupField  groupField,
            String      filterValue,
            int         limit) {

        if (!StringUtils.hasText(filterValue)) {
            throw new IllegalArgumentException("filterValue must not be empty");
        }

        QDrugEntry d = QDrugEntry.drugEntry;
        QReacEntry r = QReacEntry.reacEntry;

        // JPQLエイリアスと同じ名前を取得
        String alias = d.getMetadata().getName();  // 通常 "drugEntry"
        PathBuilder<DrugEntry> pb = new PathBuilder<>(DrugEntry.class, alias);

        // フィルタ用パス
        StringPath filterPath = pb.getString(filterField.getPath());
        // グループ用 coalesce
        StringExpression labelExpr = pb
            .getString(groupField.getPath())
            .coalesce("元データ未入力");

        // WHERE 条件構築
        BooleanBuilder where = new BooleanBuilder();
        if (filterField == FilterField.REACTION_TERM) {
            where.and(d.demo.caseId.eq(r.demo.caseId))
                 .and(r.reactionTerm.eq(filterValue));
        } else {
            where.and(filterPath.eq(filterValue));
        }

        // クエリ実行
        return queryFactory
            .select(Projections.constructor(
                NameStatsDto.class,
                labelExpr,
                d.count()))
            .from(d)
            .leftJoin(r).on(d.demo.caseId.eq(r.demo.caseId))  // 必要に応じて
            .where(where)
            .groupBy(labelExpr)
            .orderBy(d.count().desc())
            .limit(limit)
            .fetch();
    }
}
