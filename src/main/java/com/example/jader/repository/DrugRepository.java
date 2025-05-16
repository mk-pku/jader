package com.example.jader.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugSummaryDto;
import com.example.jader.model.NameCountDto; // 作成したDTOをインポート
import com.example.jader.model.NameStatsDto;

@Repository
public interface DrugRepository extends JpaRepository<DrugEntry, Integer> {

	// 医薬品（一般名）の部分一致検索
	@Query("SELECT new com.example.jader.model.NameCountDto(d.drugName, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.drugName LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.drugName "
		 + "ORDER BY COUNT(d) DESC, d.drugName ASC")
	List<NameCountDto> countByDrugNameLike(@Param("keyword") String keyword);

	// 医薬品（販売名）の部分一致検索
	@Query("SELECT new com.example.jader.model.NameCountDto(d.productName, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.productName LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.productName "
		 + "ORDER BY COUNT(d) DESC, d.productName ASC")
	List<NameCountDto> countByProductNameLike(@Param("keyword") String keyword);

	// 使用理由の部分一致検索
	@Query("SELECT new com.example.jader.model.NameCountDto(d.indication, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.indication LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.indication "
		 + "ORDER BY COUNT(d) DESC, d.indication ASC")
	List<NameCountDto> countByIndicationLike(@Param("keyword") String keyword);

	// 特定の医薬品名を持つ使用理由の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
    	 + " COALESCE(d.indication, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.drugName IN :names OR d.productName IN :names "
		 + "GROUP BY COALESCE(d.indication, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, "
		 + "COALESCE(d.indication, '元データ未入力') ASC")
	List<NameStatsDto> statsOnIndicationByMedicineName(@Param("names") List<String> names);
	
	// 特定の使用理由を持つ医薬品（一般名）の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
    	 + " COALESCE(d.drugName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.indication IN :names "
		 + "GROUP BY COALESCE(d.drugName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, "
		 + "COALESCE(d.drugName, '元データ未入力') ASC")
	List<NameStatsDto> statsOnDrugNameByIndication(@Param("names") List<String> names);

	// 特定の使用理由を持つ医薬品（販売名）の件数
	@Query("SELECT new com.example.jader.model.NameStatsDto("
    	 + " COALESCE(d.productName, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.indication IN :names "
		 + "GROUP BY COALESCE(d.productName, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, "
		 + "COALESCE(d.productName, '元データ未入力') ASC")
	List<NameStatsDto> statsOnProductNameByIndication(@Param("names") List<String> names);

	// 部分一致検索 + ページネーション
	@Query("SELECT new com.example.jader.model.DrugSummaryDto("
    	 + " d.demo.caseId, d.drugName, d.productName) "
		 + "FROM DrugEntry d "
		 + "WHERE (:nameType = 'generic' AND LOWER(d.drugName) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
		 + " OR (:nameType = 'product' AND LOWER(d.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
	Page<DrugSummaryDto> findByKeyword(
		@Param("keyword") String keyword,
		@Param("nameType") String nameType,
		Pageable pageable
	);
}