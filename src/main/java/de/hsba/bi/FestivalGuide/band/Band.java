package de.hsba.bi.FestivalGuide.band;

import de.hsba.bi.FestivalGuide.festival.Festival;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Test Felix
@Entity(name = "Band")
@Table(name = "band")
public class Band {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String name;

    @ManyToMany(mappedBy = "plays")
    private List<Festival> playsAt;


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

    public List<Festival> getPlaysAt() {
        if (playsAt == null) {
            playsAt = new ArrayList<>();
        }
        return playsAt;
    }
}

