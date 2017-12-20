package fr.dta.tests;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import fr.dta.controller.MainController;
import fr.dta.model.Book;

@Sql("classpath:test-book-data.sql")

public class BookIT extends IntegrationTests {

	@Autowired
	MainController ls;

	@Test
	@WithMockUser(authorities = "TEST")
	public void testCreate() throws Exception {
		Book u = new Book();
		u.setId(25);
		u.setTitle("Test ta mère !");
		u.setAuthor("Maman");
		u.setIsbn("14587456985");
		u.setNbPages(7485);
		u.setPublicationDate(new Date());

		this.mockMvc.perform(post("/books", u).contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u))).andExpect(status().isOk());
		
		this.mockMvc.perform(get("/books")).andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$", hasSize(3))).andExpect(status().isOk());

	}

	@Test
	@WithMockUser
	public void testCreatePreconditionFail() throws Exception {
		Book u = new Book();
		u.setId(25);
		u.setTitle("Test ta mère !");
		u.setAuthor("Maman");
		u.setIsbn("14587456985");
		u.setNbPages(7485);
		u.setPublicationDate(new Date());

		this.mockMvc
				.perform(post("/books").contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
						.content(jsonHelper.serialize(u)))
				.andDo(MockMvcResultHandlers.print())
				//.andExpect(jsonPath("$[*].field", containsInAnyOrder("id", "isbn", "publication_date", "nb_pages", "title", "author")))
				//.andExpect(jsonPath("$[0].objectName").value("book"))
				//.andExpect(jsonPath("$[*].code", containsInAnyOrder("NotBlank", "NotBlank")))
				.andExpect(status().isPreconditionFailed());

	}

	@Test
	@WithMockUser
	public void testUpdate() throws Exception {
		Book book = ls.getById(68464861);
		
		this.mockMvc
				.perform(put("/books", book).contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("UTF-8")
						.content(jsonHelper.serialize(book)));
				
				
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetBook() throws Exception {
		this.mockMvc.perform(get("/books/{id}", 68464861)).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.id").value(68464861)).andExpect(jsonPath("$.title").value("La flemme un art de vivre"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetNotFoundBook() throws Exception {
		this.mockMvc.perform(get("/books/{id}", 55)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetAllBooks() throws Exception {
		this.mockMvc.perform(get("/books")).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(status().isOk());
	}

}
