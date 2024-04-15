package com.smartscore.board.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartscore.board.auth.JwtService;
import com.smartscore.board.dto.AuthRequestDto;
import com.smartscore.board.exception.EmployeeNotFoundException;
import com.smartscore.board.models.Member;
import com.smartscore.board.repository.MemberCrudRepository;
import com.smartscore.board.repository.MemberJpaRepository;
import com.smartscore.board.repository.MemberPagingAndSortingRepository;
import com.smartscore.board.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@RestController(value = "/clients")
@RestController
@RequestMapping(path = "/api/v1/member") // This means URL's start with /api (after Application path)
@Tag(name = "Clients")
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {

	private final MemberPagingAndSortingRepository pagingAndSortingRepository;
	private final MemberJpaRepository jpaRepository;
	private final MemberCrudRepository crudRepository;
    private final JwtService jwtService;
    private final MemberService memberService;

	@PostMapping("signup")
	Member signup(@RequestBody Member member) {
		log.info("member={}", member);
		return memberService.signup(member);
	}
//	@GetMapping("login")
//	Map<String, Object> login(@RequestBody Member member) {
//		return memberService.login(member);
//	}
	@PostMapping("login")
	Map<String, String> login(@RequestBody AuthRequestDto member) {
		return memberService.login(member);
	}

	@PostMapping()
	Member newEmployee(@RequestBody Member newEmployee) {
		return crudRepository.save(newEmployee);
	}

	@GetMapping()
	Iterable<Member> all() {
		return crudRepository.findAll();
	}

	@GetMapping("{id}")
	Member one(@PathVariable Long id) {
		return crudRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

//	@PutMapping("{id}")
//	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
//
//		return repository.findById(id).map(employee -> {
//			employee.setName(newEmployee.getName());
//			employee.setRole(newEmployee.getRole());
//			return repository.save(employee);
//		}).orElseGet(() -> {
//			newEmployee.setId(id);
//			return repository.save(newEmployee);
//		});
//	}

	@DeleteMapping()
	void deleteAllEmployee() {
		crudRepository.deleteAll();
	}

	@DeleteMapping("{id}")
	void deleteEmployee(@PathVariable Long id) {
		crudRepository.deleteById(id);
	}
}
