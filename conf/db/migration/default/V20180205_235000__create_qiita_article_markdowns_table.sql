CREATE TABLE IF NOT EXISTS qiita_article_markdowns (
  item_id              VARCHAR(255) NOT NULL COMMENT 'QiitaでのID',
  markdown             LONGTEXT     NOT NULL COMMENT 'マークダウン',
  registered_date_time DATETIME(6)  NOT NULL COMMENT '登録した日時',
  PRIMARY KEY (item_id)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaの記事マークダウン';
