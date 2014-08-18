package com.itechart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        try (InputStream in = getClass().getResourceAsStream("/config.properties")) {
            props.load(in);
        } catch (Exception e) {
            LOGGER.error("Can't load properties", e);
        }
    }

    /**
     * Get bandwidth periods.
     * By default we use unlimited bandwidth for all day.
     *
     * @return bandwidth periods as string.
     */
    public String getBandwidthPeriods() {
        return props.getProperty(BANDWIDTH_PERIODS, "12:00am-12:00am=");
    }
}