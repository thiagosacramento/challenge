package com.bold.challenge.impl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bold.challenge.impl.entity.Candidate;
import com.bold.challenge.impl.repository.CandidateRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test candidate service")
class TestCandidateService {

	@Mock
	private CandidateRepository candidateRepository;

	@InjectMocks
	private CandidateService candidateService;

	@DisplayName("Test get all candidate")
	@ParameterizedTest(name = "#{index} - candidate: {0} | Expected: {1}")
	@MethodSource("testGetAllCandidatesProvider")
	void testGetAllCandidates(List<Candidate> candidates) {
		// Given
		when(this.candidateRepository.findAll()).thenReturn(candidates);

		// When
		final List<Candidate> actual = this.candidateService.getAllCandidates();

		// Then
		assertTrue(actual.size() > 0);
	}

	@DisplayName("Test create candidate")
	@ParameterizedTest(name = "#{index} - candidate: {0} | Expected: {1}")
	@MethodSource("testCreateCandidateProvider")
	void testCreateCandidate(Candidate candidate, Optional<Candidate> expected) {
		// Given
		when(this.candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

		// When
		final Optional<Candidate> actual = this.candidateService.createCandidate(candidate);

		// Then
		assertEquals(expected, actual);
	}

	@DisplayName("Test update candidate")
	@ParameterizedTest(name = "#{index} - candidate: {0} | Expected: {1}")
	@MethodSource("testUpdateCandidateProvider")
	void testUpdateCandidate(Candidate candidate, Optional<Candidate> expected) {
		// Given
		if (candidate.getId() > 0) {
			when(this.candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
			when(this.candidateRepository.save(any(Candidate.class))).thenReturn(candidate);
		}

		// When
		final Optional<Candidate> actual = this.candidateService.updateCandidate(candidate);

		// Then
		assertEquals(expected, actual);
	}

	@DisplayName("Test delete candidate")
	@ParameterizedTest(name = "#{index} - candidate: {0} | id: {1} | Expected: {2}")
	@MethodSource("testDeleteCandidateProvider")
	void testDeleteCandidate(Candidate candidate, Integer id, Optional<Boolean> expected) {
		// Given
		if (id > 0) {
			when(this.candidateRepository.findById(id)).thenReturn(Optional.of(candidate));
		}

		// When
		final Optional<Boolean> actual = this.candidateService.deleteCandidateById(id);

		// Then
		assertEquals(expected, actual);
	}

	private static Stream<Arguments> testCreateCandidateProvider() {
		final Candidate candidate =
				Candidate.builder()
						.name("THIAGO SACRAMENTO")
						.address("AV. ULTRAMAR")
						.build();

		final Candidate invalidCandidate = Candidate.builder().build();

		return Stream.of(Arguments.of(candidate, Optional.of(candidate)),
				Arguments.of(invalidCandidate, Optional.of(invalidCandidate))
		);
	}

	private static Stream<Arguments> testUpdateCandidateProvider() {
		final Candidate candidate =
				Candidate.builder()
						.id(1)
						.name("THIAGO SACRAMENTO")
						.build();

		final Candidate invalidCandidate = Candidate.builder().id(0).build();

		return Stream.of(Arguments.of(candidate, Optional.of(candidate)),
				Arguments.of(invalidCandidate, Optional.empty())
		);
	}

	private static Stream<Arguments> testDeleteCandidateProvider() {
		final Candidate candidate = Candidate.builder().id(1).build();

		final Candidate invalidCandidate = Candidate.builder().id(0).build();

		return Stream.of(Arguments.of(candidate, 1, Optional.of(Boolean.TRUE)),
				Arguments.of(invalidCandidate, 0, Optional.empty())
		);
	}

	private static Stream<Arguments> testGetAllCandidatesProvider() {
		final Candidate candidate =
				Candidate.builder()
						.id(1)
						.name("THIAGO")
						.build();

		final Candidate candidate1 =
				Candidate.builder()
						.id(2)
						.name("SACRAMENTO")
						.build();

		return Stream.of(Arguments.of(Arrays.asList(candidate, candidate1))
		);
	}
}