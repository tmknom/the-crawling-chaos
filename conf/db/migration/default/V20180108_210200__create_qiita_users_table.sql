CREATE TABLE IF NOT EXISTS qiita_users (
  user_name         VARCHAR(255) NOT NULL COMMENT 'ユーザ名',
  qiita_user_id     INT(11)      NOT NULL COMMENT 'Qiita上のユーザID',
  profile_image_url VARCHAR(255) NOT NULL COMMENT 'プロフィールイメージURL',
  PRIMARY KEY (user_name),
  UNIQUE (qiita_user_id)
) ENGINE InnoDB AUTO_INCREMENT 1 DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_bin COMMENT 'Qiitaユーザ';
