package com.example.movie_recommender_aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AccessCheckInspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Before("execution(* com.example.movie_recommender_aop.business.*.*(..)))") //All methods inside package
//    @Before("execution(* com.example.movie_recommender_aop.*.*.*(..)))") //All methods
//    @Before("execution(String com.example.movie_recommender_aop.*.*.*(..)))") //Using return type
//    @Before("execution(* com.example.movie_recommender_aop.*.*.*(String,..)))") //Using argument
//    @Before("execution(* com.example.movie_recommender_aop.*.*.Filtering(..)) || execution(String com.example.movie_recommender_aop.*.*.*(..))")//Combinig using operators\

    @Before("com.example.movie_recommender_aop.aspect.JoinPointConfig.allLayersPointcut()")
    public void userAccess(JoinPoint joinPoint){
        logger.info("Intercepted method call {}",joinPoint);
    }
}
