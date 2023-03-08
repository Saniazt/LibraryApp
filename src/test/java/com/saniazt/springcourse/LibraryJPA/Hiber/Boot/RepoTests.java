package com.saniazt.springcourse.LibraryJPA.Hiber.Boot;

import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.repositories.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@ContextConfiguration(locations = "classpath:application.properties")
@SpringBootTest
class RepoTests {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    public void testFindById() {
        // Arrange
        int expectedId = 9;
        String expectedName = "Stasy";

        // Act
        Optional<Person> personOptional = peopleRepository.findById(expectedId);

        // Assert
        Assertions.assertTrue(personOptional.isPresent());
        Person person = personOptional.get();
        Assertions.assertEquals(expectedId, person.getId());
        Assertions.assertEquals(expectedName, person.getFullName());
    }
}