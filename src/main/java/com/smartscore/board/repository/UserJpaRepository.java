package com.smartscore.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserJpaRepository extends JpaRepository<Users, Long> { // PagingAndSortingRepository
//	public interface UserRepository extends CrudRepository<User, Long> {
//	JpaRepository
  Page<Users> findAll(Pageable pageable);
  Page<Users> findByNameContains(String name, Pageable pageable);

}
