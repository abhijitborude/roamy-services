package com.roamy.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Abhijit on 4/2/2016.
 */
public class BCryptTest {

    public static void main(String[] args) {
        String t = "";
        String encode = (new BCryptPasswordEncoder()).encode(t);
        System.out.println(encode);
    }
}
