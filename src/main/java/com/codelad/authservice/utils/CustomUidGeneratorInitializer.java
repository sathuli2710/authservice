package com.codelad.authservice.utils;

import com.codelad.authservice.repositoires.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomUidGeneratorInitializer {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        CustomUidSequenceGenerator.setUserRepository(userRepository);
    }
}
