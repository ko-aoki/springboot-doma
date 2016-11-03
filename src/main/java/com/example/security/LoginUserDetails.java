package com.example.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by ko-aoki on 2016/09/03.
 */
public class LoginUserDetails extends User {

    /** ログイン情報 */
    private LoginInfo loginInfo;

    public LoginUserDetails(LoginInfo loginInfo) {
        super(loginInfo.getId(), loginInfo.getPassword(), AuthorityUtils.createAuthorityList(loginInfo.getRoleId()));
        this.loginInfo = loginInfo;
    }

    public LoginInfo getUserInfo() {
        return this.loginInfo;
    }
}