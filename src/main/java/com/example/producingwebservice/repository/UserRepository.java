package com.example.producingwebservice.repository;

import io.spring.guides.gs_producing_web_service.Address;
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


        Address address = new Address();
        address.setId(2);
        address.setApartment("52A");
        address.setStreet(street);
        address.setZipCode(100200);

        user.setAddress(address);

        users.put(user.getEmail(), user);

    }

    public User findByEmail(String email) {
        Assert.notNull(email, "Email can't be null");
        return users.get(email);
    }
}
