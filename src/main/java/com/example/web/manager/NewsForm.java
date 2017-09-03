package com.example.web.manager;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/** お知らせ画面フォームクラス. */
public class NewsForm {

  /** id */
  private Long id;
  /** ページ */
  private Integer page;
  /** 表題 */
  @NotBlank private String subject;
  /** 権限 */
  @NotBlank private String roleId;
  /** 権限名 */
  private String roleNm;
  /** URL */
  @NotBlank
  @URL(message = "お知らせURLの形式が正しくありません。")
  private String url;
  /** バージョン */
  private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }
}
