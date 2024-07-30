package com.xuanluan.mc.sdk.authenticate.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.sdk.domain.model.WrapperResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseProcessFilter extends OncePerRequestFilter {
    protected final ObjectMapper objectMapper;

    protected BaseProcessFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            process(request);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            processError(request, response);
            handleInvalid(response, e.getMessage());
        }
    }

    protected abstract void process(HttpServletRequest request);

    protected abstract void processError(HttpServletRequest request, HttpServletResponse response);

    private void handleInvalid(HttpServletResponse response, String message) throws IOException {
        WrapperResponse<Object> serviceData =
                WrapperResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(message)
                        .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(objectMapper.writeValueAsString(serviceData));
    }
}
