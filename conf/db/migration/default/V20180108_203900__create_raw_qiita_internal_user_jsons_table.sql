CREATE TABLE IF NOT EXISTS raw_qiita_internal_user_jsons (
  user_name VARCHAR(255) NOT NULL COMMENT 'ユーザ名',
  raw_json  TEXT         NOT NULL COMMENT 'Json',
  PRIMARY KEY (user_name)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'QiitaのInternalUserJson';
