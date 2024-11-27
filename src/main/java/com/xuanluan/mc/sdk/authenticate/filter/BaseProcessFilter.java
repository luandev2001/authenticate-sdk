package com.xuanluan.mc.sdk.authenticate.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

public abstract class BaseProcessFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            process(request);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            processError(request, response).accept(e);
        }
    }

    protected abstract void process(HttpServletRequest request);

    protected abstract Consumer<Exception> processError(HttpServletRequest request, HttpServletResponse response);
}
