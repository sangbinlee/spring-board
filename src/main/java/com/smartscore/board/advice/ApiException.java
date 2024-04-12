package com.smartscore.board.advice;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionEnum error;

    public ApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
