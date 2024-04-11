package com.smartscore.board.exception;

public class UsernameNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -3815115505745055680L;

	public UsernameNotFoundException(String id) {
		super("Could not find employee " + id);
	}

}
