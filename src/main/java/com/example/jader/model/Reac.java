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
@Table(name = "reac")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reac { // クラス名をReacからReactionへ変更

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

	@Column(name = "reaction_seq")
	@Comment("有害事象連番")
	private Short reactionSeq; // SMALLINT

	@Column(name = "reaction_term", length = 32)
	@Comment("有害事象")
	private String reactionTerm;

	@Column(name = "outcome", length = 16)
	@Comment("転帰")
	private String outcome;

	@Column(name = "onset_date", length = 32) // スキーマ定義は CHAR でしたが、日付関連データ。ここではStringのまま。
	@Comment("有害事象の発現日")
	private String onsetDate;

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