CREATE TABLE `demo` (
  `id`                    INT           NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id`               CHAR(11)      COMMENT '薬副番号',
  `report_count`           TINYINT       COMMENT '報告回数',
  `sex`                   CHAR(2)       COMMENT '性別',
  `age`                   CHAR(16)      COMMENT '年齢',
  `weight`                CHAR(8)       COMMENT '体重',
  `height`                CHAR(8)       COMMENT '身長',
  `report_period`          varchar(20)       COMMENT '報告年度・四半期',
  `status`                CHAR(4)       COMMENT '状況',
  `report_type`            CHAR(4)       COMMENT '報告の種類',
  `reporter_qualification` varchar(32)      COMMENT '報告者の資格',
  `e2b_type`               CHAR(2)       COMMENT 'E2B',
  `createby`              CHAR(32)      COMMENT '作成者',
  `createddate`           DATETIME      COMMENT '作成日',
  `lastmodifiedby`        CHAR(32)      COMMENT '更新者',
  `lastmodifieddate`      DATETIME      COMMENT '更新日',
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COMMENT = '症例一覧テーブル';


CREATE TABLE `drug` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` char(11) DEFAULT NULL COMMENT '識別番号',
  `report_count` tinyint DEFAULT NULL COMMENT '報告回数',
  `drug_seq` smallint DEFAULT NULL COMMENT '医薬品連番',
  `role` char(4) DEFAULT NULL COMMENT '医薬品の関与',
  `drug_name` varchar(128) DEFAULT NULL COMMENT '医薬品(一般名)',
  `product_name` varchar(128) DEFAULT NULL COMMENT '医薬品(販売名)',
  `administration_route` varchar(1024) DEFAULT NULL COMMENT '投与経路',
  `start_date` varchar(1024) DEFAULT NULL COMMENT '投与開始日',
  `end_date` varchar(1024) DEFAULT NULL COMMENT '投与終了日',
  `dose` varchar(1024) DEFAULT NULL COMMENT '投与量',
  `dose_unit` varchar(512) DEFAULT NULL COMMENT '投与単位',
  `frequency` varchar(512) DEFAULT NULL COMMENT '分割投与回数',
  `indication` varchar(768) DEFAULT NULL COMMENT '使用理由',
  `action_taken` varchar(256) DEFAULT NULL COMMENT '医薬品の処置',
  `rechallenge` varchar(1024) DEFAULT NULL COMMENT '再投与による再発の有無',
  `risk_category` varchar(256) DEFAULT NULL COMMENT 'リスク区分等(R3のみ)',
  `createby` char(32) DEFAULT NULL COMMENT '作成者',
  `createddate` datetime DEFAULT NULL COMMENT '作成日',
  `lastmodifiedby` char(32) DEFAULT NULL COMMENT '更新者',
  `lastmodifieddate` datetime DEFAULT NULL COMMENT '更新日',
  PRIMARY KEY (`id`)
)ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COMMENT = '医薬品情報テーブル';


CREATE TABLE `hist` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` char(11) DEFAULT NULL COMMENT '識別番号',
  `report_count` tinyint DEFAULT NULL COMMENT '報告回数',
  `history_seq` smallint DEFAULT NULL COMMENT '原疾患等連番',
  `disease_name` char(32) DEFAULT NULL COMMENT '原疾患等',
  `createby` char(32) DEFAULT NULL COMMENT '作成者',
  `createddate` datetime DEFAULT NULL COMMENT '作成日',
  `lastmodifiedby` char(32) DEFAULT NULL COMMENT '更新者',
  `lastmodifieddate` datetime DEFAULT NULL COMMENT '更新日',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COMMENT = '原疾患テーブル';


CREATE TABLE `reac` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `case_id` char(11) DEFAULT NULL COMMENT '識別番号',
  `report_count` tinyint DEFAULT NULL COMMENT '報告回数',
  `reaction_seq` smallint DEFAULT NULL COMMENT '有害事象連番',
  `reaction_term` char(32) DEFAULT NULL COMMENT '有害事象',
  `outcome` char(16) DEFAULT NULL COMMENT '転帰',
  `onset_date` char(32) DEFAULT NULL COMMENT '有害事象の発現日',
  `createby` char(32) DEFAULT NULL COMMENT '作成者',
  `createddate` datetime DEFAULT NULL COMMENT '作成日',
  `lastmodifiedby` char(32) DEFAULT NULL COMMENT '更新者',
  `lastmodifieddate` datetime DEFAULT NULL COMMENT '更新日',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COMMENT = '副作用テーブル';
