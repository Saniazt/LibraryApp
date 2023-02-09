package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.dao;


import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Book;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;



@Component
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    @Query(nativeQuery = true)
    public List<Book> findByTitleOfBooks(String str){
        Session session = entityManager.unwrap(Session.class);
        List<Book> list1 =  new ArrayList<>
                (session.createNativeQuery("SELECT * FROM book " +
                                        "WHERE UPPER(title) LIKE UPPER('%" + str +"%') " +
                                        "OR UPPER(author) LIKE UPPER('%" + str + "%')"
                        ,Book.class)
                        .getResultList()
                );
        return (list1.isEmpty()) ? null : list1;
    }
}
