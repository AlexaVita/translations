package com.studentProject.configs;

import com.studentProject.entities.Role;
import com.studentProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home", "/registration", "/chapters", "/chapter/*").permitAll()
                .antMatchers(HttpMethod.POST, "'/chapter/'+${chapter.id}+'/deleteComment'")
                .hasAuthority(Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.POST, "/chapter/{id}/addComment")
                .hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.GET, "/chapter/edit/*").hasAnyAuthority(Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.POST, "/chapter/edit/*").hasAnyAuthority(Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.POST, "chapter/delete/*").hasAnyAuthority(Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.GET, "/makeChapter").hasAnyAuthority(Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.POST, "/makeChapter").hasAnyAuthority(Role.ADMIN.getAuthority())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/chapters")
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
