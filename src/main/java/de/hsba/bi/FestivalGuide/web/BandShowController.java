package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserService;
import de.hsba.bi.FestivalGuide.web.form.BandForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/bands/{id}")
public class BandShowController {

    private final FormAssembler formAssembler;
    private final BandService bandService;
    private final FestivalService festivalService;
    private final UserService userService;

    public BandShowController(FormAssembler formAssembler, BandService bandService, FestivalService festivalService, UserService userService) {
        this.formAssembler = formAssembler;
        this.bandService = bandService;
        this.festivalService = festivalService;
        this.userService = userService;
    }

    //Legt die aktuelle Band ins Model
    @ModelAttribute("band")
    public Band getBand(@PathVariable("id") Long id) {
        Band band = bandService.getBand(id);
        if (band == null) {
            throw new NotFoundException();
        }
        return band;
    }

    //Legt den aktuellen User ins Model
    @ModelAttribute("user")
    public User getUser() {
        if(User.getCurrentUser() != null){
            return User.getCurrentUser();
        } else {
            User user = new User();
            return user;
        }
    }

    //Eine explizite Band aufrufen auf der seite bands/show.html
    @GetMapping
    public String showBand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("playsAt", bandService.getPlaysAt(bandService.getBand(id)));
        model.addAttribute("bandForm", formAssembler.toForm(getBand(id)));
        model.addAttribute("favourized", bandService.favourized(bandService.getBand(id)));
        model.addAttribute("festivalService", festivalService);
        return "bands/show";
    }

    //Umsetzung der Änderungen bei Abschicken des Bearbeiten-Formulars
    @PostMapping
    @PreAuthorize("authenticated")
    public String change (@PathVariable("id") Long id, @ModelAttribute("bandForm")
    @Valid BandForm bandForm, BindingResult bandBinding) {
        if (bandBinding.hasErrors()) {
            return "bands/show";
        }
        bandService.save(formAssembler.update(getBand(id), bandForm));
        return "redirect:/bands/" + id;
    }

    //Band zu Favoriten hinzufügen und aus Favoriten entfernen
    @GetMapping("/addToFavourites/{userID}")
    public String addToFavourites(@PathVariable("userID") Long userID,@PathVariable("id") Long bandID, String bandname) {
        Band band = bandService.getBand(bandID);
        User user = userService.getUser(userID);
        userService.addBand(user, band);
        return "redirect:/bands/" + bandID;
    }

    @GetMapping("/removeFromFavourites/{userID}")
    public String removeFromFavourites(@PathVariable("userID") Long userID,@PathVariable("id") Long bandID, String bandname) {
        Band band = bandService.getBand(bandID);
        User user = userService.getUser(userID);
        userService.removeBand(user, band);
        return "redirect:/bands/" + bandID;
    }

    //Band löschen
    @PostMapping(path = "/delete")
    public String delete(@PathVariable("id") Long id) {
        bandService.delete(id);
        return "redirect:/bands/";
    }


}
