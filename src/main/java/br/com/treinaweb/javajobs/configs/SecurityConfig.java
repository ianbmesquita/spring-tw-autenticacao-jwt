package br.com.treinaweb.javajobs.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private static final String API_JOBS_URI = "/api/v1/jobs/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable();
        
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, API_JOBS_URI).permitAll()
            .anyRequest().authenticated();
        
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
