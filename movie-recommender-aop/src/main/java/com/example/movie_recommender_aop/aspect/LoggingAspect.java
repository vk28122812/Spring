package com.example.movie_recommender_aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(
            value="execution(* com.example.movie_recommender_aop.data.*.*(..))",
            returning = "result"
    )
    public void logAfterExecution(JoinPoint joinPoint, Object result){
        logger.info("Method {} returned {}", joinPoint, result );
    }

    @AfterThrowing(
            value="execution(* com.example.movie_recommender_aop.*.*.* (..))",
            throwing = "exception"
    )
    public void logAfterException(JoinPoint joinPoint, Exception exception){
        logger.info("Method {} thrown exception {}" , joinPoint, exception);
    }

    @After("execution(* com.example.movie_recommender_aop.*.*.*(..))")
    public void logAfterMethod(JoinPoint joinPoint){
        logger.info("Method {} executed", joinPoint);
    }
}
