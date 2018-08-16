package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.*;

public class FestivalForm {

    @NotEmpty(message = "Bitte einen Namen eingeben")
    private String name;

    @NotEmpty(message = "Bitte einen Ort eingeben")
    private String location;

    @NotEmpty(message = "Bitte eine Stilrichtung eingeben")
    private String genre;

    @Min(value = 1, message = "Ein Monat beginnt mit Tag 1")
    @Max(value = 31, message = "Ein Monat kann nur 31 Tage haben")
    @NotNull(message = "Bitte einen Tag eingeben")
    private Integer day;

    @Min(value = 1, message = "Ein Jahr beginnt im Januar mit Monat 1")
    @Max(value = 12, message = "Ein Jahr hat nur 12 Monate")
    @NotNull(message = "Bitte einen Monat eingeben")
    private Integer month;

    @Min(value = 2018, message = "Jahre früher als 2018 liegen in der Vergangenheit")
    @NotNull(message = "Bitte ein Jahr eingeben")
    private Integer year;

    @Min(value = 1, message = "Ein Monat beginnt mit Tag 1")
    @Max(value = 31, message = "Ein Monat kann nur 31 Tage haben")
    @NotNull(message = "Bitte einen Tag eingeben")
    private Integer endDay;

    @Min(value = 1, message = "Ein Jahr beginnt im Januar mit Monat 1")
    @Max(value = 12, message = "Ein Jahr hat nur 12 Monate")
    @NotNull(message = "Bitte einen Monat eingeben")
    private Integer endMonth;

    @Min(value = 2018, message = "Jahre früher als 2018 liegen in der Vergangenheit")
    @NotNull(message = "Bitte ein Jahr eingeben")
    private Integer endYear;


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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
}
