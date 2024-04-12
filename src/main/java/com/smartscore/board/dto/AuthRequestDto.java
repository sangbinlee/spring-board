package com.smartscore.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record AuthRequestDto(
//		@Schema(example = "admin", description = "this filed  use to pass username")
//		String userName,
		@Schema(example = "test4@test.com", description = "this filed  use to pass email")
		String email,
		@Schema(example = "1234567890123456789012345678901234567890", description = "this filed  use to pass password")
		String password
		) {
}
