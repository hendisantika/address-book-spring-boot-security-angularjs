package com.hendisantika.addressbook.web;

import com.hendisantika.addressbook.domain.AppUser;
import com.hendisantika.addressbook.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by IntelliJ IDEA.
 * Project : address-book-spring-boot-security-angularjs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 9/30/17
 * Time: 7:29 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class HomeRestController {
	@Autowired
	private AppUserRepository appUserRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		appUser.setRole("USER");
		return new ResponseEntity<AppUser>(appUserRepository.save(appUser), HttpStatus.CREATED);
	}

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
}
