package com.smartscore.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartscore.board.exception.EmployeeNotFoundException;
import com.smartscore.board.models.Employee;
import com.smartscore.board.repository.EmployeeRepository;

@RestController
@RequestMapping(path = "employees") // This means URL's start with /api (after Application path)
public class EmployeeController {

	private final EmployeeRepository repository;

	public EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	@PostMapping()
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	@GetMapping()
	List<Employee> all() {
		return repository.findAll();
	}

	@GetMapping("{id}")
	Employee one(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	@PutMapping("{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	@DeleteMapping()
	void deleteAllEmployee() {
		repository.deleteAll();
	}
	@DeleteMapping("{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
