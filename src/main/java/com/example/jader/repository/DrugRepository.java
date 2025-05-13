package com.example.jader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jader.model.CaseReportIdentifierDto;
import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugNameCountDto; // 作成したDTOをインポート

@Repository
public interface DrugRepository extends JpaRepository<DrugEntry, Integer> {

	@Query("SELECT new com.example.jader.model.DrugNameCountDto(d.drugName, COUNT(d)) " +
		   "FROM DrugEntry d " +
		   "WHERE LOWER(d.drugName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		   "GROUP BY d.drugName " +
		   "ORDER BY COUNT(d) DESC, d.drugName ASC") // 件数の多い順、同じ場合は名前順
	List<DrugNameCountDto> findDrugNameAndCountByKeyword(@Param("keyword") String keyword);

	// 全件取得し、drug_name でグループ化して件数を取得するメソッド (初期表示やキーワードなしの場合)
	@Query("SELECT new com.example.jader.model.DrugNameCountDto(d.drugName, COUNT(d)) " +
		   "FROM DrugEntry d " +
		   "GROUP BY d.drugName " +
		   "ORDER BY COUNT(d) DESC, d.drugName ASC")
	List<DrugNameCountDto> findAllDrugNameAndCount();

	@Query("SELECT d FROM DrugEntry d WHERE d.drugName LIKE CONCAT('%', :keyword, '%')")
	List<DrugEntry> findByDrugNameLike(@Param("keyword") String keyword);

	@Query("SELECT DISTINCT new com.example.jader.model.CaseReportIdentifierDto(d.caseId, d.reportCount) " +
       "FROM DrugEntry d WHERE d.drugName IN :selectedDrugNames")
	List<CaseReportIdentifierDto> findCaseReportIdentifiersByDrugNames(@Param("selectedDrugNames") List<String> selectedDrugNames);
}