package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugNameCountDto; // 作成したDTOをインポート
import com.example.jader.model.IndicationCountDto;

@Repository
public interface DrugRepository extends JpaRepository<DrugEntry, Integer> {

	// 医薬品（一般名）の部分一致検索
	@Query("SELECT new com.example.jader.model.DrugNameCountDto(d.drugName, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.drugName LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.drugName "
		 + "ORDER BY COUNT(d) DESC, d.drugName ASC")
	List<DrugNameCountDto> findByGenericName(@Param("keyword") String keyword);

	// 医薬品（販売名）の部分一致検索
	@Query("SELECT new com.example.jader.model.DrugNameCountDto(d.productName, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.productName LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.productName "
		 + "ORDER BY COUNT(d) DESC, d.productName ASC")
	List<DrugNameCountDto> findByProductName(@Param("keyword") String keyword);

	// 特定の識別番号を持つ使用理由の件数
	@Query("SELECT new com.example.jader.model.IndicationCountDto("
    	 + " COALESCE(d.indication, '元データ未入力'), COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.drugName IN :names OR d.productName IN :names "
		 + "GROUP BY COALESCE(d.indication, '元データ未入力') "
		 + "ORDER BY COUNT(d) DESC, indication ASC")
	List<IndicationCountDto> findIndicationCounts(@Param("names") List<String> names);

}