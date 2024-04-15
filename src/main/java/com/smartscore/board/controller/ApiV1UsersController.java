package com.smartscore.board.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartscore.board.models.User;
import com.smartscore.board.repository.UserCrudRepository;
import com.smartscore.board.repository.UserJpaRepository;
import com.smartscore.board.repository.UserPagingAndSortingRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/users") // This means URL's start with /demo (after Application path)
@Tag(name = "User 컨트롤러", description = "Users API입니다.")
public class ApiV1UsersController {

	@Autowired
	private UserCrudRepository userCrudRepository2;
	@Autowired
	private UserJpaRepository userJpaRepository;
	@Autowired
	private UserPagingAndSortingRepository userPagingAndSortingRepository;

	@Autowired
	private UserCrudRepository userCrudRepository;

	/**
	 * 0. 등록하기 create
	 * Ex. req body (json)
	 * {
	 * 	"name": "3 Add your name in the body",
	 *  "email": "emailemailemail"
	 * }
	 * @param users
	 * @return 등록된 값 반환
	 */
	@PostMapping()
	public User create(@RequestBody User user) {
		return userCrudRepository.save(user);
	}

	/**
	 * 다중 데이터
	 * 	[
	 * 		{
	 *   		"name": "333 Add your name in the body"
	 * 		},
	 * 		{
	 *    		"name": "444 Add your name in the body"
	 * 		}
	 * 	]
	 * @param users
	 * @return
	 */
	@PostMapping("multi-add")
	public List<User> creates(@RequestBody List<User> users) {
		for (User user : users) {
			userCrudRepository.save(user);
		}
		return users;
	}

	/**
	 * 1. 전체목록 가져오기 retrieve
	 * Ex. http://localhost:8080/api/v1/users
	 * @return
	 */
	@GetMapping()
	public Iterable<User> retrieveAll() {
		return userCrudRepository.findAll();
	}

	/**
	 * 2. 부분목록 가져오기 retrieve
	 * Ex. http://localhost:8080/api/v1/users/ids
	 * Ex. req body (json)
	 * { "ids": [ 1, 2, 3, 4 ] }
	 * @param map
	 * @return
	 */
	@GetMapping("ids")
	public Iterable<User> retrieveIds(@RequestBody Map<String, List<Long>> map  ) {
		Iterable<Long> ids = map.get("ids");
		return userCrudRepository.findAllById( ids);
	}

	/**
	 * 3. 단건 가져오기 retrieve
	 * Ex. http://localhost:8080/api/v1/users/1
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public Optional<User> retrieveId(@PathVariable(name = "id") Long id) {
		return userCrudRepository.findById(id);
	}

	/**
	 * 4. 수정하기 update
	 * Ex. http://localhost:8080/api/v1/users/1
	 * Ex. req body (json)
	 * {
	 *  "id": "1",
	 * 	"name": "3 수정 Add your name in the body",
	 *  "email": "수정 emailemailemail"
	 * }
	 * @param id
	 * @return
	 */
	@PutMapping("{id}")
	public User update(@PathVariable(name = "id") Long id, @RequestBody User users) {
		return userCrudRepository.save(users);
	}

	/**
	 * 5. 이름 수정하기 update ---------------------------- jpa대신 mybatis 사용해야함.  부분 업데이트 쿼리 만들어야함.
	 *  ★★★★ jpa는 부분 업데이트 안됨
	 * Ex. http://localhost:8080/api/v1/users/1
	 * Ex. req body (json)
	 * {
	 * 	"name": "Add your name in the body"
	 * }
	 * @param id
	 * @return
	 */
	@PatchMapping("{id}")
	@Deprecated
	public User updateName(@PathVariable(name = "id") Long id, @RequestBody User users) {
		return userCrudRepository.save(users);
	}

	/**
	 * 6. 전체목록 삭제하기 delete all
	 * Ex. http://localhost:8080/api/v1/users
	 * Ex. req body (json)
	 * {
	 *  "id": "1",
	 * 	"name": "3 수정 Add your name in the body",
	 *  "email": "수정 emailemailemail"
	 * }
	 * @param id
	 * @return
	 */
	@DeleteMapping()
	public void deleteAll() {
		userCrudRepository.deleteAll();
	}

	/**
	 * 7. 부분목록 삭제하기 delete ids
	 * Ex. http://localhost:8080/api/v1/users/ids
	 * Ex. req body (json)
	 * { "ids": [ 1, 2, 3, 4 ] }
	 * @param map
	 */
	@DeleteMapping("ids")
	public void deleteIds(@RequestBody Map<String, List<Long>> map  ) {
		Iterable<Long> ids = map.get("ids");
		userCrudRepository.deleteAllById(ids);
	}

	/**
	 * 8. 단건 삭제하기 delete id
	 * Ex. http://localhost:8080/api/v1/users/1
	 * @param id
	 */
	@DeleteMapping("{id}")
	public void deleteId(@PathVariable(name = "id") Long id) {
		userCrudRepository.deleteById(id);
	}
}
