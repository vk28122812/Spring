package io.datajek.database_relationships.manytomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerService service;

    @Autowired
    PlayerProfileService profileService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public List<Player> allPlayers() {
        return service.allPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable int id){
        return service.getPlayer(id);
    }

    @PostMapping
    public Player addPlayer(@RequestBody Player player) {
        return service.addPlayer(player);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable int id) {
        service.deletePlayer(id);
    }

    @PutMapping("/{id}/profiles/{profile_id}")
    public Player assignDetail(@PathVariable int id, @PathVariable int profile_id) {
        PlayerProfile profile = profileService.getPlayerProfile(profile_id);
        return service.assignProfile(id, profile);
    }
    
    @PutMapping("/{id}/registrations/{registration_id}")
    public Player assignRegistration(@PathVariable int id, @PathVariable int registration_id) {
        Registration reg = registrationService.getRegistration(registration_id);
        return service.assignRegistration(id,reg);
    }
}