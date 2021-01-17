package com.bold.challenge.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bold.challenge.api.domain.CandidateRequest;
import com.bold.challenge.api.domain.CandidateResponse;
import com.bold.challenge.api.resource.CandidateResource;
import com.bold.challenge.impl.entity.Candidate;
import com.bold.challenge.impl.service.CandidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/candidate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CandidateResourceImpl implements CandidateResource {

	private final CandidateService candidateService;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String checkHealth() {
		return "The Server is ON";
	}

	@Override
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CandidateResponse> findAll() {
		return this.candidateService.getAllCandidates()
				.stream()
				.map(CandidateResourceImpl::toCandidateResponse)
				.collect(Collectors.toList());
	}

	@Override
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CandidateResponse create(@RequestBody CandidateRequest candidateRequest) {
		return this.candidateService.createCandidate(toCandidate(candidateRequest))
				.map(CandidateResourceImpl::toCandidateResponse)
				.orElse(CandidateResponse.builder().build());
	}

	@Override
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CandidateResponse> update(@RequestBody CandidateRequest candidateRequest) {
		return this.candidateService.updateCandidate(toCandidate(candidateRequest))
				.map(candidate -> ResponseEntity.ok(toCandidateResponse(candidate)))
				.orElse(ResponseEntity.noContent().build());
	}

	@Override
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
		return this.candidateService.deleteCandidateById(id)
				.map(deleted -> ResponseEntity.ok(Boolean.TRUE))
				.orElse(ResponseEntity.noContent().build());
	}

	private static CandidateResponse toCandidateResponse(Candidate candidate) {
		return CandidateResponse.builder()
				.id(candidate.getId())
				.name(candidate.getName())
				.address(candidate.getAddress())
				.email(candidate.getEmail())
				.creation(candidate.getCreation())
				.lastUpdate(candidate.getLastUpdate())
				.build();
	}

	private static Candidate toCandidate(CandidateRequest candidateRequest) {
		return Candidate.builder()
				.id(candidateRequest.getId())
				.name(candidateRequest.getName())
				.address(candidateRequest.getAddress())
				.email(candidateRequest.getEmail())
				.creation(LocalDateTime.now())
				.lastUpdate(candidateRequest.getLastUpdate())
				.build();
	}

}
