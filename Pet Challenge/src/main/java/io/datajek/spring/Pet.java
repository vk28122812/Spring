package io.datajek.spring;

public class Pet {
    private String name;
    private String breed;
    private String ownerName;
    private String type;
    private String[] characteristics;
    private Integer age;
    private String petId;

    public Pet() {
    }

    public Pet(String name, String breed, String ownerName, String type, String[] characteristics, int age, String petId) {
        this.name = name;
        this.breed = breed;
        this.ownerName = ownerName;
        this.type = type;
        this.characteristics = characteristics;
        this.age = age;
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String[] characteristics) {
        this.characteristics = characteristics;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }
}