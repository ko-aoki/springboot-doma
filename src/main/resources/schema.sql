DROP TABLE IF EXISTS mst_employee;
DROP TABLE IF EXISTS mst_password;
DROP TABLE IF EXISTS mst_role;
DROP TABLE IF EXISTS mst_news;

-- 従業員マスタ
CREATE TABLE mst_employee
(
	employee_id varchar(10) NOT NULL COMMENT '従業員番号',
	employee_last_name varchar(50) COMMENT '姓',
	employee_first_name varchar(50) COMMENT '名',
	role_id varchar(20) NOT NULL COMMENT '権限ID',
	version int COMMENT 'バージョン',
	insert_user varchar(20) COMMENT '登録ユーザ',
	insert_date datetime COMMENT 'insert_date',
	update_user varchar(20) COMMENT '更新ユーザ',
	update_date datetime COMMENT 'update_date',
	PRIMARY KEY (employee_id)
) COMMENT = '従業員マスタ';


-- パスワードマスタ
CREATE TABLE mst_password
(
	mst_password_id int NOT NULL COMMENT 'パスワードマスタID',
	employee_id varchar(10) NOT NULL COMMENT '従業員番号',
	password varchar(256) COMMENT 'パスワード',
	generation varchar(2) COMMENT 'パスワード世代',
	version int COMMENT 'バージョン',
	insert_user varchar(20) COMMENT '登録ユーザ',
	insert_date datetime COMMENT 'insert_date',
	update_user varchar(20) COMMENT '更新ユーザ',
	update_date datetime COMMENT 'update_date',
	PRIMARY KEY (mst_password_id),
	UNIQUE (employee_id, generation)
) COMMENT = 'パスワードマスタ';


-- 権限マスタ
CREATE TABLE mst_role
(
	role_id varchar(20) NOT NULL COMMENT '権限ID',
	role_name varchar(100) COMMENT '権限名',
	version int COMMENT 'バージョン',
	insert_user varchar(20) COMMENT '登録ユーザ',
	insert_date datetime COMMENT 'insert_date',
	update_user varchar(20) COMMENT '更新ユーザ',
	update_date datetime COMMENT 'update_date',
	PRIMARY KEY (role_id)
) COMMENT = '権限マスタ';


-- 新規テーブル
CREATE TABLE mst_news
(
	mst_news_id int NOT NULL AUTO_INCREMENT COMMENT 'mst_news_id',
	role_id varchar(10) NOT NULL COMMENT '権限ID',
	subject varchar(2000) COMMENT 'subject',
	url varchar(512) COMMENT 'url',
	version int COMMENT 'バージョン',
	insert_user varchar(20) COMMENT '登録ユーザ',
	insert_date datetime COMMENT 'insert_date',
	update_user varchar(20) COMMENT '更新ユーザ',
	update_date datetime COMMENT 'update_date',
	PRIMARY KEY (mst_news_id)
) COMMENT = '新規テーブル';
