package de.hsba.bi.FestivalGuide.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name = :name")
    User findByName(@Param("name") String name);
}
