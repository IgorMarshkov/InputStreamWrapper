package com.itechart.core.util;

import com.itechart.core.model.Bandwidth;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

/**
 * Class provides utils for work with bandwidth.
 */
public class BandwidthUtil {

    /**
     * Convert bandwidth string to list of bandwidth periods.
     *
     * @param str is bandwidth string - for example '12:00am-02:23pm=100|02:23pm-11:00pm=100|11:00pm-12:00am='
     * @return list of bandwidth periods.
     */
    public static List<Bandwidth> parsePeriod(String str) {
        List<Bandwidth> list = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("hh:mma");
        StringTokenizer st = new StringTokenizer(str, "|");
        while (st.hasMoreTokens()) {
            String periodStr = st.nextToken();
            int timeSepInd = periodStr.indexOf("-");
            int bandwidthSepInd = periodStr.indexOf("=");

            Bandwidth bandwidth = new Bandwidth();
            String fromTimeStr = periodStr.substring(0, timeSepInd);
            DateTime fromTime = dateTimeFormatter.parseDateTime(fromTimeStr);
            bandwidth.setFromTime(fromTime.toLocalTime());

            String toTimeStr = periodStr.substring(timeSepInd + 1, bandwidthSepInd);
            DateTime toTime = dateTimeFormatter.parseDateTime(toTimeStr);
            long to = toTime.toLocalTime().toDateTimeToday().getMillis() - 1;
            bandwidth.setToTime(new LocalTime(to));

            String bandwidthStr = periodStr.substring(bandwidthSepInd + 1);
            if (StringUtils.isEmpty(bandwidthStr)) {
                bandwidth.setBandwidth(Double.MAX_VALUE);
            } else {
                bandwidth.setBandwidth(Double.parseDouble(bandwidthStr) * 1000); // convert kb to bytes
            }

            list.add(bandwidth);
        }

        return list;
    }
}