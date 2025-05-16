package com.example.jader.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.DrugSummaryDto;
import com.example.jader.service.DrugService;

@Controller
@RequestMapping("/drugs")
public class DrugController {
	
	private final DrugService drugService;
	
	public DrugController(DrugService drugService) {
		this.drugService = drugService;
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
		
		Pageable pageable = PageRequest.of(page, 20, Sort.by("caseId").ascending());
        Page<DrugSummaryDto> results = drugService.findByKeyword(keyword, nameType, pageable);

		model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("nameType", nameType);
        return "drugs/name-search-result";
	}
  
//  @GetMapping  // or POST → 結果表示
//  public String searchResult(...) { … }
}
