package com.hadday.gestion_commission.controller;

import com.hadday.gestion_commission.Service.UserService;
import com.hadday.gestion_commission.entities.UserApp;
import com.hadday.gestion_commission.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            UserApp user = userService.getUserByUsername(currentUserName);
            model.addAttribute("compteIsActivated", user.isActivate());

            System.out.println(user);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
