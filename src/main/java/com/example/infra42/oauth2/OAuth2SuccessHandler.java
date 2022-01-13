package com.example.infra42.oauth2;

import com.example.infra42.entity.User;
import com.example.infra42.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpSession httpSession;
    private final static String isSigned = "isSigned";

    private final String redirectUrl = "http://localhost:3000/sign-up";

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        setSameSiteCookie(request, response, authentication);
        addIsSignedUser(request, response, authentication);
        httpSession.removeAttribute("user");
        httpSession.removeAttribute("JSESSIONID");
        deleteCookie(request, response, "JSESSIONID");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void addIsSignedUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        SessionUser sessionUser = (SessionUser) request.getSession(false).getAttribute("user");
        User user  = this.userRepository.findByName(sessionUser.getName()).get();
        boolean flag;
        if (user.getPassword() != null){
            flag = true;
        }else {
            flag = false;
        }
        Cookie cookie = new Cookie(isSigned, String.valueOf(flag));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(360);
        response.addCookie(cookie);
    }

//    private void setSameSiteCookie(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
//        boolean firstHeader = true;
//        for (String header : headers) {
//            if (firstHeader) {
//                response.setHeader(HttpHeaders.SET_COOKIE,
//                        String.format("%s; Secure; %s", header, "SameSite=None"));
//                firstHeader = false;
//                continue;
//            }
//            response.addHeader(HttpHeaders.SET_COOKIE,
//                    String.format("%s; Secure; %s", header, "SameSite=None"));
//        }
//    }
}