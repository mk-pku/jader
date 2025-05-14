package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameCountDto {
	private String name;
	private Long count;
	private double percentage;
	
	public NameCountDto(String name, long count) {
		this.name = name;
		this.count = count;
	}
	
	public String getPercentageDisplay() {
		return String.format("%.3f%%", percentage);
	}
}
