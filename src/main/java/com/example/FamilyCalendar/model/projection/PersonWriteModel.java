package com.example.FamilyCalendar.model.projection;

import com.example.FamilyCalendar.model.Person;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonWriteModel {
    private int id;
    @NotBlank(message = "Name must not be empty!")
    private String name;
    @NotBlank(message = "Surname must not be empty!")
    private String surname;
    @Valid
    private List<OccasionWriteModel> occasions = new ArrayList<>();

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

    @Valid
    public List<OccasionWriteModel> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<OccasionWriteModel> occasions) {
        this.occasions = occasions;
    }

    public Person toPerson() {
         var result = new Person(name, surname);
         result.setId(this.id);
         result.setOccasions(
                 this.getOccasions().stream()
                         .map(source -> source.toOccasion(result))
                         .collect(Collectors.toList())
         );
         return result;
    }
}
