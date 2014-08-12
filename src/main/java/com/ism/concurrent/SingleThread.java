package com.ism.concurrent;

import com.ism.stream.ThrottledInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SingleThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThread.class);

    private ThrottledInputStream is;
    private Integer id;

    public SingleThread(ThrottledInputStream is, Integer id) {
        this.is = is;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (is.read() != -1) {
                LOGGER.info("Thread #" + id + "  " + is.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return id;
    }
}