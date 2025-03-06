package io.datajek.spring.basics.movie_recommender_system.lesson3;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;




//Default scope is Singleton
@Component
@Qualifier("CBF")//Method2 of adding Qualifier
@Primary
public class ContentBasedFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    static  int instances = 0;

    @Autowired
    Movie movie;

    public ContentBasedFilter() {
        instances++;
//        System.out.println("ContentBasedFilter Constructor Called");
        logger.info("In ContentBasedFilter constructor method");
    }

    public String[] getRecommendations(String movie){

        return new String[]{"Aashiqui 2", "Drishyam", "Kal Ho Na Ho"};
    }
    public  Movie getMovie() {
        return movie;
    }
    public static int getInstances() {
        return instances;
    }

    @PostConstruct
    public void postConstruct(){
        logger.info("In cbf's PostConstruct method");
    }

    @PreDestroy
    private void preDestroy() {
        //clear movies from cache
        logger.info("In cbf's preDestroy method");
    }

}
