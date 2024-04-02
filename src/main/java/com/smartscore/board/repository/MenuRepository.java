package com.smartscore.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
//	public interface CategoryRepository extends CrudRepository<Customer, Long> {

	List<Menu> findByName(String name);

	Menu findById(long id);
}
