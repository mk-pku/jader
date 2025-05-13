package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndicationCountDto {
	private String indication;
	private Long count;
	private double percentage;
	
	public IndicationCountDto(String indication, long count) {
		this.indication = indication;
		this.count = count;
	}
	
	public String getPercentageDisplay() {
		return String.format("%.3f%%", percentage);
	}
}
