package de.hsba.bi.FestivalGuide.band;

import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Band {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @ManyToMany(mappedBy = "plays")
    @OrderBy("year ASC, month ASC, day ASC")
    private List<Festival> playsAt;

    @ManyToMany(mappedBy = "favouriteBands")
    private List<User> favourisedBy;

    //Default Konstruktor
    public Band(){
    }

    //Getter und Setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Festival> getPlaysAt() {
        if (playsAt == null) {
            playsAt = new ArrayList<>();
        }
        return playsAt;
    }

    public List<User> getFavourisedBy() {
        if (favourisedBy == null) {
            favourisedBy = new ArrayList<>();
        }
        return favourisedBy;
    }

}

