package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.web.form.BandForm;
import de.hsba.bi.FestivalGuide.web.form.FestivalForm;
import de.hsba.bi.FestivalGuide.web.form.UserForm;
import org.springframework.stereotype.Component;

@Component
public class FormAssembler {

    FestivalForm toForm (Festival festival){
        FestivalForm form = new FestivalForm();
        form.setName(festival.getName());
        form.setLocation(festival.getLocation());
        form.setDay(festival.getDay());
        form.setMonth(festival.getMonth());
        form.setYear(festival.getYear());
        return form;
    }

    Festival update (Festival festival, FestivalForm form){
        festival.setName(form.getName());
        festival.setLocation(form.getLocation());
        festival.setDay(form.getDay());
        festival.setMonth(form.getMonth());
        festival.setYear(form.getYear());
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
}
