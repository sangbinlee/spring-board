package com.smartscore.board.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartscore.board.auth.JwtService;
import com.smartscore.board.exception.BadCredentialsException;
import com.smartscore.board.exception.EmployeeNotFoundException;
import com.smartscore.board.exception.UsernameNotFoundException;
import com.smartscore.board.repository.Member;
import com.smartscore.board.repository.MemberCrudRepository;
import com.smartscore.board.repository.MemberDto;
import com.smartscore.board.repository.MemberJpaRepository;
import com.smartscore.board.repository.MemberPagingAndSortingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
//public class MemberService  {

//	private final MemberPagingAndSortingRepository pagingAndSortingRepository;
//	private final MemberJpaRepository jpaRepository;
	private final MemberCrudRepository crudRepository;
//	private final PasswordEncoder encoder;
	private final ModelMapper modelMapper;

	public Member signup(@RequestBody Member member) {
		log.info("member={}", member);
		return crudRepository.save(member);
	}


	@Override
	public UserDetails loadUserByUsername(String id)
			throws org.springframework.security.core.userdetails.UsernameNotFoundException {
		// TODO Auto-generated method stub
		Member member = crudRepository.findById(Long.parseLong(id)).orElseThrow();
//		Member member = optional.get();
		UserDetails info = modelMapper.map(member, UserDetails.class);
		return info;
	}

}
