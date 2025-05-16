package com.example.jader.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.NameCountDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.service.DrugService;
import com.example.jader.service.ReactionService;

@Controller
@RequestMapping("/drugs")
public class DrugController {
	
	private final DrugService drugService;
	private final ReactionService reacService;
	
	public DrugController(DrugService drugService, ReactionService reacService) {
		this.drugService = drugService;
		this.reacService = reacService;
	}
	
	@GetMapping("/nameSearch")
	public String nameSearchForm(Model model) {
		model.addAttribute("activeTab", "drugs");
		return "drugs/name-search";
	}

	@GetMapping("/nameSearchResult")
	public String nameSearchResult(
		@RequestParam(required=false) String keyword,
		@RequestParam(required=false, defaultValue="generic") String nameType,
		@RequestParam(required=false, defaultValue="0") int page,
		Model model) {
		
		Pageable pageable = PageRequest.of(page, 20, Sort.unsorted());
        Page<NameCountDto> results = drugService.findByDrugNameLikePage(keyword, pageable);

		model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("nameType", nameType);
        return "drugs/name-search-result";
	}

	@GetMapping("{drugName}")
	public String showDrugName(@PathVariable String drugName, Model model) {
		List<NameStatsDto> indicationStats = drugService.statsOnIndicationByDrugName(drugName);
		List<NameStatsDto> reactionTermStats = reacService.statsOnReactionTermByDrugName(drugName);
		
		model.addAttribute("activeTab", "drugs");
        model.addAttribute("drugName", drugName);
        model.addAttribute("indicationStats", indicationStats);
		model.addAttribute("reactionTermStats", reactionTermStats);
        return "drugs/show";
	}
}
