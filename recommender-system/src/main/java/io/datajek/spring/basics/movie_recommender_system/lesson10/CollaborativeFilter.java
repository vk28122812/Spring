package io.datajek.spring.basics.movie_recommender_system.lesson10;

import io.datajek.spring.basics.movie_recommender_system.lesson3.Filter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//@Component("CF")//Method1 of adding Qualifier
@Component
@Scope("prototype")
@Qualifier("CF")//Method2
@Primary
public class CollaborativeFilter implements Filter {

    public CollaborativeFilter() {
        super();
        System.out.println("In Collaborative constructor method");
    }

    public String[] getRecommendations(String movie) {
        //logic of collaborative filter
        return new String[] {"My name is Khan", "Pathaan", "Jawan"};
    }
}
