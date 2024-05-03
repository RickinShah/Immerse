package spring.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import spring.model.BooleanAndMessage;
import spring.model.Plan;
import spring.model.Role;
import spring.model.User;
import spring.model.dao.GenericDao;
import spring.web.Utility;

@RestController
public class Email {
	@Autowired
	GenericDao dao;

	@PostMapping("/register")
	public ResponseEntity<String> getRegister(@RequestParam("email1") String email, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("email", email);

		return ResponseEntity.ok("success");
	}
	
	@PostMapping("/accountsign")
	public ResponseEntity<BooleanAndMessage> signUp(@ModelAttribute("user") User reqUser, HttpServletRequest request) {
		

		reqUser.setPassword(new Utility().encryptPassword(reqUser.getPassword()));
		reqUser.setStatus(1);
		reqUser.setRole(Role.USER);
		User user = dao.getUserByEmail(reqUser.getEmail());
		if(user != null)
			return ResponseEntity.ok(new BooleanAndMessage(false, "Email Already exists!"));

		user = dao.getUserByMobile(reqUser.getMobile_no());

		if(user != null)
			return ResponseEntity.ok(new BooleanAndMessage(false, "Phone Number Already exists!"));
		dao.insert(reqUser);
		user = dao.getUserByEmail(reqUser.getEmail());

		HttpSession session = request.getSession();
		if(session.getAttribute("email") != null)
			session.removeAttribute("email");
		session.setAttribute("id", user.getId());
		System.out.println(user.getPlan());

		BooleanAndMessage booleanAndMessage = new BooleanAndMessage(true, "Login Successful");

		return ResponseEntity.ok(booleanAndMessage);
	}
	
	@PostMapping("/plan")
	public ResponseEntity<BooleanAndMessage> insertPlan(@RequestParam("pricing") String pricing, HttpServletRequest request) {

		User user = new User();
		user.setId(((Number)request.getSession(false).getAttribute("id")).intValue());
		user = dao.get(user, user.getId());
		
		user.setPlan(Plan.valueOf(pricing));
		
		dao.update(user);
		return ResponseEntity.ok(new BooleanAndMessage(true, "Plan Added Successfully"));
	}
	
}
