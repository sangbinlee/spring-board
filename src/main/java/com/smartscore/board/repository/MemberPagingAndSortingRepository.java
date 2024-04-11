package com.smartscore.board.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MemberPagingAndSortingRepository extends PagingAndSortingRepository<Member, Long> {

//	Optional<Member> List<Member> findById(Long id);
	// JpaRepository
	// Page<Member> findAll(Pageable pageable);
	// Page<Member> findByNameContains(String name, Pageable pageable);

}
