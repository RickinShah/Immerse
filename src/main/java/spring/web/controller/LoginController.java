package spring.web.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import spring.model.BooleanAndMessage;
import spring.model.User;
import spring.model.dao.GenericDao;
import spring.web.Utility;

@RestController
public class LoginController {
	@Autowired
	GenericDao dao;

	@PostMapping("/login")
	public ResponseEntity<BooleanAndMessage> login(@ModelAttribute("user") User reqUser, HttpServletRequest request) throws IOException {
		User user = dao.getUserByEmail(reqUser.getEmail());

		if(user == null) {
			user = dao.getUserByMobile(reqUser.getEmail());
			if(user == null)
				return ResponseEntity.ok(new BooleanAndMessage(false, "User Not Found!"));
		}

		boolean isPassword = new Utility().checkPass(reqUser.getPassword(), user.getPassword());

		if(!isPassword) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Incorrect Password!"));
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("id", user.getId());
		if(user.getPlan() == null) {
			return ResponseEntity.ok(new BooleanAndMessage(true, "Go Plan!"));
		}

		return ResponseEntity.ok(new BooleanAndMessage(true, "Login Successfully!"));
	}

	
}
