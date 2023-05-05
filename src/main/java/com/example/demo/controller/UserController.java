package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.SignUpForm;
import com.example.demo.binding.UnlockForm;
import com.example.demo.service.EnquiryServiceImpl;
import com.example.demo.service.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private HttpSession httpSession;
	@Autowired
	private EnquiryServiceImpl enquiryServiceImpl;

	@GetMapping("/signuppage")
	public String getSignUpPage(Model model) {
		SignUpForm signUpForm = new SignUpForm();
		model.addAttribute("signUpForm", signUpForm);
		return "signup";
	}

	@PostMapping("/saveData")
	public String saveSignUpPage(@ModelAttribute("signUpForm") SignUpForm signUpForm, Model model) {
		System.out.println("+====================================" + signUpForm);
		boolean b = userServiceImpl.saveSignUpData(signUpForm);
		if (b) {
			model.addAttribute("status", "Email has been sent to your Id");
		}

		SignUpForm signUpForm1 = new SignUpForm();
		model.addAttribute("signUpForm", signUpForm1);

		return "signup";
	}

	@GetMapping("/unlock")
	public String saveSignUpPage(@RequestParam String email, Model model) {

		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlockForm", unlockForm);

		return "unlock";
	}

	@PostMapping("/saveUnlockForm")
	public String saveUnlockForm(@ModelAttribute UnlockForm unlockForm) {
		System.out.println(unlockForm);

		System.out.println(userServiceImpl.unlockUser(unlockForm));
		return "unlock";
	}

	@GetMapping("/login")
	public String getLoginForm(Model model) {

		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "login";
	}

	@PostMapping("/saveLoginData")
	public String saveUnlockForm(@ModelAttribute LoginForm loginForm) {
		System.out.println(userServiceImpl.verifyLogin(loginForm));

		return "redirect:/dashboard";
	}

	@GetMapping("/forgetPassword")
	public String getForgetPassword() {

		return "forgotPwd";
	}

	@PostMapping("/sentForgetPassword")
	public String sentForgetPassword(@RequestParam String email) {
		System.out.println(email);
		userServiceImpl.forgetPassword(email);
		return "forgotPassword";

	}

}
