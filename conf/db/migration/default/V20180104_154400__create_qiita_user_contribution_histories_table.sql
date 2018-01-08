CREATE TABLE IF NOT EXISTS qiita_user_contribution_histories (
  qiita_user_id        INT(11)     NOT NULL,
  contribution         INT(11)     NOT NULL COMMENT 'いいね数',
  articles_count       INT(11)     NOT NULL COMMENT '記事数',
  registered_date      DATE        NOT NULL COMMENT '登録日',
  registered_date_time DATETIME(6) NOT NULL COMMENT '登録日時',
  UNIQUE (qiita_user_id, registered_date),
  FOREIGN KEY (qiita_user_id) REFERENCES deprecated_qiita_users (id)
) ENGINE InnoDB DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザいいね数履歴';
