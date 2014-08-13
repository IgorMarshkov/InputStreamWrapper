package com.itechart;

import com.itechart.core.concurrent.ClientThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public static void main(String[] args) {
        new Thread(new ClientThread()).start();
    }
}