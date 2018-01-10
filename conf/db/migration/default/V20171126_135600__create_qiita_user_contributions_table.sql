CREATE TABLE IF NOT EXISTS deprecated_qiita_user_contributions (
  qiita_user_id     INT(11)     NOT NULL,
  contribution      INT(11)     NOT NULL COMMENT 'いいね数',
  articles_count    INT(11)     NOT NULL COMMENT '記事数',
  updated_date_time DATETIME(6) NOT NULL COMMENT '更新日時',
  PRIMARY KEY (qiita_user_id),
  FOREIGN KEY (qiita_user_id) REFERENCES deprecated_qiita_users (id)
) ENGINE InnoDB DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザいいね数';
