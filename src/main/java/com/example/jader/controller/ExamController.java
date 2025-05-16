package com.example.jader.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.model.NameCountDto;
import com.example.jader.model.NameStatsDto;
import com.example.jader.service.DrugService;
import com.example.jader.service.ReactionService;

@Controller
@RequestMapping("/exam")
public class ExamController {

	private final DrugService drugService;
	private final ReactionService reactionService;

	public ExamController(DrugService drugService, ReactionService reactionService) {
		this.drugService = drugService;
		this.reactionService = reactionService;
	}
	
	@GetMapping("")
	public String index() {
		return "exam/index";
	}

	@PostMapping(value="/select", params="formType=2")
	public String selectIndicationPage(
			@RequestParam(required=false) String keyword,
			@RequestParam(required=false) String nameType,
			@RequestParam(required=false) String formType,
			Model model) {

		List<NameCountDto> results = null;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = drugService.countByIndicationLike(keyword.trim());
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("formType", formType);
		model.addAttribute("results", results);
		return "exam/select";
	}

	@PostMapping(value="/select", params="formType=3")
	public String selectReactionTermPage(
			@RequestParam(required=false) String keyword,
			@RequestParam(required=false) String nameType,
			@RequestParam(required=false) String formType,
			Model model) {

		List<NameCountDto> results = null;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = reactionService.countByReactionTermLike(keyword.trim());
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("formType", formType);
		model.addAttribute("results", results);
		return "exam/select";
	}

	@PostMapping("/select")
	public String selectPage(
			@RequestParam(required=false) String keyword,
			@RequestParam(required=false, defaultValue="generic") String nameType,
			@RequestParam(required=false) String formType,
			Model model) {

		List<NameCountDto> results = null;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = drugService.countByMedicineNameLike(keyword.trim(), nameType);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("nameType", nameType);
		model.addAttribute("formType", formType);
		model.addAttribute("results", results);
		return "exam/select";
	}

	@PostMapping(value="/result", params="formType=0")
	public String resultPage0(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "医薬品が選択されていません。");
			return "exam/index";
		}

		List<NameStatsDto> reactionCounts =
			reactionService.statsOnReactionTermByMedicineName(candArray);

		model.addAttribute("nameCounts", reactionCounts);
		model.addAttribute("selectedNamesForDisplay", String.join(", ", candArray));
		return "exam/result";
	}
	
	@PostMapping(value="/result", params="formType=1")
	public String resultPage1(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "医薬品が選択されていません。");
			return "exam/index";
		}

		List<NameStatsDto> indicationCounts =
			drugService.statsOnIndicationByMedicineName(candArray);

		model.addAttribute("nameCounts", indicationCounts);
		model.addAttribute("selectedNamesForDisplay", String.join(", ", candArray));
		return "exam/result";
	}
	
	@PostMapping(value="/result", params="formType=2")
	public String resultPage2(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "使用理由が選択されていません。");
			return "exam/index";
		}

		List<NameStatsDto> drugNameCounts =
			drugService.statsOnDrugNameByIndication(candArray);
		List<NameStatsDto> productNameCounts =
			drugService.statsOnProductNameByIndication(candArray);

		model.addAttribute("nameCounts", drugNameCounts);
		model.addAttribute("subNameCounts", productNameCounts);
		model.addAttribute("selectedNamesForDisplay", String.join(", ", candArray));
		return "exam/result";
	}

	@PostMapping(value="/result", params="formType=3")
	public String resultPage3(
			@RequestParam(required=false) List<String> candArray,
			Model model) {

		if (candArray == null || candArray.isEmpty()) {
			model.addAttribute("message", "使用理由が選択されていません。");
			return "exam/index";
		}

		List<NameStatsDto> drugNameCounts =
			reactionService.statsOnDrugNameByReactionTerm(candArray);
		List<NameStatsDto> productNameCounts =
			reactionService.statsOnProductNameByReactionTerm(candArray);

		model.addAttribute("nameCounts", drugNameCounts);
		model.addAttribute("subNameCounts", productNameCounts);
		model.addAttribute("selectedNamesForDisplay", String.join(", ", candArray));
		return "exam/result";
	}
}