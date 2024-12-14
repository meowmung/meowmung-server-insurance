package com.example.insurance.insurance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insurance")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Insurance {
    @Id
    @Column(name = "insurance_id")
    private String insuranceId;

    @Column(name = "company")
    private String company;

    @Column(name = "insurance_item")
    private String insuranceItem;

    @Column(name = "logo")
    private String logo;

    // 하나의 보험은 여러개의 특약
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Terms> terms;

    public void addTerms(List<Terms> ters) {
        this.terms = terms;
    }

}
