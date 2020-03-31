package edu.tsystems.javaschool.logapp.api.config;

import edu.tsystems.javaschool.logapp.api.exception.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetails;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetails)
                .and()
                .inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .authorities("ROLE_MANAGER");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());


        http.authorizeRequests()
                .antMatchers("/drivers/**").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/trucks/**").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/orders/**").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/myOrder/**").access("hasRole('ROLE_DRIVER')")
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .logout();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {

                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {

                return charSequence.toString().equals(s);
            }
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }
}
