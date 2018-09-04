package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.filter.Filter;
import de.hsba.bi.FestivalGuide.filter.GenreFilter;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.web.form.*;
import org.springframework.stereotype.Component;

@Component
public class FormAssembler {

    FestivalForm toForm (Festival festival){
        FestivalForm form = new FestivalForm();
        form.setName(festival.getName());
        form.setLocation(festival.getLocation());
        form.setGenre(festival.getGenre());
        form.setDay(festival.getDay());
        form.setMonth(festival.getMonth());
        form.setYear(festival.getYear());
        form.setEndDay(festival.getEndDay());
        form.setEndMonth(festival.getEndMonth());
        form.setEndYear(festival.getEndYear());
        return form;
    }

    Festival update (Festival festival, FestivalForm form){
        festival.setName(form.getName());
        festival.setLocation(form.getLocation());
        festival.setGenre(form.getGenre());
        festival.setDay(form.getDay());
        festival.setMonth(form.getMonth());
        festival.setYear(form.getYear());
        festival.setEndDay(form.getEndDay());
        festival.setEndMonth(form.getEndMonth());
        festival.setEndYear(form.getEndYear());
        return festival;
    }

    BandForm toForm (Band band){
        BandForm form = new BandForm();
        form.setName(band.getName());
        return form;
    }

    Band update(Band band, BandForm form){
        band.setName(form.getName());
        return band;
    }

    User update (User user, UserForm form) {
        user.setName(form.getName());
        user.setPassword(form.getPassword());
        return user;
    }

    Filter update (Filter filter, DateFilterForm form) {
        filter.setMonth(form.getMonth());
        filter.setYear(form.getYear());
        return filter;
    }

    GenreFilter update (GenreFilter genreFilter, GenreFilterForm form) {
        genreFilter.setGenre(form.getGenre());
        return genreFilter;
    }

}
