package com.example.springjspdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class LoginController {
	
	private User admin = new User("admin", "root");
	private ArrayList<User> users = new ArrayList<User>();
	
	@RequestMapping(value ="/login", method= RequestMethod.GET)
	public String loginPage(ModelMap model, @RequestParam(value = "errorMsg", required = false) String error) {
		if (error == null) {
			return "login";
		}
		model.put("errorMsg", error);
		return "login";
	}
	
	@RequestMapping(value ="/login", method= RequestMethod.POST)
	public RedirectView welcomePage(ModelMap model, @RequestParam String username, @RequestParam String password) {
		for(int i =0; i < users.size(); i++) {
			if (users.get(i).name.equals(username) && users.get(i).password.equals(password)) {
				RedirectView rv = new RedirectView("/welcome", true);
				rv.addStaticAttribute("username", username);
				return rv;
			}
		}
		
		RedirectView rv = new RedirectView("/login", true);
		rv.addStaticAttribute("errorMsg", "Incorrect username or password");
		return rv;
	}
	
	@RequestMapping(value ="/signup", method= RequestMethod.POST)
	public RedirectView signupPage(ModelMap model, @RequestParam String username, @RequestParam String password) {
		for(int i =0; i < users.size(); i++) {
			if (users.get(i).name.equals(username)) {
				RedirectView rv = new RedirectView("/login", true);
				rv.addStaticAttribute("errorMsg", "Username is taken");
				//model.put("errorMsg", "Username is taken");
				return rv;
			}
		}
		User newUser = new User(username, password);
		users.add(newUser);		
		
		//model.put("username", username);
		RedirectView rv = new RedirectView("/welcome", true);
		rv.addStaticAttribute("username", username);
		return rv;
	}
	
	@RequestMapping(value ="/welcome", method= RequestMethod.GET)
	public String mainPage(ModelMap model, @RequestParam("username") String username) {
		model.put("username", username);
		return "welcome";
	}
}
