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
@Table(name = "hist")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntry { // クラス名をHistからHistoryへ変更（より分かりやすく）

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

	@Column(name = "history_seq")
	@Comment("原疾患等連番")
	private Short historySeq; // SMALLINT

	@Column(name = "disease_name", length = 32)
	@Comment("原疾患等")
	private String diseaseName;

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