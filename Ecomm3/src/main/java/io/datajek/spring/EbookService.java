package io.datajek.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
public class EbookService {
    
    @Autowired
	private EbookRepository ebookRepo;
	
	//Get all Ebooks
	public List<Ebook> getAllEbooks() {
		return ebookRepo.findAll();	    
	}

    public Ebook getEbook(int id) {
        Optional<Ebook> ebook = ebookRepo.findById(id);
        if(!ebook.isPresent()){
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        return ebook.get();
	}

    //Add a Ebook
	public Ebook addEbook(Ebook b) {
		return ebookRepo.save(b);
	}

    //Update a Ebook
	public Ebook updateEbook(int id, Ebook ebookDetails) {
		//get ebook object by ID
		Optional<Ebook> optionalEbook = ebookRepo.findById(id);
        if(!optionalEbook.isPresent()){
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        Ebook ebook = optionalEbook.get();
        ebook.setTitle(ebookDetails.getTitle());
        ebook.setAuthor(ebookDetails.getAuthor());
        ebook.setPrice(ebookDetails.getPrice());
        ebook.setDiscount(ebookDetails.getDiscount());
        ebook.setPublishDate(ebookDetails.getPublishDate());
        return ebookRepo.save(ebook);
	}

    //Partial update
	public Ebook patch( int id, Map<String, Object> partialEbook) {

		Optional<Ebook> optionalEbook = ebookRepo.findById(id);
        if(!optionalEbook.isPresent()){
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        Ebook ebook = optionalEbook.get();
        partialEbook.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Ebook.class, key);
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, ebook, value);
        });
        return ebookRepo.save(ebook);			
	}

    @Transactional
    public void updatePrice(int id,double price) {
        Optional<Ebook> optionalEbook = ebookRepo.findById(id);
        if(!optionalEbook.isPresent()){
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        ebookRepo.updatePrice(id,price);
    }

    @Transactional
    public void updateDiscount(int id,double discount) {
        Optional<Ebook> optionalEbook = ebookRepo.findById(id);
        if(!optionalEbook.isPresent()){
            System.out.println("not found");
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        ebookRepo.updateDiscount(id,discount);
    }

    //delete a Ebook
	public String deleteEbook(int id) {
		Optional<Ebook> optionalEbook = ebookRepo.findById(id);
        if(!optionalEbook.isPresent()){
            throw new EbookNotFoundException("Ebook not found with id: " + id);
        }
        ebookRepo.deleteById(id);
        return "Deleted Ebook with id: " + id;
	}

}