package edu.tsystems.javaschool.logapp.api.config;

import edu.tsystems.javaschool.logapp.api.exception.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetails;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails);
        auth.authenticationProvider(authProvider());
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
                .antMatchers("/rest/**").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .logout();
    }


    @Bean
    public PasswordEncoder passwordEncoder() //convert to sha256(?)
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetails);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
