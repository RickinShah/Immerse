package spring.web.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import spring.model.NetflixModel;
import spring.model.User;

@Controller
public class Mapping {
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	@GetMapping("/video/{id}")
	public String getVideo(@PathVariable("id") String id ,Model model) {
		model.addAttribute("video", "http://localhost/media/5ba32b5f-72fa-458e-929c-ac9279871704/480p/5ba32b5f-72fa-458e-929c-ac9279871704.m3u8");
		model.addAttribute("id", id);
		NetflixModel netflix = new NetflixModel();
		netflix.setShow_id(id);
		model.addAttribute("netflix", netflix);
		return "video";
	}

	@GetMapping("/account/create")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "createaccount";
	}
	
	@GetMapping("/signout")
	public String getSignOut(HttpServletRequest request) {
		if(request.getSession(false) == null)
			return "redirect: /Immerse/";
		
		request.getSession().invalidate();
		return "redirect: /Immerse/";
	}
	
	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@GetMapping("/videologic")
	public String getLogic() {
		return "videodetails";
	}
	
	@GetMapping("/example")
	public String  getExample() {
		return "Dashboard/Example";
	}
	@GetMapping("/tryjs")
	public String tryThymeleafJs(Model model) {
		model.addAttribute("message", "Trying out Thymeleaf in JS");
		return "example";
	}

}
