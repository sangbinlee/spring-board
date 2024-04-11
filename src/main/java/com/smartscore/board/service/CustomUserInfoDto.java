package com.smartscore.board.service;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CustomUserInfoDto {

	private Long id;

	private String memberId;

	private String username;

	private String password;

	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;
}
