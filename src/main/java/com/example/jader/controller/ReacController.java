package com.example.jader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reacs")
public class ReacController {
	
	@GetMapping("/termSearch")
	public String searchForm(Model model) {
		model.addAttribute("activeTab", "reactions");
		return "reacs/term-search";
	}
  
//  @GetMapping  // or POST → 結果表示
//  public String searchResult(...) { … }
}
