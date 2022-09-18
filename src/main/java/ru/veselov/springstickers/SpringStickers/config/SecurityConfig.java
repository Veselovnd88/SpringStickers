package ru.veselov.springstickers.SpringStickers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.veselov.springstickers.SpringStickers.services.PersonDetailsService;

@EnableWebSecurity
@Configuration
/*WebSecurityConfigurerAdapter помечен Deprecated, теперь нужно определить бины внутри этого класса*/
public class SecurityConfig{
    private final PersonDetailsService personDetailsService;

    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /*Бин отвечает за правила приема http реквестов*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/","/show","/download","/login").permitAll()
                .antMatchers("/admin","/admin/add","/admin/serials","/admin/manage").hasAnyRole("ADMIN")
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/process")
                .defaultSuccessUrl("/admin")
                .failureUrl("/login?error")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        return httpSecurity.build();
    }

    /*Бин отвечает настройку аутентификации*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(personDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    /*Бин отвечает за шифрование пароля по технологии bcrypt*/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
