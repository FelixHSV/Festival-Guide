package de.hsba.bi.FestivalGuide.web;


import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.filter.Filter;
import de.hsba.bi.FestivalGuide.filter.GenreFilter;
import de.hsba.bi.FestivalGuide.web.form.DateFilterForm;
import de.hsba.bi.FestivalGuide.web.form.FestivalForm;
import de.hsba.bi.FestivalGuide.web.form.GenreFilterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("filter")
public class FestivalFilterController {
    private final FestivalService festivalService;
    private final FormAssembler formAssembler;


    public FestivalFilterController(FestivalService festivalService, FormAssembler formAssembler) {
        this.festivalService = festivalService;
        this.formAssembler = formAssembler;
    }

    //DateFilter anwenden
    @PostMapping ("/date")
    public String datefilter(Model model, @ModelAttribute("dateFilterForm") @Valid DateFilterForm dateFilterForm, BindingResult dateFilterBinding) {
        if(dateFilterBinding.hasErrors()){
            model.addAttribute("festivals",festivalService.getAll());
            model.addAttribute("festivalForm", new FestivalForm());
            model.addAttribute("genreFilterForm", new GenreFilterForm());
            return "festivals/index";
        }
        Filter filter = formAssembler.update(new Filter(), dateFilterForm);
        List<Festival> filteredFestivals = festivalService.filterDate(filter.getMonth(),filter.getYear());
        model.addAttribute("filteredFestivals", filteredFestivals);
        return "festivals/showFiltered";
    }

    //GenreFilter anwenden
    @PostMapping ("/genre")
    public String genrefilter(Model model, @ModelAttribute("genreFilterForm") @Valid GenreFilterForm genreFilterForm, BindingResult dateFilterBinding) {
        if(dateFilterBinding.hasErrors()){
            model.addAttribute("festivals",festivalService.getAll());
            model.addAttribute("festivalForm", new FestivalForm());
            model.addAttribute("dateFilterForm", new DateFilterForm());
            return "festivals/index";
        }
        GenreFilter genreFilter = formAssembler.update(new GenreFilter(), genreFilterForm);
        List<Festival> filteredFestivals = festivalService.filterGenre(genreFilter.getGenre());
        model.addAttribute("filteredFestivals", filteredFestivals);
        return "festivals/showFiltered";
    }


}




