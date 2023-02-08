package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.dao;


import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Book;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Query(nativeQuery = true)
    public List<Person> findByPersonName(String str){
        Session session = entityManager.unwrap(Session.class);
        List<Person> list1 =  new ArrayList<>
                (session.createNativeQuery("SELECT * FROM person WHERE full_name LIKE '%" + str + "%'"
                                ,Person.class)
                        .getResultList()
                );
        return (list1.isEmpty()) ? null : list1;
    }
}
