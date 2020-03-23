package edu.tsystems.javaschool.logapp.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetails;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("password").authorities("ROLE_MANAGER")
//                .and()
//                .withUser("driver").password("password").authorities("ROLE_DRIVER");
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

//        http.authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .and()
//                .httpBasic();

//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().antMatchers("/public").anonymous()
//                .and()
//                .authorizeRequests().antMatchers("/authenticated").authenticated()
//                .and()
//                .httpBasic();
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
}
