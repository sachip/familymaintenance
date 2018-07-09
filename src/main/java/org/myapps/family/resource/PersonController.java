package org.myapps.family.resource;
import java.util.ArrayList;
import java.util.List;

import org.myapps.family.domain.Person;
import org.myapps.family.service.PersonService;
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

import com.google.common.base.Preconditions;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/parents")
@Api(value = "Family Maintenance")

public class PersonController {

	private static final Logger log = LoggerFactory
			.getLogger(PersonController.class);

	private PersonService personService;

	public PersonController(PersonService personService) {
		this.personService = personService;

	}

	@PostMapping
	public ResponseEntity<?> createParent(@RequestBody final Person parent) {

		log.debug("PersonController :: - > createParent()");
		boolean isCreated = true;

		try {
			// Basic Validation
			if (!validatePersonAttributes(parent)) {

				return new ResponseEntity<String>(
						"FirstName And Gender Must be entered ",
						HttpStatus.BAD_REQUEST);
			}
			isCreated = personService.createParent(parent);

		} catch (Exception ex) {
			log.error("EX", ex);
			isCreated = false;

		}
		if (isCreated) {
			return new ResponseEntity<Person>(parent, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Failed To Create Resource",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping
	public ResponseEntity<?> getParents() {
		log.debug("PersonController :: - > getParents()");

		List<Person> persons = new ArrayList();
		try {
			persons = personService.findParentWithChildrens();

		} catch (Exception ex) {
			log.error("EX", ex);

		}
		if (persons != null && !persons.isEmpty()) {
			return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Person>>(persons,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getParent(@PathVariable("id") Long parentId) {
		log.debug("PersonController :: - > getParent()");

		Person person = new Person();
		try {
			person = personService.findParent(parentId);

		} catch (Exception ex) {
			log.error("EX", ex);
			return new ResponseEntity<Person>(person,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (person != null) {
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<Person>(person,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePerson(@PathVariable("id") Long id,
			@RequestBody Person person) {
		log.debug("PersonController :: - > updatePerson()");
		boolean isUpdated = true;
		try {
			isUpdated = personService.updatePerson(id, person);

		} catch (Exception ex) {
			log.error("EX", ex);
			isUpdated = false;

		}
		if (isUpdated) {
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed To Update Resource",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping(value = "/children/{id}")
	public ResponseEntity<?> updateChildren(@PathVariable("id") Long id,
			@RequestBody Person person) {
		log.debug("PersonController :: - > updateChildren()");
		boolean isUpdated = true;
		try {
			isUpdated = personService.updatePerson(id, person);

		} catch (Exception ex) {
			log.error("EX", ex);

		}

		if (isUpdated) {
			return new ResponseEntity<Person>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Failed To Update Resource",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Basic Validation ( First Name and Gender )
	public boolean validatePersonAttributes(Person person) {
		boolean isValidAttribute = true;
		try {
			if (person.getFirstName().isEmpty()
					|| person.getGender().trim().isEmpty()) {
				isValidAttribute = false;
			}

		} catch (Exception e) {
			isValidAttribute = false;
		}
		return isValidAttribute;

	}
}
