package academy.devdojo.springboot2essentials.config;

import academy.devdojo.springboot2essentials.service.MyUserDetaisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetaisService myUserDetaisService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
                // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", password.encode("academy"));
//        auth.inMemoryAuthentication()
//                .withUser("micaias")
//                .password(password.encode("maio"))
//                .roles("USER", "ADMIN")
//                .and()
//                .withUser("generic")
//                .password(password.encode("abril"))
//                .roles("USER");
        auth.userDetailsService(myUserDetaisService).passwordEncoder(password);
    }
}
