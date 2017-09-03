package com.example.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.LocalDateTime;

/** Created by ko-aoki on 2017/02/14. */
@WebListener
public class TestListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    LocalDateTime now = LocalDateTime.now();
    System.out.println("---------------- create at " + now + " ---------------");
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    LocalDateTime now = LocalDateTime.now();
    System.out.println("----------------destroy at " + now + "---------------");
  }
}
