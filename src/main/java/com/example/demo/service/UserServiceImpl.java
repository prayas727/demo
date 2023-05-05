package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.binding.LoginForm;
import com.example.demo.binding.SignUpForm;
import com.example.demo.binding.UnlockForm;
import com.example.demo.entity.UserDtlsEntity;
import com.example.demo.repo.UserDtlsRepo;
import com.example.demo.utility.EmailUtils;
import com.example.demo.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDtlsRepo usr;

	@Autowired
	private PwdUtils pwdUtils;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession httpSession;

	@Override
	public Boolean saveSignUpData(SignUpForm signUpform) {
		if (usr.findByEmail(signUpform.getEmail()) != null) {
			return false;
		}
		UserDtlsEntity userDtlsEntity = new UserDtlsEntity();
		BeanUtils.copyProperties(signUpform, userDtlsEntity);
		String tempPwd = pwdUtils.generateRandomPwd();
		userDtlsEntity.setPwd(tempPwd);
		userDtlsEntity.setAccstatus("LOCKED");
		usr.save(userDtlsEntity);
		String to = signUpform.getEmail();
		String subject = "Unlock Your Accout | Ashok IT";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Please find the temporary password below</h1>");
		body.append("Temporary Password:" + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8090/unlock?email=" + to + "\">Click Here to unlock password</a>");
		Boolean c = emailUtils.sendEmail(to, subject, body.toString());
		System.out.println(c);
		return c;

	}

	@Override
	public Boolean unlockUser(UnlockForm unlockForm) {

		UserDtlsEntity userDtlsEntity = usr.findByEmail(unlockForm.getEmail());
		System.out.println(userDtlsEntity);
		System.out.println(userDtlsEntity.getPwd());
//		System.out.println(unlockForm.getTempPwd());
		if (userDtlsEntity.getPwd().equals(unlockForm.getTempPwd())) {
			userDtlsEntity.setPwd(unlockForm.getPwd());
			usr.save(userDtlsEntity);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean verifyLogin(LoginForm loginForm) {
		UserDtlsEntity userDtlsEntity = usr.findByEmail(loginForm.getEmail());
		if (userDtlsEntity.getPwd().equals(loginForm.getPwd())) {
			httpSession.setAttribute("userId",userDtlsEntity.getUserId());
			return true;
		} else {
			return false;
		}

	}
//	@Override
//	public Boolean forgetPassword(String email) {
//		UserDtlsEntity ude=usr.findByEmail(email);
//		if(ude==(null))
//		{
//			return false;
//		}
//		
//		String subject="<h1>Retrive your password</h1>";
//		String body="Password:-"+ude.getPwd();
//		emailUtils.sendEmail(email, subject, body);
//		return true;
//	}

	@Override
	public boolean forgetPassword(String email) {
		UserDtlsEntity ude = usr.findByEmail(email);
		if (ude == (null)) {
			return false;
		}
		String subject = "<h1>Retrive your password</h1>";
		String body = "Password:-" + ude.getPwd();
		emailUtils.sendEmail(email, subject, body);
		return true;

	}

}
