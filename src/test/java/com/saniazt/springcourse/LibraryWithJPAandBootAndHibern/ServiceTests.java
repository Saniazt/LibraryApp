package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern;

import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Book;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.repositories.BookRepository;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAll() {
        // Создание тестовых данных
        List<Book> books = new ArrayList<>();
        books.add(new Book("Название 1", "Автор 1", 2020));
        books.add(new Book("Название 2", "Автор 2", 2021));
        when(bookRepository.findAll()).thenReturn(books);

        // Вызов метода
        List<Book> result = bookService.findAll(false);

        // Проверка результата
        assertEquals(books.size(), result.size());
        assertEquals(books.get(0), result.get(0));
        assertEquals(books.get(1), result.get(1));
    }
    @Test
    public void testFindOne() {
        // Создание тестовых данных
        Book book = new Book("Название", "Автор", 2020);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Вызов метода
        Book result = bookService.findOne(1);

        // Проверка результата
        assertEquals(book, result);
    }
    @Test
    void testGetBookOwner() {
        // Создаем mock объекты для Book и Person
        Book book = new Book("Test Book","Test author",1997);
        book.setOwner(new Person("Test person",20,"Test","Test"));
        Person owner = book.getOwner();

        // Указываем поведение mock BookRepository
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        // Вызываем тестируемый метод
        Person result = bookService.getBookOwner(1);

        // Проверяем, что возвращенный объект Person совпадает с owner
        assertEquals(owner, result);
    }



}


