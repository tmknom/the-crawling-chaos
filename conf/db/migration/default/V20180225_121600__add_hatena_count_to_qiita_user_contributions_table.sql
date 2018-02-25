ALTER TABLE qiita_user_contributions
  ADD hatena_count INT(11) NOT NULL DEFAULT 0 COMMENT 'はてなブックマーク数'
  AFTER articles_count;

ALTER TABLE qiita_user_contribution_histories
  ADD hatena_count INT(11) NOT NULL DEFAULT 0 COMMENT 'はてなブックマーク数'
  AFTER articles_count;
