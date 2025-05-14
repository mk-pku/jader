package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
