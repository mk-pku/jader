package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.NameCountDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.ReacEntry;

@Repository
public interface ReactionRepository extends JpaRepository<ReacEntry, Integer> {

	// 有害事象の部分一致検索
	@Query("SELECT new com.example.jader.model.NameCountDto(r.reactionTerm, COUNT(r)) "
		 + "FROM ReacEntry r "
		 + "WHERE r.reactionTerm LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY r.reactionTerm "
		 + "ORDER BY COUNT(r) DESC, r.reactionTerm ASC")
	List<NameCountDto> countByReactionTermLike(@Param("keyword") String keyword);

	// 特定の医薬品名を持つ有害事象の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " r.reactionTerm, "
		 + " COUNT(r.reactionTerm)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.demo.caseId = d.demo.caseId "
		 + " AND (d.drugName IN :names OR d.productName IN :names) "
		 + "GROUP BY r.reactionTerm "
		 + "ORDER BY COUNT(r.reactionTerm) DESC")
	List<NameStatsDto> statsOnReactionTermByMedicineName(@Param("names") List<String> names);

	// 特定の有害事象を持つ医薬品（一般名）の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
    	 + " COALESCE(d.drugName, '元データ未入力'), COUNT(d)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.demo.caseId = d.demo.caseId "
		 + " AND r.reactionTerm IN :names "
		 + "GROUP BY COALESCE(d.drugName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, "
		 + "COALESCE(d.drugName, '元データ未入力') ASC")
	List<NameStatsDto> statsOnDrugNameByReactionTerm(@Param("names") List<String> names);

	// 特定の有害事象を持つ医薬品（販売名）の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
    	 + " COALESCE(d.productName, '元データ未入力'), COUNT(d)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.demo.caseId = d.demo.caseId "
		 + " AND r.reactionTerm IN :names "
		 + "GROUP BY COALESCE(d.productName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, "
		 + "COALESCE(d.productName, '元データ未入力') ASC")
	List<NameStatsDto> statsOnProductNameByReactionTerm(@Param("names") List<String> names);
}
