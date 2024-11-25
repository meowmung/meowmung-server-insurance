package com.example.insurance.pet.repository;

import com.example.insurance.pet.entity.Concerned;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcernedRepository extends JpaRepository<Concerned, Long> {
}
