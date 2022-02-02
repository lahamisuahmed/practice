package com.practice.repository;

import com.practice.model.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlternativeRepository extends JpaRepository<Alternative,  Long> {
}
