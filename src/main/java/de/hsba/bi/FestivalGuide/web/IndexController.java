package de.hsba.bi.FestivalGuide.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexController {

    //Verweist auf die Seite index.html
    @GetMapping
    public String index() {
        return "index";
    }

    //Verweist auf die Seite login.html, verhindert den aufruf der seite durch angemeldete User
    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof AnonymousAuthenticationToken ? "/login" : "redirect:/";
    }
}
