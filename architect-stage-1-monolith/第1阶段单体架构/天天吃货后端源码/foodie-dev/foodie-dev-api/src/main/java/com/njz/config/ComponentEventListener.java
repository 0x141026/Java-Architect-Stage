package com.njz.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ComponentEventListener {

    @EventListener(ApplicationReadyEvent.class)
    @Order(3) // 优先级设置为1，数值越小，优先级越高
    public void firstListener() {
        System.out.println("3执行的监听器");
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2) // 优先级设置为2
    public void secondListener() {
        System.out.println("2执行的监听器");
    }

    @EventListener(ApplicationReadyEvent.class)
    // 如果不设置@Order，默认为Ordered.LOWEST_PRECEDENCE，即优先级最低
    public void lastListener() {
        System.out.println("最后执行的监听器");
    }
}
