package com.smartscore.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<Users, Long> { // PagingAndSortingRepository
  Page<Users> findAll(Pageable pageable);
  Page<Users> findByNameContains(String name, Pageable pageable);

}
