package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/favourites")
public class FavouritesIndexController {

    private final UserService userService;

    public FavouritesIndexController(UserService userService) {
        this.userService = userService;
    }

    //Anzeige der Favorisierten Festivals und Bands
    @GetMapping
    public String index(Model model) {
        long userID = User.getCurrentUser().getId();
        model.addAttribute("festivals", userService.getFavouriteFestivals(userService.getUser(userID)));
        model.addAttribute("bands", userService.getFavouriteBands(userService.getUser(userID)));
        return "favourites/index";
    }
}

