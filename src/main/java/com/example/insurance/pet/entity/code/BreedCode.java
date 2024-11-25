package com.example.insurance.pet.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "breed_code")
@Getter
public class BreedCode {

    @Id
    @Column(name = "breed_name", nullable = false)
    private String name;

    @Column(name = "breed_code", nullable = false)
    private Integer code;

    @Column(name = "pet_type", nullable = false)
    private String type;
}
