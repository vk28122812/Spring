# AOP

Aspect Oriented Programming (AOP) is the best approach for implementing cross-cutting concerns. Applications are divided into layers like web, business, data, etc. Each layer works independently. There are some concerns that are common across layers. These include security, logging, transaction management, auditing, error handling, performance tracking, etc. These concerns are present in all the layers and are thus called **cross-cutting concerns**.

![image.png](AOP%201ce76e26cece80159d2ddefc8b6e49df/image.png)

AOP simplifies the integration of additional functionalities, such as logging or performance tracking, across various layers of an application. For instance, consider the scenario where we need to log methods responsible for specific features across the web, business, or data layers. With traditional OOP methods, we would embed logging calls directly within these methods. However, this approach becomes cumbersome if we later decide to modify or remove the logs, or if we need to apply logging to a different set of methods.

In contrast, AOP offers a more flexible solution. Instead of modifying the source code directly, cross-cutting concerns are implemented separately. This means that changes related to logging, or any other concern, can be managed independently from the core application logic. By simply adjusting configuration files, we can add or remove logging functionality without the need to recompile the entire codebase. This separation of concerns enables seamless application of logging to methods across different layers, enhancing maintainability and reducing code redundancy.

![image.png](AOP%201ce76e26cece80159d2ddefc8b6e49df/image%201.png)

# Spring AOP

Spring AOP is a framework provided by the Spring framework that allows developers to modularize cross-cutting concerns in applications. It simplifies the management of cross-cutting concerns in applications, promotes better code organization, and improves code maintainability.

While it may not possess the extensive capabilities of AspectJ, Spring AOP provides sufficient functionality for most common use cases. Spring AOP can be used to intercept any calls to the beans managed by Spring. Once method calls are intercepted, cross-cutting concerns can be applied before, after, or around the method logic.

```java
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

```

## **Aspect**

An aspect in Java is a class that encapsulates cross-cutting concerns, such as logging or transaction management. It is identified by the `@Aspect` annotation and defines the types of methods to intercept and the actions to take upon interception. Aspects enable the modularization of cross-cutting concerns, allowing developers to apply them consistently across different parts of an application. They can handle various concerns like logging, performance monitoring, or transaction management that span multiple layers of an application.

## **Pointcut**

Pointcuts are expressions that specify the criteria for selecting join points in the application where advice should be applied. They determine which method calls or other join points will be intercepted and when advice should be executed. Pointcuts are defined using a specific syntax and should be carefully crafted to target the desired set of join points while avoiding unnecessary interception, as they directly impact the behavior of the aspect.

## **Advice**

The tasks performed after intercepting a method call are called **advice**. It is the logic of the methods in a class marked with `@Aspect`. Advices are basically the methods that get executed when a method calls meets a pointcut. These methods can get executed before, around the time of, or after the execution of the intercepted method call. There are different advice types as shown below.

![image.png](AOP%201ce76e26cece80159d2ddefc8b6e49df/image%202.png)

## **Joinpoint**

A **joinpoint** represents a specific point during the execution of a program, typically a method invocation in Java. It is the point where an aspect can be applied by intercepting the execution of the program. Joinpoints provide the context in which advice can be executed, allowing aspects to intervene in the execution flow of the program. It contains the name of the intercepted method call. The following figure shows the big picture of how AOP works:

![Pointcut maps to a joinpoint where an aspect can be executed during program execution](AOP%201ce76e26cece80159d2ddefc8b6e49df/image%203.png)

Pointcut maps to a joinpoint where an aspect can be executed during program execution

## **Weaving**

Weaving is the process of integrating aspects into the base code at specified joinpoints during the application's lifecycle. It combines the aspect code with the main application code to create a woven codebase where the cross-cutting concerns defined by the aspects are applied at the designated points in the program's execution flow. Weaving can occur at different stages, such as compile time, load time, or runtime, depending on the AOP framework and configuration.

Weaving links an aspect with objects in the application to create an advised object. The aspect is called at the right moment. For example, if we are tracking the execution time of some methods in our application, the weaving process will be like this:

![image.png](AOP%201ce76e26cece80159d2ddefc8b6e49df/image%204.png)

Implementing aspects around business logic

## **Weaver**

The framework that ensures that an aspect is invoked at the right time is called a **weaver**. It analyzes the aspect definitions and applies them to the appropriate points in the program's execution flow, thereby implementing the cross-cutting concerns defined by the aspects. The weaver can operate at different stages, such as compile time, load time, or runtime, depending on the AOP framework and configuration

```java
//Data Directory
@Repository
public class Movie {

    public String getMovieDetails(){
        return "Movie details";
    }
}
@Repository
public class User {
    public String getUserDetails() {
        return "User details.";
    }
}

```

```java
//Business Directory
@Service
public class FilteringTechnique1 {
    @Autowired
    private Movie movie;

    public String contentBasedFiltering(){
        String movieDetails = movie.getMovieDetails();
        return movieDetails;
    }
}

@Service
public class FilteringTechnique2 {

    @Autowired
    private User user;

    public String collaborativeFiltering(){
        String userDetails = user.getUserDetails();
        return userDetails;
    }

}
```

```java

@SpringBootApplication
public class MovieRecommenderAopApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilteringTechnique1 filteringTechnique1;

	@Autowired
	FilteringTechnique2 filteringTechnique2;

	public static void main(String[] args) {
		SpringApplication.run(MovieRecommenderAopApplication.class, args);
	}

	public void run(String...args) throws Exception{
		logger.info("Content Based Filter: {}", filteringTechnique1.contentBasedFiltering());
		logger.info("Collaborative Based Filter: {}", filteringTechnique2.collaborativeFiltering());
	}

}
```

# **Defining an Aspect**

When we intercept method calls, we have the option to perform tasks before the method is executed as well as afterwards. To define an aspect for a cross-cutting concern, we will perform the following steps:

1. Define aspect class.
2. Write methods containing the advice to be executed when method calls are intercepted.
3. Write pointcut expressions for intercepting method calls.

**Aspect**

Suppose we want to intercept method calls to check if the user has access before the method gets executed. Access check is a cross-cutting concern and instead of implementing it in every method, we can write it as an aspect.

In the `aspect` package, we will create a class named `AccessCheckAspect`. This class is a configuration entity, so we mark it with the `@Configuration` annotation. We will also add the `@Aspect` annotation to signify its AOP-related functionality.

```java
@Aspect
@Configuration
public class AccessCheckAspect {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public void userAccess(JoinPoint joinPoint) {
    logger.info("Intercepted method call");
  }
}
```

### Advice

The next step is to define a method that contains the logic of the steps that need to be carried out when a method call gets intercepted. We will create a method called `userAccess()` and print a message.

### **Pointcut expression**

User access needs to be checked before a method gets executed. We need the `@Before` annotation on our method. It ensures that the advice is run before the method is executed.

`@Before` needs an argument which specifies the method calls that will be intercepted. This is called the pointcut. Pointcuts are defined in the following format:

```java
execution(* PACKAGE.*.*(..))
```

The pointcut expression starts with a key word called a designator, which tells Spring AOP what to match. `execution` is the primary designator which matches method execution joinpoints.

- The first  in the expression corresponds to the return type. * means any return type.
- Then comes the package name followed by class and method names.
- The first  after package means any class and the second  means any method.Instead of *, we could specify the class name and method name to make the pointcut expression specific.
- Lastly, parentheses correspond to arguments. `(..)` means any kind of argument.

Suppose we want to intercept calls to methods belonging to the business package. The pointcut expression, in this case, will be:

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))")
```

If we use this pointcut expression and run the application, the message in the method will be printed twice, indicating that two method calls have been intercepted.

The `userAccess()` method is invoked before the actual method. This method contains the logic for checking user access, which is not shown.

### **Joinpoint**

To find out which method calls have been intercepted, we will use a join point as an argument to the method. The joinpoint contains the name of the method that is intercepted. We can use the joinpoint to print the name of the method as follows:

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))")
public void userAccess(JoinPoint joinPoint) {
  logger.info("Intercepted call before execution: {}", joinPoint);
}	
```

The pointcut that we defined is applicable on all methods in the business layer. We can also change the pointcut to intercept methods calls in the data layer:

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop.data.*.*(..))")
```

# Pointcut Expressions

The way pointcuts are defined is important because it decides the method calls that will be intercepted. If we have two packages, we can define which method calls will be intercepted.

### **Intercepting all method calls in a package**

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))")
public void before(JoinPoint joinPoint) {
  //intercept a call
  logger.info("Intercepted call before execution of: {}", joinPoint);
//access check logic		
}
```

This pointcut intercepts calls belonging to the business package. Since we used `*` in place of the class and method names, all calls to methods in the business package are intercepted.

### **Intercepting all method calls**

If we remove the name of the package (business), the pointcut expression becomes:

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop..*.*(..))")
```

This will intercept all calls in the `movierecommenderaop` package. The `MovieRecommenderAopApplication.run()` method will also get intercepted because it is in the `movierecommenderaop` package. All method calls in the business and data layer also get intercepted.

### **Intercepting calls using return type**

Say we want to intercept calls to all methods that return a `String` value. This can be done by specifying `String` as the return type in the pointcut expression as follows:

```java
@Before("execution(String io.datajek.springaop.movierecommenderaop..*.*(..))")
```

Here we are looking at calls in all subpackages of the `movierecommenderaop` package and matching the return type of the method calls to `String`.

### **Intercepting calls to a specific method**

If we want to intercept calls to all methods that have the word *Filtering* in it, we will use the following pointcut expression:

```java
@Before("execution(String io.datajek.springaop.movierecommenderaop..*.*Filtering(..))")
```

The wildcard `*` used in place of the method name will match all methods that have the word `Filtering` in it. Calls to `contentBasedFiltering()` and `collaborativeFiltering()` will be intercepted.

### **Intercepting calls with specific method arguments**

```java
@Before("execution(* io.datajek.springaop.movierecommenderaop..*.*(String))")
```

This pointcut will match method calls having one parameter of `String` type.

### **Combining pointcut expressions**

The `&&` , `||` and `!` symbols can be used to combine different pointcut expressions.

```java
@Before ("execution(* io.datajek.springaop.movierecommenderaop..*.*Filtering(..)) || execution(String io.datajek.springaop.movierecommenderaop..*.*(..))")
```

This pointcut expression will match method calls that have the word `Filtering` in them as well as those that return a `String`. In all, four methods will be intercepted, matching either of the two conditions.

# After Aspect

Logging is a cross-cutting concern for which aspects can be created. In this lesson, we will create another aspect that will log the values returned *after* the methods have been executed.

```java
@Aspect
@Configuration
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
```

## **@AfterReturning annotation**

The `LoggingAspect` class will have a method `LogAfterExecution()`, which will print a message if the method is successfully executed.

To tell Spring AOP that this method needs to be called after the intercepted method call is executed, we will use the `@AfterReturning` annotation.

```java
@AfterReturning("execution(* io.datajek.springaop.movierecommenderaop.data.*.*(..))")
public void LogAfterExecution(JoinPoint joinPoint) {
  logger.info("Method {} complete", joinPoint);
}
```

In the above snippet, the return values of the method are accessible through the `returning` tag. Since the `@AfterReturning` annotation now takes more than just the pointcut, we differentiate the arguments using tags:

```java
@AfterReturning(
          value = "execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))",
          returning = "result")
public void LogAfterExecution(JoinPoint joinPoint, Object result) {
    logger.info("Method {} returned with: {}", joinPoint, result);
}
```

Here, `value` contains the pointcut expression and `returning` holds the value that is returned by the executing method, which is stored in `result` and passed to the `LogAfterExecution` method. It's important to note that `result` is of type Object to accommodate different return types across methods.

We can use `result` to log the return value of all intercepted methods. If the application is run, two methods from the business layer are intercepted and the return values are also displayed.

## **@AfterThrowing annotation**

The `@AfterThrowing` annotation in Spring AOP is used to specify a method to be executed after a method invocation exits by throwing an exception.This annotation allows us to perform actions such as logging or cleanup operations in response to exceptions thrown during the execution of intercepted methods. We can get the result of the exception using the `throwing` tag.

```java
@AfterThrowing(
        value = "execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))",
        throwing = "exception")
public void LogAfterException(JoinPoint joinPoint, Object exception) {
    logger.info("Method {} returned with: {}", joinPoint, exception);
}
```

This advice will be executed in case there is an exception in any method call from the business layer.

## **@After annotation**

The `@After` annotation is a generic annotation that is used in both scenarios, whether the method execution is successful or results in an exception. The method `LogAfterMethod()` demonstrates the use of this annotation.

```java
@After("execution(* io.datajek.springaop.movierecommenderaop.business.*.*(..))")
public void LogAfterMethod(JoinPoint joinPoint) {
    logger.info("After method call {}", joinPoint);
}
```

Since `@After` is a generic annotation, if the application is run, this method also gets executed alongside the `LogAfterExecution()` method marked with the `@AfterReturning` annotation.

# Around aspect

There are aspects that are executed before and after the intercepted method calls. This lesson looks at another type of aspect, the around aspect, which is executed around the intercepted method call. This kind of aspect is useful if we want to perform a task before the intercepted method starts execution and after the method has returned.

A good example of an around aspect is measuring the time taken by the method call to execute. We can note the time when the method call is intercepted, then allow the intercepted method to execute, and note the time after the method returns. Instead of having two separate annotations, `@Before` and `@After`, we can accomplish this task using the more advanced `@Around` annotation.

```java
@Aspect
@Configuration
public class ExecutionTimeAspect {

}
```

In the `ExecutionTimeAspect` class, we will create a method called `calculateExecutionTime()`. The parameter type of this method will be `ProceedingJoinPoint` instead of `JoinPoint`, which we have used with methods marked with `@Before` and `@After` annotations. `ProceedingJoinPoint` allows the continuation of the execution. This method will return an `Object` that contains the values returned after the execution of the intercepted method call. The `proceed()` method of `ProceedingJoinPoint` should either be surrounded by a `try` `catch` block or should include a `throws` declaration with the method definition.

```java
@Around("execution(* io.datajek.springaop.movierecommenderaop..*.*(..))")
public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
  //note start time
  long startTime = System.currentTimeMillis();

  //allow method call to execute
  Object returnValue = joinPoint.proceed();

  //time taken = end time - start time
  long timeTaken = System.currentTimeMillis() - startTime;

  logger.info("Time taken by {} to complete execution is: {}", joinPoint, timeTaken);
  return returnValue;
}
```

The variable `startTime` notes the time when the call is intercepted. The `proceed()` method allows the intercepted method call to execute. The variable `returnValue` contains the values returned by the method. After the method call has returned, we calculate the execution time in a variable `timeTaken`.

 we will use the `@Around` annotation to define a pointcut for method calls for which we want the execution time to be tracked. If we want the time of all methods to be tracked, the following pointcut expression will be used

```java
@Around("execution(* io.datajek.springaop.movierecommenderaop..*.*(..))")
```

# **JoinPoint Configuration File**

Creating aspects to intercept method calls and perform tasks before, after, or around the execution is a repetitive task that repeats the same pointcuts over and over again. In an application with a large number of aspects, this can become cumbersome.

A best practice related to AOP is to have a separate configuration file that defines all the pointcuts. This way, all the pointcut definitions will be in one place. Hence they will be easy to manage. We can then use the definitions in any aspect.

## **For a specific layer**

We will create a class called `JoinPointConfig` and define pointcuts using the `@pointcut` annotation. This annotation will be used on an empty method as follows:

```java
@Pointcut("execution(* io.datajek.springaop.movierecommenderaop.data.*.*(..))")
public void dataLayerPointcut() {}
```

Now, we can use the `dataLayerPointcut()` method by providing its fully qualified name wherever we want to intercept execution of methods in the data layer.

Previously, we had been using pointcuts as follows:

```java
@AfterReturning("execution(* io.datajek.springaop.movierecommenderaop.data.*.*(..))")
public void logAfterExecution(JoinPoint joinPoint) {
    //...
}
```

Now, we will use the method that defines this pointcut in the configuration file as follows:

```java
@AfterReturning("io.datajek.springaop.movierecommenderaop.aspect.JoinPointConfig.dataLayerPointcut()")	
public void logAfterExecution(JoinPoint joinPoint) {
    //...
}
```

## **For multiple layers**

We can also combine pointcuts using the AND (`&&`), (OR) `||`, and (NOT) `!` operators. The method `allLayerPointcut()` will intercept calls belonging to either the business layer or the data layer.

```java
@Pointcut("io.datajek.springaop.movierecommenderaop.aspect.JoinPointConfig.dataLayerPointcut() || "
   	+ "io.datajek.springaop.movierecommenderaop.aspect.JoinPointConfig.businessLayerPointcut()")
public void allLayersPointcut() {}
```

When this pointcut is used in `AccessCheckAspect`, four method calls are intercepted.

## **For a bean**

We can also define a pointcut to intercept calls belonging to a particular bean. Say we want to log the execution of all methods belonging to beans that have the word `movie` in their name. We can define a pointcut as follows:

```java
@Pointcut("bean(movie*)")
public void movieBeanPointcut() {}
```

When this pointcut is used in `AccessCheckAspect`, it will intercept calls from the `Movie` and `MovieRecommenderAopApplication` beans.

# **Defining a Custom Annotation for Aspects**

## **A custom annotation**

An aspect can be used to track the execution time of a method that intercepts calls to a particular layer. Another approach could be to define a custom annotation on any method for which we want to track the execution time.`spring-aop` allows us to create our own annotations and define aspects to implement them.

Suppose we want to call our annotation `@MeasureTime`. We will create this annotation in the same folder as the other aspects. This creates an interface:

```java
package io.datajek.springaop.movierecommenderaop.aspect;

@Target(ElementType.METHOD)
public @interface MeasureTime {

}
```

The `@Target` annotation specifies the kinds of elements to which the annotation can be applied. It takes an array of `ElementType` constants as its value, indicating the valid target elements. Common `ElementType` constants include `TYPE` (classes, interfaces, enums), `METHOD` (methods), `FIELD` (fields), and others. We will restrict the use of this annotation to methods only by using the `@Target` annotation, with `ElementType` set to `METHOD`.

The `@Retention` annotation specifies how long the annotated annotation should be retained. It takes a `RetentionPolicy` constant as its value, indicating the retention policy. Common `RetentionPolicy` constants include `SOURCE` (annotations are retained only in the source files and not in the compiled class files), `CLASS` (annotations are retained in the class files, but not at runtime), and `RUNTIME` (annotations are retained in the class files and can be accessed at runtime using reflection).

We would also like the annotation information to be available at runtime. We will use the `@Retention` annotation to define a retention policy as follows:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MeasureTime {

}
```

Now that we have defined our annotation, we can add it to the `JoinPointConfig` file and create a pointcut as follows:

```java
@Pointcut("@annotation(io.datajek.springaop.movierecommenderaop.aspect.MeasureTime)")
public void measureTimeAnnotation() {}
```

We can now use this pointcut to calculate the execution time of only chosen methods. Previously, the `ExecutionTimeAspect` looked like this:

```java
public class ExecutionTimeAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("io.datajek.springaop.movierecommenderaop.aspect.JoinPointConfig.businessLayerPointcut()")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //...
    }
}
```

```java
public class ExecutionTimeAspect {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Around("io.datajek.springaop.movierecommenderaop.aspect.JoinPointConfig.measureTimeAnnotation()")
    public Object calculateExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //...
    }
}
```

Since we haven’t used the annotation anywhere in the code yet, no call will be intercepted. Suppose we want to track the time for the `contentBasedFiltering()` method of the `FilteringTechnique1` class and the `getMovieDetails()` method of the `Movie` class. We will use our custom annotation on these methods:

```java
@MeasureTime
public String contentBasedFiltering() {
    String movieDetails = movie.getMovieDetails();
    return movieDetails;
}
```