# Spring Intro

The Spring framework is an open-source Java application framework, which is based on two key principles: **dependency injection** and **Inversion of Control**. Spring has the ability to autowire the dependency at run time, which allows the developer to write *loosely coupled* code.

![image.png](Spring%20Intro%201ab76e26cece80ca85e5d2901e0a4d98/image.png)

Spring framework uses metadata in the form of xml file or java annotations to create objects and identifies dependencies, thereby producing a ready-to-use application.

A typical web application is divided into three layers: web, business, and the data layer. These layers have objects that collaborate with each other to make the application work. These collaborations are called **dependencies**. A typical application has a lot of classes and dependencies.

### Tight Coupling

Tightly coupled code involves creating an instance of the dependency inside the class. As an example, suppose we have an application that recommends movies to watch. The application uses content-based filtering that employs item-to-item similarity as well as user preferences. The class `MovieRecommender` is directly instantiating an object of `ContentBasedFilter`, which makes `ContentBasedFilter` a dependency of `MovieRecommender`.

```java
// MovieRecommender class
 public class MovieRecommender {
    ContentBasedFilter filter = new ContentBasedFilter();
    //...
 }
 
 // ContentBasedFilter class
 public class ContentBasedFilter {
 	//...
 }
```

Problems can arise when we want to use a different option for the dependency. Suppose we did not get good movie recommendations from the content-based filter and want to switch to a collaborative filter which takes into account the choices of users who have watched similar movies.This entails changing the code of `MovieRecommender`, which would be a disadvantage of using tightly coupled code.

### Loose Coupling

A better way would be to implement an interface. This will remove the direct instantiation of the `ContentBasedFilter`, and instead, ask for the type of filter as an argument to the constructor.

```java
interface Filter {
    //method declarations
}
public class ContentBasedFilter implements Filter {
    //implement interface methods
}
public class MovieRecommender {
    Filter filter;

    public MovieRecommender(Filter filter) {
        this.filter = filter;
    }
    //...
}
```

This way `MovieRecommender` is not dependent on a specific type of filter and can be used with both a content-based filter and a collaborative filter. The above code snippet is an example of loosely coupled code. Loose coupling has a number of advantages.

```java
public static void main(String[] args) {
   MovieRecommender recommender = new MovieRecommender(new ContentBasedFilter());
    //...
}
```

Spring takes control of populating the dependencies and injecting the `ContentBasedFilter` object into the `MovieRecommender` object. This is in contrast to the approach shown in the first code snippet where `MovieRecommender` instantiated the `ContentBasedFilter` object itself. Spring inverts the control by taking responsibility for populating the dependency. This is referred to as Inversion of Control (IoC).

`Spring is a dependency injection framework that promotes loosely coupled code.`

## Beans

Beans are the objects of classes that are managed by Spring. Traditionally, objects used to create their own dependencies, but Spring manages all the dependencies of an object and instantiates the object after injecting the required dependencies. The `@Component` annotation is the most common method of defining beans.

```java
@Component
public class Vehicle {
   //...
}

```

## Autowiring

The process of identifying a dependency, looking for a match, and then populating the dependency is called autowiring. The `@Autowired` annotation tells Spring to find and inject a collaborating bean into another. If more than one bean of the same type is available, Spring throws an error. In the following scenario, two beans of type `Operator` are detected by Spring:

```java
@Component
class Arithmetic(){
    @Autowired
    private Operator operator;
    //...
}

@Component
class Addition implements Operator {
   //...
}

@Component
class Subtraction implements Operator {
  //...
}
```

Spring will not know which bean to inject in the `Arithmetic` bean unless the developer explicitly specifies it.

## Dependency Injection

Dependency injection is the process by which Spring looks up the beans that are needed for a particular bean to function and injects them as a dependency. Spring can perform dependency injection by using constructor or by using a setter method.

## Inversion of Control

Traditionally, the class which needed the dependency created an instance of the dependency. The class decided when to create the dependency and how to create it. For example, `Engine` class is a dependency of `Vehicle` class, which creates its object:

```java
class Vehicle{
    private Engine engine = new Engine();
    //...
}
```

Spring takes this responsibility from the class and creates the object itself. The developer simply mentions the dependency and the framework takes care of the rest.

Thus, control moves from the component that needs the dependency to the framework. The framework takes the responsibility for finding out the dependencies of a component, ensuring their availability and injecting them in the component. This process is called Inversion of Control.

![image.png](Spring%20Intro%201ab76e26cece80ca85e5d2901e0a4d98/image%201.png)

## IoC Container

An IoC container is a framework that provides the Inversion of Control functionality.

The IoC container manages the beans. For the above mentioned example, it creates an instance of the `Engine` class, then creates an instance of `Vehicle` class, and then injects the `Engine` object as a dependency into the `Vehicle` object.

IoC container is a generic term. It is not framework specific. Spring offers two implementations of the IoC container:

1. Bean factory
2. Application context

```java
class Vehicle {
  private Engine engine;
  //...   
}
```

![image.png](Spring%20Intro%201ab76e26cece80ca85e5d2901e0a4d98/image%202.png)

Both of them are interfaces that have different implementations available. Application context is the typical IoC container in the context of Spring. Spring recommends using it unless there is a memory concern, like in a mobile device. If available memory is low, bean factory should be used.

## Bean Factory

The basic version of the Spring IoC container is bean factory. It is the legacy IoC container and provides basic management for beans and wiring of dependencies. In Spring, bean factory still exists to provide backward compatibility.

## Application Context

Application context adds more features to the bean factory that are typically needed by an enterprise application. It is the most important part of the Spring framework. All the core logic of Spring happens here. It includes basic management of beans and wiring of dependencies as provided by the bean factory. Additional features in application context include Spring AOP features, internationalization, web application context, etc

# Spring Architecture

Spring is built in a modular way and this enables some modules to be used without using the whole framework. It also makes integration with other frameworks easy. The developer can choose which module to use and discard ones that are not required.

## Spring Modules

The modules of Spring architecture, grouped together in layers, are shown below:

![image.png](Spring%20Intro%201ab76e26cece80ca85e5d2901e0a4d98/image%203.png)

The Core Container contains the following modules: Beans, Core, Context, and Spring Expression Language (SpEL). These modules provide fundamental functionality of the Spring framework, like Inversion of Control (IoC), dependency injection, internationalization as well as support for querying the object at run time.

### **Data access/ integration**

Spring has very good integration with data and integration layers, and provides support to interact with databases. It contains modules like JDBC, ORM, OXM, JMS, and Transactions.

- The **JDBC** (Java Database Connectivity) module allows the data layer to interact with databases to get data or store data, or to interact with other systems without the need of cumbersome JDBC coding. Spring JDBC is very straightforward as compared to plain JDBC and makes the code very short.
- The **ORM** (Object Relational Mapping) module provides support to integrate with ORM frameworks including Hibernate and JPA.
- The **JMS** (Java Messaging Service) module talks to other applications through the queue to produce and consume messages.
- The **OXM** (object-XML mapping) module makes the object-to-XML transformation easy by providing useful features.
- The transaction management module provides support for successful rollback in case a transaction fails.

### **Web (MVC/remoting)**

It contains the Web, Servlets, Portlets, and Sockets modules to support the creation of a web application. Spring offers a web framework of its own called Spring MVC.

### **Test**

The Test module handles the cross cutting concern of unit testing. The Spring Test framework supports testing with JUnit, TestNG, as well as creating mock objects for testing the code in isolation.

### **AOP**

The AOP module provides Aspect Oriented Programming functionality like method interception and pointcuts as well as security and logging features. Spring has its own module called Spring AOP that offers basic, aspect-oriented programming functionality. Advanced AOP functionality can be implemented through integration with AspectJ. AOP features cross cutting concerns from business logic.

## **Spring projects**

Spring also provides solutions to different enterprise application problems through Spring projects. Some of them are discussed below:

**Spring Boot** is used to develop micro services. It makes developing applications easy through features like startup projects, auto configuration, and actuator. Spring Boot has gained massive popularity since it was first released in 20142014.

**Spring Cloud** allows the development of cloud native applications that can be dynamically configured and deployed. It provides functionality for handling common patterns in distributed systems.

**Spring Data** provides consistent access to SQL and NoSQL databases.

**Spring Integration** implements the patterns outlined by the book *Enterprise Application Integration Patterns*. It allows enterprise applications to be connected easily through messaging and declarative adapters.

**Spring Batch** provides functionality to handle large volumes of data like ability to restart, ability to read from and write to different systems, chunk processing, parallel processing, and transaction management.

**Spring Security** provides security solutions for different applications be it a web application or a REST service. It also provides authentication and authorization features.

**Spring Session** manages session information and makes it easier to share session data between services in the cloud regardless of the platform/container. It also supports multiple sessions in a single browser instance.

**Spring Mobile** offers device detection and progressive rendering options that make mobile web application development easy.

**Spring Android** facilitates the development of Android applications.