package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.repository.ReactionRepository;
import com.example.jader.util.CountToPercentage;

@Service
@Transactional(readOnly = true)
public class ReactionService {
	private final ReactionRepository reactionRepository;

	public ReactionService(ReactionRepository reactionRepository) {
		this.reactionRepository = reactionRepository;
	}

	public List<NameStatsDto> statsOnReactionTermByMedicineName(List<String> medicineNames) {
		if (medicineNames == null || medicineNames.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = reactionRepository.statsOnReactionTermByMedicineName(medicineNames);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnDrugNameByReactionTerm(List<String> reactionTerm) {
		if (reactionTerm == null || reactionTerm.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = reactionRepository.statsOnDrugNameByReactionTerm(reactionTerm);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnProductNameByReactionTerm(List<String> reactionTerm) {
		if (reactionTerm == null || reactionTerm.isEmpty()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = reactionRepository.statsOnProductNameByReactionTerm(reactionTerm);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnReactionTermByDrugName(String drugName) {
		if (drugName == null || drugName.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = reactionRepository.statsOnReactionTermByDrugName(drugName);
		return CountToPercentage.process(raw);
	}

	public List<NameStatsDto> statsOnReactionTermByProductName(String productName) {
		if (productName == null || productName.isBlank()) {
			return Collections.emptyList();
		}
		List<NameStatsDto> raw = reactionRepository.statsOnReactionTermByProductName(productName);
		return CountToPercentage.process(raw);
	}
	
	public Page<CountResultDto> countByFieldLike(
			String fieldName, String keyword, Pageable pageable) {
		return reactionRepository.countByFieldLike(fieldName, keyword, pageable);
	}
	
	public List<NameStatsDto> getStats(
			String filterField,
			String  groupField,
			String      filterValue,
			int         limit) {
		List<NameStatsDto> raw = reactionRepository.statsBy(filterField, groupField, filterValue, limit);
		return CountToPercentage.process(raw);
	}
}