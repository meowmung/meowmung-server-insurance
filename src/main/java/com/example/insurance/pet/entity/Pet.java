package com.example.insurance.pet.entity;

import com.example.insurance.insurance.entity.RecommendedInsurance;
import com.example.insurance.pet.entity.code.BreedCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "pet_age", nullable = false)
    private Integer age;

    @Column(name = "pet_gender", nullable = false)
    private Boolean gender;

    @Column(name = "pet_neutered", nullable = false)
    private Boolean neutered;

    @OneToOne
    @JoinColumn(name = "breed_name", nullable = false)
    private BreedCode breedCode;

    @OneToMany
    @JoinColumn(name = "concerned_id")
    private List<Concerned> concerned = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "additional_info_id", nullable = true)
    private AdditionalInfo additionalInfo;

    @OneToMany
    @JoinColumn(name = "recommended_insurance_id")
    private List<RecommendedInsurance> recommendedInsurance = new ArrayList<>();

    public void addConcerned(List<Concerned> concerned) {
        this.concerned.addAll(concerned);
    }

    // setter 안쓰려면
    public void addAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
