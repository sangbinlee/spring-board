package com.smartscore.board.exception;

public class EmployeeNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -3815115505745055680L;

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}

}
