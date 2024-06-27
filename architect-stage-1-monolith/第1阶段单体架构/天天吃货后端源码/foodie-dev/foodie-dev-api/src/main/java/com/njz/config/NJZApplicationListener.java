package com.njz.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(1)
@Component
public class NJZApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override

    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.getenv().forEach((key, value) -> {
            System.out.println("NJZApplicationListener:" + key + "=" + value);
        });
    }
}
