package com.example.FamilyCalendar.model.projection;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.Person;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class OccasionReadModel {
    @NotBlank(message = "Occasion description must not be empty!")
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public OccasionReadModel(Occasion source) {
        this.description = source.getDescription();
        this.date = source.getDate();
    }

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
}
