// src/main/java/com/example/jader/repository/ReactionStatsRepositoryImpl.java
package com.example.jader.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jader.model.DrugEntry;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.QDrugEntry;
import com.example.jader.model.QReacEntry;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReactionRepositoryImpl implements ReactionRepositoryCustom {

    private final JPAQueryFactory qf;

    @Override
    public List<NameStatsDto> statsBy(
            FilterField filterField,
            GroupField  groupField,
            String      filterValue,
            int         limit) {

        if (!StringUtils.hasText(filterValue)) {
            throw new IllegalArgumentException("filterValue must not be empty");
        }

        QDrugEntry   d = QDrugEntry.drugEntry;
        QReacEntry   r = QReacEntry.reacEntry;

        // DrugEntry 側の JPQL エイリアス名取得
        String alias = d.getMetadata().getName();  // 通常 "drugEntry"
        PathBuilder<DrugEntry> pb = new PathBuilder<>(DrugEntry.class, alias);

        // 1) WHERE 用パス（drugName or productName）
        StringPath filterPath = pb.getString(filterField.getPath());

        // 2) GROUP BY／COALESCE 用ラベル
        StringExpression labelExpr;
        if (groupField == GroupField.REACTION_TERM) {
            // ReacEntry の reactionTerm をグループ
            labelExpr = r.reactionTerm.coalesce("元データ未入力");
        } else {
            // DrugEntry 側または Indication をグループ
            labelExpr = pb
                .getString(groupField.getPath())
                .coalesce("元データ未入力");
        }

        // WHERE 句構築（ReacEntry と JOIN）
        BooleanBuilder where = new BooleanBuilder()
            .and(d.demo.caseId.eq(r.demo.caseId))
            .and(filterPath.eq(filterValue));

        return qf
            .select(Projections.constructor(
                NameStatsDto.class,
                labelExpr,   // グループラベル
                d.count()    // COUNT(d)
            ))
            .from(d)
            .leftJoin(r).on(d.demo.caseId.eq(r.demo.caseId))
            .where(where)
            .groupBy(labelExpr)
            .orderBy(d.count().desc())
            .limit(limit)
            .fetch();
    }
}
