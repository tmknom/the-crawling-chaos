CREATE TABLE IF NOT EXISTS qiita_articles (
  id               INT(11)      NOT NULL AUTO_INCREMENT,
  item_id          VARCHAR(255) NOT NULL COMMENT 'QiitaでのID',
  title            VARCHAR(255) NOT NULL COMMENT 'タイトル',
  url              VARCHAR(255) NOT NULL COMMENT 'URL',
  posted_user_name VARCHAR(255) NOT NULL COMMENT '投稿者名',
  posted_date_time DATETIME(6)  NOT NULL COMMENT '投稿日時',
  PRIMARY KEY (id),
  UNIQUE (item_id),
  FOREIGN KEY (posted_user_name) REFERENCES deprecated_qiita_users (user_name)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaの記事';
