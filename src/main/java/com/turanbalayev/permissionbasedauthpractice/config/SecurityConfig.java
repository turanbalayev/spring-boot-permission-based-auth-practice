package com.turanbalayev.permissionbasedauthpractice.config;

import com.turanbalayev.permissionbasedauthpractice.repository.UserRepository;
import com.turanbalayev.permissionbasedauthpractice.repository.UserRoleRepository;
import com.turanbalayev.permissionbasedauthpractice.security.filter.CustomAuthenticationFilter;
import com.turanbalayev.permissionbasedauthpractice.security.filter.CustomAuthorizationFilter;
import com.turanbalayev.permissionbasedauthpractice.service.CustomUserDetailsService;
import com.turanbalayev.permissionbasedauthpractice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService(userRepository,userRoleRepository,roleService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/roles/**").hasAnyAuthority("role:write");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/v1/roles/**").hasAnyAuthority("role:write");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/v1/roles/**").hasAnyAuthority("role:write");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/roles/**").hasAnyAuthority("role:read");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic();

        http.csrf().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
