package com.example.jader.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.service.DrugService;
import com.example.jader.service.ReactionService;

@Controller
@RequestMapping("/reacs")
public class ReacController {
	
	private final DrugService drugService;
	private final ReactionService reacService;
	
	public ReacController(DrugService drugService, ReactionService reacService) {
		this.drugService = drugService;
		this.reacService = reacService;
	}
	
	@GetMapping("/search/count")
	public String searchCount(
			@RequestParam(required = false) String fieldName,
			@RequestParam(required=false) String keyword,
			Pageable pageable,
			Model model) {
		
		Page<CountResultDto> countResultsPage = reacService.countByFieldLike(fieldName, keyword, pageable);
		model.addAttribute("countResultsPage", countResultsPage);
		model.addAttribute("fieldName", fieldName);
		model.addAttribute("keyword", keyword);

        return "reacs/search-count";
	}
	
	@GetMapping("reactionTerm/{reactionTerm}")
	public String showReactionTerm(@PathVariable String reactionTerm, Model model) {
		List<NameStatsDto> drugNameStats = drugService.statsOnDrugNameByReactionTerm(reactionTerm);
		List<NameStatsDto> productNameStats = drugService.statsOnProductNameByReactionTerm(reactionTerm);

        model.addAttribute("fieldName", reactionTerm);
        model.addAttribute("fieldStats1", drugNameStats);
        model.addAttribute("fieldStats2", productNameStats);
        return "reacs/show";
	}
}