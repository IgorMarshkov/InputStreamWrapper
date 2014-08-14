package com.itechart.core.concurrent;

import com.itechart.core.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class RunnableTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableTask.class);

    private InputStream is;

    public RunnableTask(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        ClientManager.getInstance().add();
        try {
            LOGGER.info("new thread is started");
            while (is.read() != -1) {
                LOGGER.info(is.toString());
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            ClientManager.getInstance().remove();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            is.close();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}