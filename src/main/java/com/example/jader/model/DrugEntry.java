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
@Table(name = "drug")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DrugEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Comment("id")
	private Integer id;

	@Column(name = "case_id", length = 11)
	@Comment("識別番号")
	private String caseId;

	@Column(name = "report_count")
	@Comment("報告回数")
	private Byte reportCount; // TINYINT

	@Column(name = "drug_seq")
	@Comment("医薬品連番")
	private Short drugSeq; // SMALLINT

	@Column(name = "role", length = 4)
	@Comment("医薬品の関与")
	private String role;

	@Column(name = "drug_name", length = 128)
	@Comment("医薬品(一般名)")
	private String drugName;

	@Column(name = "product_name", length = 128)
	@Comment("医薬品(販売名)")
	private String productName;

	@Column(name = "administration_route", length = 1024)
	@Comment("投与経路")
	private String administrationRoute;

	@Column(name = "start_date", length = 1024) // スキーマ定義は VARCHAR でしたが、日付関連データ。ここではStringのまま。
	@Comment("投与開始日")
	private String startDate;

	@Column(name = "end_date", length = 1024) // スキーマ定義は VARCHAR でしたが、日付関連データ。ここではStringのまま。
	@Comment("投与終了日")
	private String endDate;

	@Column(name = "dose", length = 1024)
	@Comment("投与量")
	private String dose;

	@Column(name = "dose_unit", length = 512)
	@Comment("投与単位")
	private String doseUnit;

	@Column(name = "frequency", length = 512)
	@Comment("分割投与回数")
	private String frequency;

	@Column(name = "indication", length = 768)
	@Comment("使用理由")
	private String indication;

	@Column(name = "action_taken", length = 256)
	@Comment("医薬品の処置")
	private String actionTaken;

	@Column(name = "rechallenge", length = 1024)
	@Comment("再投与による再発の有無")
	private String rechallenge;

	@Column(name = "risk_category", length = 256)
	@Comment("リスク区分等(R3のみ)")
	private String riskCategory;

	@Column(name = "createby", length = 32)
	@Comment("作成者")
	private String createdBy;

	@Column(name = "createddate")
	@Comment("作成日")
	private LocalDateTime createdDate;

	@Column(name = "lastmodifiedby", length = 32)
	@Comment("更新者")
	private String lastModifiedBy;

	@Column(name = "lastmodifieddate")
	@Comment("更新日")
	private LocalDateTime lastModifiedDate;
}