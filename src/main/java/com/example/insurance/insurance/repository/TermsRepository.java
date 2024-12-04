package com.example.insurance.insurance.repository;

import com.example.insurance.insurance.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {
}
