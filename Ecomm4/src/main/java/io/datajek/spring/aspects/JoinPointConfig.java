package io.datajek.spring.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfig {

    @Pointcut("execution(* io.datajek.spring.data.*.*(..)) || execution(* io.datajek.spring.data.CustomEbookRepository.updateTitle(..))")
    public void dataPointCut(){}


    @Pointcut("@annotation(io.datajek.spring.aspects.AuthCheck)")
    public void authorizationPointcut(){}

}