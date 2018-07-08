package org.myapps.family.resource;
import org.myapps.family.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/parents")
@Api(value = "Family Maintenance")

public class PersonController {

	private static final Logger log = LoggerFactory
			.getLogger(PersonController.class);

	@PostMapping
	public ResponseEntity<?> createParent(@RequestBody final Person parent) {

		return new ResponseEntity<Person>(parent, HttpStatus.CREATED);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getParent(@PathVariable("id") Long parentId) {

		return new ResponseEntity<Person>(new Person(), HttpStatus.OK);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePerson(@PathVariable("id") Long id,
			@RequestBody Person person) {

		return new ResponseEntity<Person>(new Person(), HttpStatus.NO_CONTENT);

	}

	@PutMapping(value = "/children/{id}")
	public ResponseEntity<?> updateChildren(@PathVariable("id") Long id,
			@RequestBody Person person) {

		return new ResponseEntity<Person>(new Person(), HttpStatus.NO_CONTENT);

	}

}
