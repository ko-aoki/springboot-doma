package com.example.helper;

import com.example.security.LoginUserDetails;
import com.example.security.UserInfo;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationTestHelper {

    public static void 管理者権限の設定() {

        // 認証状態にする
        UserInfo userInfo = new UserInfo();

        userInfo.setId("01");
        userInfo.setEmployeeFirstName("テスト");
        userInfo.setEmployeeLastName("太郎");
        userInfo.setRoleId("ROLE_ADMIN");
        userInfo.setPassword("pwd");

        LoginUserDetails details = new LoginUserDetails(userInfo);

        Authentication authentication =
                new TestingAuthenticationToken(details, null, "ROLE_ADMIN");

        setAuthentication(authentication);

    }

    public static void 一般権限の設定() {

        // 認証状態にする
        UserInfo userInfo = new UserInfo();

        userInfo.setId("01");
        userInfo.setEmployeeFirstName("テスト");
        userInfo.setEmployeeLastName("太郎");
        userInfo.setRoleId("ROLE_USER");
        userInfo.setPassword("pwd");

        LoginUserDetails details = new LoginUserDetails(userInfo);

        Authentication authentication =
                new TestingAuthenticationToken(details, null, "ROLE_USER");

        setAuthentication(authentication);

    }

    private static void setAuthentication(Authentication authentication) {
        SecurityContext securityContext;
        securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}