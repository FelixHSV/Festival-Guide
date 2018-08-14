package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
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

    public FestivalShowController(FestivalService festivalService, FormAssembler formAssembler, BandService bandService) {
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
        this.bandService = bandService;
    }

    @ModelAttribute("festival")
    public Festival getFestival(@PathVariable("id") Long id) {
        Festival festival = festivalService.getFestival(id);
        if (festival == null) {
            throw new NotFoundException();
        }
        return festival;
    }

    //Ein explizites Festival aufrufen auf der Seite festivals/show.html
    @GetMapping
    public String showFestivals(@PathVariable("id") Long id, Model model){
        model.addAttribute("bandForm", new BandForm());
        model.addAttribute("plays", festivalService.getPlays(festivalService.getFestival(id)));
        model.addAttribute("playsNot",festivalService.getNotPlays(festivalService.getFestival(id), bandService));
        model.addAttribute("festivalForm", formAssembler.toForm(getFestival(id)));
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

    //Name eines Festivals ändern
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

    //bereits bestehenede Band zu Festival hinzufügen
    @GetMapping("/add/{bandid}")
    public String addBand2(@PathVariable("bandid") Long bandid,@PathVariable("id") Long fesid, String bandname) {
        Festival festival = festivalService.getFestival(fesid);
        Band band = bandService.getBand(bandid);
        festivalService.addBand(festival, band);
        return "redirect:/festivals/" + fesid;
    }

    //Festival löschen (Momentan noch nicht eingepflegt)
    @PostMapping(path = "/delete")
    public String delete(@PathVariable("id") Long id) {
        festivalService.delete(id);
        return "redirect:/festivals/";
    }
}
