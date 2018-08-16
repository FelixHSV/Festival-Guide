package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserService;
import de.hsba.bi.FestivalGuide.web.form.BandForm;
import de.hsba.bi.FestivalGuide.web.form.FestivalForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/festivals/{id}")
public class FestivalShowController {

    private final FestivalService festivalService;
    private final FormAssembler formAssembler;
    private final BandService bandService;
    private final UserService userService;

    public FestivalShowController(FestivalService festivalService, FormAssembler formAssembler, BandService bandService, UserService userService) {
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
        this.bandService = bandService;
        this.userService = userService;
    }

    //Legt das aktuelle Festival ins Model
    @ModelAttribute("festival")
    public Festival getFestival(@PathVariable("id") Long id) {
        Festival festival = festivalService.getFestival(id);
        if (festival == null) {
            throw new NotFoundException();
        }
        return festival;
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

    //Ein explizites Festival aufrufen auf der Seite festivals/show.html
    @GetMapping
    public String showFestivals(@PathVariable("id") Long id, Model model){
        model.addAttribute("bandForm", new BandForm());
        model.addAttribute("plays", festivalService.getPlays(festivalService.getFestival(id)));
        model.addAttribute("playsNot",festivalService.getNotPlays(festivalService.getFestival(id), bandService));
        model.addAttribute("festivalForm", formAssembler.toForm(getFestival(id)));
        model.addAttribute("startDate",  festivalService.startDatum(festivalService.getFestival(id)));
        model.addAttribute("endDate",  festivalService.endDatum(festivalService.getFestival(id)));
        model.addAttribute("favourized", festivalService.favourized(festivalService.getFestival(id)));
        return "festivals/show";
    }

    //Band erstellen und direkt zu Festival hinzufügen
    @PostMapping("/add")
    @PreAuthorize("authenticated")
    public String addBand(@PathVariable("id") Long id, @ModelAttribute("bandForm") @Valid BandForm bandForm, BindingResult bandBinding){
        if(bandBinding.hasErrors()){
            return "festivals/" + id;
        }
        Band band = bandService.createBand(formAssembler.update(new Band(), bandForm));
        Festival festival = festivalService.getFestival(id);
        festivalService.addBand(festival, band);
        return "redirect:/festivals/" + id;
    }

    //Daten des Festivals bearbeiten
    @PostMapping
    @PreAuthorize("authenticated")
    public String change (Model model, @PathVariable("id") Long id, @ModelAttribute("festivalForm")
    @Valid FestivalForm festivalForm, BindingResult festivalBinding) {
        if (festivalBinding.hasErrors()) {
            model.addAttribute("bandForm", new BandForm());
            return "festivals/show";
        }
        festivalService.save(formAssembler.update(getFestival(id), festivalForm));
        return "redirect:/festivals/" + id;
    }

    //Hinzufügen/Löschen einer Assoziation zwischen Festival und Band
    @GetMapping("/add/{bandid}")
    public String addExistingBand(@PathVariable("bandid") Long bandid,@PathVariable("id") Long fesid) {
        Festival festival = festivalService.getFestival(fesid);
        Band band = bandService.getBand(bandid);
        festivalService.addBand(festival, band);
        return "redirect:/festivals/" + fesid;
    }

    @GetMapping("/remove/{bandid}")
    public String removeBand(@PathVariable("bandid") Long bandid,@PathVariable("id") Long fesid) {
        Festival festival = festivalService.getFestival(fesid);
        Band band = bandService.getBand(bandid);
        festivalService.removeBand(festival, band);
        return "redirect:/festivals/" + fesid;
    }

    //Festival zu Favoriten hinzufügen und aus Favoriten entfernen
    @GetMapping("/addToFavourites/{userID}")
    public String addToFavourites(@PathVariable("userID") Long userID,@PathVariable("id") Long festID) {
        Festival festival = festivalService.getFestival(festID);
        User user = userService.getUser(userID);
        userService.addFestival(user, festival);
        return "redirect:/festivals/" + festID;
    }

    @GetMapping("/removeFromFavourites/{userID}")
    public String removeFromFavourites(@PathVariable("userID") Long userID,@PathVariable("id") Long festID) {
        Festival festival = festivalService.getFestival(festID);
        User user = userService.getUser(userID);
        userService.removeFestival(user, festival);
        return "redirect:/festivals/" + festID;
    }

    //Festival löschen
    @PostMapping(path = "/delete")
    public String delete(@PathVariable("id") Long id) {
        festivalService.delete(id);
        return "redirect:/festivals/";
    }
}
