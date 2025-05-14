package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.NameStatsDto;
import com.example.jader.model.ReacEntry;

@Repository
public interface ReactionRepository extends JpaRepository<ReacEntry, Integer> {

	// 特定の医薬品名を持つ有害事象の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " r.reactionTerm, "
		 + " COUNT(r.reactionTerm)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.caseId = d.caseId "
		 + " AND (d.drugName IN :names OR d.productName IN :names) "
		 + "GROUP BY r.reactionTerm "
		 + "ORDER BY COUNT(r.reactionTerm) DESC")

	List<NameStatsDto> statsOnReactionTermByMedicineName(
		@Param("names") List<String> names);
}
