package com.project.clinic.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.clinic.model.User;
import com.project.clinic.service.UserService;
import com.project.clinic.dto.UserDto;

@Controller
public class UserController {

 @Autowired
 private UserDetailsService userDetailsService;

 private UserService userService;

 public UserController(UserService userService) {
  this.userService = userService;
 }

 @GetMapping("/home")
 public String home(Model model, Principal principal) {
  UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
  model.addAttribute("userdetail", userDetails);
  return "home";
 }

 @GetMapping("/login")
 public String login(Model model, UserDto userDto) {

  model.addAttribute("user", userDto);
  return "login";
 }

 @GetMapping("/register")
 public String register(Model model, UserDto userDto) {
  model.addAttribute("user", userDto);
  return "register";
 }

 @PostMapping("/register")
public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
    try {
        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            model.addAttribute("Userexist", "Email already registered!");
            return "register"; // Stay on the register page and show an error
        }
    } catch (RuntimeException e) {
        // User not found, meaning it's a new user, so continue with registration
    }

    userService.save(userDto);
    return "redirect:/register?success";
}
}