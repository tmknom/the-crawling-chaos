CREATE TABLE IF NOT EXISTS qiita_user_contributions (
  user_name         VARCHAR(255) NOT NULL COMMENT 'ユーザ名',
  contribution      INT(11)      NOT NULL COMMENT 'いいね数',
  articles_count    INT(11)      NOT NULL COMMENT '記事数',
  updated_date_time DATETIME(6)  NOT NULL COMMENT '更新日時',
  PRIMARY KEY (user_name),
  FOREIGN KEY (user_name) REFERENCES qiita_users (user_name)
) ENGINE InnoDB DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザ評価';
