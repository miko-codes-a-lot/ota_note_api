package com.ota.api.note.spring;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.stream.Collectors;

/**
 * This class extends HttpServletRequestWrapper to cache the request body.
 * It reads the request body InputStream and caches it in a byte array to allow multiple read operations.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] cachedBody;

    /**
     * Constructs a CachedBodyHttpServletRequest by caching the request body from the original HttpServletRequest.
     *
     * @param request The original HTTP servlet request
     * @throws IOException If an I/O error occurs during the reading of the request body
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    /**
     * Overrides getInputStream() to return a ServletInputStream that reads from the cached request body byte array.
     *
     * @return ServletInputStream that reads from the cached request body byte array
     */
    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    /**
     * Overrides getReader() to return a BufferedReader that reads from the cached request body byte array.
     *
     * @return BufferedReader that reads from the cached request body byte array
     */
    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    public String getBody() {
        return this.getReader().lines().collect(Collectors.joining());
    }
}