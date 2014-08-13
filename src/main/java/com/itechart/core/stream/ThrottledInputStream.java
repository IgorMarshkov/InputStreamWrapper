package com.itechart.core.stream;

import com.itechart.core.BandwidthManager;

import java.io.IOException;
import java.io.InputStream;

public class ThrottledInputStream extends InputStream {
    private final InputStream inputStream;
    private final long startTime = System.currentTimeMillis();

    private long bytesRead = 0;
    private long totalSleepTime = 0;

    private static final long SLEEP_DURATION_MS = 50;


    public ThrottledInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public int read() throws IOException {
        throttle();
        int data = inputStream.read();
        if (data != -1) {
            bytesRead++;
        }
        return data;
    }

    private void throttle() throws IOException {
        if (getBytesPerSec() > BandwidthManager.getInstance().getAvgBandwidth().longValue()) {
            try {
                Thread.sleep(SLEEP_DURATION_MS);
                totalSleepTime += SLEEP_DURATION_MS;
            } catch (InterruptedException e) {
                throw new IOException("Thread aborted", e);
            }
        }
    }

    /**
     * Getter for the number of bytes read from this stream, since creation.
     * @return The number of bytes.
     */
    public long getTotalBytesRead() {
        return bytesRead;
    }

    /**
     * Getter for the read-rate from this stream, since creation.
     * Calculated as bytesRead/elapsedTimeSinceStart.
     * @return Read rate, in bytes/sec.
     */
    public long getBytesPerSec() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        if (elapsed == 0) {
            return bytesRead;
        } else {
            return bytesRead / elapsed;
        }
    }

    /**
     * Getter the total time spent in sleep.
     * @return Number of milliseconds spent in sleep.
     */
    public long getTotalSleepTime() {
        return totalSleepTime;
    }

    @Override
    public String toString() {
        return "ThrottledInputStream{" +
                "bytesRead=" + bytesRead +
                ", maxBytesPerSec=" + BandwidthManager.getInstance().getAvgBandwidth().longValue() +
                ", bytesPerSec=" + getBytesPerSec() +
                ", totalSleepTime=" + totalSleepTime +
                '}';
    }
}