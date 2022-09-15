package com.example.FamilyCalendar.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name must not be empty!")
    private String name;
    @NotBlank(message = "Surname must not be empty!")
    private String surname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private List<Occasion> occasions = new ArrayList<>();

    public Person() {}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Occasion> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<Occasion> occasions) {
        this.occasions = occasions;
    }
}
