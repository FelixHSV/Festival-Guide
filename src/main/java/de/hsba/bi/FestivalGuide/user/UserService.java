package de.hsba.bi.FestivalGuide.user;


import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.festival.Festival;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //Konstruktor
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Initiale Useranlage (1x Admin, 3x User)
    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            createUser("Admin", "admin", "ADMIN");
            createUser("Niklas", "niklas", "USER");
            createUser("Felix", "felix", "USER");
            createUser("Malte", "malte", "USER");
        }
    }

    private void createUser(String username, String password, String role) {
        userRepository.save(new User(username, passwordEncoder.encode(password), role));
    }

    public User createUser(User user) {
        String username = user.getName();
        String password = user.getPassword();
        String role = "USER";
        return userRepository.save(new User(username, passwordEncoder.encode(password), role));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUser (Long id){
        return userRepository.getOne(id);
    }

    //Check des Usernames für Registrierung
    public boolean checkUsername (String name) {
        List<User> users = this.findAll();
        for (User user: users) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //Hinzufügen/Löschen einer Assoziation zwischen User und Band
    public void addBand (User user, Band band){
        user.getFavouriteBands().add(band);
        band.getFavourisedBy().add(user);
    }

    public void removeBand (User user, Band band){
        user.getFavouriteBands().remove(band);
        band.getFavourisedBy().remove(user);
    }

    //Hinzufügen/Löschen einer Assoziation zwischen User und Festival
    public void addFestival (User user, Festival festival){
        user.getFavouriteFestivals().add(festival);
        festival.getFavourisedBy().add(user);
    }

    public void removeFestival (User user, Festival festival){
        user.getFavouriteFestivals().remove(festival);
        festival.getFavourisedBy().remove(user);
    }

    //Abrufen von Listen mit Favorisierten Festivals bzw. Bands
    public List<Festival> getFavouriteFestivals (User user){
        return (List<Festival>) user.getFavouriteFestivals();
    }

    public List<Band> getFavouriteBands (User user){
        return (List<Band>) user.getFavouriteBands();
    }
}

