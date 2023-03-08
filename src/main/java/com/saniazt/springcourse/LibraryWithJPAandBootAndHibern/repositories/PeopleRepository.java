package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFullName(String fullName);


}
