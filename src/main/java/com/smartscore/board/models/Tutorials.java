package com.smartscore.board.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tutorials", uniqueConstraints = {
		@UniqueConstraint(columnNames = "title"),
		@UniqueConstraint(columnNames = "description")
		}
)
@Data
public class Tutorials {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String title;

	@NotBlank
	@Size(max = 120)
	private String description;

}
