package com.codelad.authservice.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class StringToVerificationStatus extends PropertyEditorSupport {
    @Override
    public void setAsText(String stringInput) throws IllegalArgumentException {
        try {
            boolean isVerificationStatusFound = false;
            for(VerificationStatus status : VerificationStatus.values()) {
                if(Short.parseShort(stringInput) == status.getValue()){
                    isVerificationStatusFound = true;
                    setValue(status);
                    break;
                }
            }
            if(!isVerificationStatusFound) throw new Exception();
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid Short Input Format");
        }
    }
}
