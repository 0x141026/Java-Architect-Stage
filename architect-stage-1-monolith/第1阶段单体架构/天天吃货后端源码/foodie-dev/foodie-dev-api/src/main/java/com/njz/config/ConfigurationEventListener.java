package com.njz.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

@Configuration
public class ConfigurationEventListener {

    @Order(4) // 优先级设置为2
    @EventListener(ApplicationReadyEvent.class)
    public void secondListener() {
        System.out.println("Configuration执行的监听器");
    }
}
