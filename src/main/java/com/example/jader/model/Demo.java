package com.example.jader.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;

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
public class Demo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Comment("id")
	private Integer id;

	@Column(name = "case_id", length = 11)
	@Comment("薬副番号")
	private String caseId;

	@Column(name = "report_count")
	@Comment("報告回数")
	private Byte reportCount; // TINYINT

	@Column(name = "sex", length = 2)
	@Comment("性別")
	private String sex;

	@Column(name = "age", length = 16)
	@Comment("年齢")
	private String age;

	@Column(name = "weight", length = 8)
	@Comment("体重")
	private String weight;

	@Column(name = "height", length = 8)
	@Comment("身長")
	private String height;

	@Column(name = "report_period", length = 20)
	@Comment("報告年度・四半期")
	private String reportPeriod;

	@Column(name = "status", length = 4)
	@Comment("状況")
	private String status;

	@Column(name = "report_type", length = 4)
	@Comment("報告の種類")
	private String reportType;

	@Column(name = "reporter_qualification", length = 32)
	@Comment("報告者の資格")
	private String reporterQualification;

	@Column(name = "e2b_type", length = 2)
	@Comment("E2B")
	private String e2bType;

	@Column(name = "createby", length = 32)
	@Comment("作成者")
	private String createdBy; // フィールド名をキャメルケースに

	@Column(name = "createddate")
	@Comment("作成日")
	private LocalDateTime createdDate; // DATETIME -> LocalDateTime

	@Column(name = "lastmodifiedby", length = 32)
	@Comment("更新者")
	private String lastModifiedBy; // フィールド名をキャメルケースに

	@Column(name = "lastmodifieddate")
	@Comment("更新日")
	private LocalDateTime lastModifiedDate; // DATETIME -> LocalDateTime
}