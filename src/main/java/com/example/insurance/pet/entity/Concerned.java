package com.example.insurance.pet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "concerned")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concerned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concerned_id")
    private Long id;

    @Column(name = "concerned_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

}
