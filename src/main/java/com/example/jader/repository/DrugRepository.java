package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugNameCountDto; // 作成したDTOをインポート

@Repository
public interface DrugRepository extends JpaRepository<DrugEntry, Integer> {

	// 医薬品（一般名）の部分一致検索
	@Query("SELECT new com.example.jader.model.DrugNameCountDto(d.drugName, COUNT(d)) "
		 + "FROM DrugEntry d "
		 + "WHERE d.drugName LIKE CONCAT('%', :keyword, '%') "
		 + "GROUP BY d.drugName "
		 + "ORDER BY COUNT(d) DESC, d.drugName ASC")
	List<DrugNameCountDto> findDrugNameAndCountByKeyword(@Param("keyword") String keyword);
}