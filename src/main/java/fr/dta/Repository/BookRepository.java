package fr.dta.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.dta.model.Book;

@Repository
@Transactional
public class BookRepository {
	/*
	 * Ne pas oublier le @Repository et le @Transactional au dessus de la class
	 * Bonnes pratiques : tout mettre dans des try catch
	 */

	@PersistenceContext // NE PAS OUBLIER
	private EntityManager entityManager;

	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void persist(Object entity) {
		entityManager.persist(entity);
	}

	/*
	 * Renvoie la liste des livres
	 */
	public List<Book> getAll() {
		try {
			List<Book> b;
			TypedQuery<Book> tq = entityManager.createQuery("select b from Book b ", Book.class);
			b = tq.getResultList();
			return b;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	/*
	 * retourne le livre en fonction de l'id
	 */
	public Book getById(int id) {
		try {
			Book b = new Book();

			TypedQuery<Book> tq = entityManager.createQuery("select b from Book b where b.id = :id ", Book.class);
			tq.setParameter("id", id);
			b = tq.getSingleResult();

			return b;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}
	

	/*
	 * Bon la y a une couille, mais le prof a expliquer pendant 3h sur 
	 * des trucs qui avaient rien � voir alors j'ai oubli�, MAIS en gros
	 * il faut r�importer une instance du livre dans la session avant de 
	 * le supprimer sinon �a marche pas.
	 * DONC il ne faut PAS faire juste un entityManager.remove(objet) 
	 * directement. Il faut tester si l'objet est dans la session, si
	 * oui, on le supprimer, sinon, on le r�cup�re, PUIS on le supprime. 
	 * Le tout en une ligne : 
	 * entityManager.remove(entityManager.contains(objet) ? objet : entityManager.merge(objet));
	 */
	public boolean delete(int id) {
		try {
			Book book = getById(id);
			entityManager.remove(entityManager.contains(book) ? book : entityManager.merge(book));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
	
	/*
	 * Ajoute une livre, pas de probl�me de session ou de commit/rollback ici normalement, 
	 * sinon faire la m�me chose que pr�c�demment.
	 */
	public boolean add(Book b) {
		try {
			entityManager.persist(b);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	/*
	 * M�me probl�me que pour delete
	 */
	public boolean update(Book b) {
		try {
			entityManager.merge(b);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
}