package com.synergisticit.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synergisticit.domain.User;
import com.synergisticit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping(value = "/login")
    public String login(@RequestParam(required = false) String logout, @RequestParam(required = false) String error,
                        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        String message = "";
        if (error != null) {
            message = "Invalid Credentials";
        }
        if (logout != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
            }
            message = "Logout";
            return "login";
        }
        model.addAttribute("Message", message);
        return "login";

    }

    @GetMapping(value = "/accessDeniedPage")
    public String accessDenied(Principal principal, Model model) {
        String message = principal.getName() + ", Unauthorised access";
        model.addAttribute("Message", message);
        return "accessDenied";

    }

    @GetMapping(value = "/signup")
    public String signup() {
        return "signup";
    }


    @GetMapping(value = "/userProfile")
    public String userProfile() {
        return "userProfile";

    }

    @GetMapping(value = "/user/{username}")
    @ResponseBody
    public String getUserByUsername(@PathVariable String username) {
        return userService.findByUserName(username).getEmail();
    }


//	@Bean
//	public BCryptPasswordEncoder bCryptpeasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
