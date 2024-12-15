package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.Insurance;
import com.example.insurance.insurance.entity.Results;
import com.example.insurance.insurance.entity.Terms;
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

    // terms 뽑는 쿼리
//    @Query(value = """
//            SELECT *\s
//            FROM terms\s
//            WHERE term_id IN (
//                SELECT sub.term_id
//                FROM (
//                    SELECT t.*, COUNT(t.insurance_id) OVER (PARTITION BY t.insurance_id) AS insurance_count
//                    FROM terms AS t
//                    INNER JOIN results AS r ON t.term_id = r.term_id
//                    WHERE r.disease_name = '슬관절'
//                      AND r.term_id LIKE '%dog%'
//                ) AS sub
//                INNER JOIN (
//                    SELECT t.insurance_id
//                    FROM terms AS t
//                    INNER JOIN results AS r ON t.term_id = r.term_id
//                    WHERE r.disease_name = '슬관절'
//                      AND r.term_id LIKE '%dog%'
//                    GROUP BY t.insurance_id
//                    ORDER BY COUNT(t.insurance_id) DESC
//                    LIMIT 2
//                ) AS top_insurance ON sub.insurance_id = top_insurance.insurance_id
//            )
//            """, nativeQuery = true)
//    List<Insurance> getResults(@Param("concernedNames") List<String> concernedNames
//            , @Param("petType") String petType);

//     Insurance 뽑는 쿼리
//@Query("""
//    SELECT sub
//        FROM (
//            SELECT t, COUNT(t.insurance.insuranceId) OVER (PARTITION BY t.insurance.insuranceId) AS insurance_count
//            FROM Terms t
//            INNER JOIN Results r ON t.id = r.terms.id
//            WHERE r.diseaseName = :concernedNames
//            AND r.terms.id LIKE CONCAT('%', :petType, '%')
//        ) AS sub
//        INNER JOIN (
//            SELECT t.insurance.insuranceId
//            FROM Terms t
//            INNER JOIN Results r ON t.id = r.terms.id
//            WHERE r.diseaseName = :concernedNames
//            AND r.terms.id LIKE CONCAT('%', :petType, '%')
//            GROUP BY t.insurance.insuranceId
//            ORDER BY COUNT(t.insurance.insuranceId) DESC
//            LIMIT 2
//        ) AS top_insurance ON sub.insurance.insuranceId = top_insurance.insuranceId
//""")
//    List<Insurance> getResults(@Param("concernedNames") List<String> concernedNames
//            , @Param("petType") String petType);


//    @Query("""
//                SELECT i
//                FROM Insurance i
//                JOIN i.terms t
//                WHERE t.id IN (
//                    SELECT sub.termId
//                    FROM (
//                        SELECT t.id AS termId, COUNT(t.insurance.insuranceId) OVER (PARTITION BY t.insurance.insuranceId) AS insuranceCount
//                        FROM Terms t
//                        JOIN t.results r
//                        WHERE r.diseaseName = ':concernedNames'
//                          AND r.termId LIKE %:petType%
//                    ) AS sub
//                    WHERE sub.insuranceCount IN (
//                        SELECT DISTINCT t.insurance.insuranceId
//                        FROM Terms t
//                        JOIN t.results r
//                        WHERE r.diseaseName = ':concernedNames'
//                          AND r.termId LIKE %:petType%
//                        GROUP BY t.insurance.insuranceId
//                        ORDER BY COUNT(t.insurance.insuranceId) DESC
//                        LIMIT 2
//                    )
//                )
//            """, nativeQuery = true)
//    List<Insurance> getResults(@Param("concernedNames") List<String> concernedNames
//                            , @Param("petType") String petType);





//
//
//    @Query("""
//            SELECT t
//            FROM Terms t
//            INNER JOIN t.results r
//            WHERE t.insurance.insuranceId = :insuranceId
//            AND r.diseaseName IN :concernedNames
//        """)
//    List<Terms> getTerms(@Param("insuranceId") String insuranceId
//            , @Param("concernedNames") List<String> concernedNames);

}
