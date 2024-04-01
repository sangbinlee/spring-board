package com.smartscore.board.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {

	@Id @GeneratedValue
	private Long id;

	private String name;
	private String role;

}
