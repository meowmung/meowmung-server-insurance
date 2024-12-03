package com.example.insurance.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "results")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Results {
    @Id
    @Column(name = "result_id")
    private Long id;

    @Column(name = "disease_name")
    private String diseaseName;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    @JsonIgnore
    public Terms terms;



}
