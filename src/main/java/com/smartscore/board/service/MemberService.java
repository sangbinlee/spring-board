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
public class MemberService implements UserDetailsService{


	private final MemberPagingAndSortingRepository pagingAndSortingRepository;
	private final MemberJpaRepository jpaRepository;
	private final MemberCrudRepository crudRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtUtil;

	public Member signup(@RequestBody Member member) {
		log.info("member={}", member);
		return crudRepository.save(member);
	}

	/**
	 * email
	 * password
	 *
	 * @param dto
	 * @return
	 */
	public String login(Member dto) {

        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = jpaRepository.findByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        MemberDto memberDto = modelMapper.map(member, MemberDto.class);

        String accessToken = jwtUtil.createAccessToken(member);
        String accessToken2 = jwtUtil.createAccessToken(memberDto);

        log.info("accessToken={}", accessToken);
        log.info("accessToken2={}", accessToken2);
        return accessToken;

	}

	@Override
	public UserDetails loadUserByUsername(Long id) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Member> member =  crudRepository.findById(id);

		return member.;
	}


}
