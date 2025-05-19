package com.example.jader.repository;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class AbstractRepository<T, Q extends EntityPathBase<T>> {
	protected final JPAQueryFactory queryFactory;
	private final Class<T> entityClass;
	private final Q qEntity;
	private final Consumer<JPAQuery<?>> joinConfigurer;

	protected AbstractRepository(
		JPAQueryFactory queryFactory,
		Class<T>        entityClass,
		Q               qEntity,
		Consumer<JPAQuery<?>> joinConfigurer
	) {
		this.queryFactory    = queryFactory;
		this.entityClass     = entityClass;
		this.qEntity         = qEntity;
		this.joinConfigurer  = joinConfigurer;
	}

	protected StringPath getStringPath(
		PathBuilder<T> pb,
		String         field
	) {
		try {
			return pb.getString(field);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
				"Unknown field: " + field, e);
		}
	}

	public Page<CountResultDto> countByFieldLikeGeneric(
		String     fieldName,
		String     keyword,
		Pageable   pageable
	) {
		if (!StringUtils.hasText(fieldName)
		 || !StringUtils.hasText(keyword)) {
			return Page.empty(pageable);
		}
		PathBuilder<T> pb = new PathBuilder<>(entityClass, qEntity.getMetadata().getName());
		StringPath path = getStringPath(pb, fieldName);

		var base = queryFactory
			.select(Projections.constructor(
				CountResultDto.class,
				path, qEntity.count()
			))
			.from(qEntity)
			.where(path.containsIgnoreCase(keyword))
			.groupBy(path)
			.orderBy(qEntity.count().desc(), path.asc());

		List<CountResultDto> content = base
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
		long total = queryFactory
			.select(path.countDistinct())
			.from(qEntity)
			.where(path.containsIgnoreCase(keyword))
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	public List<NameStatsDto> statsByGeneric(
		String filterField,
		String groupField,
		String filterValue,
		int    limit
	) {
		if (!StringUtils.hasText(filterField)
		 || !StringUtils.hasText(groupField)
		 || !StringUtils.hasText(filterValue)) {
			throw new IllegalArgumentException(
				"filterField, groupField, filterValue must not be empty");
		}
		PathBuilder<T> pb = new PathBuilder<>(entityClass, qEntity.getMetadata().getName());
		StringPath filtPath = getStringPath(pb, filterField);
		StringPath grpPath  = getStringPath(pb, groupField);
		StringExpression label = grpPath.coalesce("元データ未入力");

		JPAQuery<NameStatsDto> q = queryFactory
			.select(Projections.constructor(
				NameStatsDto.class,
				label, qEntity.count()
			))
			.from(qEntity);

		joinConfigurer.accept(q);

		return q.where(filtPath.eq(filterValue))
		        .groupBy(label)
		        .orderBy(qEntity.count().desc())
		        .limit(limit)
		        .fetch();
	}
}
