package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.*;

public class FestivalForm {

    @NotEmpty(message = "Bitte einen Namen eingeben")
    private String name;

    @NotEmpty(message = "Bitte einen Ort eingeben")
    private String location;

    @Min(value = 1, message = "Ein Monat beginnt mit Tag 1")
    @Max(value = 31, message = "Ein Monat kann nur 31 Tage haben")
    @NotNull(message = "Bitte einen Tag eingeben")
    private Integer day;

    @Min(value = 1, message = "Ein Jahr beginnt im Januar mit Monat 1")
    @Max(value = 12, message = "Ein Jahr hat nur 12 Monate")
    @NotNull(message = "Bitte einen Monat eingeben")
    private Integer month;

    @Min(value = 2018, message = "Jahre früher als 2018, liegen in der Vergangenheit")
    @NotNull(message = "Bitte ein Jahr eingeben")
    private Integer year;


    //Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
