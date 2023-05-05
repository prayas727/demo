package com.example.demo.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
@Component
public class PwdUtils {
	public String generateRandomPwd() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~?";
		String pwd = RandomStringUtils.random(15, characters);
		return pwd;
	}
}
