package com.smartscore.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.smartscore.board.models.RefreshToken;
import com.smartscore.board.models.User;
import java.util.List;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  @Modifying
  int deleteByUser(User user);

  List<RefreshToken> findByUser(User user);
}
