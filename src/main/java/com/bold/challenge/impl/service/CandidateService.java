package com.bold.challenge.impl.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bold.challenge.impl.entity.Candidate;
import com.bold.challenge.impl.repository.CandidateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CandidateService {

	private final CandidateRepository candidateRepository;

	public List<Candidate> getAllCandidates() {
		return Optional.of(this.candidateRepository.findAll())
				.orElse(Collections.emptyList());
	}

	public Optional<Candidate> createCandidate(Candidate candidate) {
		return Optional.of(this.candidateRepository.save(candidate));
	}

	public Optional<Candidate> updateCandidate(Candidate candidate) {
		return this.candidateRepository.findById(candidate.getId())
				.map(can -> {
					final Candidate c =
							Candidate.builder()
									.id(can.getId())
									.name(candidate.getName())
									.address(candidate.getAddress())
									.email(candidate.getEmail())
									.creation(can.getCreation())
									.lastUpdate(LocalDateTime.now())
									.build();
					return this.candidateRepository.save(c);
				});
	}

	public Optional<Boolean> deleteCandidateById(Integer id) {
		return this.candidateRepository.findById(id)
				.map(c -> {
					this.candidateRepository.deleteById(c.getId());
					return Boolean.TRUE;
				});
	}

}
