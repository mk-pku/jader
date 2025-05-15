CREATE TABLE `demo`(
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` CHAR(11) COMMENT '薬副番号',
  `report_count` TINYINT COMMENT '報告回数',
  `sex` CHAR(2) COMMENT '性別',
  `age` CHAR(16) COMMENT '年齢',
  `weight` CHAR(8) COMMENT '体重',
  `height` CHAR(8) COMMENT '身長',
  `report_period` VARCHAR(20) COMMENT '報告年度・四半期',
  `status` CHAR(4) COMMENT '状況',
  `report_type` CHAR(4) COMMENT '報告の種類',
  `reporter_qualification` VARCHAR(32) COMMENT '報告者の資格',
  `e2b_type` CHAR(2) COMMENT 'E2B',
  `createby` CHAR(32) COMMENT '作成者',
  `createddate` DATETIME COMMENT '作成日',
  `lastmodifiedby` CHAR(32) COMMENT '更新者',
  `lastmodifieddate` DATETIME COMMENT '更新日',
  PRIMARY KEY(`case_id`),
  UNIQUE KEY `uq_demo_id`(`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '症例一覧テーブル'
;

CREATE TABLE `drug`(
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` CHAR(11) NOT NULL COMMENT '識別番号',
  `report_count` TINYINT COMMENT '報告回数',
  `drug_seq` SMALLINT NOT NULL COMMENT '医薬品連番',
  `role` CHAR(4) COMMENT '医薬品の関与',
  `drug_name` VARCHAR(128) COMMENT '医薬品(一般名)',
  `product_name` VARCHAR(128) COMMENT '医薬品(販売名)',
  `administration_route` VARCHAR(1024) COMMENT '投与経路',
  `start_date` VARCHAR(1024) COMMENT '投与開始日',
  `end_date` VARCHAR(1024) COMMENT '投与終了日',
  `dose` VARCHAR(1024) COMMENT '投与量',
  `dose_unit` VARCHAR(512) COMMENT '投与単位',
  `frequency` VARCHAR(512) COMMENT '分割投与回数',
  `indication` VARCHAR(768) COMMENT '使用理由',
  `action_taken` VARCHAR(256) COMMENT '医薬品の処置',
  `rechallenge` VARCHAR(1024) COMMENT '再投与による再発の有無',
  `risk_category` VARCHAR(256) COMMENT 'リスク区分等(R3のみ)',
  `createby` CHAR(32) COMMENT '作成者',
  `createddate` DATETIME COMMENT '作成日',
  `lastmodifiedby` CHAR(32) COMMENT '更新者',
  `lastmodifieddate` DATETIME COMMENT '更新日',
  PRIMARY KEY(`case_id`, `drug_seq`),
  UNIQUE KEY `uq_drug_id`(`id`),
  CONSTRAINT `fk_drug_demo` FOREIGN KEY(`case_id`) REFERENCES `demo`(`case_id`)
ON
  UPDATE
    CASCADE
  ON  DELETE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '医薬品情報テーブル'
;

CREATE TABLE `hist`(
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` CHAR(11) NOT NULL COMMENT '識別番号',
  `report_count` TINYINT COMMENT '報告回数',
  `history_seq` SMALLINT NOT NULL COMMENT '原疾患等連番',
  `disease_name` CHAR(32) COMMENT '原疾患等',
  `createby` CHAR(32) COMMENT '作成者',
  `createddate` DATETIME COMMENT '作成日',
  `lastmodifiedby` CHAR(32) COMMENT '更新者',
  `lastmodifieddate` DATETIME COMMENT '更新日',
  PRIMARY KEY(`case_id`, `history_seq`),
  UNIQUE KEY `uq_hist_id`(`id`),
  CONSTRAINT `fk_hist_demo` FOREIGN KEY(`case_id`) REFERENCES `demo`(`case_id`)
ON
  UPDATE
    CASCADE
  ON  DELETE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '原疾患テーブル'
;

CREATE TABLE `reac`(
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` CHAR(11) NOT NULL COMMENT '識別番号',
  `report_count` TINYINT COMMENT '報告回数',
  `reaction_seq` SMALLINT NOT NULL COMMENT '有害事象連番',
  `reaction_term` CHAR(32) COMMENT '有害事象',
  `outcome` CHAR(16) COMMENT '転帰',
  `onset_date` CHAR(32) COMMENT '有害事象の発現日',
  `createby` CHAR(32) COMMENT '作成者',
  `createddate` DATETIME COMMENT '作成日',
  `lastmodifiedby` CHAR(32) COMMENT '更新者',
  `lastmodifieddate` DATETIME COMMENT '更新日',
  PRIMARY KEY(`case_id`, `reaction_seq`),
  UNIQUE KEY `uq_reac_id`(`id`),
  CONSTRAINT `fk_reac_demo` FOREIGN KEY(`case_id`) REFERENCES `demo`(`case_id`)
ON
  UPDATE
    CASCADE
  ON  DELETE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '副作用テーブル'
;