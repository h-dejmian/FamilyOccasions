package com.example.FamilyCalendar.model.projection;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.Person;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

public class PersonReadModel {
    private int id;
    @NotBlank(message = "Name must not be empty!")
    private String name;
    @NotBlank(message = "Surname must not be empty!")
    private String surname;
    private List<OccasionReadModel> occasions;

    public PersonReadModel(Person source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.occasions = source.getOccasions().stream()
                .map(OccasionReadModel::new)
                .collect(Collectors.toList());
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

    public List<OccasionReadModel> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<OccasionReadModel> occasions) {
        this.occasions = occasions;
    }
}
