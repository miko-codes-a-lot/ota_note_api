package com.ota.api.note.spring;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * {@link ServletOutputStream} wrapper that caches the output stream.
 * This wrapper is used to return a copy of the output stream instead of reading directly,
 * ensuring that subsequent reads do not result in an empty string.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
public class CachedBodyServletOutputStream extends ServletOutputStream {
    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;

    /**
     * Constructs a {@link CachedBodyServletOutputStream} with the provided {@link OutputStream}.
     *
     * @param outputStream The original OutputStream to be wrapped.
     */
    public CachedBodyServletOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }


    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
    }

    /**
     * Writes the specified byte to the output stream.
     *
     * @param b The byte to be written.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    /**
     * Retrieves the cached output stream as a byte array.
     *
     * @return The cached output stream as a byte array.
     */
    public byte[] getCopy() {
        return copy.toByteArray();
    }
}
