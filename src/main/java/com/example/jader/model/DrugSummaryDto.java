package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugSummaryDto {
	private String caseId;
	private String drugName;
	private String productName;
}
