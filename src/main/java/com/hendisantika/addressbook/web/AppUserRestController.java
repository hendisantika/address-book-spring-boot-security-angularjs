package com.hendisantika.addressbook.web;

import com.hendisantika.addressbook.domain.AppUser;
import com.hendisantika.addressbook.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
@RequestMapping(value = "/api")
public class AppUserRestController {
	@Autowired
	private AppUserRepository appUserRepository;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<AppUser> users() {
		return appUserRepository.findAll();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<AppUser> userById(@PathVariable Long id) {
		Optional<AppUser> appUser = appUserRepository.findById(id);
		if (!appUser.isPresent()) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<AppUser>(appUser.get(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteUser(@PathVariable Long id) {
		Optional<AppUser> appUser = appUserRepository.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		if (!appUser.isPresent()) {
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		} else if (appUser.get().getUsername().equalsIgnoreCase(loggedUsername)) {
			throw new RuntimeException("You cannot delete your account");
		} else {
			appUserRepository.delete(appUser.get());
			return new ResponseEntity<AppUser>(appUser.get(), HttpStatus.OK);
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		return new ResponseEntity<AppUser>(appUserRepository.save(appUser), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public AppUser updateUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findOneByUsername(appUser.getUsername()) != null
				&& appUserRepository.findOneByUsername(appUser.getUsername()).getId() != appUser.getId()) {
			throw new RuntimeException("Username already exist");
		}
		return appUserRepository.save(appUser);
	}

}
