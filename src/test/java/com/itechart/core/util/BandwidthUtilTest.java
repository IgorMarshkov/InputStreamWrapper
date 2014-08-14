package com.itechart.core.util;

import com.itechart.core.model.Bandwidth;
import org.joda.time.LocalTime;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class BandwidthUtilTest extends Assert {

    @Test
    public void testParsePeriod() {
        List<Bandwidth> bandwidths = BandwidthUtil.parsePeriod("");
        assertEquals(bandwidths.size(), 0);

        bandwidths = BandwidthUtil.parsePeriod("12:00am-02:23pm=64|02:23pm-11:00pm=100|11:00pm-12:00am=");
        assertEquals(bandwidths.size(), 3);

        assertEquals(bandwidths.get(0).getBandwidth(), 64000.);
        assertEquals(bandwidths.get(1).getBandwidth(), 100000.);
        assertEquals(bandwidths.get(2).getBandwidth(), Double.MAX_VALUE);

        assertEquals(bandwidths.get(0).getFromTime(), bandwidths.get(2).getToTime());

        assertEquals(bandwidths.get(0).getFromTime(), new LocalTime(0, 0, 0));
        assertEquals(bandwidths.get(0).getToTime(), new LocalTime(14, 23, 0));

        assertEquals(bandwidths.get(1).getFromTime(), new LocalTime(14, 23, 0));
        assertEquals(bandwidths.get(1).getToTime(), new LocalTime(23, 00, 0));

        assertEquals(bandwidths.get(2).getFromTime(), new LocalTime(23, 00, 0));
        assertEquals(bandwidths.get(2).getToTime(), new LocalTime(0, 0, 0));
    }
}
