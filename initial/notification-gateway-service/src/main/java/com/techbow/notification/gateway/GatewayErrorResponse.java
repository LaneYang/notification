package com.techbow.notification.gateway;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GatewayErrorResponse implements ClientHttpResponse {
    private HttpStatus httpStatus;
    private String message;

    public GatewayErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return this.httpStatus;
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return this.httpStatus.value();
    }

    @Override
    public String getStatusText() throws IOException {
        return this.message;
    }

    @Override
    public void close() {
    }

    @Override public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(message.getBytes());
    }

    @Override public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return headers;
    }
}
