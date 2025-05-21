package me.hwangjoonsoung.pefint.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // security 적용하지 않을 디렉토리나 url 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers("/static/**"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/","/user/new","/user/login","/user/success","/user/alluser").permitAll().anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/user/login")
                            .defaultSuccessUrl("/user/success").permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/user/logout")
                            .invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
                })
                .csrf(scrf -> {
                            scrf.disable();
                        }
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
