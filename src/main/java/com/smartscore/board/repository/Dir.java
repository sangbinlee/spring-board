package com.smartscore.board.repository;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY) // Null 값인 필드 제외
public class Dir {
    @JsonIgnore
	@Id
	@GeneratedValue
//	(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    @Schema(description = "사용자 이름", nullable = false, example = "한식")
	private String name;

//    @OneToOne(mappedBy="id", cascade= CascadeType.ALL)
//    private Dir parent;

	// This field is a table column
	// It identifies the parent of the current row
	// It it will be written as the type of dirId
	// By default this relationship will be eagerly fetched
	// , which you may or may not want

//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//	private Dir parent;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	Dir parent;


	// This field is not a table column
	// It is a collection of those Dir rows that have this row as a parent.
	// This is the other side of the relationship defined by the parent field.
//	@OneToMany(mappedBy = "parent")
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private Set<Dir> children;
//	private List<Files> children;

}
