DROP TABLE IF EXISTS mst_employee;
DROP TABLE IF EXISTS mst_password;
DROP TABLE IF EXISTS mst_role;
DROP TABLE IF EXISTS trn_news;


CREATE TABLE mst_employee
(
	employee_id varchar(10) NOT NULL,
	employee_last_name varchar(20),
	employee_first_name varchar(20),
	role_id varchar(10),
	version int,
	insert_date datetime,
	update_date datetime,
	PRIMARY KEY (employee_id)
);


CREATE TABLE mst_password
(
	mst_password_id int NOT NULL,
	employee_id varchar(10) NOT NULL,
	password varchar(256),
	generation varchar(2),
	version int,
	insert_date datetime,
	update_date datetime,
	PRIMARY KEY (mst_password_id),
	UNIQUE (employee_id, generation)
);


CREATE TABLE mst_role
(
	role_id varchar(10) NOT NULL,
	role_name varchar(60),
	version int,
	insert_date datetime,
	update_date datetime,
	PRIMARY KEY (role_id)
);

-- お知らせテーブル
CREATE TABLE trn_news
(
	trn_news_id int NOT NULL  auto_increment COMMENT 'trn_news_id',
	role_id varchar(10) NOT NULL COMMENT '権限ID',
	subject varchar(2000) COMMENT 'subject',
	url varchar(512) COMMENT 'url',
	version int COMMENT 'バージョン',
	insert_date datetime COMMENT 'insert_date',
	update_date datetime COMMENT 'update_date',
	PRIMARY KEY (trn_news_id)
) COMMENT = 'お知らせテーブル';
