package com.techbow.notification.gateway;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
class GatewayFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof HystrixTimeoutException) {
            return new GatewayErrorResponse(HttpStatus.TOO_MANY_REQUESTS, "Too many requests, please come back later!");
        }

        return new GatewayErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error happens, please come back later!");
    }
}
