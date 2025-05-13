package com.example.jader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.DrugNameCountDto;
import com.example.jader.model.ReactionTermCountDto;
import com.example.jader.service.DrugService;
import com.example.jader.service.ReactionService;

@Controller
@RequestMapping("/drugs")
public class DrugController {

	private final DrugService drugService;
	private final ReactionService reactionService;

	@Autowired
	public DrugController(DrugService drugService, ReactionService reactionService) {
		this.drugService = drugService;
		this.reactionService = reactionService;
	}
	
	@GetMapping("")
	public String index() {
		return "index";
	}

	@GetMapping("/select")
	public String selectPage(@RequestParam(name="drugNameKeyword", required=false) String keyword, Model model) {
		List<DrugNameCountDto> results = null;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = drugService.searchDrugNameAndCount(keyword.trim());
		}

		model.addAttribute("results", results);
		model.addAttribute("keyword", keyword);
		return "select";
	}

	@PostMapping("/result")
	public String resultPage(
			@RequestParam(name="selectedDrugNames", required=false) List<String> selectedDrugNames,
			Model model) {

		if (selectedDrugNames == null || selectedDrugNames.isEmpty()) {
			model.addAttribute("message", "医薬品が選択されていません。");
			return "index";
		}

		List<ReactionTermCountDto> reactionCounts =
			reactionService.getReactionTermCounts(selectedDrugNames);

		model.addAttribute("reactionCounts", reactionCounts);
		model.addAttribute("selectedDrugNamesForDisplay",
						   String.join(", ", selectedDrugNames));
		return "result";
	}
}