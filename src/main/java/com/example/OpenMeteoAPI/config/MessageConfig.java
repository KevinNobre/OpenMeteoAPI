package com.example.OpenMeteoAPI.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class MessageConfig {

    @Bean
    public MessageSource messageSourceBean() {
        ReloadableResourceBundleMessageSource messageSourceBean = new ReloadableResourceBundleMessageSource();
        messageSourceBean.setBasename("classpath:messages");
        messageSourceBean.setDefaultEncoding("UTF-8");
        return messageSourceBean;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptorBean() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }


}
