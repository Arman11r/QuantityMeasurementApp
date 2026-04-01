package com.app.quantitymeasurement.config;
import java.util.List;
import com.app.quantitymeasurement.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Both origins in ONE list — two setAllowedOrigins calls overwrites the first
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://quantity-measurement-app-frontend-fawn.vercel.app"
        ));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/quantities/history/**").authenticated()
                        .requestMatchers("/api/v1/quantities/**").permitAll()
                        .requestMatchers(
                                "/",
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/oauth2/**",
                                "/login/oauth2/code/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/auth/oauth-success", true)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}