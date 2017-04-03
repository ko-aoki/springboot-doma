delete from mst_employee;
delete from mst_password;
insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('01', '管理', '太郎', 'ROLE_ADMIN');
insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('02', '一般', '二郎', 'ROLE_USER');
insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('03', '一般', '三郎', 'ROLE_USER');
insert into mst_password (mst_password_id, employee_id, password, generation) values(1, '01', '$2a$10$i7ZAPWh9xNT98pkJ4B6TyuPkPdehn6oZiwOOjm9/GXc3ZNlbUdQLq', '01');
insert into mst_password (mst_password_id, employee_id, password, generation) values(2, '02', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i', '01');
insert into mst_password (mst_password_id, employee_id, password, generation) values(3, '01', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i', '02');
