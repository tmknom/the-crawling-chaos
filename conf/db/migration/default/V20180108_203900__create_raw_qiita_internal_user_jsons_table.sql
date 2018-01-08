CREATE TABLE IF NOT EXISTS raw_qiita_internal_user_jsons (
  user_name         VARCHAR(255) NOT NULL COMMENT 'ユーザ名',
  raw_json          TEXT         NOT NULL COMMENT 'Json',
  crawled_date_time DATETIME(6)  NOT NULL COMMENT 'クロールした日時',
  PRIMARY KEY (user_name)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'QiitaのInternalUserJson';
