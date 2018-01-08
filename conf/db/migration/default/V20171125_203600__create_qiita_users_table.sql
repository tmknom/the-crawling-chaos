CREATE TABLE IF NOT EXISTS deprecated_qiita_users (
  id                   INT(11)      NOT NULL AUTO_INCREMENT,
  user_name            VARCHAR(255) NOT NULL COMMENT 'ユーザ名',
  registered_date_time DATETIME(6)  NOT NULL COMMENT '登録日時',
  PRIMARY KEY (id),
  UNIQUE (user_name)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザ';
