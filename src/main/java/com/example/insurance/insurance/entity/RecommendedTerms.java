package com.example.insurance.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recommended_terms")
public class RecommendedTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_terms_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recommended_insurance_id")
    private RecommendedInsurance recommendedInsurance;
}
