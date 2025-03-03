package io.datajek.spring.basics.movie_recommender_system.lesson3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RecommenderImplSetter {

    private Filter filter;

    @Autowired
    @Qualifier("collaborativeFilter")
    void setFilter(Filter filter) {
        this.filter = filter;
        System.out.println("Setter method  the component default bean becomes CBFinvoked");
    }
    public String [] recommendMovies (String movie) {

        //print the name of interface implementation being used
        System.out.println("\nName of the filter in use: " + filter + "\n");
        String[] results = filter.getRecommendations("Finding Dory");
        return results;
    }
}
