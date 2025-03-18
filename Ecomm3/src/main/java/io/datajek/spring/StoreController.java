package io.datajek.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
	
	@GetMapping("/welcome")
	public String welcome() {
	    return "Ebook Store — REST API";
	}

}