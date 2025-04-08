package io.datajek.spring.business;

import io.datajek.spring.aspects.AuthCheck;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import io.datajek.spring.data.EbookRepository;
import io.datajek.spring.data.Ebook;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

@Service
public class EbookService {

    @Autowired
    EbookRepository ebookRepo;

	@AuthCheck
    @Transactional
	public void updateTitle(int id, String title) {
		Optional<Ebook> tempEbook = ebookRepo.findById(id);

		if(tempEbook == null)
			throw new RuntimeException("Ebook with id {"+ id +"} not found");

		ebookRepo.updateTitle(id, title);
	}

	public List<Ebook> getAllEbooks() {
		return ebookRepo.findAll();	    
	}
}