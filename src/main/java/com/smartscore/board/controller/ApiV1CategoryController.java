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

import com.smartscore.board.models.Category;
import com.smartscore.board.repository.CategoryRepository;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/category") // This means URL's start with /demo (after Application path)
public class ApiV1CategoryController {

	@Autowired
	private CategoryRepository userRepository2;

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
	public Category create(@RequestBody Category users) {
		return userRepository2.save(users);
	}

	/**
	 * 1. 전체목록 가져오기 retrieve
	 * Ex. http://localhost:8080/api/v1/users
	 * @return
	 */
	@GetMapping()
	public Iterable<Category> retrieveAll() {
		return userRepository2.findAll();
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
	public Iterable<Category> retrieveIds(@RequestBody Map<String, List<Long>> map  ) {
		Iterable<Long> ids = map.get("ids");
		return userRepository2.findAllById( ids);
	}

	/**
	 * 3. 단건 가져오기 retrieve
	 * Ex. http://localhost:8080/api/v1/users/1
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public Optional<Category> retrieveId(@PathVariable(name = "id") Long id) {
		return userRepository2.findById(id);
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
	public Category update(@PathVariable(name = "id") Long id, @RequestBody Category users) {
		return userRepository2.save(users);
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
	public Category updateName(@PathVariable(name = "id") Long id, @RequestBody Category users) {
		return userRepository2.save(users);
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
		userRepository2.deleteAll();
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
		userRepository2.deleteAllById(ids);
	}

	/**
	 * 8. 단건 삭제하기 delete id
	 * Ex. http://localhost:8080/api/v1/users/1
	 * @param id
	 */
	@DeleteMapping("{id}")
	public void deleteId(@PathVariable(name = "id") Long id) {
		userRepository2.deleteById(id);
	}
}
