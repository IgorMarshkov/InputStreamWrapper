package com.itechart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class provides utils for work with file config.properties.
 */
public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private static final String BANDWIDTH_PERIODS = "bandwidth.periods";

    private static AppConfig instance = new AppConfig();

    private Properties props;

    public static AppConfig getInstance() {
        return instance;
    }

    private AppConfig() {
        props = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/config.properties");
            props.load(in);
        } catch (Exception e) {
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
}