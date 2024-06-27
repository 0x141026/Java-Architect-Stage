package com.njz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteTime {
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteTime.class);
    boolean receiveSucceess;

    public WriteTime() {
        this.receiveSucceess = false;
    }

    public synchronized void waitTime(long milliseconds) {
        long endTime = milliseconds + System.currentTimeMillis();
        long timeToWait;
        while(!receiveSucceess && (timeToWait = endTime - System.currentTimeMillis()) > 0) {
            try {
                System.out.println("类WaitTime-waitTime方法try：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                wait(timeToWait);
            } catch (InterruptedException e) {
                LOGGER.info("被中断！");
                System.out.println("类WaitTime-waitTime方法catch：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                return;
            }
        }
        if ((timeToWait = endTime - System.currentTimeMillis()) > 0) {
            LOGGER.info("总时长{}，在大约第{}毫秒被唤醒", milliseconds, milliseconds-timeToWait);
        } else {
            LOGGER.info("等待{}毫秒，没有被唤醒，自动唤醒了。", milliseconds);
        }
    }

    public void A() {
        System.out.println("类WriteTime-方法A:" + Thread.currentThread().getId() + Thread.currentThread().getName());
        B();
    }
    public void B() {
        System.out.println("类WriteTime-方法B:" + Thread.currentThread().getId() + Thread.currentThread().getName());
    }

}
