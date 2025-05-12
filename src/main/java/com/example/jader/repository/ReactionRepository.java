// ReactionRepository.java
package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.dto.ReactionTermCountDto;
import com.example.jader.model.Reac;

@Repository
public interface ReactionRepository extends JpaRepository<Reac, Integer> {

    @Query("SELECT new com.example.jader.dto.ReactionTermCountDto("
         + " r.reactionTerm, "
         + " COUNT(DISTINCT CONCAT(r.caseId, '-', r.reportCount)) ) "
         + "FROM Reac r, Drug d "
         + "WHERE r.caseId = d.caseId "
         + "  AND r.reportCount = d.reportCount "
         + "  AND d.drugName IN :selectedDrugNames "
         + "GROUP BY r.reactionTerm "
         + "ORDER BY COUNT(DISTINCT CONCAT(r.caseId, '-', r.reportCount)) DESC"
    )
    List<ReactionTermCountDto> findReactionTermCounts(
        @Param("selectedDrugNames") List<String> selectedDrugNames);
}
