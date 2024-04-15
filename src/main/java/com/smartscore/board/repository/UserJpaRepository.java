package com.smartscore.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartscore.board.models.User;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserJpaRepository extends JpaRepository<User, Long> { // PagingAndSortingRepository
//	public interface UserRepository extends CrudRepository<User, Long> {
//	JpaRepository
  Page<User> findAll(Pageable pageable);
//  Page<User> findByNameContains(String name, Pageable pageable);
}
