package com.codelad.authservice.utils;

import com.codelad.authservice.repositoires.UserRepository;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomUidSequenceGenerator implements IdentifierGenerator {

    private static UserRepository userRepository;

    public static void setUserRepository(UserRepository userRepository){
        CustomUidSequenceGenerator.userRepository = userRepository;
    }
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formattedDateTimeStr = currentDateTime.format(formatter);
        int innerSequenceNextValue = userRepository.getInnerSequenceNextValue();
        System.out.println(innerSequenceNextValue);
        formattedDateTimeStr += innerSequenceNextValue;
        return Long.parseLong(formattedDateTimeStr);
    }
}
