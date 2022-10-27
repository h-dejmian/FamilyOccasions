package com.example.FamilyCalendar.service;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.OccasionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OccasionService {
    private OccasionRepository repository;

    public OccasionService(OccasionRepository repository) {
        this.repository = repository;
    }

    public List<Occasion> readAll() {
        return new ArrayList<>(repository.findAll());
    }

    public Optional<Occasion> findById(Integer id) {
         return repository.findById(id);
    }

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    public Occasion save(Occasion entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
