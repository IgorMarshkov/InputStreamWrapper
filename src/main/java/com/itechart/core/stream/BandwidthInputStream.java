package com.itechart.core.stream;

import java.io.IOException;
import java.io.InputStream;

public abstract class BandwidthInputStream extends InputStream {
    private final InputStream inputStream;
    private final long startTime = System.currentTimeMillis();

    private long bytesRead = 0;

    private static final long SLEEP_DURATION_MS = 50;

    protected abstract double getAvgBandwidth();
    protected abstract void addClient();
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

    @Override
    public String toString() {
        return "ThrottledInputStream{" +
                "bytesRead=" + bytesRead +
                ", maxBytesPerSec=" + getAvgBandwidth() +
                ", bytesPerSec=" + getBytesPerSec() +
                '}';
    }
}
