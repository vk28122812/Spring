package io.datajek.database_relationships.onetomany.uni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    @Autowired
    TournamentService service;

    @Autowired
    RegistrationService registrationService;

    @GetMapping
    public List<Tournament> allTournaments() {
        return service.allTournaments();
    }

    @GetMapping("/{id}")
    public Tournament getTournament(@PathVariable int id) {
        return service.getTournament(id);
    }


    @PostMapping
    public Tournament addTournament(@RequestBody Tournament tournament) {
        return service.addTournament(tournament);
    }

    @PutMapping("/{id}/registrations/{registration_id}")
    public Tournament addRegistration(@PathVariable int id, @PathVariable int registration_id) {
        Registration reg = registrationService.getRegistration(registration_id);
        System.out.println(reg);
        return service.addRegistration(id, reg);

    }

    @PutMapping("/{id}/remove_registrations/{registration_id}")
    public Tournament removeRegistration(@PathVariable int id, @PathVariable int registration_id) {
        Registration reg = registrationService.getRegistration(registration_id);
        return service.removeRegistration(id, reg);
    }

    @DeleteMapping("/{id}")
    public void deleteTournament(@PathVariable int id) {
        service.deleteTournament(id);
    }
}
