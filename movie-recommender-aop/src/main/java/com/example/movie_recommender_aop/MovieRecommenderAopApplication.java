package com.example.movie_recommender_aop;

import com.example.movie_recommender_aop.business.FilteringTechnique1;
import com.example.movie_recommender_aop.business.FilteringTechnique2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieRecommenderAopApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilteringTechnique1 filteringTechnique1;

	@Autowired
	FilteringTechnique2 filteringTechnique2;

	public static void main(String[] args) {
		SpringApplication.run(MovieRecommenderAopApplication.class, args);
	}

	public void run(String...args) throws Exception{

		logger.info("Content Based Filter: {}", filteringTechnique1.contentBasedFiltering());
		logger.info("Collaborative Based Filter: {}", filteringTechnique2.collaborativeFiltering());
	}

}
