package com.smartscore.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartscore.board.models.Member;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
