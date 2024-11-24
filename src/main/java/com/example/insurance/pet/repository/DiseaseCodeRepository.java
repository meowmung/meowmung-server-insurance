package com.example.insurance.pet.repository;

import com.example.insurance.pet.entity.code.DiseaseCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseCodeRepository extends JpaRepository<DiseaseCode, String> {
}
