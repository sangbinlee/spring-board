package com.smartscore.board.exception;

public class BadCredentialsException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -3815115505745055680L;

	public BadCredentialsException(String id) {
		super("Could not find employee " + id);
	}

}
