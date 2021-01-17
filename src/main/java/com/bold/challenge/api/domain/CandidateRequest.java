package com.bold.challenge.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CandidateRequest implements Serializable {

	private static final long serialVersionUID = -5145214014677732481L;
	private Integer id;
	private String name;
	private String address;
	private String email;
	private LocalDateTime creation;
	private LocalDateTime lastUpdate;
}