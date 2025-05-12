package com.example.jader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jader.dto.DrugNameCountDto;
import com.example.jader.dto.ReactionTermCountDto;
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

	@GetMapping("/search")
	public String searchPage(@RequestParam(name = "drugNameKeyword", required = false) String keyword, Model model) {
		List<DrugNameCountDto> results;
		if (keyword != null && !keyword.trim().isEmpty()) {
			results = drugService.searchDrugNameAndCount(keyword.trim());
		} else {
			// 初期表示時やキーワードが空の場合は、例えば全件の集計結果を表示するか、何も表示しないか選べます。
			// results = drugService.getAllDrugNameAndCount(); // 全件集計
			results = null; // または空のリストやnullで、テンプレート側で表示を制御
		}

		model.addAttribute("results", results);
		model.addAttribute("keyword", keyword); // 検索フォームに前回のキーワードを再表示するため
		return "drug-search"; // src/main/resources/templates/drug-search.html を参照
	}

	@PostMapping("/adverse-reactions")  // フォーム action と合わせる
    public String showAdverseReactionList(
            @RequestParam(name = "selectedDrugNames", required = false) List<String> selectedDrugNames,
            Model model) {

        if (selectedDrugNames == null || selectedDrugNames.isEmpty()) {
            model.addAttribute("message", "医薬品が選択されていません。");
            return "drug-search";
        }

        List<ReactionTermCountDto> reactionCounts =
            reactionService.getReactionTermCounts(selectedDrugNames);

        model.addAttribute("reactionCounts", reactionCounts);
        model.addAttribute("selectedDrugNamesForDisplay",
                           String.join(", ", selectedDrugNames));
        return "reaction-term-list";
    }
}