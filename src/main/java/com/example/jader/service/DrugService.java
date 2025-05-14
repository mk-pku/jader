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
	public List<NameCountDto> countByMedicineNameLike(String keyword, String nameType) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}
		keyword = keyword.trim();
		if ("product".equals(nameType)) {
			return drugRepository.countByProductNameLike(keyword);
		} else {
			return drugRepository.countByDrugNameLike(keyword);
		}
	}
	
	public List<NameCountDto> countByIndicationLike(String keyword) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return Collections.emptyList();
		}
		keyword = keyword.trim();
		return drugRepository.countByIndicationLike(keyword);
	}
	
	public List<NameStatsDto> statsOnIndicationByMedicineName(List<String> medicineNames) {
		if (medicineNames == null || medicineNames.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnIndicationByMedicineName(medicineNames);
		return CountToPercentage.process(raw);
	}
	
	public List<NameStatsDto> statsOnDrugNameByIndication(List<String> indications) {
		if (indications == null || indications.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnDrugNameByIndication(indications);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnProductNameByIndication(List<String> indications) {
		if (indications == null || indications.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnProductNameByIndication(indications);
		return CountToPercentage.process(raw);
	}
}