package com.example.producingwebservice.repository;

import io.spring.guides.gs_producing_web_service.Address;
import io.spring.guides.gs_producing_web_service.Role;
import io.spring.guides.gs_producing_web_service.Street;
import io.spring.guides.gs_producing_web_service.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {
    private  Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
    }

    @PostConstruct
    public void initData() {
        User user = new User();
        user.setId(1);
        user.setEmail("janedoe@gmail.com");
        user.setFullName("Jane Doe");
        user.setPassword("password123");
        user.setSecretKey("secret_key");

        Street street = new Street();
        street.setId(3);
        street.setStreetName("Burbone Street");
        street.setStreetNumber(15);

        Address address1 = new Address();
        address1.setId(2);
        address1.setApartment("52A");
        address1.setStreet(street);
        address1.setZipCode(100200);

        Address address2 = new Address();
        address2.setId(5);
        address2.setApartment("52A");
        address2.setStreet(street);
        address2.setZipCode(100300);

        user.getAddress().add(address1);
        user.getAddress().add(address2);

        User.RoleMap roleMap = new User.RoleMap();

        User.RoleMap.Entry roleEntry1 = new User.RoleMap.Entry();
        Role role1 = new Role();
        role1.setId(10);
        role1.setRoleName("ADMIN");
        role1.setSecretValue("Admin secret value");
        roleEntry1.setKey("admin");
        roleEntry1.setValue(role1);

        User.RoleMap.Entry roleEntry2 = new User.RoleMap.Entry();
        Role role2 = new Role();
        role2.setId(11);
        role2.setRoleName("USER");
        role2.setSecretValue("User secret value");
        roleEntry2.setKey("user");
        roleEntry2.setValue(role2);

        roleMap.getEntry().add(roleEntry1);
        roleMap.getEntry().add(roleEntry2);


        user.getRoleMap().add(roleMap);

        users.put(user.getEmail(), user);

    }

    public User findByEmail(String email) {
        Assert.notNull(email, "Email can't be null");
        return users.get(email);
    }
}
