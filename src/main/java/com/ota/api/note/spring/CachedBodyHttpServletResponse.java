package com.ota.api.note.spring;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.SneakyThrows;
import lombok.val;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Wrapper for HttpServletResponse that caches the response body.
 * This wrapper is used to prevent multiple reads of the response output stream,
 * ensuring that subsequent reads do not result in an empty string.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private CachedBodyServletOutputStream cachedOutput;

    /**
     * Constructs a {@link CachedBodyHttpServletResponse} with the provided {@link HttpServletResponse}.
     *
     * @param response The original HttpServletResponse to be wrapped.
     */
    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    /**
     * Returns the output stream of the response.
     *
     * @return The output stream of the response.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            cachedOutput = new CachedBodyServletOutputStream(outputStream);
        }

        return cachedOutput;
    }

    /**
     * Returns the writer of the response.
     *
     * @return The writer of the response.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            cachedOutput = new CachedBodyServletOutputStream(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(cachedOutput, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    /**
     * Flushes the buffer of the response.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            cachedOutput.flush();
        }
    }

    /**
     * Retrieves the cached response body as a string.
     *
     * @return The cached response body as a string.
     */
    @SneakyThrows
    public String getBody() {
        val charEncoding = this.getResponse().getCharacterEncoding();
        return new String(this.getCopy(), charEncoding);
    }

    /**
     * Retrieves the cached response body as a byte array.
     *
     * @return The cached response body as a byte array.
     */
    private byte[] getCopy() {
        if (cachedOutput != null) {
            return cachedOutput.getCopy();
        } else {
            return new byte[0];
        }
    }
}
