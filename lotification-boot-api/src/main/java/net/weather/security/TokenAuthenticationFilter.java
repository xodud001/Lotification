package net.weather.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    protected TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher("/login/kakao"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isToken(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }



        return null;
    }

    private boolean isToken(HttpServletRequest request) {
        String token = request.getHeader("Authentication");



        return token != null && token.startsWith("Bearer ");
    }


}
