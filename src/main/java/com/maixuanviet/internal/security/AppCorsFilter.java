package com.maixuanviet.internal.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author VietMX
 */

@Component
@Lazy
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppCorsFilter extends OncePerRequestFilter {

    private static String allowedHeaders = "Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection," +
            "Content-Length,Content-Type,Cookie,Host,Origin,Pragma,Referer,User-Agent,Authorization," +
            "Content-Disposition,TimeZoneOffset,RequestId,Language,Platform";
    private static String exposedHeaders ="Cache-Control,Connection,Content-Type,Date,Expires,Pragma,Server,Set-Cookie," +
            "Transfer-Encoding,X-Content-Type-Options,X-XSS-Protection,X-Frame-Options,X-Application-Context," +
            "Authorization,Content-Disposition,TimeZoneOffset,RequestId,Language,Platform";
    private static long maxAge = 3600L;
    private static String allowedMethods = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
    private static String allowedOrigins = "*";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientOrigin = request.getHeader("Origin");

        if ("*".equals(allowedOrigins)) {
            response.setHeader("Access-Control-Allow-Origin", clientOrigin);
        } else if (allowedOrigins.contains(clientOrigin)) {
            response.setHeader("Access-Control-Allow-Origin", clientOrigin);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "");
        }

        response.setHeader("Access-Control-Allow-Methods", allowedMethods);
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders);
        response.setHeader("Access-Control-Expose-Headers", exposedHeaders);
        response.setHeader("Access-Control-Max-Age", Long.toString(maxAge));
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (request.getMethod().equals("OPTIONS") || request.getMethod().equals("HEAD"))
            response.setStatus(HttpServletResponse.SC_OK);
        else {
            filterChain.doFilter(request, response);
        }

    }
}
