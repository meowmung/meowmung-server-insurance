package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.DiseaseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiseaseCodeRepository extends JpaRepository<DiseaseCode, String> {
    DiseaseCode findByName(String name);

    @Query("SELECT d.name FROM DiseaseCode d WHERE d.code = :code")
    String findByCode(@Param("code") int code);
}
