package com.example.jader.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.model.ReacEntry;

@Repository
public interface ReactionRepository extends
		JpaRepository<ReacEntry, Integer>,
		ReactionRepositoryCustom {

	// 有害事象の部分一致検索
	@Query("SELECT new com.example.jader.model.CountResultDto(r.reactionTerm, COUNT(r)) "
		 + "FROM ReacEntry r "
		 + "WHERE r.reactionTerm LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY r.reactionTerm "
		 + "ORDER BY COUNT(r) DESC, r.reactionTerm ASC")
	Page<CountResultDto> countByReactionTermLike(@Param("keyword") String keyword, Pageable pageable);

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

	// 特定の医薬品（一般名名）を持つ有害事象の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(r.reactionTerm, '元データ未入力'), COUNT(r)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.demo.caseId = d.demo.caseId "
		 + " AND d.drugName = :names "
		 + "GROUP BY COALESCE(r.reactionTerm, '元データ未入力') "
		 + "ORDER BY COUNT(r) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnReactionTermByDrugName(@Param("names") String name);

	// 特定の医薬品（販売名）を持つ有害事象の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(r.reactionTerm, '元データ未入力'), COUNT(r)) "
		 + "FROM ReacEntry r, DrugEntry d "
		 + "WHERE r.demo.caseId = d.demo.caseId "
		 + " AND d.productName = :names "
		 + "GROUP BY COALESCE(r.reactionTerm, '元データ未入力') "
		 + "ORDER BY COUNT(r) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnReactionTermByProductName(@Param("names") String name);

		// 特定の使用理由を持つ医薬品（一般名）の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(d.drugName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.indication = :name "
		 + "GROUP BY COALESCE(d.drugName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnDrugNameByIndication(@Param("name") String name);

	// 特定の使用理由を持つ医薬品（販売名）の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(d.productName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.indication = :name "
		 + "GROUP BY COALESCE(d.productName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnProductNameByIndication(@Param("name") String name);
}