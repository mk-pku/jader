package com.example.jader.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.DrugNameCountDto;
import com.example.jader.model.NameCountDto;
import com.example.jader.service.DrugService;
import com.example.jader.service.ReactionService;

@Controller
@RequestMapping("/drugs")
public class DrugController {

	private final DrugService drugService;
	private final ReactionService reactionService;

	public DrugController(DrugService drugService, ReactionService reactionService) {
		this.drugService = drugService;
		this.reactionService = reactionService;
	}
	
	@GetMapping("")
	public String index() {
		return "index";
	}

	@PostMapping("/select")
	public String selectPage(
			@RequestParam(required=false) String keyword,
			@RequestParam(required=false, defaultValue="generic") String nameType,
			@RequestParam(required=false) String formType,
			Model model) {

		List<DrugNameCountDto> results = null;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = drugService.searchDrugNameAndCount(keyword.trim(), nameType);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("nameType", nameType);
		model.addAttribute("formType", formType);
		model.addAttribute("results", results);
		return "select";
	}

	@PostMapping(value="/result", params="formType=0")
	public String resultPage0(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "医薬品が選択されていません。");
			return "index";
		}

		List<NameCountDto> reactionCounts =
			reactionService.getReactionTermCounts(candArray);

		model.addAttribute("nameCounts", reactionCounts);
		model.addAttribute("selectedNamesForDisplay",
						   String.join(", ", candArray));
		return "result";
	}
	
	@PostMapping(value="/result", params="formType=1")
	public String resultPage1(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "医薬品が選択されていません。");
			return "index";
		}

		List<NameCountDto> reactionCounts =
			drugService.getIndicationCounts(candArray);

		model.addAttribute("nameCounts", reactionCounts);
		model.addAttribute("selectedNamesForDisplay",
						   String.join(", ", candArray));
		return "result";
	}
}