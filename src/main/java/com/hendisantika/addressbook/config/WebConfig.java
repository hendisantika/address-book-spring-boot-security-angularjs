package com.hendisantika.addressbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by IntelliJ IDEA.
 * Project : address-book-spring-boot-security-angularjs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 9/30/17
 * Time: 7:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
@EnableWebSecurity
// Modifying or overriding the default spring boot security.
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AppUserDetailsService appUserDetailsService;

	// This method is for overriding the default AuthenticationManagerBuilder.
	// We can specify how the user details are kept in the application. It may
	// be in a database, LDAP or in memory.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(appUserDetailsService);
	}

	// This method is for overriding some configuration of the WebSecurity
	// If you want to ignore some request or request patterns then you can
	// specify that inside this method
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	// This method is used for override HttpSecurity of the web Application.
	// We can specify our authorization criteria inside this method.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// starts authorizing configurations
				.authorizeRequests()
				// ignoring the "/", "/index.html", "/app/**", "/register",
				// "/favicon.ico"
				.antMatchers("/", "/index.html", "/app/**", "/register", "/favicon.ico").permitAll()
				// authenticate all remaining URLS
				.anyRequest().fullyAuthenticated().and()
				// enabling the basic authentication
				.httpBasic().and()
				// configuring the session as state less. Which means there is
				// no session in the server
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// disabling the CSRF - Cross Site Request Forgery
				.csrf().disable();
	}

}
