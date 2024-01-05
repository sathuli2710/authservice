package com.codelad.authservice.services.implementations;

import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import com.codelad.authservice.repositoires.UserRepository;
import com.codelad.authservice.services.UserService;
import com.codelad.authservice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserUtils userUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in DB"));
            }
        };
    }

    @Override
    public UserDetails getUserByUid(UUID uid) throws UsernameNotFoundException {
        return userRepository.findByUuid(uid).orElseThrow(() -> new UsernameNotFoundException("User not found in DB"));
    }

    @Override
    public UserEntity getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in DB"));
    }

    @Override
    public UserEntity createUser(UserDto userDto) throws Exception{
        try {
            System.out.println(userDto.getVerificationStatus());
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            UserEntity userEntity = userUtils.userDtoToUserEntity(userDto);
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new Exception("Could not create User in DB");
        }
    }
}
