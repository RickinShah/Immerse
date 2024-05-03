package spring.web.controller;

import java.io.IOException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import spring.model.BooleanAndMessage;
import spring.model.OtpModel;
import spring.model.User;
import spring.model.dao.GenericDao;
import spring.web.MailSender;
import spring.web.Utility;

@RestController
@RequestMapping("/account/forgotpassword")
public class ForgotPasswordController {
	@Autowired
	GenericDao dao;
	@Autowired
	MailSender mailSender;
	
	@PostMapping("/sendotp")
	public ResponseEntity<BooleanAndMessage> sendOtp(@ModelAttribute("user") User request, HttpSession session) throws IOException {
		User user = new User();
		if(session.getAttribute("id") != null)
			user = dao.get(user, ((Number)session.getAttribute("id")).intValue());
		else
			user = dao.getUserByEmail(request.getEmail());

		if(user == null) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Username not found!"));
		}
		int genOtp = new SecureRandom().nextInt(100001, 1000000);
		long created_at = System.currentTimeMillis();
		OtpModel otp;
		if(user.getOtp() == null)
			otp = new OtpModel(genOtp, created_at, user);
		 else 
			otp = new OtpModel(user.getOtp().getUser_id(),genOtp, created_at);
		
		user.setOtp(otp);
		System.out.println(user.getEmail());

		user = dao.update(user);
		final String msg = "<html><body>Hello, <br/><br/>" + user.getOtp().getOtp() + 
					" is your One-time passcode (OTP) for the Immerse</body></html>";
		final String subject = "Your One-Time Passcode from Immerse";
		final String to = user.getEmail();
		session.setAttribute("email", user.getEmail());

		mailSender.sendMail(to, subject, msg);
		return ResponseEntity.ok(new BooleanAndMessage(true, "Mail Sent to the registered email!"));
	}
	
	@PostMapping("/validate")
	public ResponseEntity<BooleanAndMessage> checkOtp(@ModelAttribute("otpModel") OtpModel userOtp, HttpSession session) throws IOException {

		String email = (String)session.getAttribute("email");
		System.out.println(email);
		User user = dao.getUserByEmail(email);

		OtpModel otp = dao.get(userOtp, user.getOtp().getUser_id());

		if(otp.getOtp() != userOtp.getOtp()) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Wrong OTP!"));
		}
		return ResponseEntity.ok(new BooleanAndMessage(true, "Correct OTP!"));
	}
	 
	@PostMapping("/updatepassword")
	public ResponseEntity<BooleanAndMessage> updatePassword(@ModelAttribute("request") User request, HttpSession session) throws IOException {
		User user = dao.getUserByEmail((String)session.getAttribute("email"));
		user.setPassword(new Utility().encryptPassword(request.getPassword()));
		user = dao.update(user);
		boolean isPassword = new Utility().checkPass(request.getPassword(), user.getPassword());
		if(!isPassword) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Something went wrong!"));
		}
		session.removeAttribute("email");
		if(session.getAttribute("id") != null)
			return ResponseEntity.ok(new BooleanAndMessage(true, "/dashboard/settings"));
		return ResponseEntity.ok(new BooleanAndMessage(true, "/login"));
		
	}
	
	
}
