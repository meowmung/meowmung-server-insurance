package com.example.insurance.pet.entity;

import com.example.insurance.pet.entity.code.DiseaseCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "additional_info")
public class AdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_info_id")
    private Long id;

    @Column(name = "additional_info_weight", nullable = false)
    private Integer weight;

    @Column(name = "additional_info_food_count", nullable = false)
    private Integer foodCount;

    @OneToOne
    @JoinColumn(name = "disease_name", nullable = false)
    private DiseaseCode diseaseCode;
}
