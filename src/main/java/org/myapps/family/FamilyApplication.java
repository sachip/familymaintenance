package org.myapps.family;

import java.util.Arrays;
import java.util.HashSet;

import org.myapps.family.data.PersonRepository;
import org.myapps.family.domain.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FamilyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilyApplication.class, args);
	}
	
	
	
}
