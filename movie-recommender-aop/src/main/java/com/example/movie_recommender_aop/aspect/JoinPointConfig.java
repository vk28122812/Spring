package com.example.movie_recommender_aop.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfig {

    @Pointcut("execution(* com.example.movie_recommender_aop.data.*.*(..))")
    public void dataLayerPointcut(){}

    @Pointcut("execution(* com.example.movie_recommender_aop.business.*.*(..))")
    public  void businessLayerPointcut(){}

    @Pointcut("execution(* com.example.movie_recommender_aop.data.*.*(..)) || " +
            "execution(* com.example.movie_recommender_aop.business.*.*(..))"
    )
    public  void allLayersPointcut(){}

    @Pointcut("bean(movie*)")
    public  void movieBeanPointcut(){}


    @Pointcut("@annotation(com.example.movie_recommender_aop.aspect.MeasureTIme)")
    public void measureTimeAnnotation(){}

}
