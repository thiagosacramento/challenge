package com.bold.challenge.api.resource;

import java.util.List;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "SWAGGER")
@RequestMapping(path = "/api/candidate")
public interface CandidateResource {

	@ApiOperation(value = "Checks if the server is on")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	String checkHealth();

	@ApiOperation(value = "Returns all the candidates")
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	List<CandidateResponse> findAll();

	@ApiOperation(value = "Create a candidate")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	CandidateResponse create(@RequestBody CandidateRequest candidateRequest);

	@ApiOperation(value = "Update a candidate")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<CandidateResponse> update(@RequestBody CandidateRequest candidateRequest);

	@ApiOperation(value = "Delete a candidate by id")
	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity delete(@PathVariable("id") Integer id);
}