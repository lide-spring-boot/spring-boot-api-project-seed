package com.conpany.project.encode;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import testBase.Tester;

/**
 * @author Zoctan
 * @date 2018/05/27
 */

public class PasswordEncryptor extends Tester {


    @Test
    public void encode() throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        final String admin = passwordEncoder.encode("admin");
        final String user = passwordEncoder.encode("user");
        System.err.println("admin password = " + admin);
        System.err.println("user password = " + user);
    }
}