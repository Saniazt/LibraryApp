package com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.models;




import com.saniazt.springcourse.LibraryWithJPAandBootAndHibern.services.PeopleService;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "email")
    @Email(message = "Please enter valid email")
    @NotEmpty(message = "Please enter valid email")
    private String email;

    @Column(name = "phone")
    private String phone;



    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    // Конструктор по умолчанию нужен для Spring
    public Person() {

    }

    public Person(String fullName, int yearOfBirth, String email, String phone) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  fullName;
    }
    public List<String> stringOfBooks(){
        return books.stream().map(a->a.getTitle()).collect(Collectors.toList());
    }
}
