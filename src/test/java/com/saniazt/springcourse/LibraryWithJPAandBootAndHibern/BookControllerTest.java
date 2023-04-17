package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern;


import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.controllers.BooksController;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.dao.BookDAO;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Book;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.reports.BookReport;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.BookService;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = BooksController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private BookService bookService;
    @MockBean private PeopleService peopleService;
    @MockBean private BookDAO bookDAO;
    @MockBean private BookReport bookReport;

    // Тесты метода index()
    @Test
    public void testIndex() throws Exception {
        when(bookService.findAll(false)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("books/index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"));
    }

    // Тесты метода show()
    @Test
    public void testShow() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        when(bookService.findOne(1)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("books/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"))
                .andExpect(MockMvcResultMatchers.model().attribute("book", book));
    }

    // Тесты метода newBook()
    @Test
    public void testNewBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("books/new"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book"));
    }

}
