package de.hsba.bi.FestivalGuide.festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FestivalRepository extends JpaRepository<Festival, Long> {

}
