package ml.socshared.service.textanalyze.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static ml.socshared.service.textanalyze.security.config.Constants.TEST_PROFILE;


@Configuration
@EnableWebSecurity
@Profile({TEST_PROFILE})
@Slf4j
public class SecurityConfigLocal extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Run LOCAL/TEST Security Configuration");
        http.
                csrf().disable()
                .authorizeRequests().anyRequest().permitAll();
    }

}
