package com.hendisantika.addressbook.web;

import com.hendisantika.addressbook.domain.Address;
import com.hendisantika.addressbook.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * Time: 7:20 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/api")
public class AddressRestController {
	@Autowired
	private AddressRepository addressRepository;

	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public List<Address> address() {
		return addressRepository.findAll();
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public ResponseEntity<Address> addressById(@PathVariable Long id) {
		Optional<Address> address = addressRepository.findById(id);
		if (!address.isPresent()) {
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Address>(address.get(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Address> deleteAddress(@PathVariable Long id) {
		Optional<Address> address = addressRepository.findById(id);
		if (!address.isPresent()) {
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		} else {
			addressRepository.delete(address.get());
			return new ResponseEntity<Address>(address.get(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/address", method = RequestMethod.POST)
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		return new ResponseEntity<Address>(addressRepository.save(address), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/address", method = RequestMethod.PUT)
	public Address updateAddress(@RequestBody Address address) {
		return addressRepository.save(address);
	}

}
