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
public class ControllerT {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@GetMapping("/")
	public String getIndex() {
		return "Index";
	}
	
	@GetMapping("/login")
	public String signIn() {
		return "Login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "Register";
	}
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam("username") String username ,//
			@RequestParam("password") String password , ModelAndView mv) {
		
		UserInfo user = userRepository.findByUsername(username);
		mv.addObject("username", username);
		
		
		List<Blog> blogs  = blogRepository.findAllByUsername(username); 
		mv.addObject("blogs",blogs);
		
		
		if(user != null && username.equals(user.getUsername()) && password.equals(user.getPassword())) {
			mv.setViewName("Blog");
		}else {
			mv.setViewName("fail");
		}
		return mv;
	}
	
	@PostMapping("/edit")
	public ModelAndView editView(ModelAndView mv, @RequestParam("username")String username, @RequestParam("blogtitle")String blogtitle) {
		
		Blog blog = blogRepository.findByBlogtitle(blogtitle);
		
		mv.addObject("blogid",blog.getId());
		mv.addObject("blogtitle", blog.getBlogtitle());
		mv.addObject("blogsummary", blog.getBlogsummary());
		mv.addObject("blogcontent", blog.getBlogcontent());
		mv.addObject("username", username);
		mv.setViewName("Update");
		return mv;
	}
	
	@PostMapping("update")
	public ModelAndView edit(@RequestParam("blogtitle")String blogtitle, //
			@RequestParam("blogsummary")String blogsummary, //
			@RequestParam("blogcontent")String blogcontent, //
			@RequestParam("username")String username, //
			@RequestParam("id")Long id , ModelAndView mv) {
		
		Blog blog =  blogRepository.findById(id).get();
		blog.setBlogtitle(blogtitle);
		blog.setBlogsummary(blogsummary);
		blog.setBlogcontent(blogcontent);
		blogRepository.save(blog);
		
		List<Blog> blogs  = blogRepository.findAllByUsername(username);
		mv.addObject("blogs",blogs);
		
		mv.addObject("username", username);
		mv.setViewName("Blog");
		return mv;
	}
}
