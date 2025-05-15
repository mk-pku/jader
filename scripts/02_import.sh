#!/bin/bash
set -e

mysql -u root -p"$MYSQL_ROOT_PASSWORD" jader_db <<'EOSQL'
-- demo.csv の取り込み
LOAD DATA LOCAL INFILE '/csv/demo.csv'
INTO TABLE demo
CHARACTER SET utf8
FIELDS TERMINATED BY ','
IGNORE 1 LINES
(case_id, report_count, sex, age, weight, height,
 report_period, status, report_type, reporter_qualification, e2b_type);

-- drug.csv の取り込み
LOAD DATA LOCAL INFILE '/csv/drug.csv'
INTO TABLE drug
CHARACTER SET utf8
FIELDS TERMINATED BY ','
IGNORE 1 LINES
(case_id, report_count, drug_seq, role, drug_name, product_name,
 administration_route, start_date, end_date,
 dose, dose_unit, frequency, indication,
 action_taken, rechallenge, risk_category);

-- hist.csv の取り込み
LOAD DATA LOCAL INFILE '/csv/hist.csv'
INTO TABLE hist
CHARACTER SET utf8
FIELDS TERMINATED BY ','
IGNORE 1 LINES
(case_id, report_count, history_seq, disease_name);

-- reac.csv の取り込み
LOAD DATA LOCAL INFILE '/csv/reac.csv'
INTO TABLE reac
CHARACTER SET utf8
FIELDS TERMINATED BY ','
IGNORE 1 LINES
(case_id, report_count, reaction_seq, reaction_term,
 outcome, onset_date);

EOSQL