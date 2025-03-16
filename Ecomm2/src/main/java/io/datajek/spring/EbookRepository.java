package io.datajek.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
@Transactional
public class EbookRepository{

	@PersistenceContext
	EntityManager em;

	public int deleteEbookById(int id){
	    Ebook ebook = em.find(Ebook.class,id);
		em.remove(ebook);
		return 1;
	}

	public int updateEbook(Ebook ebook){
	    return em.merge(ebook).getId();
	}

	public int insertEbook(Ebook ebook){
	   	return em.merge(ebook).getId();
	}

	public Ebook getEbookById(int id) {
	    Ebook ebook = em.find(Ebook.class, id);
		return ebook;
	}

	public List<Ebook> getAllEbooks() {
		TypedQuery<Ebook> namedQuery = em.createNamedQuery("get_all_ebooks", Ebook.class);
		return namedQuery.getResultList();
	}
}