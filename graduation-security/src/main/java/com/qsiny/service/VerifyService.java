package com.qsiny.service;

public interface VerifyService {

    Boolean verifyPhoneCode(String phonenumber,String code);

    Boolean createChecksum(String phonenumber);
}
