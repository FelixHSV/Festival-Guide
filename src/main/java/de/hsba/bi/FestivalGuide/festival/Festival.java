package de.hsba.bi.FestivalGuide.festival;


import de.hsba.bi.FestivalGuide.band.Band;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Festival")
@Table(name = "festival")
public class Festival {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String location;

    @Basic(optional = false)
    private Integer day;

    @Basic(optional = false)
    private Integer month;

    @Basic(optional = false)
    private Integer year;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinTable(name = "festival_band", // <- Zuordnungstabelle, da N:M-Beziehung
            joinColumns = @JoinColumn(name = "festival_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id")

    )
    private List<Band> plays;


    @Basic(optional = false)
    @OneToMany
    private List<Band> notPlays;

    //Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Band> getPlays() {
        if(plays == null){
            plays = new ArrayList<>();
        }
        return plays;
    }

    public List<Band> getNotPlays(){
        if(notPlays == null){
            notPlays = new ArrayList<>();

        }
        return notPlays;
    }
}


