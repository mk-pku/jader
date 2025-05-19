package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugSearchDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.repository.DrugRepository;
import com.example.jader.specification.DrugSpecification;
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
//	public List<CountResultDto> countByMedicineNameLike(String keyword, String nameType) {
//		if (keyword == null || keyword.trim().isEmpty()) {
//			return Collections.emptyList();
//		}
//		keyword = keyword.trim();
//		if ("product".equals(nameType)) {
//			return drugRepository.countByProductNameLike(keyword);
//		} else {
//			return drugRepository.countByDrugNameLike(keyword);
//		}
//	}
//	
//	public List<CountResultDto> countByIndicationLike(String keyword) {
//		if (keyword == null || keyword.trim().isEmpty()) {
//			return Collections.emptyList();
//		}
//		keyword = keyword.trim();
//		return drugRepository.countByIndicationLike(keyword);
//	}
	
	public List<NameStatsDto> statsOnIndicationByMedicineName(List<String> medicineNames) {
		if (medicineNames == null || medicineNames.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnIndicationByMedicineName(medicineNames);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnIndicationByDrugName(String drugName) {
		if (drugName == null || drugName.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnIndicationByDrugName(drugName);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnIndicationByProductName(String productName) {
		if (productName == null || productName.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnIndicationByProductName(productName);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnDrugNameByIndication(String indication) {
		if (indication == null || indication.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnDrugNameByIndication(indication);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnProductNameByIndication(String indication) {
		if (indication == null || indication.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnProductNameByIndication(indication);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnDrugNameByReactionTerm(String reactionTerm) {
		if (reactionTerm == null || reactionTerm.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnDrugNameByReactionTerm(reactionTerm);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnProductNameByReactionTerm(String reactionTerm) {
		if (reactionTerm == null || reactionTerm.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = drugRepository.statsOnProductNameByReactionTerm(reactionTerm);
		return CountToPercentage.process(raw);
	}
	
	public Page<DrugEntry> search(DrugSearchDto criteria, Pageable pageable) {
		Specification<DrugEntry> specCaseId = DrugSpecification.byCaseId(criteria.getCaseId());
		Specification<DrugEntry> specDrugName = DrugSpecification.byDrugName(criteria.getDrugName());
		Specification<DrugEntry> specProductName = DrugSpecification.byProductName(criteria.getProductName());
		Specification<DrugEntry> specAdministrationRoute = DrugSpecification.byAdministrationRoute(criteria.getAdministrationRoute());
		Specification<DrugEntry> specIndication = DrugSpecification.byIndication(criteria.getIndication());
		
		Specification<DrugEntry> combinedSpec = Specification
				.where(specCaseId)
				.and(specDrugName)
				.and(specProductName)
				.and(specAdministrationRoute)
				.and(specIndication);
		
		return drugRepository.findAll(combinedSpec, pageable);
	}
	
	public Page<CountResultDto> countByFieldLike(String fieldName, String keyword, Pageable pageable) {
		if (!StringUtils.hasText(fieldName) || !StringUtils.hasText(keyword)) {
			return Page.empty(pageable);
		}
		
		return switch (fieldName) {
			case "drugName" -> drugRepository.countByDrugNameLike(keyword, pageable);
			case "productName" -> drugRepository.countByProductNameLike(keyword, pageable);
			case "indication" -> drugRepository.countByIndicationLike(keyword, pageable);
			default -> throw new IllegalArgumentException("Unknown fieldName: " + fieldName);
		};
	}
}