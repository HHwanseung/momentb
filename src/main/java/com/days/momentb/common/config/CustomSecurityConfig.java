package com.days.momentb.common.config;//package com.days.momentb.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.days.momentb.security.filter.TokenCheckFilter;
import com.days.momentb.security.filter.TokenGenerateFilter;
import com.days.momentb.security.util.JWTUtil;



@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        log.info("CustomSecurityConfig..configure....");
        http.formLogin().loginPage("/customLogin").loginProcessingUrl("/login"); //인가/인증에 문제시 로그인 화면
        http.csrf().disable();
        http.logout();

        http.addFilterBefore(tokenCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenGenerateFilter(), UsernamePasswordAuthenticationFilter.class);
        //로그인전에 확인하는것.

    }

    @Bean
    public TokenCheckFilter tokenCheckFilter(){
        return new TokenCheckFilter(jwtUtil());
    }

    @Bean
    public TokenGenerateFilter tokenGenerateFilter() throws Exception {
        return new TokenGenerateFilter("/jsonApiLogin",authenticationManager(),jwtUtil());//이 안에 문자열이 로그인 경로가 되는것임
    }

    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }


}
