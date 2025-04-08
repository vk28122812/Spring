package io.datajek.spring.aspects;

import io.datajek.spring.business.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class SecurityAspect {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("io.datajek.spring.aspects.JoinPointConfig.dataPointCut()")
    public void checkUserAuthentication(JoinPoint joinPoint) throws RuntimeException {
        if(!userService.isUserAuthenticated()){
            throw new RuntimeException("User not authenticated");
        }
        logger.info("Intercepted method {} ", joinPoint);
    }

    @Before("io.datajek.spring.aspects.JoinPointConfig.authorizationPointcut()t()")
    public void checkUserAuthorization(JoinPoint joinPoint)throws RuntimeException{
        if(!userService.isUserAuthorized()){
            throw new RuntimeException("User not authorized");
        }
        logger.info("Intercepted method {} ", joinPoint);
    }

}