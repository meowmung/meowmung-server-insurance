package com.example.insurance.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "disease_code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiseaseCode {

    @Id
    @Column(name = "disease_name")
    private String name;

    @Column(name = "disease_code")
    private int code;

}
