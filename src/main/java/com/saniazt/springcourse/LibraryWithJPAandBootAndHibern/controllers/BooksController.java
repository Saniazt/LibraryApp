package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.controllers;

import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.dao.BookDAO;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.reports.BookReport;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.repositories.BookRepository;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.repositories.PeopleRepository;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.BookService;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Book;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;
import javax.validation.Valid;



@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final PeopleService peopleService;
    private final BookDAO bookDAO;
    private final BookReport bookReport;



    @Autowired
    public BooksController(PeopleRepository peopleRepository, BookRepository bookRepository, BookService bookService, PeopleService peopleService, BookDAO bookDAO, BookReport bookReport) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.peopleService = peopleService;
        this.bookDAO = bookDAO;
        this.bookReport = bookReport;
    }


    @GetMapping()
    public String index(Model model,
                        @RequestParam(value="page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear
    ) {
        if (page==null || booksPerPage ==null){
            model.addAttribute("books", bookService.findAll(sortByYear));
        }
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Person bookOwner = bookService.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleRepository.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book Book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(Book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // У selectedPerson назначено только поле id, остальные поля - null
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearchPage(Model model, @RequestParam("query") String query){
      //  model.addAttribute("books", bookService.searchByTitleNam(query));
       model.addAttribute("books", bookDAO.findByTitleOfBooks(query));
        return "books/search";
    }

    @GetMapping("/expired")
    public String expiredBooks(Model model){
        model.addAttribute("expiredBooks",bookService.findExpired());
        return "books/expired";
    }

    @GetMapping("/report")
    public String reportBooks(){
        bookReport.createReport();
        return "redirect:/books";
    }
}
