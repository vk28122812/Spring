package io.datajek.spring;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbookController {

	@Autowired
	EbookService service;

	@GetMapping("/ebooks")
	public List<Ebook> allEbooks() {
		return service.getAllEbooks();   
	}

    @GetMapping("/ebooks/{id}")
	public Ebook getEbook(@PathVariable int id){
		return service.getEbook(id);
	}

    @PostMapping("/ebooks")
	public Ebook addEbook(@RequestBody Ebook ebook) {
    		ebook.setId(0);
		return service.addEbook(ebook);
	}

    @PutMapping("/ebooks/{id}")
	public Ebook updateEbook(@PathVariable int id, @RequestBody Ebook ebook) {
		return service.updateEbook(id, ebook);
	}

    @PatchMapping("/ebooks/{id}")
	public Ebook partialUpdate(@PathVariable int id, @RequestBody Map<String, Object> ebookPatch) {
		return service.patch(id, ebookPatch);
	}

	@PatchMapping("/ebooks/{id}/price")
	public void updatePrice(@PathVariable int id, @RequestBody double price){
		service.updatePrice(id,price);
	}

	@PatchMapping("/ebooks/{id}/discount")
	public void updateDiscount(@PathVariable int id, @RequestBody double discount){
		service.updateDiscount(id,discount);
	}

    @DeleteMapping("/ebooks/{id}")
	public void deleteEbook(@PathVariable int id) {
		service.deleteEbook(id);
	}


}