package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.web.form.FestivalForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/festivals")
public class FestivalIndexController {

    private final FestivalService festivalService;
    private final FormAssembler formAssembler;

    public FestivalIndexController(FestivalService festivalService, FormAssembler formAssembler) {
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
    }

    //Auf die Seite festivals/index.html verweisen
    @GetMapping
    public String festivalsIndex(Model model){
        model.addAttribute("festivals",festivalService.getAll());
        model.addAttribute("festivalForm", new FestivalForm());
        return "festivals/index";
    }

    @PostMapping
    @PreAuthorize("authenticated")
    public String createFestival(@ModelAttribute("festivalForm") @Valid FestivalForm festivalForm, BindingResult festivalBinding){
        if(festivalBinding.hasErrors()){
            return "festivals/index";
        }
        Festival festival = festivalService.createFestival(formAssembler.update(new Festival(),festivalForm));
        return "redirect:/festivals/" + festival.getId();
    }
}