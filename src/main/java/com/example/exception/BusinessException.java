package com.example.exception;

import java.util.Arrays;
import java.util.List;

/** 業務例外クラス. */
public class BusinessException extends RuntimeException {

  /** メッセージリスト */
  private List<String> messageList;

  public BusinessException(String message) {
    this.messageList = Arrays.asList(message);
  }

  public BusinessException(List<String> messageList) {
    this.messageList = messageList;
  }

  /** メッセージリストを返します. */
  public List<String> getMessageList() {
    return this.messageList;
  }
}
