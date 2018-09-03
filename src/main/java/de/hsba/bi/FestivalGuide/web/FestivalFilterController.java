package de.hsba.bi.FestivalGuide.web;


import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.filter.Filter;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserService;
import de.hsba.bi.FestivalGuide.web.form.DateFilterForm;
import de.hsba.bi.FestivalGuide.web.form.FestivalForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("filter")
public class FestivalFilterController {
    private final FestivalService festivalService;
    private final FormAssembler formAssembler;
    private final BandService bandService;
    private final UserService userService;

    public FestivalFilterController(FestivalService festivalService, FormAssembler formAssembler, BandService bandService, UserService userService) {
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
        this.bandService = bandService;
        this.userService = userService;
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

    //DateFilter anwenden
    @PostMapping
    public String datefilter(Model model, @ModelAttribute("dateFilterForm") @Valid DateFilterForm dateFilterForm, BindingResult dateFilterBinding) {
        if(dateFilterBinding.hasErrors()){
            model.addAttribute("festivals",festivalService.getAll());
            model.addAttribute("dateFilterForm", new DateFilterForm());
            model.addAttribute("festivalForm", new FestivalForm());
            return "festivals/index";
        }
        Filter filter = formAssembler.update(new Filter(), dateFilterForm);
        List<Festival> allFestivals = festivalService.getAll();
        List<Festival> filteredFestivals = new ArrayList<>();
        for (Festival festival: allFestivals) {
            if ((festival.getMonth().equals(filter.getMonth()) || festival.getEndMonth().equals(filter.getMonth())) && festival.getYear().equals(filter.getYear())) {
                filteredFestivals.add(festival);
            }
        }
        model.addAttribute("filteredFestivals", filteredFestivals);
        return "festivals/showFiltered";
    }

}




