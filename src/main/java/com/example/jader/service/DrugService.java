package com.example.jader.service;

import com.example.jader.dto.DrugNameCountDto;
import com.example.jader.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 読み取り専用でも付与を推奨

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true) // 読み取り処理が主なので readOnly = true を推奨
public class DrugService {

	private final DrugRepository drugRepository;

	@Autowired
	public DrugService(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
	}

	/**
	 * 指定されたキーワードで医薬品一般名を検索し、名称ごとの件数を取得します。
	 * キーワードがnullまたは空の場合は、全医薬品の名称ごとの件数を返します。
	 *
	 * @param keyword 検索キーワード
	 * @return 医薬品一般名と件数のリスト
	 */
	public List<DrugNameCountDto> searchDrugNameAndCount(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			// return drugRepository.findAllDrugNameAndCount(); // 全件集計を返す場合
			return Collections.emptyList(); // キーワードなしの場合は空リストを返す仕様も可
		}
		return drugRepository.findDrugNameAndCountByKeyword(keyword.trim());
	}

	/**
	 * 全ての医薬品について、一般名ごとの件数を取得します。
	 * @return 医薬品一般名と件数のリスト
	 */
	public List<DrugNameCountDto> getAllDrugNameAndCount() {
		return drugRepository.findAllDrugNameAndCount();
	}
}