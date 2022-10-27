package com.example.FamilyCalendar.service;

import com.example.FamilyCalendar.model.Person;
import com.example.FamilyCalendar.model.PersonRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> readAll() {
        return repository.findAll();
    }

    public Optional<Person> findById(Integer id) {
        return repository.findById(id);
    }

    public Person createPerson(Person source) {
         if(repository.findById(source.getId()).isPresent()) {
             return repository.findById(source.getId()).get();
         }
         return repository.save(source);
    }

    public boolean existsById(Integer id) {return repository.existsById(id);}

    public boolean existsByNameAndSurname(String name, String surname) {return repository.existsByNameAndSurname(name,surname);}

    public Person findByNameAndSurname(String name, String surname) {return repository.findByNameAndSurname(name, surname);}
}
