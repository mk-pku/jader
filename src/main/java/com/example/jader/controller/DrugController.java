package com.example.jader.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.CountResultDto;
import com.example.jader.model.DrugEntry;
import com.example.jader.model.DrugSearchDto;
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

	@GetMapping("/search/result")
	public String nameSearchResult(
			@ModelAttribute DrugSearchDto drugSearchDto,
			@PageableDefault(size=20, sort="id", direction=Sort.Direction.ASC) Pageable pageable,
			Model model) {

		Page<DrugEntry> resultsPage = drugService.search(drugSearchDto, pageable);

		model.addAttribute("results", resultsPage);
		model.addAttribute("drugSearchDto", drugSearchDto);
		return "drugs/search-result";
	}
	
	@GetMapping("/search/count")
	public String searchCount(
			@RequestParam(required = false) String fieldName,
			@RequestParam(required=false) String keyword,
			Pageable pageable,
			Model model) {
		
		Page<CountResultDto> countResultsPage = drugService.countByFieldLike(fieldName, keyword, pageable);
		model.addAttribute("countResultsPage", countResultsPage);
		model.addAttribute("fieldName", fieldName);
		model.addAttribute("keyword", keyword);

        return "drugs/search-count";
	}

	@GetMapping("drugName/{drugName}")
	public String showDrugName(@PathVariable String drugName, Model model) {
		List<NameStatsDto> indicationStats = drugService.getStats("drugName", "indication", drugName, 10);
		List<NameStatsDto> reactionTermStats = reacService.statsOnReactionTermByDrugName(drugName);

        model.addAttribute("fieldName", drugName);
        model.addAttribute("fieldStats1", indicationStats);
		model.addAttribute("fieldStats2", reactionTermStats);
        return "drugs/show";
	}
	
	@GetMapping("productName/{productName}")
	public String showProductName(@PathVariable String productName, Model model) {
		List<NameStatsDto> indicationStats = drugService.getStats("productName", "indication", productName, 10);
		List<NameStatsDto> reactionTermStats = reacService.statsOnReactionTermByProductName(productName);

        model.addAttribute("fieldName", productName);
        model.addAttribute("fieldStats1", indicationStats);
		model.addAttribute("fieldStats2", reactionTermStats);
        return "drugs/show";
	}
	
	@GetMapping("indication/{indication}")
	public String showIndication(@PathVariable String indication, Model model) {
		List<NameStatsDto> drugNameStats = drugService.getStats("indication", "drugName", indication, 10);
		List<NameStatsDto> productNameStats = drugService.getStats("indication", "productName", indication, 10);

        model.addAttribute("fieldName", indication);
        model.addAttribute("fieldStats1", drugNameStats);
        model.addAttribute("fieldStats2", productNameStats);
        return "drugs/show";
	}
}