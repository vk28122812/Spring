package io.datajek.spring.basics.movie_recommender_system.lesson3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;



@Component
@Qualifier("CBF")//Method2 of adding Qualifier
@Primary
public class ContentBasedFilter implements Filter {

    public String[] getRecommendations(String movie){

        return new String[]{"Aashiqui 2", "Drishyam", "Kal Ho Na Ho"};
    }
}
