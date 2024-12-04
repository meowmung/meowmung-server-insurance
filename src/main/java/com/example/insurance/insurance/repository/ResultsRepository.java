package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.entity.Results;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultsRepository extends JpaRepository<Results, Long> {
    @Query("""
            SELECT i FROM Insurance i JOIN  i.terms t WHERE i.insuranceId IN (
            SELECT t.insuranceId FROM 
                    (SELECT
                        i.insuranceId insuranceId
                    FROM Terms t
                    INNER JOIN t.results r 
                    INNER JOIN t.insurance i
                    WHERE r.diseaseName IN :predictionDiseaseName 
                    AND t.id LIKE CONCAT('%', :petType, '%')
                    GROUP BY t.insurance.insuranceId
                    ORDER BY  COUNT(i.insuranceId) DESC
                    LIMIT 1
                    ) t)
            """)
    Optional<Insurance> getResult(@Param("predictionDiseaseName") String predictionDiseaseName
            , @Param("petType") String petType);

    @Query("""
            SELECT i FROM Insurance i JOIN FETCH i.terms t WHERE i.insuranceId IN ( 
                SELECT t.insuranceId FROM 
                    (SELECT
                        i.insuranceId insuranceId
                    FROM Terms t
                    INNER JOIN t.results r 
                    INNER JOIN t.insurance i
                    WHERE r.diseaseName IN :concernedNames 
                    AND t.id LIKE CONCAT('%', :petType, '%')
                    GROUP BY t.insurance.insuranceId
                    ORDER BY  COUNT(i.insuranceId) DESC
                    LIMIT 2
                    ) t)
            """)
    List<Insurance> getResults(@Param("concernedNames") List<String> concernedNames
            , @Param("petType") String petType);
}
