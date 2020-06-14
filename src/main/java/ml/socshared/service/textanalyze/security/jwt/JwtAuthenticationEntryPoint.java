package ml.socshared.service.textanalyze.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.textanalyze.handler.RestApiError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        RestApiError restApiError = new RestApiError();
        restApiError.setError(HttpStatus.UNAUTHORIZED);
        restApiError.setMessage("unauthorized");
        restApiError.setPath(request.getPathInfo());
        restApiError.setStatus(HttpStatus.UNAUTHORIZED.value());
        restApiError.setTimestamp(LocalDateTime.now());

        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(restApiError);

        response.getWriter().write(body);
        response.setHeader("Content-Type", "application/json");
        log.error(authException.getMessage());
    }
}
