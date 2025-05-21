package com.example.jader.repository;

import static com.example.jader.model.QDrugEntry.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.DrugEntry;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.QDrugEntry;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class DrugRepositoryImpl implements DrugRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private static final QDrugEntry D = drugEntry;

	public DrugRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<CountResultDto> countByFieldLike(
		String     fieldName,
		String     keyword,
		Pageable   pageable
	) {
		if (!StringUtils.hasText(fieldName) || !StringUtils.hasText(keyword)) {
			return Page.empty(pageable);
		}

		String alias = D.getMetadata().getName();
		PathBuilder<DrugEntry> pb = new PathBuilder<>(DrugEntry.class, alias);

		final StringPath path;
		try {
			path = pb.getString(fieldName);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknown field: " + fieldName);
		}

		var base = queryFactory
			.select(Projections.constructor(
				CountResultDto.class,
				path,
				D.count()
			))
			.from(D)
			.where(path.containsIgnoreCase(keyword))
			.groupBy(path)
			.orderBy(D.count().desc(), path.asc());

		List<CountResultDto> content = base
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = queryFactory
			.select(path.countDistinct())
			.from(D)
			.where(path.containsIgnoreCase(keyword))
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public List<NameStatsDto> statsBy(
		String filterField,
		String groupField,
		String filterValue,
		int    limit
	) {
		if (!StringUtils.hasText(filterField)
		 || !StringUtils.hasText(groupField)
		 || !StringUtils.hasText(filterValue)) {
			throw new IllegalArgumentException("filterField, groupField, filterValue must not be empty");
		}

		String alias = D.getMetadata().getName();
		PathBuilder<DrugEntry> pb = new PathBuilder<>(DrugEntry.class, alias);

		final StringPath filtPath, grpPath;
		try {
			filtPath = pb.getString(filterField);
			grpPath  = pb.getString(groupField);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknown field(s): " + filterField + ", " + groupField);
		}

		StringExpression label = grpPath.coalesce("元データ未入力");

		return queryFactory
			.select(Projections.constructor(
				NameStatsDto.class,
				label,
				D.count()
			))
			.from(D)
			.where(filtPath.eq(filterValue))
			.groupBy(label)
			.orderBy(D.count().desc())
			.limit(limit)
			.fetch();
	}
}
