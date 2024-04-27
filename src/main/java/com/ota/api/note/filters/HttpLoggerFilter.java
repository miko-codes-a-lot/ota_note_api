package com.ota.api.note.filters;

import com.ota.api.note.Config;
import com.ota.api.note.models.dto.LogDTO;
import com.ota.api.note.spring.CachedBodyHttpServletRequest;
import com.ota.api.note.spring.CachedBodyHttpServletResponse;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

import static com.ota.api.note.utils.JSONUtils.stringify;

/**
 * Filter that logs and caches the HTTP request and response.
 * This filter wraps the request and response to cache their bodies, then logs the details.
 * The logged details include request and response information such as method, path, status, etc.
 * <p>
 * This filter is applied to all URL patterns.
 *
 * @see CachedBodyHttpServletRequest
 * @see CachedBodyHttpServletResponse
 * @see LogDTO
 * @see Config
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
@Order(1)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class HttpLoggerFilter extends OncePerRequestFilter {
    private final Config config;

    /**
     * Constructs an instance of {@link HttpLoggerFilter} with the specified {@link Config}.
     *
     * @param config The environment configuration.
     */
    @Autowired
    public HttpLoggerFilter(Config config) {
        this.config = config;
    }

    /**
     * Filters the incoming HTTP request and outgoing HTTP response.
     * Wraps the request and response to cache their bodies, then logs the details.
     *
     * @param httpServletRequest  The HTTP servlet request.
     * @param httpServletResponse The HTTP servlet response.
     * @param filterChain         The filter chain.
     * @throws ServletException if an error occurs during the filtering process.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        // InputStream & OutputStream can only be read once as such we have to wrap them
        val cachedRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        val cachedResponse = new CachedBodyHttpServletResponse(httpServletResponse);

        // Make sure the downstream pipes are also using the wrapped IO
        filterChain.doFilter(cachedRequest, cachedResponse);

        cachedResponse.flushBuffer();

        // Log the request
        val tracingId = cachedRequest.getAttribute(config.tracingIdKey()).toString();
        val logDTO = LogDTO.builder()
                .id(tracingId)
                .ipAddress(cachedRequest.getRemoteAddr())
                .method(cachedRequest.getMethod())
                .path(cachedRequest.getRequestURI())
                .requestBody(cachedRequest.getBody())
                .status(cachedResponse.getStatus())
                .responseBody(cachedResponse.getBody())
                .timestamp(Instant.now().toString())
                .build();
        logger.info(stringify(logDTO));
    }
}