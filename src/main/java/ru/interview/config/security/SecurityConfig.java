package ru.interview.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.interview.services.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    private RestAuthenticationFailureHandler failureHandler;

    @Autowired
    public SecurityConfig(UserService userService,
                          RestAuthenticationSuccessHandler restAuthenticationSuccessHandler,
                          RestAuthenticationFailureHandler failureHandler) {
        this.userService = userService;
        this.restAuthenticationSuccessHandler = restAuthenticationSuccessHandler;
        this.failureHandler = failureHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/profile/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .permitAll()
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(failureHandler)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setHideUserNotFoundExceptions(false);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
