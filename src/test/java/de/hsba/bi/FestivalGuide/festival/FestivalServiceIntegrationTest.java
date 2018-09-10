package de.hsba.bi.FestivalGuide.festival;

import de.hsba.bi.FestivalGuide.festival.Festival;
import de.hsba.bi.FestivalGuide.festival.FestivalService;
import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserAdapter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FestivalServiceIntegrationTest {

    @Autowired
    private FestivalService serviceToTest;

    @Test
    public void shouldFindFestivals() {
        // given
        Festival f1 = buildFestivalWithGenre("Rock");
        Festival f2 = buildFestivalWithGenre("Rock");
        Festival f3 = buildFestivalWithGenre("HipHop");
        Festival f4 = buildFestivalWithGenre("Pop");
        Festival f5 = buildFestivalWithGenre("Pop");
        Festival f6 = buildFestivalWithGenre("Pop");
        Festival f7 = buildFestivalWithDate(2, 8, 5, 5, 2020, 2020);
        Festival f8 = buildFestivalWithDate(29, 2, 5, 6, 2020, 2020);
        // when
        Collection<Festival> festivals = serviceToTest.filterGenre("Rock");
        // then
        assertEquals(2, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f1, f2)));

        // when
        festivals = serviceToTest.filterGenre("Jazz");
        // then
        assertTrue(festivals.isEmpty());

        // when
        festivals = serviceToTest.filterGenre("HipHop");
        // then
        assertEquals(1, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f3)));


        // when
        festivals = serviceToTest.filterGenre("Pop");
        // then
        assertEquals(3, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f4, f5, f6)));

        // when
        festivals = serviceToTest.filterDate(2, 2019);
        // then
        assertEquals(6, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f1, f2, f3, f4, f5, f6)));

        // when
        festivals = serviceToTest.filterDate(5, 2020);
        // then
        assertEquals(2, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f7, f8)));

        // when
        festivals = serviceToTest.filterDate(6, 2020);
        // then
        assertEquals(1, festivals.size());
        assertTrue(festivals.containsAll(Arrays.asList(f8)));
    }

    private Festival buildFestivalWithGenre(String genre) {
        Festival festival = new Festival();
        festival.setName("Test");
        festival.setGenre(genre);
        festival.setDay(1);
        festival.setEndDay(2);
        festival.setMonth(1);
        festival.setEndMonth(2);
        festival.setYear(2019);
        festival.setEndYear(2019);
        festival.setLocation("Hamburg");
        return serviceToTest.save(festival);
    }

    private Festival buildFestivalWithDate(Integer day, Integer endDay, Integer month, Integer endMonth, Integer year, Integer endYear) {
        Festival festival = new Festival();
        festival.setName("Test");
        festival.setGenre("Divers");
        festival.setDay(day);
        festival.setEndDay(endDay);
        festival.setMonth(month);
        festival.setEndMonth(endMonth);
        festival.setYear(year);
        festival.setEndYear(endYear);
        festival.setLocation("Hamburg");
        return serviceToTest.save(festival);
    }
}
