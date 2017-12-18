package fr.dta.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.dta.Repository.BookRepository;
import fr.dta.model.Book;

@Service
public class LibraryService {
	
	@Autowired
	private BookRepository br;

	public static LibraryService instance=null;
	public static LibraryService getInstance(){
		if(instance!=null) return instance;
		else instance = new LibraryService();
		return instance;
	}
	
	private ArrayList<Book> list = new ArrayList<Book>();
	
	public boolean addBook(Book b){
		return br.add(b);
	}
	
	public boolean removeBook(int id){
		return br.delete(id);
	}
	
	public ArrayList<Book> search(String criteria){
		ArrayList<Book> result = new ArrayList<Book>();
		//for(Book b: list)if(b.matches(criteria.toLowerCase()))result.add(b);
		return result;
	}
	
	
	public boolean contains(int id){
		return getById(id)!=null;
	}
	
	public Book getById(int id){
		return br.getById(id);
		
	}
	
	public List<Book> getAll(){
		return br.getAll();
	}
	
	public Boolean updateBook(Book book) {
		return br.update(book);
	}
	
}
