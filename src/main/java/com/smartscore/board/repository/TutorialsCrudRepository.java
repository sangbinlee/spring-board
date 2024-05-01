package com.smartscore.board.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartscore.board.models.Tutorials;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

//public interface UserCrudRepository extends PagingAndSortingRepository<User, Long> { // PagingAndSortingRepository
public interface TutorialsCrudRepository extends CrudRepository<Tutorials, Long> {

}
