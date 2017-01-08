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
/*%if @isNotEmpty(url) */
    n.url LIKE /* @prefix(url) */'http'
/*%end*/
/*%if @isNotEmpty(subject) */
AND
    n.subject LIKE /* @prefix(subject) */'今日'
/*%end*/
/*%if @isNotEmpty(roleId) */
AND
    n.role_id = /* roleId */'01'
/*%end*/
ORDER BY
  n.mst_news_id
