package com.onchain.tademo;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author zhouq
 * @date 2017/11/23
 */
@Slf4j
@Component
public class Helper {

    /**
     * check param whether is null or ''
     *
     * @param params
     * @return boolean
     */
    public static Boolean isEmptyOrNull(Object... params) {
        if (params != null) {
            for (Object val : params) {
                if ("".equals(val) || val == null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static Boolean isNotEmptyAndNull(Object...params){
        return !isEmptyOrNull(params);
    }

    /**
     * get current method name
     *
     * @return
     */
    public static String currentMethod() {
        return new Exception("").getStackTrace()[1].getMethodName();
    }


    public static String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        log.info("header authorization:{}",authHeader);
        return authHeader;

    }

    public static String generateRequestId(){
        return UUID.randomUUID().toString();
    }


    public static String generateUnPassClaimId(){
        return "unpass"+UUID.randomUUID().toString().replace("-","");
    }



}
