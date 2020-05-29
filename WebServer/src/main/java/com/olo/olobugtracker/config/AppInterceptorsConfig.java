package com.olo.olobugtracker.config;

import com.olo.olobugtracker.middlewares.AppRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AppInterceptorsConfig implements WebMvcConfigurer {
    @Autowired
    private AppRequestInterceptor appRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(appRequestInterceptor);
    }
}
