package com.ism.core.concurrent;

import com.ism.core.stream.ThrottledInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SingleThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThread.class);

    private ThrottledInputStream is;

    public SingleThread(ThrottledInputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        try {
            while (is.read() != -1) {
                LOGGER.info(is.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}