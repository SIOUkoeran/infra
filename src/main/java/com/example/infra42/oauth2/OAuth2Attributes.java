package com.example.infra42.oauth2;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter  @Setter
public class OAuth2Attributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String login;
    private boolean isStaff;
    private String poolYear;
    private String poolMonth;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes,
                            String nameAttributeKey,
                            String login,
                            boolean isStaff, String poolYear,
                            String poolMonth){
        this.login = login;
        this.nameAttributeKey = nameAttributeKey;
        this.attributes = attributes;
        this.isStaff = isStaff;
        this.poolYear = poolYear;
        this.poolMonth = poolMonth;
    }

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes){
        return OAuth2Attributes.builder()
                .login((String) attributes.get("login"))
                .isStaff((boolean) attributes.get("staff?"))
                .poolYear(String.valueOf(attributes.get("pool_year")))
                .poolMonth(String.valueOf(attributes.get("pool_month")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }
}
