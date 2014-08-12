package com.ism;


import com.ism.core.concurrent.SingleThread;
import com.ism.core.BandwidthManager;
import com.ism.core.stream.ThrottledInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        BandwidthManager monitor = BandwidthManager.getInstance();

        double bandwidth = monitor.getAvgBandwidth().doubleValue();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), bandwidth))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), bandwidth))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), bandwidth))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), bandwidth))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), bandwidth))).start();
    }
}