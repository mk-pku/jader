package com.example.jader.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.QDrugEntry;
import com.example.jader.model.QReacEntry;
import com.example.jader.model.ReacEntry;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ReactionRepositoryImpl
	extends AbstractRepository<ReacEntry, QReacEntry>
	implements ReactionRepositoryCustom {

	public ReactionRepositoryImpl(JPAQueryFactory jf) {
		super(
			jf,
			ReacEntry.class,
			QReacEntry.reacEntry,
			q -> {
				q.leftJoin(QReacEntry.reacEntry.demo.drugs, QDrugEntry.drugEntry);
			}
		);
	}

	@Override
	public Page<CountResultDto> countByFieldLike(
		String fieldName, String keyword, Pageable pageable
	) {
		return countByFieldLikeGeneric(fieldName, keyword, pageable);
	}

	@Override
	public List<NameStatsDto> statsBy(
		String filterField, String groupField, String filterValue, int limit
	) {
		return statsByGeneric(filterField, groupField, filterValue, limit);
	}
}
