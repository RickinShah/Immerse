package spring.web.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import spring.model.OtpModel;
import spring.model.User;
import spring.model.dao.GenericDao;

@Controller
@RequestMapping("/account/forgotpassword")
public class ForgotPasswordMapping {
	@Autowired
	GenericDao dao;

	@GetMapping("/details")
	public String getForgotPassword(Model model, HttpSession session) {
		User user = new User();
		if(session != null && session.getAttribute("id") != null) {
			user = dao.get(user, ((Number)session.getAttribute("id")).intValue());
		}
		model.addAttribute("user", user);
		return "ForgotPassword/forgotpassword";
	}
	
	@GetMapping("/validateotp")
	public String getValidateOtp(Model model) {
		model.addAttribute("otpModel", new OtpModel());
		return "ForgotPassword/validateotp";
	}
	
	@GetMapping("/changepassword")
	public String getChangePassword(Model model) {
		model.addAttribute("request", new User());
		return "ForgotPassword/changepassword";
	}

}
