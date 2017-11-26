CREATE TABLE IF NOT EXISTS qiita_user_contributions (
  qiita_user_id INT(11) NOT NULL,
  contribution  INT(11) NOT NULL COMMENT 'いいね数',
  PRIMARY KEY (qiita_user_id),
  FOREIGN KEY (qiita_user_id) REFERENCES qiita_users (id)
) ENGINE InnoDB DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザいいね数';
