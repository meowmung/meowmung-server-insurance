package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, String> {
}
