package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionTermCountDto {
	private String reactionTerm;
	private Long count;
	private double percentage;
	
	public ReactionTermCountDto(String reactionTerm, long count) {
		this.reactionTerm = reactionTerm;
		this.count = count;
	}
	
	public String getPercentageDisplay() {
		return String.format("%.3f%%", percentage);
	}
}
