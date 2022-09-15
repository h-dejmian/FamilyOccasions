package com.example.FamilyCalendar.controller;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.OccasionRepository;
import com.example.FamilyCalendar.model.Person;
import com.example.FamilyCalendar.model.projection.OccasionReadModel;
import com.example.FamilyCalendar.model.projection.OccasionWriteModel;
import com.example.FamilyCalendar.service.OccasionService;
import com.example.FamilyCalendar.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/occasions")
public class OccasionController {
    private static final Logger logger = LoggerFactory.getLogger(OccasionController.class);
    private final OccasionService occasionService;
    private final PersonService personService;
    private final OccasionRepository repository;

    public OccasionController(OccasionService occasionService, PersonService personService, OccasionRepository repository) {
        this.occasionService = occasionService;
        this.personService = personService;
        this.repository = repository;
    }

 /*   @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Occasion>> readAllOccasions() {
        logger.warn("All data exposed!");
        return ResponseEntity.ok(repository.findAll());
    } */

    @GetMapping(value = "/{id}")
    ResponseEntity<Occasion> readOccasionById(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

     @GetMapping
     String showOccasions(Model model) {
        //  model.addAttribute("occasion", new OccasionWriteModel());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM");
        var occasions = (List<Occasion>) repository.findAll().stream()
                .filter(occasion -> occasion.getDate() != null)
                .peek(occasion -> occasion.getDate().format(pattern))
                        .collect(Collectors.toList());

        model.addAttribute("occasions", occasions);
        model.addAttribute("occasion", new OccasionWriteModel());
        return "occasions";
    }

     @PostMapping
     String addOccasion(@ModelAttribute("occasion") @Valid OccasionWriteModel current,
                        BindingResult bindingResult,
                        Model model){
         if(bindingResult.hasErrors()) {
             return "occasions";
         }
         occasionService.save(current);
         model.addAttribute("occasion", new OccasionWriteModel());
         model.addAttribute("occasions", getOccasions());
         model.addAttribute("message", "Dodano okazję!");
         return "occasions";
     }

    @PostMapping(params = "addPerson")
    public String addPerson(@ModelAttribute("occasion") OccasionWriteModel current,
                            @RequestBody @Valid Person person) {
        current.setPerson(person);
        return "occasions";
    }

  /*  @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Occasion> createOccasion( @Valid Occasion occasion) {
        Occasion occ = repository.save(occasion);
        return ResponseEntity.created(URI.create("/" + occ.getId())).body(occ);
    } */

    @PutMapping("/{id}")
    ResponseEntity<?> updateOccasion(@PathVariable int id, @RequestBody @Valid Occasion occasion ) {
         if(!repository.existsById(id)){return ResponseEntity.notFound().build();}
         occasion.setId(id);
         repository.save(occasion);
         return ResponseEntity.noContent().build();
    }

    //TODO PATCH MAPPING

    @DeleteMapping("/{id}")
    ResponseEntity<Occasion> deleteOccasion(@PathVariable int id) {
        if(!repository.existsById(id)){return ResponseEntity.notFound().build();}
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ModelAttribute("occasions")
    List<Occasion> getOccasions() {
        return occasionService.readAll();
    }
}
