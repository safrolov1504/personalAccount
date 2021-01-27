package ru.interview.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.interview.errors.ErrorUser;
import ru.interview.controllsers.MainController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        String message = e.getMessage();
        if(e instanceof BadCredentialsException){
            message = ErrorUser.NOT_CORRECT_PASSWORD.getDescription();
        }
        httpServletResponse.sendRedirect("/account/login?error="+java.net.URLEncoder.encode(message,java.nio.charset.StandardCharsets.UTF_8.toString()));
    }
}
