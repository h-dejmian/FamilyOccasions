package com.example.FamilyCalendar.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, Integer> {
    List<Occasion> findAll();

    Optional<Occasion> findById(Integer id);

    boolean existsById(Integer id);

    Occasion save(Occasion entity);

    Page<Occasion> findAll(Pageable page);

    void deleteById(Integer id);
}
