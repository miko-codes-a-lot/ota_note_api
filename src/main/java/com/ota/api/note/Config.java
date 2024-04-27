package com.ota.api.note;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A record representing environment configuration properties.
 * It is annotated with @ConfigurationProperties to bind properties prefixed with "app" from application.properties (or similar) file.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
@ConfigurationProperties(prefix = "app")
public record Config(String tracingIdKey) {
}
