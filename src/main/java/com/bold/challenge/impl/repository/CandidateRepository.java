package com.bold.challenge.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bold.challenge.impl.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
}
