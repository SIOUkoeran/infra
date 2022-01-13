package com.example.infra42.entity;

import com.example.infra42.entity.enums.UserRole;
import com.example.infra42.entity.enums.UserStatus;
import com.example.infra42.oauth2.OAuth2Attributes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @Size(min = 8)
    private String password;


    private UserStatus user;

    private String poolYear;
    private String poolMonth;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    private String tokenValue;
    private String issuedAt;
    private String expiresAt;
    private String tokenType;
    private String scopes;

    public String getRoleKey(){
        return role.getKey();
    }

    public User(OAuth2Attributes oAuth2Attributes, String password)
    {
        this.name = oAuth2Attributes.getLogin();
        this.tokenType = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenType());
        this.scopes = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getScopes());
        this.tokenValue = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenValue());
        this.issuedAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getIssuedAt());
        this.expiresAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getExpiresAt());
        this.poolMonth = oAuth2Attributes.getPoolMonth();
        this.poolYear = oAuth2Attributes.getPoolYear();
        this.password = password;

    }
    @Builder
    public User(OAuth2Attributes oAuth2Attributes)
    {
        this.name = oAuth2Attributes.getLogin();
        this.tokenType = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenType());
        this.scopes = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getScopes());
        this.tokenValue = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenValue());
        this.issuedAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getIssuedAt());
        this.expiresAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getExpiresAt());
        this.poolMonth = oAuth2Attributes.getPoolMonth();
        this.poolYear = oAuth2Attributes.getPoolYear();
        this.user = UserStatus.OAUTH;
    }

    public User updateToken(OAuth2Attributes oAuth2Attributes)
    {
        this.tokenType = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenType());
        this.scopes = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getScopes());
        this.tokenValue = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getTokenValue());
        this.issuedAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getIssuedAt());
        this.expiresAt = String.valueOf(oAuth2Attributes.getOAuth2AccessToken().getExpiresAt());
        return this;
    }

}
