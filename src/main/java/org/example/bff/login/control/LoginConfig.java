package org.example.bff.login.control;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.stereotype.Controller;

@Controller
public class LoginConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository repository) {
    // Authenticate through configured OpenID Provider
    return http.oauth2Login(Customizer.withDefaults())
        .logout(logout -> logout.logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(repository)))
        .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated())
        .headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.mode(Mode.SAMEORIGIN)))
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .build();
  }
}
