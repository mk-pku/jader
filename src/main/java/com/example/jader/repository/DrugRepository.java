package com.example.jader.repository;

import com.example.jader.model.Drug;
import com.example.jader.dto.DrugNameCountDto; // 作成したDTOをインポート
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {

    /**
     * 医薬品一般名(drug_name)で部分一致検索し、
     * drug_name でグループ化して各名称の件数を取得します。
     *
     * @param keyword 検索キーワード (drug_name の一部)
     * @return 医薬品一般名と件数のリスト (DrugNameCountDto)
     */
    @Query("SELECT new com.example.jader.dto.DrugNameCountDto(d.drugName, COUNT(d)) " +
           "FROM Drug d " +
           "WHERE LOWER(d.drugName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "GROUP BY d.drugName " +
           "ORDER BY COUNT(d) DESC, d.drugName ASC") // 件数の多い順、同じ場合は名前順
    List<DrugNameCountDto> findDrugNameAndCountByKeyword(@Param("keyword") String keyword);

    // 全件取得し、drug_name でグループ化して件数を取得するメソッド (初期表示やキーワードなしの場合)
    @Query("SELECT new com.example.jader.dto.DrugNameCountDto(d.drugName, COUNT(d)) " +
           "FROM Drug d " +
           "GROUP BY d.drugName " +
           "ORDER BY COUNT(d) DESC, d.drugName ASC")
    List<DrugNameCountDto> findAllDrugNameAndCount();
}