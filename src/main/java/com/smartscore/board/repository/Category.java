package com.smartscore.board.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue
//	(strategy=GenerationType.AUTO)
	private Long id;
	private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

//	@ManyToMany(fetch = FetchType.LAZY)
//    private Category parent;

//	@OneToMany(fetch = FetchType.LAZY)
//    private Category parent;


}
