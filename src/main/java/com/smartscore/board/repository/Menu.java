package com.smartscore.board.repository;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable{

	@Id
	@GeneratedValue
//	(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
	private String name;


    @ManyToOne
//    @JoinColumn(name = "parent_ID", insertable = false, updatable = false)
    private Category category;

}
