package com.example.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

/** お知らせ情報Dtoクラス. */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class NewsDto {

  /** id */
  private Long id;
  /** 表題 */
  private String subject;
  /** 権限 */
  private String roleId;
  /** 権限名 */
  private String roleNm;
  /** URL */
  private String url;
  /** バージョン */
  private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleNm() {
    return roleNm;
  }

  public void setRoleNm(String roleNm) {
    this.roleNm = roleNm;
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
