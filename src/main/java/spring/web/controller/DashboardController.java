package spring.web.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import spring.model.BooleanAndMessage;
import spring.model.NetflixModel;
import spring.model.User;
import spring.model.Wishlist;
import spring.model.dao.GenericDao;
import spring.web.Utility;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	@Autowired
	GenericDao dao;

	@SuppressWarnings("unchecked")
	@PostMapping("/addmovie")
	public ResponseEntity<Boolean> insertIntoWishlist(@RequestParam("show_id") String show_id,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = new User();
		NetflixModel netflix = new NetflixModel();

		user.setId(((Number)session.getAttribute("id")).intValue());
		netflix.setShow_id(show_id);
		
		Wishlist wishlist = new Wishlist();
		
		wishlist.setUser(user);
		wishlist.setNetflix(netflix);
		
		List<Wishlist> wishlists = dao.getById("Wishlist", "uid", user.getId());

		List<Wishlist> inWishlist = wishlists.stream()
				.filter(a -> Objects.equals(a.getNetflix().getShow_id(), netflix.getShow_id()))
				.collect(Collectors.toList());

		try {
			inWishlist.get(0);
		} catch(IndexOutOfBoundsException e) {
			dao.insert(wishlist);
		}
		System.out.println(wishlists.get(0).getNetflix().getTitle());

		return ResponseEntity.ok(true);
		
	}

	@PostMapping("/updateemail")
	public ResponseEntity<BooleanAndMessage> updateEmail(@ModelAttribute("request") User request, HttpSession session) {
		
		User user = dao.getUserByEmail(request.getEmail());
		if(user != null) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Email already exists!"));
		}
		user = dao.get(request, ((Number)session.getAttribute("id")).intValue());
		
		boolean isPassword = new Utility().checkPass(request.getPassword(), user.getPassword());
		if(!isPassword) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Incorrect Password!"));
		}
		user.setEmail(request.getEmail());
		dao.update(user);
		
		return ResponseEntity.ok(new BooleanAndMessage(true, "Email Changed Successfuly!"));
	}
	
	@PostMapping("/updatephone")
	public ResponseEntity<BooleanAndMessage> updatePhone(@ModelAttribute("request") User request, HttpSession session) {
		User user = dao.getUserByMobile(request.getMobile_no());
		if(user != null) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Mobile No. already exists!"));
		}
		user = dao.get(request, ((Number)session.getAttribute("id")).intValue());
		
		boolean isPassword = new Utility().checkPass(request.getPassword(), user.getPassword());
		if(!isPassword) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Incorrect Password!"));
		}
		user.setMobile_no(request.getMobile_no());
		dao.update(user);
		return ResponseEntity.ok(new BooleanAndMessage(true, "Phone No. Updated Successfully!"));
	}
	
	@PostMapping("/updatepassword")
	public ResponseEntity<BooleanAndMessage> updatePassword(@RequestParam("password") String password, @RequestParam("new_password") String newPassword, HttpSession session) {
		User user = dao.get(new User(), ((Number)session.getAttribute("id")).intValue());
		
		boolean isPassword = new Utility().checkPass(password, user.getPassword());
		if(!isPassword) {
			return ResponseEntity.ok(new BooleanAndMessage(false, "Wrong Password!"));
		}
		
		user.setPassword(new Utility().encryptPassword(newPassword));
		dao.update(user);
		
		return ResponseEntity.ok(new BooleanAndMessage(true, "Password Updated Successfully!"));
	}
	
}
