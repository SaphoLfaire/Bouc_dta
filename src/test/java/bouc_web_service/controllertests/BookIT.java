package bouc_web_service.controllertests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import fr.dta.model.Book;
import fr.dta.services.LibraryService;

@Sql("classpath:test-book-data.sql")
public class BookIT extends IntegrationTests{

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Autowired
	LibraryService ls;

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

		this.mockMvc.perform(post("/app/books").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u))).andExpect(status().isCreated());
		this.mockMvc.perform(get("/app/books")).andDo(MockMvcResultHandlers.print())
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

		this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(jsonPath("$[*].field", containsInAnyOrder("password", "login")))
		.andExpect(jsonPath("$[0].objectName").value("user"))
		.andExpect(jsonPath("$[*].code", containsInAnyOrder("NotBlank", "NotBlank")))
		.andExpect(status().isPreconditionFailed());

	}
	
	@Test
	@WithMockUser
	public void testUpdate() throws Exception {
		Book book = ls.getById(7);
		//Assert.assertEquals("admin@iocean.fr", book.getLogin());

		//book.setLogin("test@iocean.fr");
		this.mockMvc
				.perform(put("/api/users/{id}", 1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
						.content(jsonHelper.serialize(book)))
				.andExpect(jsonPath("$.login").value("test@iocean.fr")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetUser() throws Exception {
		this.mockMvc.perform(get("/api/users/{id}", 1)).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.login").value("admin@iocean.fr"))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetNotFoundUser() throws Exception {
		this.mockMvc.perform(get("/api/users/{id}", 55)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(authorities = "TEST")
	public void testGetAllUsers() throws Exception {
		this.mockMvc.perform(get("/api/users")).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(status().isOk());
	}


}
