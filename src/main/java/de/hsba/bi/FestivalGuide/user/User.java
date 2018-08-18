package de.hsba.bi.FestivalGuide.user;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.festival.Festival;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements Comparable<User> {

    //Statische Methode, die den aktuellen User zurück gibt
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof de.hsba.bi.FestivalGuide.user.UserAdapter) {
            return ((UserAdapter) principal).getUser();
        }
        return null;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private String role;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinTable(name = "USER_BAND",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "band_id") }
    )
    @OrderBy("name ASC")
    private List<Band> favouriteBands;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinTable(name = "USER_FESTIVAL",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "festival_id") }
    )
    @OrderBy("name ASC")
    private List<Festival> favouriteFestivals;

    //Konstruktoren
    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password, String role) {
        this(name);
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Band> getFavouriteBands() {
        if(favouriteBands == null){
            favouriteBands = new ArrayList<>();
        }
        return favouriteBands;
    }

    public List<Festival> getFavouriteFestivals() {
        if(favouriteFestivals == null){
            favouriteFestivals = new ArrayList<>();
        }
        return favouriteFestivals;
    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
