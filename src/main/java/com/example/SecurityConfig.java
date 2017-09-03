package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/** Spring Security設定. */
@EnableWebSecurity // Spring Securityが提供するConfigurationクラスのインポート
// WebSecurityConfigurerAdapterでデフォルトのBean定義を適用
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/css/**")
        .permitAll()
        .antMatchers("/fonts/**")
        .permitAll()
        .antMatchers("/js/**")
        .permitAll()
        .antMatchers("/loginForm")
        .permitAll()
        // /manager配下はADMIN権限のみ(自動でROLE_が付加されROLE_ADMIN)
        .antMatchers("/manager/**")
        .hasRole("ADMIN")
        // 認証していないリクエストは不許可
        .anyRequest()
        .authenticated();
    // フォーム認証
    http.formLogin()
        // 認証パス
        .loginProcessingUrl("/login")
        // ログインフォーム表示用のパス設定
        .loginPage("/loginForm")
        // 認証失敗時のパス
        .failureUrl("/loginForm?error")
        // 認証成功時のパス
        .defaultSuccessUrl("/", true)
        // ユーザ名のリクエストパラメータ
        .usernameParameter("id")
        // パスワードのリクエストパラメータ
        .passwordParameter("password");
    http.logout()
        // ログアウトURL
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
        // ログアウト後の遷移先
        .logoutSuccessUrl("/loginForm");
    http.headers().cacheControl().disable();
  }

  /** パスワードの暗号化方式 */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
