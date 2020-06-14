package ml.socshared.service.textanalyze.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.textanalyze.security.jwt.JwtAuthenticationEntryPoint;
import ml.socshared.service.textanalyze.security.jwt.JwtConfigurer;
import ml.socshared.service.textanalyze.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static ml.socshared.service.textanalyze.configuration.Constants.*;

@Configuration
@EnableWebSecurity
@Profile({DEV_PROFILE, PROD_PROFILE, LOCAL_PROFILE})
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PRIVATE_ENDPOINT = "/api/v1/private/**";

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Run DEV/PROD Security Configuration");
        http.
                httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PRIVATE_ENDPOINT).authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }
}
