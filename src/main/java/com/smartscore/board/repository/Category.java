package com.smartscore.board.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue
//	(strategy=GenerationType.AUTO)
//	@Column(name = "category_id")
	private Long id;
	private String name;

//	https://vladmihalcea.com/recursive-associations-jpa-hibernate/
//    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "c_id")
//    private Category parent;

//    @OneToMany(fetch = FetchType.LAZY)
//    private Category children;

//	  @ManyToMany
//	  @OneToMany
//	  @JoinTable(name = "menu")
//	            joinColumns = @JoinColumn(name = "parent_id"))
//	  @JoinColumn(name = "c_id")
//	  List<Menu> menu = new ArrayList<Menu>();

	  @OneToMany(mappedBy = "category")
//	  @JoinColumn(name = "category_id")
//	  @JoinColumn(name = "id")
	  private List<Menu> menus = new ArrayList<>();



}
