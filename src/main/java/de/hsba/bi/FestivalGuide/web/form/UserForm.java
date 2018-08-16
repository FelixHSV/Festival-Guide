package de.hsba.bi.FestivalGuide.web.form;

import javax.validation.constraints.*;

public class UserForm {


    @NotEmpty(message = "Bitte einen Namen eingeben")
    private String name;

    @NotEmpty(message = "Bitte ein Passwort eingeben")
    private String password;

    //Getter und Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}