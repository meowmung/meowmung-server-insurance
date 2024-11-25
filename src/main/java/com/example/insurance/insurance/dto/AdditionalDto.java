package com.example.insurance.insurance.dto;

public class AdditionalDto {
    private Integer age;
    private Boolean gender;
    private Boolean neutered;
    private Integer breedCode;
    private Integer weight;
    private Integer foodCount;

    public AdditionalDto(Integer age, Boolean gender, Boolean neutered, Integer breedCode, Integer weight,
                         Integer foodCount) {
        this.age = age;
        this.gender = gender;
        this.neutered = neutered;
        this.breedCode = breedCode;
        this.weight = weight;
        this.foodCount = foodCount;
    }
}

