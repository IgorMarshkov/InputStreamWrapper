package com.itechart.core.model;

import org.joda.time.LocalTime;

public class Bandwidth {
    private LocalTime fromTime;
    private LocalTime toTime;
    private double bandwidth; // bytes/second

    public Bandwidth() {

    }

    public Bandwidth(LocalTime fromTime, LocalTime toTime, int bandwidth) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.bandwidth = bandwidth;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }
}