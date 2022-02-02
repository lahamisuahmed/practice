package com.practice.config;

import com.practice.security.JWTAccessDeniedHandler;
import com.practice.security.JWTAuthenticationEntryPoint;
import com.practice.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;

    private final JWTAuthenticationEntryPoint jwtAuthEntryPoint;

    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(CustomUserDetailService customUserDetailService,
                          JWTAuthenticationEntryPoint jwtAuthEntryPoint,
                          JWTAccessDeniedHandler jwtAccessDeniedHandler) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * defines what you want to secure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                // this disables session creation on Spring Security, we don’t need sessions so this will prevent the creation of session cookies
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth", "/auth/user")
                .permitAll()
                .antMatchers("/signup/**")
                .permitAll()
                .antMatchers("/users/userinfo")
                .permitAll()
                .antMatchers("/password-reset/**")
                .permitAll()
                .anyRequest()
                .authenticated();

    }

    /**
     * defines how you want to secure it
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8000","http://localhost:3000", "http://127.0.0.1:8000", "https://www.aaerlaw.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setMaxAge(Long.valueOf(3600));
        configuration.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
