package com.smartscore.board.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartscore.board.auth.JwtService;
import com.smartscore.board.dto.User;
import com.smartscore.board.repository.Member;
import com.smartscore.board.repository.MemberCrudRepository;
import com.smartscore.board.repository.MemberJpaRepository;
import com.smartscore.board.repository.MemberPagingAndSortingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InMemoryUserDetailsService implements UserDetailsService {
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
	private final MemberCrudRepository crudRepository;

//    private final Map<String, User> users = new ConcurrentHashMap<>();

//    public InMemoryUserDetailsService() {
//        users.put(USER,new User(USER, "{noop}"+USER, List.of(USER_ROLE)));
//        users.put(ADMIN, new User(ADMIN, "{noop}"+ADMIN, List.of(ADMIN_ROLE)));
//    }
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = crudRepository.findByEmail(id);
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

