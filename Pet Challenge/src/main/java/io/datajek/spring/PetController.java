package io.datajek.spring;

import io.datajek.spring.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PetController {

    @Autowired
    PetService petService;

     @RequestMapping("/getPetForm")
    public String getPetForm(){
         return "get-pet-form";
     }

    @RequestMapping("/getPetDetails")
    public String getPetDetails(@RequestParam(value = "name", defaultValue = "Fluffy") String name, Model model){
         Pet pet = petService.getPetByName(name);
         if(pet==null){
             return "get-pet-form";
         }
         model.addAttribute("name", pet.getName());
         model.addAttribute("breed", pet.getBreed());
         model.addAttribute("owner", pet.getOwnerName());
         model.addAttribute("type", pet.getType());
         return "pet-details";
     }

}