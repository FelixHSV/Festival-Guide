package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.NotEmpty;

public class BandForm {

    @NotEmpty(message = "Bitte einen Namen eingeben")
    private String name;

    //Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
