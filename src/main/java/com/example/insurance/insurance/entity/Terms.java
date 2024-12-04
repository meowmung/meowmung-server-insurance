package com.example.insurance.insurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terms")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Terms {
    @Id
    @Column(name = "term_id")
    private String id;

    @Column(name = "term_name")
    private String name;

    @Column(name = "term_causes")
    private String causes;

    @Column(name = "term_limits")
    private String limits;

    @Column(name = "term_details")
    private String details;

    // 하나의 특약은 여러개의 추천 결과
    @OneToMany(mappedBy = "terms", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Results> results;

    // 하나의 보험은 여러개의 특약
    @ManyToOne
    @JoinColumn(name = "insurance_id", nullable = false)
    @JsonIgnore
    private Insurance insurance;
}
