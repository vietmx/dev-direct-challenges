package com.maixuanviet.internal.security;

import com.maixuanviet.internal.logger.MyLog;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author VietMX
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    @Autowired
    private MyLog log;

    @Autowired
    private JwtTokenService jwtTokenService;
    
    public static final String PROFILE_URI_REGEX = "(\\/profile[\\/]*[a-z,A-Z,0-9,$&+,:;=?@#|'<>.^*()%!-]*)?";
    public static final String ENTITIES_URI_REGEX = "(\\/[a-z,A-Z,0-9,$&+,:;=?@#|'<>.^*()%!-]*Entities)?";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	String uri = request.getRequestURI();
        if(org.apache.commons.lang3.StringUtils.isEmpty(uri) 
       		 || "/".equals(uri)
       		 || uri.matches(PROFILE_URI_REGEX)
       		 || uri.matches(ENTITIES_URI_REGEX))
        {
       	 response.setContentType("text/html");
			response.sendError(401, "http.401");
			response.getWriter().flush();
		} else {
			final Optional<String> jwt = getJwtFromRequest(request);
			if (!jwt.isEmpty()) {
				jwt.ifPresent(token -> {
					try {
						if (jwtTokenService.validateToken(token)) {
							setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
						}
					} catch (IllegalArgumentException | MalformedJwtException | ExpiredJwtException e) {
						log.error("Unable to get JWT Token or JWT Token has expired");
					}
				});

			}
			filterChain.doFilter(request, response);
		}
    }

    private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenService.getUsernameFromToken(token);
        final List<String> roles = jwtTokenService.getRoles(token);
        final UserDetails userDetails = new User(username, "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(authDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(BEARER.length()));
        }
        return Optional.empty();
    }
}
