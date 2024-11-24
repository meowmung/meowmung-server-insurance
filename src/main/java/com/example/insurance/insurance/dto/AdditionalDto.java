package com.example.insurance.insurance.dto;

public class AdditionalDto {
    private Integer age;
    private Boolean gender;
    private Boolean neutered;
    private Integer breedCode;
    private Integer weight;
    private Integer foodCount;
    private Integer diseaseCode;

    public AdditionalDto(Integer age, Boolean gender, Boolean neutered, Integer breedCode, Integer weight,
                         Integer foodCount, Integer diseaseCode) {
        this.age = age;
        this.gender = gender;
        this.neutered = neutered;
        this.breedCode = breedCode;
        this.weight = weight;
        this.foodCount = foodCount;
        this.diseaseCode = diseaseCode;
    }
}

