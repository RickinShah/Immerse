package spring.web.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import spring.model.NetflixModel;
import spring.model.User;
import spring.model.Wishlist;
import spring.model.dao.GenericDao;

@Controller
@RequestMapping("/dashboard")
public class DashboardMapping {
	@Autowired
	GenericDao dao;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/browse")
	public String getBrowse(Model model, HttpServletRequest request) {
		List<NetflixModel> netflix = dao.getAll("NetflixModel");
		model.addAttribute("netflix", netflix);
		List<Wishlist> wishlists = dao.getById("Wishlist", "uid", ((Number)request.getSession(false).getAttribute("id")).intValue());
		Map<String, List<NetflixModel>> movies = new HashMap<String, List<NetflixModel>>();
		
		for(NetflixModel net : netflix) {
			String[] genres = net.getListed_in().split(",");
			for(String genre : genres) {
				movies.computeIfAbsent(genre, k -> new ArrayList<>()).add(net);
			}
		}
		
		for(Wishlist wishlist : wishlists)  {
			System.out.println(wishlist.getNetflix().getTitle());
		}

		model.addAttribute("movies", movies);
		model.addAttribute("wishlists", wishlists);

		return "Dashboard/browse";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/mylist")
	public String getMyList(Model model, HttpServletRequest request) {
		List<Wishlist> wishlists = dao.getById("Wishlist", "uid", ((Number)request.getSession(false).getAttribute("id")).intValue());
		model.addAttribute("wishlists", wishlists);
		return "Dashboard/mylist";
	}

	@GetMapping("/movies")
	public String getMovies(Model model) {
		List<NetflixModel> movies = dao.getAll("NetflixModel");
		for(NetflixModel movie : movies)
			System.err.println(movie.getTitle());
		model.addAttribute("movies", movies);
		return "Dashboard/movies";
	}

	@GetMapping("/single/{vid}")
	public String getSingle(@PathVariable("vid") String vid, Model model) {
		NetflixModel netflix = dao.get(new NetflixModel(), vid);
		System.out.println(netflix.getShow_id());
		model.addAttribute("netflix", netflix);
		return "Dashboard/single";
	}
	
	@GetMapping("/settings")
	public String getSettings(Model model, HttpServletRequest request) {
		User user = dao.get(new User(), ((Number)request.getSession(false).getAttribute("id")).intValue());
		model.addAttribute("user", user);
		return "Dashboard/settings";
	}
	
	@GetMapping("/emailchange")
	public String getEmailChange(Model model) {
		model.addAttribute("request", new User());
		return "Dashboard/changeEmail";
	}

	@GetMapping("/passchange")
	public String getPassChange() {
		return "Dashboard/changePassword";
	}

	@GetMapping("/changephone")
	public String getChangePhone(Model model) {
		model.addAttribute("request", new User());
		return "Dashboard/ChangePhoneNumber";
	}
}
