package com.momentus.foundation.common;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
    @Bean
    @Primary
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(
                "FoundErrorMessages",
                "AppError",
                "Labels",
                "Menus"
        );
        source.setDefaultEncoding("UTF-8");
        return source;
    }

}
