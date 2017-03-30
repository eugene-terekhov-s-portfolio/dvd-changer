package ru.nergal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("admin").password("12345").roles("USER");

        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select login as username, password, enabled from dvd.c_users where login = ?")
            .authoritiesByUsernameQuery("select u.login as username, ur.user_role as authority from dvd.c_users u join dvd.m_user_roles ur on u.user_id=ur.user_user_id and u.login = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home", "/register").permitAll()
                .antMatchers("/console/**", "/console").permitAll()
                .anyRequest().authenticated().and()
            .formLogin()
                .loginPage("/login").permitAll().successForwardUrl("/collection")
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
        http.exceptionHandling().accessDeniedPage("/403");
    }
}
