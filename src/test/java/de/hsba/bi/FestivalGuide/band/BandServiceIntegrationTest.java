package de.hsba.bi.FestivalGuide.band;

import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BandServiceIntegrationTest {

    @Autowired
    private BandService serviceToTest;
    @Autowired
    private FestivalService festivalservice;

    @Test
    public void shouldFindFestivalgigs() {
        // given
        Band b1 = serviceToTest.save(new Band());
        Festival f1 = buildFestival();
        Festival f2 = buildFestival();
        Festival f3 = buildFestival();
        Festival f4 = buildFestival();
        Festival f5 = buildFestival();

        // when
        festivalservice.addBand(f1, b1);
        festivalservice.addBand(f4, b1);
        // then
        assertEquals(2, serviceToTest.getPlaysAt(b1).size());
        assertTrue(serviceToTest.getPlaysAt(b1).containsAll(Arrays.asList(f1, f4)));

    }

    private Festival buildFestival() {
        Festival festival = new Festival();
        festival.setName("Test");
        festival.setGenre("Diverse");
        festival.setDay(1);
        festival.setEndDay(2);
        festival.setMonth(1);
        festival.setEndMonth(2);
        festival.setYear(2019);
        festival.setEndYear(2019);
        festival.setLocation("Hamburg");
        return festivalservice.save(festival);
    }
}
