package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.controllers;

import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.dao.PersonDAO;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonDAO personDAO) {
        this.peopleService = peopleService;
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        model.addAttribute("expired",peopleService.totalPlusExpired(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {


        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "people/search";
    }
    @PostMapping("/search")
    public String makeSearchPage(Model model, @RequestParam("query") String query){
        //  model.addAttribute("books", bookService.searchByTitleNam(query));
        model.addAttribute("people", personDAO.findByPersonName(query));
        return "people/search";
    }
}
