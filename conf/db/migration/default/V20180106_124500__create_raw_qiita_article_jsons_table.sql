CREATE TABLE IF NOT EXISTS raw_qiita_article_jsons (
  qiita_article_item_id VARCHAR(255) NOT NULL COMMENT 'QiitaでのID',
  raw_json              TEXT         NOT NULL COMMENT 'Json',
  PRIMARY KEY (qiita_article_item_id),
  FOREIGN KEY (qiita_article_item_id) REFERENCES qiita_articles (item_id)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'QiitaのArticleJson';
