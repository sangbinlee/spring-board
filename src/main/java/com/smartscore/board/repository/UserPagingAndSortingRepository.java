package com.smartscore.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.smartscore.board.models.User;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, Long> { // PagingAndSortingRepository
  Page<User> findAll(Pageable pageable);
//  Page<User> findByNameContains(String name, Pageable pageable);

}
