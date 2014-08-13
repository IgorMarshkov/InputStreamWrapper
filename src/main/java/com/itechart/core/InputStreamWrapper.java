package com.itechart.core;

import com.itechart.core.concurrent.RunnableTask;
import com.itechart.core.stream.ThrottledInputStream;

import java.io.InputStream;

public class InputStreamWrapper {
    private InputStream inputStream;

    public InputStreamWrapper(String url) {
        this.inputStream = getClass().getResourceAsStream(url);
    }

    public void load() {
        if (inputStream != null) {
            new Thread(new RunnableTask(new ThrottledInputStream(inputStream))).start();
            ClientManager.getInstance().add();
        }
    }
}