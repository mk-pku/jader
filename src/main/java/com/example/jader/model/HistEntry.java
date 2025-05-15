package com.example.jader.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	private Byte reportCount;
	private Short historySeq;
	private String diseaseName;
	private String createdBy;
	private LocalDateTime createdDate;
	private String lastModifiedBy;
	private LocalDateTime lastModifiedDate;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "case_id", referencedColumnName = "case_id")
	 private DemoEntry demo;
}