package com.codelad.authservice.utils;

public enum VerificationStatus {
    not_verified((short) 0), verified((short) 1);

    private final short value;

    VerificationStatus(short value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
