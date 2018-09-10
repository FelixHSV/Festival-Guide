package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DateFilterForm {
    @Min(value = 1, message = "Wir beginnen am besten mit Monat 01 ;)")
    @Max(value = 12, message = "Mehr als 12 Monate hat ein Jahr leider nicht.")
    @NotNull(message = "Bitte einen Monat eingeben")
    private Integer month;

    @Min(value = 1959, message = "Das allererster Festival der Welt war 1959 das Newport Folk Festival.")
    @Max(value = 2099, message = "Festivals in dem Zeitraum überlassen wir mal unseren Nachkommen.")
    @NotNull(message = "Bitte ein Jahr eingeben")
    private Integer year;

    //Getter und Setter

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
