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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FestivalBandController {

    //Variablen initalisieren
    private final BandService bandService;
    private final FestivalService festivalService;
    private final FormAssembler formAssembler;

    public FestivalBandController(BandService bandService, FestivalService festivalService, FormAssembler formAssembler) {
        this.bandService = bandService;
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
    }


    // ----------
    // FESTIVALS
    // ----------

    //Auf die Seite festivals/index.html verweisen
    @GetMapping("/festivals")
    public String festivalsIndex(Model model){
        model.addAttribute("festivals",festivalService.getAll());
        model.addAttribute("festivalForm", new FestivalForm());
        return "festivals/index";
    }

    //Neues Festival erstellen
    @PostMapping("/festivals")
    @PreAuthorize("authenticated")
    public String createFestival(@ModelAttribute("festivalForm") @Valid FestivalForm festivalForm, BindingResult festivalBinding){
        if(festivalBinding.hasErrors()){
            return "festivals/index";
        }
        Festival festival = festivalService.createFestival(formAssembler.update(new Festival(),festivalForm));
        return "redirect:/festivals/" + festival.getId();
    }

    //Ein explizites Festival aufrufen auf der Seite festivals/show.html
    @GetMapping(path ="/festivals/{id}")
    public String showFestivals(@PathVariable("id") Long id, Model model){
        Festival festival = festivalService.getFestival(id);
        model.addAttribute("festival", festival);
        model.addAttribute("festivals",festivalService.getAll());
        model.addAttribute("bands",bandService.getAll());
        model.addAttribute("bandForm2", new BandForm());
        model.addAttribute("plays", festivalService.getPlays(festival));
        model.addAttribute("playsNot",festivalService.getNotPlays(festival, bandService));
        return "festivals/show";
    }

    //Band erstellen und direkt zu Festival hinzufügen
    @PostMapping(path = "/festivals/{id}")
    @PreAuthorize("authenticated")
    public String addBand(@PathVariable("id") Long id, @ModelAttribute("bandForm2") @Valid BandForm bandForm, BindingResult bandBinding){
        if(bandBinding.hasErrors()){
            return "festivals/" + id;
        }
        Band band = bandService.createBand(formAssembler.update(new Band(), bandForm));
        Festival festival = festivalService.getFestival(id);
        festivalService.addBand(festival, band);
        return "redirect:/festivals/" + id;
    }

    //bereits bestehenede Band zu Festival hinzufügen
    @GetMapping(path = "/festivals/add/{bandid}/{fesid}")
    public String addBand2(@PathVariable("bandid") Long bandid,@PathVariable("fesid") Long fesid, String bandname) {
        Festival festival = festivalService.getFestival(fesid);
        Band band = bandService.getBand(bandid);
        festivalService.addBand(festival, band);
        return "redirect:/festivals/" + fesid;
    }

    //Festival löschen (Momentan noch nicht eingepflegt)
    @PostMapping(path = "festivals/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        festivalService.delete(id);
        return "redirect:/festivals/";
    }



    // ------
    // BANDS
    // ------

    //Auf die Seite bands/index.html verweisen
    @GetMapping("/bands")
    public String index(Model model){
        model.addAttribute("bands", bandService.getAll());
        model.addAttribute("bandForm", new BandForm());
        return "bands/index";
    }

    //Band erstellen auf der Seite bands/index.html
    @PostMapping("/bands")
    @PreAuthorize("authenticated")
    public String create(@ModelAttribute("bandForm") @Valid BandForm bandForm, BindingResult bandBinding){
        if(bandBinding.hasErrors()){
            return "bands/index";
        }
        Band band = bandService.createBand(formAssembler.update(new Band(), bandForm));
        return "redirect:/bands/" + band.getId();
    }

    //Eine explizite Band aufrufen auf der Seite bands/show.html
    @GetMapping(path ="/bands/{id}")
    public String showBand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("band", bandService.getBand(id));
        model.addAttribute("playsAt", bandService.getPlaysAt(bandService.getBand(id)));
        return "bands/show";
    }



















}

    /*
    @PostMapping("/festivals")
    public String createFestivals(String name, String location, Integer day, Integer month, Integer year) {
        Festival festival = festivalService.createFestival(name, location, day, month, year);
        return "redirect:/festivals/" + festival.getId();
    }
    */

    /*@PostMapping("/bands")
    public String createBand(String name){
        Band band = bandService.createBand(name);
        return "redirect:/bands/" + band.getId();
    }*/

    /*
    @PostMapping(path = "/festivals/{id}")
    public String addBand(@PathVariable("id") Long id,String bandname) {
        Band band = bandService.createBand(bandname);
        Festival festival = festivalService.getFestival(id);
        festivalService.addBand(festival, band);
        //bandService.addFestival(band, festival);
        return "redirect:/festivals/" + id;
    }
    */