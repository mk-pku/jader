package com.example.jader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugSearchDto {
	private String caseId;
	private String drugName;
	private String productName;
	private String administrationRoute;
	private String indication;
}