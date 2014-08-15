package com.itechart.core.stream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract class provides functionality to control InputStream depend based on client streams and bandwidth.
 */
public abstract class BandwidthInputStream extends InputStream {
    private static final long SLEEP_DURATION_MS = 50;

    private final InputStream inputStream;
    private final long startTime = System.currentTimeMillis();

    private long bytesRead = 0;

    /**
     * Get current average bandwidth by one client stream.
     *
     * @return average bandwidth by one client stream.
     */
    protected abstract double getAvgBandwidth();

    /**
     * Add new client stream.
     */
    protected abstract void addClient();

    /**
     * Remove client stream.
     */
    protected abstract void removeClient();

    protected BandwidthInputStream(InputStream inputStream) {
        addClient();
        this.inputStream = inputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public int read() throws IOException {
        balanceBandwidth();
        int data = inputStream.read();
        if (data != -1) {
            bytesRead++;
        } else {
            removeClient();
        }

        return data;
    }

    @Override
    public int read(byte[] b) throws IOException {
        balanceBandwidth();
        int readLen = inputStream.read(b);
        if (readLen != -1) {
            bytesRead += readLen;
        } else {
            removeClient();
        }

        return readLen;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        balanceBandwidth();
        int readLen = inputStream.read(b, off, len);
        if (readLen != -1) {
            bytesRead += readLen;
        } else {
            removeClient();
        }

        return readLen;
    }

    private void balanceBandwidth() throws IOException {
        if (getBytesPerSec() > getAvgBandwidth()) {
            try {
                Thread.sleep(SLEEP_DURATION_MS);
            } catch (InterruptedException e) {
                throw new IOException("Thread aborted", e);
            }
        }
    }

    /**
     * Get read-rate from this stream, since creation.
     * Calculated as bytesRead/elapsedTimeSinceStart.
     *
     * @return read rate, in bytes/sec.
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
        StringBuilder builder = new StringBuilder();
        builder.append("BandwidthInputStream-> ");
        builder.append("bytesRead=").append(bytesRead);
        builder.append(", bytesPerSec=").append(getBytesPerSec());
        return builder.toString();
    }
}
