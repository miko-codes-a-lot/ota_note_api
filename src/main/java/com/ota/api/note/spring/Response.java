package com.ota.api.note.spring;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * Response is a custom ResponseEntity subclass designed to enhance the readability and usability
 * of ResponseEntity in Spring applications.
 *
 * @param <T> The type of the response body.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
public class Response<T> extends ResponseEntity<T> {
    public Response(@Nullable T body, MultiValueMap<String, String> headers, HttpStatusCode status) {
        super(body, headers, status);
    }

    public Response(HttpStatus status, MultiValueMap<String, String> headers) {
        super(null, headers, status);
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public static class ResponseBuilder<T> {
        private HttpStatus status;
        private MultiValueMap<String, String> headers;
        private T body;

        public ResponseBuilder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder<T> headers(MultiValueMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public ResponseBuilder<T> body(T body) {
            this.body = body;
            return this;
        }

        public Response<T> build() {
            return new Response<>(body, headers, status);
        }

        public Response<Void> buildV() {
            return new Response<>(status, headers);
        }
    }
}
