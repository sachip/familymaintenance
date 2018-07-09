package org.myapps.family.resource;
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

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/parents")
@Api(value = "Family Maintenance")

public class PersonController {

	private static final Logger log = LoggerFactory
			.getLogger(PersonController.class);
	
	
	private PersonService personService;
     
		
	   
	    public PersonController(PersonService personService) {
	    	this.personService=personService;
	    
	    }
	    

	@PostMapping
	public ResponseEntity<?> createParent(@RequestBody final Person parent) {

		  if(personService.createParent(parent)){
	    		return new ResponseEntity<Person>(parent, HttpStatus.CREATED);
	    	} else {
	    		return new ResponseEntity<String>("Failed to create resource", HttpStatus.OK);
	    	}

	}

	 @GetMapping
	    public List<Person> getParents(){
	   
	    	return personService.findParentWithChildrens();
	       
	    }
	    
	    @GetMapping(value = "/{id}")
	    public Person getParent(@PathVariable("id") Long parentId){
	    	
	    	return personService.findParent(parentId);
	       
	    }
	    @PutMapping(value = "/{id}")
	    public ResponseEntity<?> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
	    	
	        if(personService.updateParent(id, person)){
	    		return new ResponseEntity<Person>(person, HttpStatus.NO_CONTENT);
	    	} else {
	    		return new ResponseEntity<String>("Fail to creat parent", HttpStatus.OK);
	    	}
		    	
	        
	    }
	    
	    @PutMapping(value = "/children/{id}")
	    public ResponseEntity<?> updateChildren(@PathVariable("id") Long id, @RequestBody Person person) {
	       
	    	if(personService.updateChildren(id, person)){
	    		return new ResponseEntity<Person>(person, HttpStatus.NO_CONTENT);
	    	} else {
	    		return new ResponseEntity<String>("Fail to creat parent", HttpStatus.OK);
	    	}
	    	
	    }
	    
}
