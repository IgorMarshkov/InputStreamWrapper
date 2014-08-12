package com.ism;


import com.ism.concurrent.SingleThread;
import com.ism.core.model.BandwidthMonitor;
import com.ism.stream.ThrottledInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        BandwidthMonitor monitor = BandwidthMonitor.getInstance();

        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), monitor.getAvgBandwidth()))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), monitor.getAvgBandwidth()))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), monitor.getAvgBandwidth()))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), monitor.getAvgBandwidth()))).start();
        new Thread(new SingleThread(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt"), monitor.getAvgBandwidth()))).start();
    }
}