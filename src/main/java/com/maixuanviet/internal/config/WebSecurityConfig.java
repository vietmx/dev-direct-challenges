package com.maixuanviet.internal.config;

import com.maixuanviet.internal.constants.APIPathConst;
import com.maixuanviet.internal.constants.SwaggerConst;
import com.maixuanviet.internal.security.JwtAuthenticationEntryPoint;
import com.maixuanviet.internal.security.JwtRequestFilter;
import com.maixuanviet.internal.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author VietMX
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //API Request not need authentication
        http.authorizeRequests()

                //SwaggerUI
                .antMatchers(SwaggerConst.HEALTH).permitAll()
                .antMatchers(SwaggerConst.HOME).permitAll()
                .antMatchers(SwaggerConst.SWAGGER).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_CSRF).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_SECURITY).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_RESOURCE).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_RESOURCE_UI).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_CSS).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_UI).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_BUNDLE).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_PRESET).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_JS).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FAVICON).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FAVICON_32).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FAVICON_16).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_API_DOCS).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_API_DOCS).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_01).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_02).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_03).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_04).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_05).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_06).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_07).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_08).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_09).permitAll()
                .antMatchers(SwaggerConst.SWAGGER_FONT_10).permitAll()

                /* Authenticate */
                .antMatchers(APIPathConst.V1_API_URL + "/" + APIPathConst.CALL_LOGS_ANALYSE).permitAll()
                .antMatchers(APIPathConst.V1_API_URL + "/" + APIPathConst.MINESWEEPER_EXECUTE).permitAll()

                .anyRequest().authenticated()
                ;

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
