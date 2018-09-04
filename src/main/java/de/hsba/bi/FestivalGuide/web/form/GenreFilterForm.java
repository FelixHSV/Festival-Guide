package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.NotNull;

public class GenreFilterForm {
    @NotNull(message = "Bitte ein Genre wählen")
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
