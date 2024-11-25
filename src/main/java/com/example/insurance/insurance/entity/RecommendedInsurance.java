package com.example.insurance.insurance.entity;

import com.example.insurance.pet.entity.Pet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recommended_insurance")
public class RecommendedInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_insurance_id")
    private Long id;

    @Column(name = "recommended_insurance_level", nullable = false)
    private Integer level;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany
    @JoinColumn(name = "recommended_terms_id")
    private List<RecommendedTerms> recommendedTerms = new ArrayList<>();

}
