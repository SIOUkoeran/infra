package com.example.infra42.oauth2;

import com.example.infra42.entity.User;
import com.example.infra42.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        OAuth2AccessToken oAuth2AccessToken  = userRequest.getAccessToken();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuth2Attributes attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes(), oAuth2AccessToken);

//        this.userRepository.save(new User(attributes, "password"));

        User user = save(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(null,
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User save(OAuth2Attributes attributes){
        Optional<User> user = this.userRepository.findByName(attributes.getLogin());
        if (user.isPresent()){
            User updateUser = user.get().updateToken(attributes);
            return this.userRepository.save(updateUser);
        }else{
            return this.userRepository.save(attributes.toEntity(attributes));
        }
    }
}

