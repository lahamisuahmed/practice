package com.practice.repository;

import com.practice.model.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlternativeRepository extends JpaRepository<Alternative,  Long> {
}
