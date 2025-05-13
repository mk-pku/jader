package com.example.jader.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "drug")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DrugEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String caseId;
	private Byte reportCount;
	private Short drugSeq;
	private String role;
	private String drugName;
	private String productName;
	private String administrationRoute;
	private String startDate;
	private String endDate;
	private String dose;
	private String doseUnit;
	private String frequency;
	private String indication;
	private String actionTaken;
	private String rechallenge;
	private String riskCategory;
	private String createdBy;
	private LocalDateTime createdDate;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedDate;
}