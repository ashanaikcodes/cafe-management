package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	
	//Basic Auth
	@Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(requests -> requests
                		.requestMatchers("/actuator/**").hasRole("ACTUATOR")
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }


//Role based method level auth
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {

		UserDetails admin = User.withUsername("Ash").password(encoder.encode("123")).roles("ADMIN", "USER").build();

		UserDetails user = User.withUsername("Naik").password(encoder.encode("123")).roles("USER").build();
		UserDetails actuator = User.builder().username("Actuator").password(encoder.encode("123")).roles("ACTUATOR").build();

		return new InMemoryUserDetailsManager(admin, user, actuator);
	}

	// Configuring HttpSecurity

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests().requestMatchers("/auth/welcome").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated().and().authorizeHttpRequests()
				.requestMatchers("/auth/admin/**").authenticated().and().formLogin().and().build();
	}

	// Password Encoding

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	 
  
}

