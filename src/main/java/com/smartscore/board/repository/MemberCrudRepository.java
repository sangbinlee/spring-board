package com.smartscore.board.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smartscore.board.models.Member;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MemberCrudRepository extends CrudRepository<Member, Long> {
	// JpaRepository
	// Page<Member> findAll(Pageable pageable);
	// Page<Member> findByNameContains(String name, Pageable pageable);
//	Optional<Member> findById(Long id);
//	Optional<Member> findById(Long id);

//	Member findByEmail(String email);
	Optional<Member> findByEmail(String email);
}
