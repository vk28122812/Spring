package io.datajek.spring.basics.movie_recommender_system.lesson11;

import ch.qos.logback.classic.Logger;
import io.datajek.spring.basics.movie_recommender_system.lesson3.Filter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@Named
@Service
public class RecommenderImplementation {

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    private Filter filter;

    @Value("${recommender.implementation.favoriteMovie:defaultValue}")
    String favoriteMovie;

    @Inject
    @Qualifier("contentBasedFilter")
    public void setFilter(Filter filter) {
        logger.info("In RecommenderImplementation setter method..dependency injection");
        this.filter = filter;
    }

    @PostConstruct
    public void postConstruct() {
        //initialization code goes here
        logger.info("In RecommenderImplementation postConstruct method");
    }

    @PreDestroy
    public void preDestroy(){
        logger.info("In RecommenderImplementation  postDestroy method");
    }

    public String getFavoriteMovie() {
        return favoriteMovie;
    }

    public void setFavoriteMovie(String favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }

    public String[] recommendMovies(String movie){
        System.out.println("Filter in use: "+ filter);

        String[]results = filter.getRecommendations(movie);
        return results;
    }

}