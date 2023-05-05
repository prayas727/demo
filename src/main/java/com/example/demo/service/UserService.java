package com.example.demo.service;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.SignUpForm;
import com.example.demo.binding.UnlockForm;

public interface UserService {
	
	public Boolean saveSignUpData(SignUpForm signUpform);
	public Boolean verifyLogin(LoginForm loginForm);
	public Boolean unlockUser(UnlockForm unlockForm);
	public boolean forgetPassword(String email);
	

}
