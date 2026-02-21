package in.zoukme.zouk_album.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests
                .requestMatchers("/admin/**")
                .authenticated()
                .anyRequest()
                .permitAll())
        .formLogin(formLogin -> formLogin.loginPage("/login").failureUrl("/login?error=true"))
        .logout(LogoutConfigurer::permitAll);
    return http.build();
  }

  // @Bean
  // UserDetailsService userDetailsService() {
  // var manager = new InMemoryUserDetailsManager();
  // manager.createUser(
  // User.withDefaultPasswordEncoder()
  // .username("admin")
  // .password("password")
  // .roles("ADMIN")
  // .build());
  // return manager;
  // }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
