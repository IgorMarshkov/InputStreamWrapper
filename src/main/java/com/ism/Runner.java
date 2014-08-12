package com.ism;


import com.ism.concurrent.SingleThread;
import com.ism.stream.ThrottledInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    private static final Integer MAX_READ_BYTES = 1024;

    public static void main(String[] args) {
        Runner runner = new Runner();

        ThrottledInputStream throttledInputStream = new ThrottledInputStream(runner.getClass().getResourceAsStream("/test.txt"), MAX_READ_BYTES);

        LOGGER.info("---GO---");

        new Thread(new SingleThread(throttledInputStream, 1)).start();
        new Thread(new SingleThread(throttledInputStream, 2)).start();
        new Thread(new SingleThread(throttledInputStream, 3)).start();
        new Thread(new SingleThread(throttledInputStream, 4)).start();
        new Thread(new SingleThread(throttledInputStream, 5)).start();
    }
}