package de.hsba.bi.FestivalGuide.festival;

import de.hsba.bi.FestivalGuide.band.Band;
import de.hsba.bi.FestivalGuide.band.BandService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository repository;

    public FestivalService(FestivalRepository repository) {
        this.repository = repository;
    }

    public Festival createFestival(Festival festival){
        return repository.save(festival);
    }

    public Festival getFestival (Long id){
        return repository.getOne(id);
    }

    public List<Band> getPlays (Festival festival) {
        return (List<Band>) festival.getPlays();
    }

    public Collection<Band> getNotPlays (Festival festival, BandService bandService) {
        //Ausgabe initialisieren
        List<Band> notPlays = festival.getNotPlays();
        //leeren, da sonst bei erneutem Aufruf der Seite, z.B. Werte doppelt hinzugefügt werden
        notPlays.removeAll(notPlays);
        //alle Bands
        Collection<Band> allBands = bandService.getAll();
        //Alle Bands hinzufügen
        notPlays.addAll(allBands);
        //Alle Bands, die auf dem Festival spielen, rausschmeissen
        notPlays.removeAll(festival.getPlays());
        //return
        return (List <Band>) notPlays;
    }

    public void addBand (Festival festival, Band band){
        festival.getPlays().add(band);
        band.getPlaysAt().add(festival);
    }

    public Collection<Festival> getAll(){
        return repository.findAll();
    }

    public void delete(Long id){this.repository.deleteById(id);}

}
