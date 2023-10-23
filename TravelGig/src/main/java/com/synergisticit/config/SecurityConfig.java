package com.synergisticit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user =
//			 User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}



    @Bean
    public SecurityFilterChain apiFilterChain2(HttpSecurity http) throws Exception {
        http
                .apply(MyCustomDsl.customDsl()).flag(true).and()
                .authorizeRequests().requestMatchers("/home/**", "", "/register").permitAll().and()
                .authorizeRequests().requestMatchers(("/home")).hasAnyRole("USER", "ADMIN")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll();
        return http.build();
    }
}


