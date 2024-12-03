package com.example.insurance;

import com.example.insurance.insurance.service.RecommendServiceImpl;
import com.example.insurance.pet.repository.BreedCodeRepository;
import com.example.insurance.pet.repository.PetRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InsuranceApplicationTests {
	@Autowired
    RecommendServiceImpl service;

	@Test
	void contextLoads() {
//		breedCodeRepository.findAll().forEach(s-> System.out.println(s.getCode()));
		service.searchResult(List.of("슬관절"));
	}

}
