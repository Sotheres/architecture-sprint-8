package ru.yandex.practicum.configuration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${REPORTS_ROLE}")
    private String reportsRole;

    @Bean
    public JwtAuthenticationConverter jac() {
        JwtAuthenticationConverter jac = new JwtAuthenticationConverter();
        jac.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess =
                    (Map<String, Object>) jwt.getClaims().getOrDefault("realm_access", Map.<String, Object>of());
            Collection<String> roles = (Collection<String>) realmAccess.getOrDefault("roles", List.<String>of());
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        jac.setPrincipalClaimName("preferred_username");
        return jac;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jac()))
                ).authorizeHttpRequests(auth -> auth
                        .requestMatchers("/reports").hasAuthority(reportsRole)
                        .anyRequest().authenticated()
                ).cors(Customizer.withDefaults());
        return http.build();
    }
}
