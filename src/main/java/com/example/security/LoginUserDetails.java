package com.example.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by ko-aoki on 2016/09/03.
 */
public class LoginUserDetails extends User {

    /** ログイン情報 */
    private UserInfo userInfo;

    public LoginUserDetails(UserInfo userInfo) {
        super(userInfo.getId(), userInfo.getPassword(), AuthorityUtils.createAuthorityList(userInfo.getRoleId()));
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }
}