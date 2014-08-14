package com.itechart.core.stream;

import com.itechart.core.BandwidthManager;
import com.itechart.core.ClientManager;

import java.io.IOException;
import java.io.InputStream;

public class ExperimentalThrottledInputStream extends InputStream {
    private final InputStream inputStream;
    private final long startTime = System.currentTimeMillis();

    private long bytesRead = 0;

    public ExperimentalThrottledInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public int read() throws IOException {
        if (!isLimitReached()) {
            int data = inputStream.read();
            if (data != -1) {
                bytesRead++;
            }
            return data;
        } else {
            return 0;
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (!isLimitReached()) {
            int readLen = inputStream.read(b);
            if (readLen != -1) {
                bytesRead += readLen;
            }
            return readLen;
        } else {
            return 0;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (!isLimitReached()) {
            int readLen = inputStream.read(b, off, len);
            if (readLen != -1) {
                bytesRead += readLen;
            }
            return readLen;
        } else {
            return 0;
        }
    }

    private boolean isLimitReached() {
        return (getBytesPerSec() >= BandwidthManager.getInstance().getAvgBandwidth());
    }

    /**
     * Getter for the number of bytes read from this stream, since creation.
     *
     * @return The number of bytes.
     */
    public long getTotalBytesRead() {
        return bytesRead;
    }

    /**
     * Getter for the read-rate from this stream, since creation.
     * Calculated as bytesRead/elapsedTimeSinceStart.
     *
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

    @Override
    public String toString() {
        return "ThrottledInputStream{" +
                "bytesRead=" + bytesRead +
                ", maxBytesPerSec=" + BandwidthManager.getInstance().getAvgBandwidth() +
                ", bytesPerSec=" + getBytesPerSec() +
                '}' + "clients: " + ClientManager.getInstance().getClients();
    }
}