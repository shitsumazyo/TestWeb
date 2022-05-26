package com.example.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.test.model.Blog;
import com.example.test.model.UserInfo;
import com.example.test.repository.BlogRepository;
import com.example.test.repository.UserRepository;


@Controller
public class ControllerTI {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@PostMapping("/register")
	public ModelAndView register(@RequestParam("username") String username, //
			@RequestParam("password") String password, //
			@RequestParam("repeat_password") String repeatpassword, //
			ModelAndView mv) {
		if (!password.equals(repeatpassword)) {
			mv.setViewName("fail");
			mv.addObject("message", "Passwords do not match");
		} else if(username.equals("")) {
			mv.setViewName("fail");
			mv.addObject("message", "username entered is not in a valid format");
		} else {
//			ArrayList<Blog> blogs  = new ArrayList<>(); 
//			UserInfo userinfo = UserInfo.builder().username(username).password(password).blogInfo(blogs).build();
			List<UserInfo> user =  userRepository.findAll();
			for(UserInfo i : user) {
				if(i.getUsername().equals(username)) {
					mv.setViewName("fail");
					mv.addObject("message", "username already exists");
					return mv;
				}
			}
			UserInfo userinfo = UserInfo.builder().username(username).password(password).build();
			userRepository.save(userinfo);
			mv.setViewName("Login");
		}
		mv.addObject("username", username);

		return mv;
	}
	
	@GetMapping("/add")
	public ModelAndView addView(ModelAndView mv, @RequestParam("username")String username) {
		mv.setViewName("PostBlog");
		mv.addObject("username", username);
		return mv;
	}
	
	@PostMapping("/add")
	public ModelAndView add(@RequestParam("blogtitle")String blogtitle, //
			@RequestParam("blogsummary")String blogsummary, //
			@RequestParam("blogcontent")String blogcontent, //
			@RequestParam("username")String username, ModelAndView mv) {
		Blog blog = Blog.builder().blogtitle(blogtitle).blogsummary(blogsummary).blogcontent(blogcontent).username(username).build();
		blogRepository.save(blog);
		
//		UserInfo user = userRepository.findByUsername(username);
//		user.getBlogInfo().add(blog);
//		userRepository.save(user);
		
		List<Blog> blogs  = blogRepository.findAllByUsername(username);
		mv.addObject("blogs",blogs);

		mv.addObject("blogtitle", blogtitle);
		mv.addObject("username", username);
		
		mv.setViewName("Blog");
		
		return mv;
	}
	
	@PostMapping("delete")
	public ModelAndView del(ModelAndView mv, @RequestParam("username")String username, @RequestParam("blogtitle")String blogtitle) {
		
		blogRepository.deleteByBlogtitle(blogtitle);
		
//		UserInfo user = userRepository.findByUsername(username);
//		ArrayList<Blog> blogs  = user.getBlogInfo(); 
//		for(int i= 0; i < blogs.size(); i++) {
//			if(blogs.get(i).getBlogtitle() == blogtitle){
//				blogs.remove(i);
//			}
//		}
//		user.setBlogInfo(blogs);
//		userRepository.save(user);
		
		List<Blog> blogs  = blogRepository.findAllByUsername(username);
		
		mv.addObject("blogs",blogs);
		
		mv.addObject("username", username);
		mv.setViewName("Blog");
		
		return mv;
	}
	
}
