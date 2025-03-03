package io.datajek.spring.basics.movie_recommender_system;

//Lesson1 imports
//import io.datajek.spring.basics.movie_recommender_system.lesson1.RecommenderImpl;


//Lesson2 imports
//import io.datajek.spring.basics.movie_recommender_system.lesson2.CollaborativeFilter;
//import io.datajek.spring.basics.movie_recommender_system.lesson2.ContentBasedFilter;
//import io.datajek.spring.basics.movie_recommender_system.lesson2.RecommenderImpl;


//Lesson3 imports
import io.datajek.spring.basics.movie_recommender_system.lesson3.RecommenderImpl;

//Lesson4 imports
//import io.datajek.spring.basics.movie_recommender_system.lesson4.RecommenderImpl;

import io.datajek.spring.basics.movie_recommender_system.lesson3.RecommenderImplConstructor;
import io.datajek.spring.basics.movie_recommender_system.lesson3.RecommenderImplSetter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class MovieRecommenderSystemApplication {

	public static void main(String[] args) {
//		Tight Coupling: Lesson1
//		RecommenderImpl recommender = new RecommenderImpl();
//		String[] result = recommender.recommendMovies("Finding Dory");
//		System.out.println(Arrays.toString(result));


//		Loose Coupling: Lesson2
//		RecommenderImpl recommender =  new RecommenderImpl(new CollaborativeFilter());
//		RecommenderImpl recommender =  new RecommenderImpl(new ContentBasedFilter());
//		String[] result = recommender.recommendMovies("Finding Dory");
//		System.out.println(Arrays.toString(result));

		//Dependency Injection+Autowiring: Lesson3+

		//This is simple field injection
		ApplicationContext ctx = SpringApplication.run(MovieRecommenderSystemApplication.class, args);
		RecommenderImpl recommender = ctx.getBean(RecommenderImpl.class);
		String[] result = recommender.recommendMovies("Finding Dory");
		System.out.println(Arrays.toString(result));

		//This is constructor injection
		RecommenderImplConstructor recommenderImplConstructor = ctx.getBean(RecommenderImplConstructor.class);
		String[] result2 = recommenderImplConstructor.recommendMovies("Finding Dory");
		System.out.println(Arrays.toString(result2));

		//This is setter injection
		RecommenderImplSetter recommenderImplSetter = ctx.getBean(RecommenderImplSetter.class);
		String[] result3 = recommenderImplSetter.recommendMovies("Finding Dory");
		System.out.println(Arrays.toString(result3));
	}
}
