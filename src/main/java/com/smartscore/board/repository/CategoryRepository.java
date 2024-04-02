package com.smartscore.board.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//public interface CategoryRepository extends JpaRepository<Category, Long> {
	public interface CategoryRepository extends CrudRepository<Category, Long> {

	List<Category> findByName(String name);

	Category findById(long id);
}
