package de.hsba.bi.FestivalGuide.festival;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Festival {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String location;

    @Basic(optional = false)
    private String genre;

    @Basic(optional = false)
    private Integer day;

    @Basic(optional = false)
    private Integer month;

    @Basic(optional = false)
    private Integer year;

    @Basic(optional = false)
    private Integer endDay;

    @Basic(optional = false)
    private Integer endMonth;

    @Basic(optional = false)
    private Integer endYear;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinTable(name = "FESTIVAL_BAND",
            joinColumns = { @JoinColumn(name = "festival_id") },
            inverseJoinColumns = { @JoinColumn(name = "band_id") }
    )
    @OrderBy("name ASC")
    private List<Band> plays;

    @ManyToMany(mappedBy = "favouriteFestivals")
    private List<User> favourisedBy;

    //Default Konstruktor
    public Festival(){

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

    public List<Band> getPlays() {
        if(plays == null){
            plays = new ArrayList<>();
        }
        return plays;
    }

    public List<User> getFavourisedBy() {
        if(favourisedBy == null){
            favourisedBy = new ArrayList<>();
        }
        return favourisedBy;
    }
}


