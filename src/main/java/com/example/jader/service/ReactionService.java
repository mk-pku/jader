package com.example.jader.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jader.dto.ReactionTermCountDto;
import com.example.jader.repository.ReactionRepository;

@Service
@Transactional(readOnly = true)
public class ReactionService {
    private final ReactionRepository reactionRepository;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository;
    }

    public List<ReactionTermCountDto> getReactionTermCounts(List<String> selectedDrugNames) {
        if (selectedDrugNames == null || selectedDrugNames.isEmpty()) {
            return Collections.emptyList();
        }
        return reactionRepository.findReactionTermCounts(selectedDrugNames);
    }
}
