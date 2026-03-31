package com.app.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://quantity-measurement-app.s3-website.ap-south-1.amazonaws.com"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://quantity-measurement-app.s3-website.ap-south-1.amazonaws.com"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
        urlSource.registerCorsConfiguration("/**", config);
        return urlSource;
    }

    @Bean
    @Order(-2)
    public WebFilter preflightCorsFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                var headers = exchange.getResponse().getHeaders();
                headers.set("Access-Control-Allow-Origin",
                        exchange.getRequest().getHeaders().getOrigin());
                headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                headers.set("Access-Control-Allow-Headers", "*");
                headers.set("Access-Control-Allow-Credentials", "true");
                headers.set("Access-Control-Max-Age", "3600");
                exchange.getResponse().setStatusCode(HttpStatus.OK);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }
}