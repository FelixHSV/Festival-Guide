package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.web.form.BandForm;
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
@RequestMapping("/bands")
public class BandIndexController {
    private BandService bandService;
    private FormAssembler formAssembler;

    public BandIndexController(BandService bandService, FormAssembler formAssembler) {
        this.bandService = bandService;
        this.formAssembler = formAssembler;
    }

    //Auf die Seite bands/index.html verweisen und dort eine Liste aller Bands sowie ein Anlage-Formular bereitstellen
    @GetMapping
    public String index(Model model) {
        model.addAttribute("bands", bandService.getAll());
        model.addAttribute("bandForm", new BandForm());
        return "bands/index";
    }

    //Band erstellen auf der Seite bands/index.html und Weiterleitung auf bands/show.html
    @PostMapping
    @PreAuthorize("authenticated")
    public String create(@ModelAttribute("bandForm") @Valid BandForm bandForm, BindingResult bandBinding) {
        if (bandBinding.hasErrors()) {
            return "bands/index";
        }
        Band band = bandService.createBand(formAssembler.update(new Band(), bandForm));
        return "redirect:/bands/" + band.getId();
    }
}
