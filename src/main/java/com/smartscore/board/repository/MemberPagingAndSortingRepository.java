package com.smartscore.board.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MemberPagingAndSortingRepository extends PagingAndSortingRepository<Member, Long> {
	// JpaRepository
	// Page<Member> findAll(Pageable pageable);
	// Page<Member> findByNameContains(String name, Pageable pageable);

}
