package com.momentus.foundation.common;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class GeneralMessages {

  public static final String UNIDIENTIFABLE_ERROR = "10000";

  public static final String ORG_NAME_MANDATORY = "10001";
  public static final String ORG_CODE_MANDATORY = "10002";
  public static final String ORG_INDUSTRY_MANDATORY = "10003";
  public static final String ORG_SECTOR_MANDATORY = "10004";
  public static final String KEY_FIELD_MANDATORY = "10005";
  public static final String KEY_NOT_UNIQUE = "10006";
  public static final String VERSION_NOT_PROVIDED = "10007";
  public static final String ENTITY_NOT_EXISTING = "10008";
  public static final String ENTITY_DELETED = "10009";
  public static final String STALE_UPDATE = "10010";
  public static final String ENTITY_ALREADY_DELETED = "10011";
  public static final String UNAUTHORIZED_SYSTEM_OPERATION = "10012";
  public static final String PASSWORDS_NOT_MATCH = "10013";
  public static final String PASSWORD_NOT_VALID = "10014";
  public static final String PASSWORD_ENTERED_WRONG = "10015";
  public static final String PASSWORD_UPDATED_SUCCESFULLY = "10016";
  public static final String ORG_CODE_EXISTS = "10017";
  public static final String ORG_CREATED_SUCCESS = "10018";

  private final MessageSource messageSource;

  public GeneralMessages(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String getMessage(String errorCode, Locale locale) {
    if (errorCode == null || errorCode.trim().length() == 0) return "";
    else return messageSource.getMessage(errorCode, null, locale);
  }

  public String getMessage(String errorCode, Object[] args, Locale locale) {
    if (errorCode == null || errorCode.trim().length() == 0) return "";
    else return messageSource.getMessage(errorCode, args, locale);
  }
}
