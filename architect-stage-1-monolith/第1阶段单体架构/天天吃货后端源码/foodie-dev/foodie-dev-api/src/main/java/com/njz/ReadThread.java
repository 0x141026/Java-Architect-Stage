package com.njz;

public class ReadThread implements Runnable{
    private WaitTime waitTimeUnderTest = new WaitTime();

    @Override
    public void run() {
        System.out.println("类ReadThread-方法run：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
        waitTimeUnderTest.waitTime(10 * 1000);
    }

    public static void main(String[] args) {
        ReadThread readThread = new ReadThread();
        System.out.println(readThread.waitTimeUnderTest);
    }
}
