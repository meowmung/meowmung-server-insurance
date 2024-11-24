package com.example.insurance.pet.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "disease_code")
public class DiseaseCode {

    @Id
    @Column(name = "disease_name")
    private String name;

    @Column(name = "disease_code")
    private long code;

}
