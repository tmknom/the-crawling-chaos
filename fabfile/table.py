# -*- encoding:utf-8 -*-
#
# テーブル名の定義
#
class Table:
    def __init__(self, name, order_query):
        self.name = name
        self.order_query = order_query


TABLES = [
    Table('qiita_user_names', 'user_name ASC'),
    Table('raw_qiita_internal_user_jsons', 'crawled_date_time ASC'),
    Table('qiita_users', 'qiita_user_id ASC'),
    Table('qiita_user_contributions', 'updated_date_time ASC'),
    Table('qiita_user_contribution_histories', 'registered_date_time ASC'),
    Table('qiita_article_ids', 'id ASC'),
    Table('raw_qiita_props_article_jsons', 'crawled_date_time ASC'),
    Table('qiita_articles', 'posted_date_time ASC'),
]
