package io.datajek.spring.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.datajek.spring.Pet;

@Service
public class PetService {
    private List<Pet> pets;

    public PetService() {
        pets = new ArrayList<>(Arrays.asList(
            new Pet("Buddy", "Labrador Retriever", "John", "Dog", new String[]{"Friendly", "Active"}, 5, "PET001"),
            new Pet("Fluffy", "Persian", "Alice", "Cat", new String[]{"Calm", "Independent"}, 3, "PET002"),
            new Pet("Max", "German Shepherd", "Mike", "Dog", new String[]{"Loyal", "Energetic"}, 2, "PET003")
        ));
    }

    public Pet getPetByName(String name) {
        return pets.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Pet> getPetsByOwner(String ownerName) {
        return pets.stream().filter(p -> p.getOwnerName().equals(ownerName)).collect(Collectors.toList());
    }

    public List<Pet> getAllPets() {
        return pets;
    }

    public void savePet(Pet pet) {
        pets.add(pet);
    }
}