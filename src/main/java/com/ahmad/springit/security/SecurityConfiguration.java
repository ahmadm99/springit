package com.ahmad.springit.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public UserDetailsServiceImplementation userDetailsService;

    public SecurityConfiguration(UserDetailsServiceImplementation userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.to("info")).permitAll() // now /actuator/info is available for everyone
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN") //meanwhile now any other endpoint requires a role of admin and we customize in the antmatchers
                .antMatchers("/actuator/").hasRole("ADMIN") // /actuator requires admin role
                .antMatchers("/").permitAll() // home page is available
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/link/submit").hasRole("USER").and().formLogin().loginPage("/login").permitAll() //submit requires user role
                .usernameParameter("email") //we do this because we named our username = email; if we kept it username we wouldn't need this. same in password, we kept the default password name password so we don't need to add a password parameter
                .and()
                .logout()
                .and()
                .rememberMe(); //we are using the default parameter name in out login page which is remember-me, else we need to specify here rememberMeParameter()
//                .and()
//                .csrf().disable()
//                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }
}
