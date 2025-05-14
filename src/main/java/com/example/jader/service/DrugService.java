package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jader.model.NameCountDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.repository.DrugRepository;
import com.example.jader.util.CountToPercentage;

@Service
@Transactional(readOnly = true)
public class DrugService {

	private final DrugRepository drugRepository;

	public DrugService(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
	}

	/**
	 * @param nameType "generic" or "product"
	 */
	public List<NameCountDto> searchDrugNameAndCount(String keyword, String nameType) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}
		keyword = keyword.trim();
		if ("product".equals(nameType)) {
			return drugRepository.findByProductName(keyword);
		} else {
			return drugRepository.findByGenericName(keyword);
		}
	}
	
	public List<NameStatsDto> getIndicationCounts(List<String> selectedDrugNames) {
		if (selectedDrugNames == null || selectedDrugNames.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.findIndicationCounts(selectedDrugNames);
		return CountToPercentage.process(raw);
	}
}