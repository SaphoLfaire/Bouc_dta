package fr.dta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.dta.model.Book;
import fr.dta.services.LibraryService;

@RestController
@RequestMapping(path = "/books")
public class MainController {

	@Autowired
	LibraryService ls;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAll() {
		return ls.getAll();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Book getById(@PathVariable int id) {
		return ls.getById(id);
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public int createBook(@RequestBody Book book) {
		ls.addBook(book);
		return book.getId();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public int deleteBook(@PathVariable int id) {
		ls.removeBook(id);
		return id;
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public int updateBook(@RequestBody Book book) {
		ls.updateBook(book);
		return book.getId();
	}

}
