CREATE TABLE IF NOT EXISTS qiita_article_contributions (
  item_id        VARCHAR(255) NOT NULL COMMENT 'QiitaでのID',
  likes_count    INT(11)      NOT NULL COMMENT 'Qiitaいいね数',
  comments_count INT(11)      NOT NULL COMMENT 'Qiitaコメント数',
  hatena_count   INT(11)      NOT NULL COMMENT 'はてなブックマーク数',
  facebook_count INT(11)      NOT NULL COMMENT 'Facebook数',
  pocket_count   INT(11)      NOT NULL COMMENT 'Pocket数',
  PRIMARY KEY (item_id),
  FOREIGN KEY (item_id) REFERENCES qiita_articles (item_id)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaの記事評価';
