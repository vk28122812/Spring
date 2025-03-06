package io.datajek.spring.basics.movie_recommender_system.lesson3;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
@Repository
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//No proxy then a single bean is created if parent is singleton
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Movie {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  static int instances;
    private int id;
    private String name, genre, producer;

    public Movie() {
        instances++;
//        System.out.println("Movie Constructor Called");
        logger.info("Movie Constructor Called");
    }

    @PostConstruct
    public void postConstruct() {
        logger.info("In movie postConstruct Method");
    }
    @PreDestroy
    public void preDestroy(){
        logger.info("In Movie preDestroy method");
    }
    public static int getInstances() {
        return instances;
    }
}
