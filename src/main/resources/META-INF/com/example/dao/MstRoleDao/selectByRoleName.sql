select
  role_id,
  role_name,
  version,
  insert_date,
  update_date
FROM
  mst_role
WHERE
  role_name =  /* roleName */'一般';