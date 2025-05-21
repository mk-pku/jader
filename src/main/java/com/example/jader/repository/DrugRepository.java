package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.DrugEntry;
import com.example.jader.model.NameStatsDto;

@Repository
public interface DrugRepository extends
		JpaRepository<DrugEntry, Integer>,
		JpaSpecificationExecutor<DrugEntry>,
		DrugRepositoryCustom {

	// 特定の有害事象を持つ医薬品（一般名）の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(d.drugName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d, ReacEntry r "
		 + "WHERE d.demo.caseId = r.demo.caseId "
		 + " AND r.reactionTerm = :name "
		 + "GROUP BY COALESCE(d.drugName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnDrugNameByReactionTerm(@Param("name") String name);

	// 特定の有害事象を持つ医薬品（販売名）の件数（10件）
	@Query("SELECT new com.example.jader.model.NameStatsDto("
		 + " COALESCE(d.productName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d, ReacEntry r "
		 + "WHERE d.demo.caseId = r.demo.caseId "
		 + " AND r.reactionTerm = :name "
		 + "GROUP BY COALESCE(d.productName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC "
		 + "LIMIT 10")
	List<NameStatsDto> statsOnProductNameByReactionTerm(@Param("name") String name);
}