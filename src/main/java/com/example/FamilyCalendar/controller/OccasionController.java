package com.example.FamilyCalendar.controller;

import com.example.FamilyCalendar.model.Occasion;
import com.example.FamilyCalendar.model.OccasionRepository;
import com.example.FamilyCalendar.model.Person;
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
import java.util.Set;
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

  /*  @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Occasion>> readAllOccasions() {
        logger.warn("All data exposed!");
        return ResponseEntity.ok(occasionService.readAll());
    } */

    @GetMapping(value = "/{id}")
    ResponseEntity<Occasion> readOccasionById(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


     @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
     String showOccasions(Model model) {
         var occasions = (List<Occasion>) repository.findAll();
         model.addAttribute("occasions", occasions);
         model.addAttribute("occasion", new Occasion());
         return "occasions";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addOccasion(@ModelAttribute("occasion") @RequestBody @Valid Occasion current,
                       BindingResult bindingResult,
                       Model model){
        if(bindingResult.hasErrors()) {
            return "occasions";
        }

        Person person = current.getPerson();

        if(personService.existsByNameAndSurname(person.getName(), person.getSurname())) {
            current.setPerson(personService.findByNameAndSurname(person.getName(), person.getSurname()));
            occasionService.save(current);
        }

        else occasionService.save(current);

        model.addAttribute("occasions", getOccasions());
        model.addAttribute("message", "Dodano okazję!");

        return "occasions";
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Occasion> createOccasion( @RequestBody @Valid Occasion occasion) {
        Occasion occ = repository.save(occasion);
        return ResponseEntity.created(URI.create("/" + occ.getId())).body(occ);
    }

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
