package com.hendisantika.addressbook.repository;

import com.hendisantika.addressbook.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : address-book-spring-boot-security-angularjs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 9/30/17
 * Time: 7:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

}
