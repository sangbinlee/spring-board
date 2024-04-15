package com.smartscore.board.service;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartscore.board.auth.JwtService;
import com.smartscore.board.dto.AuthRequestDto;
import com.smartscore.board.models.Member;
import com.smartscore.board.repository.MemberCrudRepository;
import com.smartscore.board.repository.MemberJpaRepository;
import com.smartscore.board.repository.MemberPagingAndSortingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
//public class MemberService  {

	private final MemberPagingAndSortingRepository pagingAndSortingRepository;
	private final MemberJpaRepository jpaRepository;
	private final MemberCrudRepository crudRepository;
	private final PasswordEncoder encoder;
	private final ModelMapper modelMapper;
	private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

	public Member signup(@RequestBody Member member) {
		log.info("member={}", member);
		String password = member.getPassword();
		String password_encoded = encoder.encode(password);
		member.setPassword(password_encoded);
		return crudRepository.save(member);
	}



    public Map<String, String> getToken( UserDetails userDetails) {
        final var roles = userDetails.getAuthorities();
        final var username = userDetails.getUsername();
        final var token = jwtService.generateToken(Map.of("role", roles), username);
        return Map.of("token", token);
    }
	/**
	 * email password
	 *
	 * @param dto
	 * @return
	 */
	public Map<String, String> login(AuthRequestDto authRequestDto) {


	       final var authenticate = authenticationManager
	    		   .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.email(), authRequestDto.password()));
	       final var userDetails =  (UserDetails) authenticate.getPrincipal();
	       return   getToken(userDetails);

//		String email = authRequestDto.email();
//		String password = authRequestDto.password();
//		Member member = jpaRepository.findByEmail(email);
//		if (member == null) {
//			throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
//		}
//
//		// 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
//		if (!encoder.matches(password, member.getPassword())) {
//			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//		}
//		MemberDto memberDto = modelMapper.map(member, MemberDto.class);
//
//		String accessToken = jwtService.createAccessToken(member);
//		String accessToken2 = jwtService.createAccessToken(memberDto);
//
//		log.info("accessToken={}", accessToken);
//		log.info("accessToken2={}", accessToken2);
//
//
//
//		Map<String, Object> claimMap = new HashMap<>();
//		claimMap.put("accessToken", accessToken);
//		claimMap.put("accessToken2", accessToken2);
//		return claimMap;

	}

//	@Override
//	public UserDetails loadUserByUsername(String id)
//			throws org.springframework.security.core.userdetails.UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		Member member = crudRepository.findById(Long.parseLong(id)).orElseThrow();
////		Member member = optional.get();
//		UserDetails info = modelMapper.map(member, UserDetails.class);
//		return info;
//	}

}
