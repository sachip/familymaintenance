package org.myapps.family.data;

import java.util.List;

import org.myapps.family.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query("SELECT person from Person person where person.parent.id  IS NULL")
	List<Person> findAllParent();
}
