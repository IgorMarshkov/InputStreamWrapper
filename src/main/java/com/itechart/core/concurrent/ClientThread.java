package com.itechart.core.concurrent;

import com.itechart.core.BandwidthManager;
import com.itechart.core.ClientManager;
import com.itechart.core.stream.ThrottledInputStream;
import com.itechart.core.util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientThread.class);

    @Override
    public void run() {
        AppConfig config = AppConfig.getInstance();
        ClientManager manager = ClientManager.getInstance();
        BandwidthManager monitor = BandwidthManager.getInstance();

        int count = config.getClientMaxCount();

        while (count > 0) {
            manager.add();
            new Thread(new RunnableTask(new ThrottledInputStream(monitor.getClass().getResourceAsStream("/test.txt")))).start();
            count--;
            try {
                Thread.sleep(config.getClientGeneratorDelay() * 1000);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}