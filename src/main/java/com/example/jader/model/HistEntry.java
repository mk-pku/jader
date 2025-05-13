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
@Table(name = "hist")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HistEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String caseId;
	private Byte reportCount;
	private Short historySeq;
	private String diseaseName;
	private String createdBy;
	private LocalDateTime createdDate;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedDate;
}