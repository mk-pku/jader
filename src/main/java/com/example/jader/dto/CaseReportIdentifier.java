package com.example.jader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode; // Setで使うため
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // Set での重複判定に必要
public class CaseReportIdentifier {
	private String caseId;
	private Byte reportCount;
}