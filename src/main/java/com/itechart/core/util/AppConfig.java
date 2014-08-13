package com.itechart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private static final String BANDWIDTH_PERIODS = "bandwidth.periods";
    private static final String BANDWIDTH_DOWN_STEP = "bandwidth.down.step";
    private static final String CLIENT_MAX_COUNT = "client.max.count";
    private static final String CLIENT_GENERATOR_DELAY = "client.generator.delay";

    private static AppConfig instance = null;

    private Properties props;

    public synchronized static AppConfig getInstance() {
        return instance == null ? new AppConfig() : instance;
    }

    private AppConfig() {
        props = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/config.properties");
            props.load(in);
        }
        catch (Exception e) {
            LOGGER.error("Can't load properties");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                LOGGER.error("Can't load properties");
            }
        }
    }

    public String getBandwidthPeriods() {
        return props.getProperty(BANDWIDTH_PERIODS, "");
    }

    public int getBandwidthDownStep() {
        String value = props.getProperty(BANDWIDTH_DOWN_STEP, "10");
        return Integer.parseInt(value);
    }

    public int getClientMaxCount() {
        String value = props.getProperty(CLIENT_MAX_COUNT, "1");
        return Integer.parseInt(value);
    }

    public int getClientGeneratorDelay() {
        String value = props.getProperty(CLIENT_GENERATOR_DELAY, "30");
        return Integer.parseInt(value);
    }
}
