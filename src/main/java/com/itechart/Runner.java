package com.itechart;

import com.itechart.core.stream.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Runner {
    private class Task implements Runnable {
        private final Logger LOGGER = LoggerFactory.getLogger(Task.class);
        private final InputStream inputStream;

        public Task(final InputStream inputStream) {
            this.inputStream = inputStream;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        LOGGER.info(inputStream.toString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                }
            }).start();
        }

        @Override
        public void run() {
            try {
                while ((inputStream.read()) != -1) {
                }
            } catch (IOException e) {
                LOGGER.debug(e.getMessage(), e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private void go() {
        InputStreamWrapper wrapper1 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper2 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper3 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));
        InputStreamWrapper wrapper4 = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"));

        new Thread(new Task(wrapper1)).start();
        new Thread(new Task(wrapper2)).start();
        new Thread(new Task(wrapper3)).start();
        new Thread(new Task(wrapper4)).start();
    }

    public static void main(String[] args) {
        new Runner().go();
    }
}