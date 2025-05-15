package com.example.jader.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "demo")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DemoEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "case_id")
	private String caseId;
	
	private Byte reportCount;
	private String sex;
	private String age;
	private String weight;
	private String height;
	private String reportPeriod;
	private String status;
	private String reportType;
	private String reporterQualification;
	private String e2bType;
	private String createdBy;
	private LocalDateTime createdDate;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedDate;
	
	@OneToMany(mappedBy = "demo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DrugEntry> drugs;
	
	@OneToMany(mappedBy = "demo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReacEntry> reactions;
}