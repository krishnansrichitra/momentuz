package com.momentus.foundation.common;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ErrorMessages {

    public static final String ORG_NAME_MANDATORY = "10001";
    public static final String ORG_CODE_MANDATORY = "10002";
    public static final String ORG_INDUSTRY_MANDATORY = "10003";
    public static final String ORG_SECTOR_MANDATORY = "10004";


    private final MessageSource messageSource;

    public ErrorMessages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String errorCode, Locale locale) {
        return messageSource.getMessage(
                errorCode,
                null,
                locale
        );
    }
}
