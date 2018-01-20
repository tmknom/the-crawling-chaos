CREATE TABLE IF NOT EXISTS raw_qiita_article_jsons (
  item_id           VARCHAR(255) NOT NULL COMMENT 'QiitaでのID',
  raw_json          TEXT         NOT NULL COMMENT 'Json',
  crawled_date_time DATETIME(6)  NOT NULL COMMENT 'クロールした日時',
  PRIMARY KEY (item_id)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'QiitaのArticleJson';
