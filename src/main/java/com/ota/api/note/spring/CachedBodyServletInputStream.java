package com.ota.api.note.spring;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class extends ServletInputStream to provide a stream that reads from a cached request body byte array.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
public class CachedBodyServletInputStream extends ServletInputStream {
    private final InputStream cachedBodyInputStream;

    /**
     * Constructs a CachedBodyServletInputStream with the cached request body byte array.
     *
     * @param cachedBody The cached request body byte array
     */
    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    /**
     * Checks if the stream has reached the end.
     *
     * @return true if the stream has reached the end; otherwise, false
     */
    @SneakyThrows
    @Override
    public boolean isFinished() {
        return cachedBodyInputStream.available() == 0;
    }

    /**
     * Indicates the data is available for reading.
     *
     * @return true if data is available; otherwise, false
     */
    @Override
    public boolean isReady() {
        return true;
    }

    /**
     * Sets the ReadListener for asynchronous processing.
     * This method throws UnsupportedOperationException because servlet input streams do not support asynchronous processing.
     *
     * @param readListener The ReadListener (not supported)
     * @throws UnsupportedOperationException If asynchronous processing is attempted
     */
    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return The next byte of data, or -1 if the end of the stream is reached
     * @throws IOException If an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
}