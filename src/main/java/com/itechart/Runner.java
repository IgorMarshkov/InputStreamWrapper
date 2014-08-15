package com.itechart;

import com.itechart.core.stream.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    private class Task implements Runnable {
        private final Logger LOGGER = LoggerFactory.getLogger(Task.class);
        private final InputStream inputStream;
        private OutputStream outputStream;

        public Task(final InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;

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
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    if (outputStream != null) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
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
                if (outputStream != null) {
                    try {
                        outputStream.close();
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

        new Thread(new Task(wrapper1, null)).start();
        new Thread(new Task(wrapper2, null)).start();
    }

    public static void main(String[] args) {
        new Runner().go();
    }
}