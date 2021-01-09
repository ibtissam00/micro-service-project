package org.sid.securityservice.sec;

import org.sid.securityservice.sec.filters.JwtAuthorizationFilter;
import org.sid.securityservice.sec.filters.JwtAuthticationFilter;
import org.sid.securityservice.sec.sec.service.AccountService;
import org.sid.securityservice.sec.sec.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailServiceImpl userDetailsService;



    public SecurityConfig(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

     /*   auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser appUser=accountService.loadUserByUsername(username);
                Collection<GrantedAuthority> authorities=new ArrayList<>();
                appUser.getAppRoles().forEach(
                        r->{
                            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                        });
                return new User(appUser.getUsername(),appUser.getPassword(),authorities); } });*/ }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthticationFilter(authenticationManager()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); }
}
