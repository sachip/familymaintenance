package org.myapps.family.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.myapps.family.domain.Person;
import org.myapps.family.service.PersonService;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

	}

	@Test
	public void createParent_Success() throws Exception {
		Person parent = constructPerson(true);
		Mockito.when(personService.createParent(parent)).thenReturn(true);
		this.mockMvc
				.perform(
						post("/parents").contentType(MediaType.APPLICATION_JSON)
								.content(asJsonString(parent)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", Matchers.is("John")));

	}

	@Test
	public void createParent_Failed() throws Exception {
		Person parent = constructPerson(true);
		Mockito.when(personService.createParent(parent)).thenReturn(false);
		this.mockMvc
				.perform(
						post("/parents").contentType(MediaType.APPLICATION_JSON)
								.content(asJsonString(parent)))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Failed To Create Resource"));

	}

	@Test

	public void getParentById_Failed() throws Exception {

		Person parent = constructPerson(false);

		Mockito.when(personService.findParent(parent.getId()))
				.thenReturn(parent);
		ResultActions responseResult = this.mockMvc.perform(
				get("/parents/123").contentType(MediaType.APPLICATION_JSON));
		responseResult.andExpect(status().isInternalServerError());

	}

	@Test

	public void getParentById_Success() throws Exception {

		Person parent = constructPerson(false);

		Mockito.when(personService.findParent(parent.getId()))
				.thenReturn(parent);
		ResultActions responseResult = this.mockMvc.perform(
				get("/parents/1").contentType(MediaType.APPLICATION_JSON));
		responseResult.andExpect(status().isOk());

	}

	@Test

	public void getParents_Success() throws Exception {
		Person parent = constructPerson(false);
		List<Person> parents = new ArrayList<Person>();
		parents.add(parent);
		Mockito.when(personService.findParentWithChildrens())
				.thenReturn(parents);
		this.mockMvc
				.perform(
						get("/parents").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	@Test

	public void getParents_Failed() throws Exception {
		Person parent = constructPerson(false);
		List<Person> parents = new ArrayList<Person>();
		parents.add(parent);
		Mockito.when(personService.findParentWithChildrens()).thenReturn(null);
		this.mockMvc
				.perform(
						get("/parents").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}

	@Test
	public void updatePerson_Success() throws Exception {
		Person person = constructPerson(false);
		Mockito.when(personService.updatePerson(person.getId(), person))
				.thenReturn(true);
		this.mockMvc
				.perform(put("/parents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(person)))
				.andExpect(status().isOk());
	}

	@Test
	public void updatePerson_Failed() throws Exception {
		Person person = constructPerson(false);
		Mockito.when(personService.updatePerson(person.getId(), person))
				.thenReturn(false);
		this.mockMvc
				.perform(put("/parents/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(person)))
				.andExpect(status().isInternalServerError());
	}

	public Person constructPerson(boolean withChild) {
		Person person = new Person();
		person.setId(Long.valueOf(1));
		person.setFirstName("John");
		person.setLastName("Deo");
		person.setEmailAddess("John@gmail.com");
		person.setGender("Male");
		if (withChild) {
			Person child = new Person();
			person.setChildren(new HashSet<Person>(Arrays.asList(child)));
		}
		return person;

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
