package org.example.bff.user.boundary;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserResource {

  @GetMapping("/user")
  public Map<String, Object> index(
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
      @AuthenticationPrincipal(errorOnInvalidType = true) OidcUser user) {
    Map<String, Object> model = new HashMap<>();

    model.put("clientName", authorizedClient.getClientRegistration().getClientName());
    model.put("userName", user.getName());
    model.put("userAttributes", user.getAttributes());
    return model;
  }
}
