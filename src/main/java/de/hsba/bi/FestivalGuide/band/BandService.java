package de.hsba.bi.FestivalGuide.band;

import de.hsba.bi.FestivalGuide.festival.Festival;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

//Test Felix
@Service
@Transactional
public class BandService {

    //Test

    private final BandRepository repository;

    public BandService(BandRepository repository) {
        this.repository = repository;
    }

    public Band createBand(Band band){return repository.save(band);}

    public Band getBand (Long id){
        return repository.getOne(id);
    }

    public Band searchBand (Band band) {return band;}

    public List<Festival> getPlaysAt (Band band){
        return (List<Festival>) band.getPlaysAt();
    }

    public void addFestival(Band band, Festival festival){
        band.getPlaysAt().add(festival);
        festival.getPlays().add(band);
    }

    public Collection<Band> getAll() { return repository.findAll();}

    public void delete(Long id){this.repository.deleteById(id);}

    public Band save(Band band) { return repository.save(band); }
}
