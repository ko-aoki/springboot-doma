SELECT
	e.employee_id employee_id,
	e.employee_last_name employee_last_name,
	e.employee_first_name employee_first_name,
	e.role_id role_id,
	p.password password
FROM
  mst_employee e
INNER JOIN
  mst_password p
ON
  e.employee_id = p.employee_id
INNER JOIN
  mst_role r
ON
  e.role_id = r.role_id
WHERE
  e.employee_id = /* id */'01'
;