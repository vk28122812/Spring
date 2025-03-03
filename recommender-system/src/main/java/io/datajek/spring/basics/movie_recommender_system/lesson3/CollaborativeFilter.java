package io.datajek.spring.basics.movie_recommender_system.lesson3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


//@Component("CF")//Method1 of adding Qualifier
@Component
@Qualifier("CF")//Method2
@Primary
public class CollaborativeFilter implements Filter {
    public String[] getRecommendations(String movie) {
        //logic of collaborative filter
        return new String[] {"My name is Khan", "Pathaan", "Jawan"};
    }
}
