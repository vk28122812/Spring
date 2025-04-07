package com.example.movie_recommender_aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ExecutionTimeAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Around("execution(* com.example.movie_recommender_aop.*.*.* (..))")
    @Around("com.example.movie_recommender_aop.aspect.JoinPointConfig.measureTimeAnnotation()")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time taken by {} to execute: {}",joinPoint,timeTaken);
        return returnValue;
    }
}
