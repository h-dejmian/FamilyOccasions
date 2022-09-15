package com.example.FamilyCalendar.model.projection;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.Person;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class OccasionWriteModel {
    @NotBlank(message = "Occasion description must not be empty!")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Person person;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Occasion toOccasion() {
        return new Occasion(description, date, person);
    }

    public Occasion toOccasion(Person person) {
        return new Occasion(description, date, person);
    }
}
