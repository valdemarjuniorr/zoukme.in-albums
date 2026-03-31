package in.zoukme.zouk_album.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  private static final int TOKEN_VALIDITY_SECONDS = 7 * 24 * 60 * 60; // 1 week

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/users/account/**").authenticated()
                .anyRequest()
                .permitAll())
        .formLogin(formLogin -> formLogin.loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
            .permitAll())
        .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(TOKEN_VALIDITY_SECONDS))
        .logout(logout -> logout.logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/").permitAll());
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
