package com.commerce.controller;

import com.commerce.service.BackofficeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private BackofficeUserDetailService backofficeUserDetailService;

    @GetMapping({"/backoffice/home", "/backoffice"})
    public String renderHomePage(Model model){
        return "home";
    }

    @GetMapping("/access_denied")
    public String renderForbiddenAccessPage(){ return "access_denied"; }

    @GetMapping({"/", "/frontoffice"})
    public String renderShopPage(Model model){
        return "frontoffice_home";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}