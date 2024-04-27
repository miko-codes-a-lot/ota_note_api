package com.ota.api.note.filters;

import com.ota.api.note.Config;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


/**
 * This filter class enhances logging by adding a unique tracing identifier to each incoming request.
 * The tracing identifier is generated as a UUID and added to the Mapped Diagnostic Context (MDC) of the logging framework.
 * This allows for correlating log statements across different components of the application for a single request.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
@Order(0)
@Component
public class TracingFilter extends OncePerRequestFilter {
    private final Config config;

    @Autowired
    public TracingFilter(Config config) {
        this.config = config;
    }

    /**
     * Generates a unique tracing identifier for the incoming request, adds it to the request attributes,
     * and sets it in the MDC of the logging framework. After processing the request, clears the MDC.
     *
     * @param request  HTTP servlet request
     * @param response HTTP servlet response
     * @param chain    Filter chain for invoking the next filter or the servlet
     * @throws ServletException If an exception occurs that interferes with the filter's normal operation
     * @throws IOException      If an I/O error occurs during the execution of the filter
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        val tracingId = UUID.randomUUID().toString().toUpperCase().replace("-", "");

        request.setAttribute(config.tracingIdKey(), tracingId);

        MDC.put(config.tracingIdKey(), tracingId);
        chain.doFilter(request, response);

        MDC.clear();
    }
}
