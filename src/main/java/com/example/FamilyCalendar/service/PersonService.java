package com.example.FamilyCalendar.service;

import com.example.FamilyCalendar.model.Person;
import com.example.FamilyCalendar.model.PersonRepository;
import com.example.FamilyCalendar.model.projection.PersonReadModel;
import com.example.FamilyCalendar.model.projection.PersonWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonReadModel> readAll() {
        return repository.findAll().stream()
                .map(PersonReadModel::new)
                .collect(Collectors.toList());
    }

    public Optional<Person> findById(Integer id) {
        return repository.findById(id);
    }

    public PersonReadModel createPerson(PersonWriteModel source) {
         if(repository.existsById(source.getId())) return new PersonReadModel(repository.getById(source.getId()));
         else {
             Person person = repository.save(source.toPerson());
             return new PersonReadModel(person);
         }
    }

    public boolean existsById(Integer id) {return repository.existsById(id);}
}
