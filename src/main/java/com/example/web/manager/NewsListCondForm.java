package com.example.web.manager;

/** お知らせ一覧画面条件フォームクラス. */
public class NewsListCondForm {

  /** ページ */
  private Integer page;
  /** 表題 */
  private String subject;
  /** 権限 */
  private String roleId;
  /** 権限名 */
  private String roleNm;
  /** URL */
  private String url;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getRoleId() {
    return roleId;
  }

  public String getRoleNm() {
    return roleNm;
  }

  public void setRoleNm(String roleNm) {
    this.roleNm = roleNm;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
