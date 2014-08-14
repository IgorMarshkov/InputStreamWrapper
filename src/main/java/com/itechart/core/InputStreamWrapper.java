package com.itechart.core;

import com.itechart.core.concurrent.RunnableTask;
import com.itechart.core.stream.ThrottledInputStream;

import java.io.InputStream;

public class InputStreamWrapper {
    private InputStream inputStream;

    public InputStreamWrapper(InputStream inputStream) {
        this.inputStream = new ThrottledInputStream(inputStream);
    }

    public void load() {
        if (inputStream != null) {
            new Thread(new RunnableTask(inputStream)).start();
        }
    }
}