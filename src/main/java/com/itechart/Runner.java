package com.itechart;

import com.itechart.core.InputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        Runner runner = new Runner();

        InputStreamWrapper stream1 = new InputStreamWrapper("/test.txt");
        InputStreamWrapper stream2 = new InputStreamWrapper("/test.txt");

        stream1.load();
        //stream2.load();
    }
}