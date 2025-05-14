package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.NameCountDto;
import com.example.jader.model.ReacEntry;

@Repository
public interface ReactionRepository extends JpaRepository<ReacEntry, Integer> {

	// 特定の識別番号を持つ有害事象の件数
	@Query("SELECT new com.example.jader.model.NameCountDto("
		 + " r.reactionTerm, "
		 + " COUNT(DISTINCT CONCAT(r.caseId, '-', r.reportCount)) ) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.caseId = d.caseId "
		 + "  AND r.reportCount = d.reportCount "
		 + "  AND d.drugName IN :selectedDrugNames "
		 + "GROUP BY r.reactionTerm "
		 + "ORDER BY COUNT(DISTINCT CONCAT(r.caseId, '-', r.reportCount)) DESC"
	)
	List<NameCountDto> findReactionTermCounts(
		@Param("selectedDrugNames") List<String> selectedDrugNames);
}
