package com.example.insurance.pet.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "disease_code")
@Getter
public class DiseaseCode {

    @Id
    @Column(name = "disease_name")
    private String name;

    @Column(name = "disease_code")
    private Integer code;

}
