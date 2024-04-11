package com.smartscore.board.auth;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartscore.board.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Autowired
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtUtil;

//	public static void main(String[] args) {
//		String test = "Bearer 12345678901234567890";
//		log.info("test.substring(7)={}", test.substring(7));
//	}
//    @Autowired
//    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        //Bearer 2352345235sdfrsfgsdfsdf
        log.info(" Header :  {}", request);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {

            //looking good
            token = requestHeader.substring(7);


            if (jwtUtil.validateToken(token)) {
            	Long memberId = jwtUtil.getMemberId();

            	UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId.toString());
            	
                if (userDetails != null) {
                    //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }  
        filterChain.doFilter(request, response); //doubt hai
    }
}
