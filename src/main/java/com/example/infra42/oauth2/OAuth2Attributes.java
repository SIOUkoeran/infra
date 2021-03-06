package com.example.infra42.oauth2;


import com.example.infra42.entity.User;
import com.example.infra42.repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import java.util.Map;

@Getter  @Setter
@RequiredArgsConstructor
public class OAuth2Attributes {

    private UserRepository userRepository;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String login;
    private boolean isStaff;
    private String poolYear;
    private String poolMonth;
    private OAuth2AccessToken oAuth2AccessToken;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes,
                            String nameAttributeKey,
                            String login,
                            boolean isStaff, String poolYear,
                            String poolMonth,
                            OAuth2AccessToken oAuth2AccessToken){
        this.login = login;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
        this.isStaff = isStaff;
        this.poolYear = poolYear;
        this.poolMonth = poolMonth;
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes, OAuth2AccessToken oAuth2AccessToken){
        return OAuth2Attributes.builder()
                .login((String) attributes.get("login"))
                .isStaff((boolean) attributes.get("staff?"))
                .poolYear(String.valueOf(attributes.get("pool_year")))
                .poolMonth(String.valueOf(attributes.get("pool_month")))
                .oAuth2AccessToken(oAuth2AccessToken)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public User toEntity(OAuth2Attributes oAuth2Attributes){
        return User.builder()
                .oAuth2Attributes(oAuth2Attributes)
                .build();
    }
}
