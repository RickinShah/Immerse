package spring.web.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import spring.model.User;

@Controller
@RequestMapping("/signup")
public class RegisterMapping {
	
	@GetMapping("/registration")
	public String getRegistration() {
		return "Register/registration";
	}

	@GetMapping("/regform")
	public String getRegForm(Model model, HttpServletRequest request) throws ServletException {
		if(request.getSession(false) != null) {
			String email = (String)request.getSession(false).getAttribute("email");
			model.addAttribute("user", new User(email));
		}
		else
			model.addAttribute("user", new User());
		return "Register/regform";
	}
	
	@GetMapping("")
	public String getSignUp() {
		return "Register/signup";
	}
	
	@GetMapping("/planform")
	public String getPlanForm() {
		return "Register/planform";
	}

}
