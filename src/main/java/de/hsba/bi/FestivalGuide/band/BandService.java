package de.hsba.bi.FestivalGuide.band;

import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class BandService {

    private final BandRepository repository;

    //Konstruktor
    public BandService(BandRepository repository) {
        this.repository = repository;
    }


    public Band createBand(Band band){ return repository.save(band); }

    public Band getBand (Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Band> getAll() {
        List<Band> allBands = repository.findAll();
        Collections.sort(allBands, new BandNameComparator());
        return allBands;
    }

    public Band save(Band band) { return repository.save(band); }

    public List<Festival> getPlaysAt (Band band){
        return (List<Festival>) band.getPlaysAt();
    }

    //Prüfung, ob die Band vom aktuellen User favorisiert ist oder nicht
    public Boolean favourized(Band band) {
        return User.getCurrentUser() != null && band.getFavourisedBy().contains(User.getCurrentUser());
    }

    //Durch die Many-To-Many Beziehungen zu Usern und Festivals (welche jeweils die Eigentümerseite darstellen)
    //Müssen vor dem löschen der Band die Assoziationen gelöscht werden
    public void delete(Long id){
        for (Festival festival : getBand(id).getPlaysAt()) {
            festival.getPlays().remove(getBand(id));
        }
        for (User user : getBand(id).getFavourisedBy()) {
            user.getFavouriteBands().remove(getBand(id));
        }
        this.repository.deleteById(id);
    }
}
