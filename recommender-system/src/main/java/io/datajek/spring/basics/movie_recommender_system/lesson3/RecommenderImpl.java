package io.datajek.spring.basics.movie_recommender_system.lesson3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//Field Injection
@Component
public class RecommenderImpl {

//    @Autowired
//    private Filter filter;
//
//    public String[] recommendMovies(String movie){
//        System.out.println("Filter in use: "+ filter);
//
//        String[]results = filter.getRecommendations(movie);
//        return results;
//    }

    //Autowiring by name, fails vs Autowiring by type
//    @Autowired
//    private Filter contentBasedFilter;
//
//    public String[] recommendMovies(String movie){
//        System.out.println("Filter in use: "+ contentBasedFilter);
//
//        String[]results = contentBasedFilter.getRecommendations(movie);
//        return results;
//    }

    //Autowiring by qualifier, precedence over Autowiring by type
    @Autowired
    @Qualifier("CBF")
    private Filter filter;
    public String[] recommendMovies(String movie){
        System.out.println("Filter in use: "+ filter);

        String[]results = filter.getRecommendations(movie);
        return results;
    }
}
