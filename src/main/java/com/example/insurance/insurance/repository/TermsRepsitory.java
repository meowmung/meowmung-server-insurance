package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.Terms;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TermsRepsitory extends JpaRepository<Terms, String> {
    @Query("""
            SELECT t
            FROM Terms t
            INNER JOIN t.results r
            WHERE t.insurance.insuranceId = :insuranceId
            AND r.diseaseName IN :concernedNames
        """)
    List<Terms> getTerms(@Param("insuranceId") String insuranceId
            , @Param("concernedNames") List<String> concernedNames);

    @Query("""
            SELECT t
            FROM Terms t
            INNER JOIN t.results r
            WHERE t.insurance.insuranceId = :insuranceId
            AND r.diseaseName IN :predictionDiseaseName
        """)
    List<Terms> getSecondTerms(@Param("insuranceId") String insuranceId
            , @Param("predictionDiseaseName") String predictionDiseaseName);

}
