package com.example.dto;

/**
 * お知らせ情報Dtoクラス.
 */
public class NewsDto {

    /** 表題 */
    private String subject;
    /** 権限 */
    private String roleId;
    /** URL */
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
