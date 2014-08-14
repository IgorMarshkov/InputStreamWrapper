package com.itechart;

import com.itechart.core.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        Runner runner = new Runner();

        InputStream is1 = runner.getClass().getResourceAsStream("/test.txt");
        InputStream is2 = runner.getClass().getResourceAsStream("/test1.txt");

        InputStreamWrapper stream1 = new InputStreamWrapper(is1);
        InputStreamWrapper stream2 = new InputStreamWrapper(is2);

        stream1.load();
        stream2.load();
    }
}