package com.example.web.manager;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by ko-aoki on 2016/11/01.
 */
public class NewsForm {

    /** 表題 */
    @NotBlank
    private String subject;
    /** 権限 */
    @NotBlank
    private String role;
    /** URL */
    @NotBlank
    @URL(message = "お知らせURLの形式が正しくありません。")
    private String url;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
