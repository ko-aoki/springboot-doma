insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('01', '管理', '太郎', '01');
insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('02', '一般', '二郎', '02');
insert into mst_role (role_id, role_name) values('01', 'Admin');
insert into mst_role (role_id, role_name) values('02', 'User');
insert into mst_password (mst_password_id, employee_id, password) values(1, '01', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i');
insert into mst_password (mst_password_id, employee_id, password) values(2, '02', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i');
insert into trn_news (role_id, subject, url, version) values('01', '表題テスト', 'http://hoge/test1', 0);
insert into trn_news (role_id, subject, url, version) values('02', 'テスト表題テスト', 'http://hoge/test2', 0);
