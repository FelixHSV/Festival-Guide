package de.hsba.bi.FestivalGuide.festival;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandNameComparator;
import de.hsba.bi.FestivalGuide.band.BandService;
import de.hsba.bi.FestivalGuide.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLTransactionRollbackException;
import java.util.*;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository repository;

    //Konstruktor
    public FestivalService(FestivalRepository repository) {
        this.repository = repository;
    }


    public Festival createFestival(Festival festival) {
        return repository.save(festival);
    }

    public Festival save(Festival festival) {
        return repository.save(festival);
    }

    public Festival getFestival(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Band> getPlays(Festival festival) {
        return (List<Band>) festival.getPlays();
    }

    public List<Festival> getAll() {
        List<Festival> allFestivals = repository.findAll();
        Collections.sort(allFestivals, new FestivalNameComparator());
        return allFestivals;
    }

    //Bands, die nicht auf dem Festival auftreten
    public List<Band> getNotPlays(Festival festival, BandService bandService) {
        List<Band> notPlays = new ArrayList<>();
        notPlays.addAll(bandService.getAll());
        notPlays.removeAll(festival.getPlays());
        Collections.sort(notPlays, new BandNameComparator());
        return notPlays;
    }

    //Hinzufügen/Löschen einer Assoziation zwischen Festival und Band
    public void addBand(Festival festival, Band band) {
        festival.getPlays().add(band);
        band.getPlaysAt().add(festival);
    }

    public void removeBand(Festival festival, Band band) {
        festival.getPlays().remove(band);
        band.getPlaysAt().remove(festival);
    }

    //Hilfsmethode, die prüft, ob eine Zahl einstellig ist
    private String oneDigit(int zahl) {
        if (zahl < 10) {
            return "0" + zahl;
        } else {
            return "" + zahl;
        }
    }

    //Umformatierung der Attribute "Tag", "Monat" und "Jahr" zu einem String der das Datum repräsentiert
    public String startDatum(Festival festival) {
        String startDatum = oneDigit(festival.getDay()) + "." + oneDigit(festival.getMonth()) + "." + festival.getYear();
        return startDatum;
    }

    public String endDatum(Festival festival) {
        String endDatum = oneDigit(festival.getEndDay()) + "." + oneDigit(festival.getEndMonth()) + "." + festival.getEndYear();
        return endDatum;
    }

    //Prüfung, ob die Band vom aktuellen User favorisiert ist oder nicht
    public Boolean favourized(Festival festival) {
        return User.getCurrentUser() != null && festival.getFavourisedBy().contains(User.getCurrentUser());
    }

    //Löschen eines Festivals & vorheriges Löschen aller möglichen User-Assoziationen
    public void delete(Long id) {
        for (User user : getFestival(id).getFavourisedBy()) {
            user.getFavouriteFestivals().remove(getFestival(id));
        }
        this.repository.deleteById(id);
    }

    //Genre-Filter anwenden
    public List<Festival> filterGenre (String genre) {
        List<Festival> allFestivals = this.getAll();
        List<Festival> filteredFestivals = new ArrayList<>();
        for (Festival festival: allFestivals) {
            if ((festival.getGenre().equals(genre))) {
                filteredFestivals.add(festival);
            }
        }
        return filteredFestivals;
    }

    //Datumsfilter anwenden
    public List<Festival> filterDate (Integer month, Integer year) {
        List<Festival> allFestivals = this.getAll();
        List<Festival> filteredFestivals = new ArrayList<>();
        for (Festival festival: allFestivals) {
        if ((festival.getMonth().equals(month) && (festival.getYear().equals(year))) || (festival.getEndMonth().equals(month) && festival.getEndYear().equals(year))) {
            filteredFestivals.add(festival);
        }
        }
        return filteredFestivals;
    }

}