package org.myapps.family.service;

import java.util.Collection;

import org.myapps.family.data.PersonRepository;
import org.myapps.family.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PersonService {

	private static final Logger log = LoggerFactory
			.getLogger(PersonService.class);

	@Autowired
	PersonRepository personRepository;

	public PersonService() {

	}

	public boolean createParent(Person parent) {
		boolean isSaved = true;

		try {
			Collection<Person> childrens = parent.getChildren();
			for (Person child : childrens) {
				child.setParent(parent);
			}

			personRepository.save(parent);
			personRepository.save(childrens);

		} catch (Exception e) {
			e.printStackTrace();
			isSaved = false;
		}
		return isSaved;
	}

	
}
