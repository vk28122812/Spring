package io.datajek.spring.basics.movie_recommender_system;

//Lesson1 imports
//import io.datajek.spring.basics.movie_recommender_system.lesson1.RecommenderImpl;


//Lesson2 imports
//import io.datajek.spring.basics.movie_recommender_system.lesson2.CollaborativeFilter;
//import io.datajek.spring.basics.movie_recommender_system.lesson2.ContentBasedFilter;
//import io.datajek.spring.basics.movie_recommender_system.lesson2.RecommenderImpl;

//Lesson3 imports
import io.datajek.spring.basics.movie_recommender_system.lesson3.*;

import io.datajek.spring.basics.movie_recommender_system.lesson11.RecommenderImplementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

//Put this file to lesson3 and then run the application (and use refactor )to understand component scan
//@SpringBootApplication
//@ComponentScan(basePackages="io.datajek.spring.basics.movie_recommender_system.lesson10,io.datajek.spring.basics.movie_recommender_system.lesson10")
// @ComponentScan(includeFilters = @ComponentScan.Filter (type= FilterType.REGEX, pattern="io.datajek.spring.basics.movie_recommender_system.lesson3.*"))

/* Spring Boot App Default Configuration*/
@SpringBootApplication

/* Spring  App Configuration*/
//@Configuration
//@ComponentScan

@PropertySource("classpath:app.properties")
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

		//Spring Application Configuration
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MovieRecommenderSystemApplication.class);


		//XML Configuration
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("appContext.xml");


		//Dependency Injection+Autowiring: Lesson3+

		//This is simple field injection
		ApplicationContext ctx = SpringApplication.run(MovieRecommenderSystemApplication.class, args);
//		RecommenderImpl recommender = ctx.getBean(RecommenderImpl.class);
//		String[] result = recommender.recommendMovies("Finding Dory");
//		System.out.println(Arrays.toString(result));

		//This is constructor injection
//		RecommenderImplConstructor recommenderImplConstructor = ctx.getBean(RecommenderImplConstructor.class);
//		String[] result2 = recommenderImplConstructor.recommendMovies("Finding Dory");
//		System.out.println(Arrays.toString(result2));

		//This is setter injection
//		RecommenderImplSetter recommenderImplSetter = ctx.getBean(RecommenderImplSetter.class);
//		String[] result3 = recommenderImplSetter.recommendMovies("Finding Dory");
//		System.out.println(Arrays.toString(result3));


		//Singleton bean is default, no new instances will be created
//		ContentBasedFilter cbf1 = ctx.getBean(ContentBasedFilter.class);
//		ContentBasedFilter cbf2 = ctx.getBean(ContentBasedFilter.class);
//		ContentBasedFilter cbf3 = ctx.getBean(ContentBasedFilter.class);
//		System.out.println(cbf1);
//		System.out.println(cbf2);
//		System.out.println(cbf3);

		//If we add prototype scope to the bean, we will get different instances of the bean
//		CollaborativeFilter cf1 = new CollaborativeFilter();
//		CollaborativeFilter cf2 = new CollaborativeFilter();
//		CollaborativeFilter cf3 = new CollaborativeFilter();
//		System.out.println(cf1);
//		System.out.println(cf2);
//		System.out.println(cf3);

		ContentBasedFilter filter = ctx.getBean(ContentBasedFilter.class);
		System.out.println("ContentBasedFilter bean with singleton scope: "+filter);
		Movie movie1 = filter.getMovie();
		Movie movie2 = filter.getMovie();
		Movie movie3 = filter.getMovie();

		System.out.println("Movie bean with Prototype Scope ");
		System.out.println(movie1);
		System.out.println(movie2);
		System.out.println(movie3);

		System.out.println("\nContentBasedFilter instances created: " + ContentBasedFilter.getInstances());
		System.out.println("Movie instances created: "+ Movie.getInstances());
		/*
		If we don't use proxy on Movie class:
		The output of the above code widget shows that the same Movie bean is returned every time.
		 Moreover, the number of instances of the prototype bean created is one instead of three.
		  A singleton bean is created when the context is loaded. The Movie constructor was called by Spring when it was creating the ContentBasedFilter bean.
		  The prototype bean is injected into the singleton bean at the time of creation of the singleton bean when the container initializes it.

		  If we use proxy, then we get different instances of the Movie prototype bean
		 */

		//Lesson10: Put this file into lesson3 folder and uncomment the @ComponentScan annotation above
		System.out.println(ctx.containsBean("collaborativeFilter"));
		System.out.println(ctx.containsBean("contentBasedFilter"));


		RecommenderImplementation recommender2 = ctx.getBean(RecommenderImplementation.class);
		System.out.println(recommender2);
		System.out.println(recommender2.getFavoriteMovie());


//		ctx.close();
	}
}
