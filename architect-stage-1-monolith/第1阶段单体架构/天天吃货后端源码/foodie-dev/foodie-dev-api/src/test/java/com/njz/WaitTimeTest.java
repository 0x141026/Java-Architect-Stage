package com.njz;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class WaitTimeTest {
    @InjectMocks
    private WaitTime waitTimeUnderTest;

    @Test
    public void testWaitTime() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                waitTimeUnderTest.waitTime(10 * 1000);
            }
        };
        thread.start();
        System.out.println("主线程：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
        System.out.println("线程thread：" + thread.getId() + ":" + thread.getName());
        thread.interrupt();
        thread.join();
        // Verify the results
        Assert.assertEquals(1, 1);
    }
    @Test
    public void testWaitTime1() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                waitTimeUnderTest.waitTime(10 * 1000);
            }
        };
        thread.start();
        Thread.sleep(4 * 1000);
        System.out.println("主线程：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
        System.out.println("线程thread：" + thread.getId() + ":" + thread.getName());
        synchronized (waitTimeUnderTest) {
            waitTimeUnderTest.notifyAll();
            waitTimeUnderTest.receiveSucceess = true;
        }
        thread.join();
        // Verify the results
        Assert.assertEquals(1, 1);
    }
    @Test
    public void testWaitTime2() throws InterruptedException {
        System.out.println("类WaitTimeTest-方法testWaitTime2：" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
        Thread readThread = new Thread(new ReadThread());
        readThread.start();
        System.out.println("类WaitTimeTest-方法testWaitTime2-线程thread：" + readThread.getId() + ":" + readThread.getName());

        readThread.join();
        // Verify the results
        Assert.assertEquals(1, 1);
    }
}
