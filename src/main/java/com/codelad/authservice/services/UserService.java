package com.codelad.authservice.services;

import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.repositoires.UserRepository;
import com.codelad.authservice.utils.UserUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserUtils userUtils;

    public UserEntity createUser(UserDto userDto) throws Exception{
        try {
            UserEntity userEntity = userUtils.userDtoToUserEntity(userDto);
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new Exception("Could not create User in DB");
        }
    }
}
