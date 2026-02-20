package com.momentus.foundation.common;

import com.momentus.foundation.common.utils.GeneralConfiguration;
import java.util.Locale;
import java.util.MissingResourceException;
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
  public static final String PRIMARY_USER_EMAIL_MANDATORY = "10019";
  public static final String ROLE_SAVED_SUCCESSFULLY = "10020";

  private final MessageSource messageSource;

  private final GeneralConfiguration generalConfiguration;

  public GeneralMessages(MessageSource messageSource, GeneralConfiguration generalConfiguration) {
    this.messageSource = messageSource;
    this.generalConfiguration = generalConfiguration;
  }

  public String getMessage(String errorCode, Locale locale) {
    try {
      if (errorCode == null || errorCode.trim().length() == 0) return "";
      else return messageSource.getMessage(errorCode, null, locale);
    } catch (Exception ex) {
      if (generalConfiguration.getErroronmissinglabel() == false) return errorCode;
      else
        throw new MissingResourceException(
            "Resource Missing", GeneralMessages.class.getName(), errorCode);
    }
  }

  public String getMessage(String errorCode, Object[] args, Locale locale) {
    try {
      if (errorCode == null || errorCode.trim().length() == 0) return "";
      else return messageSource.getMessage(errorCode, args, locale);
    } catch (Exception ex) {
      if (generalConfiguration.getErroronmissinglabel() == false) return errorCode;
      else
        throw new MissingResourceException(
            "Resource Missing", GeneralMessages.class.getName(), errorCode);
    }
  }
}
