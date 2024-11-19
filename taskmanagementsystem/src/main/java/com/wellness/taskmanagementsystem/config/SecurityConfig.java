package com.wellness.taskmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		  .csrf()
		  .disable()
		  .authorizeRequests()
		  .requestMatchers("/tasks/**")
		  .authenticated()
		  .anyRequest()
		  .permitAll() 
		  .and()
		  .httpBasic();
		return http.build();
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	 @Bean
	    public UserDetailsService userDetailsService() {
	        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	        
	        // User "admin" with password "admin123"
	        manager.createUser(User.withUsername("admin")
	                .password(passwordEncoder().encode("admin123"))
	                .roles("ADMIN")
	                .build());
	        
	        // User "user" with password "user123"
	        manager.createUser(User.withUsername("user")
	                .password(passwordEncoder().encode("user123"))
	                .roles("USER")
	                .build());
	        
	        return manager;
	    }
}
