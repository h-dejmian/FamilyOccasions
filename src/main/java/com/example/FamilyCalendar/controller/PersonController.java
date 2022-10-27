package com.example.FamilyCalendar.controller;

import com.example.FamilyCalendar.model.Person;
import com.example.FamilyCalendar.model.PersonRepository;
import com.example.FamilyCalendar.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonService service;
    private final PersonRepository repository;

    public PersonController(PersonService service, PersonRepository repository) {
        this.service = service;
        this.repository = repository;
    }

  /*  @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Person>> readAllPersons() {
        logger.warn("All data exposed!");
        return ResponseEntity.ok(service.readAll());
    } */

    @GetMapping(value = "/{id}")
    ResponseEntity<Person> readPerson(@PathVariable int id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String showPersons(Model model) {
        var persons = (List<Person>) service.readAll();
        model.addAttribute("persons", persons);
        return "persons";
    }

    @PostMapping
    ResponseEntity<Person> createPerson(@RequestBody @Valid Person person) {
        Person per = repository.save(person);
        return ResponseEntity.created(URI.create("/" + per.getId())).body(per);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updatePerson(@PathVariable int id, @RequestBody @Valid Person person ) {
        if(!repository.existsById(id)){return ResponseEntity.notFound().build();}
        person.setId(id);
        repository.save(person);
        return ResponseEntity.noContent().build();
    }
}
