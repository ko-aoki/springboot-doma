package com.example.web.manager;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by ko-aoki on 2016/11/01.
 */
public class EntryForm {

    @NotBlank
    private String entryNm;
    @NotBlank
    @URL(message = "お知らせURLの形式が正しくありません。")
    private String entryUrl;

    public String getEntryNm() {
        return entryNm;
    }

    public void setEntryNm(String entryNm) {
        this.entryNm = entryNm;
    }

    public String getEntryUrl() {
        return entryUrl;
    }

    public void setEntryUrl(String entryUrl) {
        this.entryUrl = entryUrl;
    }
}
