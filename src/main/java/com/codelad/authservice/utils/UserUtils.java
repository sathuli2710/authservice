package com.codelad.authservice.utils;

import com.codelad.authservice.dtos.UserDto;
import com.codelad.authservice.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    public UserEntity userDtoToUserEntity(UserDto userDto){
        return UserEntity.builder().username(userDto.getUsername()).email(userDto.getEmail()).password(userDto.getPassword()).firstName(userDto.getFirstName()).middleName(userDto.getMiddleName()).lastName(userDto.getLastName()).phoneNumber(userDto.getPhoneNumber()).verificationStatus(userDto.getVerificationStatus()).build();
    }

    public UserDto userEntityToUserDto(UserEntity userEntity){
        return UserDto.builder().uid(userEntity.getUid()).username(userEntity.getUsername()).email(userEntity.getEmail()).password(userEntity.getPassword()).firstName(userEntity.getFirstName()).middleName(userEntity.getMiddleName()).lastName(userEntity.getLastName()).phoneNumber(userEntity.getPhoneNumber()).verificationStatus(userEntity.getVerificationStatus()).build();
    }
}
