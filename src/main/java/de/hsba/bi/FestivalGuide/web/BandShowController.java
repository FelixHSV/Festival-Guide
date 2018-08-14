package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
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
@RequestMapping("/bands/{id}")
public class BandShowController {

    private final FormAssembler formAssembler;
    private final BandService bandService;

    public BandShowController(FormAssembler formAssembler, BandService bandService) {
        this.formAssembler = formAssembler;
        this.bandService = bandService;
    }

    @ModelAttribute("band")
    public Band getBand(@PathVariable("id") Long id) {
        Band band = bandService.getBand(id);
        if (band == null) {
            throw new NotFoundException();
        }
        return band;
    }


    @GetMapping
    public String showBand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("band", bandService.getBand(id));
        model.addAttribute("playsAt", bandService.getPlaysAt(bandService.getBand(id)));
        model.addAttribute("bandForm", formAssembler.toForm(getBand(id)));
        return "bands/show";
    }

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

    @PostMapping(path = "/delete")
    public String delete(@PathVariable("id") Long id) {
        bandService.delete(id);
        return "redirect:/bands/";
    }


}
