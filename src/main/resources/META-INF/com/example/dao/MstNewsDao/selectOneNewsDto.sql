select
  n.mst_news_id id,
  n.role_id role_id,
  r.role_name role_nm,
  n.subject subject,
  n.url url,
  n.version version
FROM
  mst_news n
INNER JOIN
  mst_role r
ON
  n.role_id = r.role_id
WHERE
  n.mst_news_id = /* id */1