CREATE TABLE IF NOT EXISTS qiita_user_initials (
  id      INT(11)    NOT NULL AUTO_INCREMENT,
  initial VARCHAR(1) NOT NULL COMMENT 'イニシャル',
  page    INT(11)    NOT NULL COMMENT 'ページ数',
  PRIMARY KEY (id),
  UNIQUE (initial)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザイニシャル';
