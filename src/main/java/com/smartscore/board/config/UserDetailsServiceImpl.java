package com.smartscore.board.config;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartscore.board.dto.User;
import com.smartscore.board.models.Member;
import com.smartscore.board.repository.MemberCrudRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

	private final MemberCrudRepository crudRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = crudRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
		User user = new User(member.getEmail(), member.getPassword(), new ArrayList<>());
        return getUser(user);
    }


    private UserDetails getUser(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.email())
                .password(user.password())
                .roles(user.roles().toArray(new String[0]))
                .build();
    }
}

