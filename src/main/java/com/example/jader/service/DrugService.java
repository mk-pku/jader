package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jader.model.DrugNameCountDto;
import com.example.jader.repository.DrugRepository;

@Service
@Transactional(readOnly = true)
public class DrugService {

	private final DrugRepository drugRepository;

	@Autowired
	public DrugService(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
	}

	public List<DrugNameCountDto> searchDrugNameAndCount(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
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