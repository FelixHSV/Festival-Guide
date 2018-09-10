package de.hsba.bi.FestivalGuide.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        // delete all entries that have been created by the UserService's init method (1x Admin, 3x User)
        repository.deleteAll();
        repository.flush();
    }

    @Test
    public void shouldFindByName() {
        // given
        User horst = new User("horst", "", "USER");
        User peter = new User("peter", "", "USER");
        User maike = new User("maike", "", "USER");
        repository.save(horst);
        repository.save(peter);
        repository.save(maike);

        // then
        assertEquals(horst, repository.findByName("horst"));
        assertEquals(peter, repository.findByName("peter"));
        assertEquals(maike, repository.findByName("maike"));
        assertNull(repository.findByName("karsten"));
    }
}

