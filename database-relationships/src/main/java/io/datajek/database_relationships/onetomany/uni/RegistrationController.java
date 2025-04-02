package io.datajek.database_relationships.onetomany.uni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    RegistrationService service;

    @GetMapping
    public List<Registration> allRegistrations() {
        return service.allRegistrations();
    }

    @GetMapping("/{id}")
    public Registration getRegistration(@PathVariable int id){
        return service.getRegistration(id);
    }

    @PostMapping
    public Registration addRegistration(@RequestBody Registration registration) {
        return service.addRegistration(registration);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable int id) {
        service.deleteRegistration(id);
    }
}
