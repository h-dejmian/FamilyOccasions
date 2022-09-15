package com.example.FamilyCalendar.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findAll();

    Optional<Person> findById(Integer id);

    boolean existsById(Integer id);

    Person save(Person entity);

    Page<Person> findAll(Pageable page);

    void deleteById(Integer id);
}
