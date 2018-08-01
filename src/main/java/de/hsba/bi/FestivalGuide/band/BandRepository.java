package de.hsba.bi.FestivalGuide.band;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BandRepository extends JpaRepository<Band,Long> {

}
