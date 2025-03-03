# Spring Basics

## **Challenges of building a Spring application**

Building a Spring application from scratch can be hard. The developer needs to decide which Maven dependencies to use, set up the configuration for XML or Java, install Tomcat or another server, etc. All these things are needed for building the infrastructure of the application. This involves a lot of configuration in XML or Java, which is difficult and susceptible to errors.

## **Introducing Spring Boot: Simplifying Spring project development**

Spring Boot offers a quick-fire way to create a Spring project. It makes getting started with the actual application development easy by skipping the manual configuration part. The highlight of Spring Boot is its auto-configuration feature whereby it automatically includes all the dependencies of a project based on property files and JAR classpaths. Spring Boot is basically the Spring framework along with embedded servers. Spring boot removes the need for XML configuration.

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image.png)

## **Creating a Spring Boot project**

**Spring Initializr** is one way to create a Spring Boot project, where we can simply select the dependencies and create a basic project structure with a Maven or Gradle build specification. 

Spring Initializr, by default, creates Spring as one of the dependencies of the project, so we do not need to explicitly specify any dependency. 

- **src/main/java** is where the Java code will be written. Right now, it contains the project file `MovieRecommenderSystemApplication.java`
- **src/main/resources** is where the application properties are written.
- **src/test/java** is where the tests will be written.

The `pom.xml` file contains the project metadata information and lists the dependencies.

## Dependency

**Recommender systems**

A recommender system is a system that filters some entities based on the user’s history. Recommender systems also rank these items based on user preferences. The system works by taking an input and then finding items similar to that input.

There are various ways in which recommendations can be found. One method is content-based filtering in which item-to-item similarity is used as a basis for finding matches

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%201.png)

```java

public class ContentBasedFilter {
  public String[] getRecommendations(String movie) {
    //logic of content based filter
    return new String[] {"Happy Feet", "Ice Age", "Shark Tale"};
  }
}
public class RecommenderImplementation {
  public String[] recommendMovies(String movie) {
    //use content based filter to find similar movies
    ContentBasedFilter filter = new ContentBasedFilter();
    String[] results = filter.getRecommendations(movie);
    //return the results
    return results;
  }
}
//Tight Coupling
public class MovieRecommenderSystemApplication {
  public static void main(String[] args) {
    RecommenderImplementation recommender = new RecommenderImplementation();	
    String[] result = recommender.recommendMovies("Finding Dory");
    System.out.println(Arrays.toString(result));
  }
}
```

If we want to use another filter in place of the content-based filter, we will need to change the code in the `RecommenderImplementation` class.

Consider a scenario, where we want to use one type of filter in one situation and another type of filter in another situation. Tight coupling makes this difficult to achieve.

In a typical enterprise application, there are a large number of objects which work together to provide some end result to the user. This results in a lot of dependencies. Spring is a dependency injection framework that makes the process of managing these dependencies easy.

## Dependency Components

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%202.png)

```java
public interface Filter {
    public String[] getRecommendations(String movie);
}
public class ContentBasedFilter implements Filter{
    //...
}
public class CollaborativeFilter implements Filter{
    //...
}
public class RecommenderImplementation {
  //use filter interface to select filter
  private Filter filter;
    
  public RecommenderImplementation(Filter filter) {
      super();
      this.filter = filter;
  }

  //use a filter to find recommendations
  public String [] recommendMovies (String movie) {
      //...
  }
}

public class MovieRecommenderSystemApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MovieRecommenderSystemApplication.class, args);

		//passing name of the filter as constructor argument
		RecommenderImplementation recommender = new RecommenderImplementation(new ContentBasedFilter());	
		//call method to get recommendations
		String[] result = recommender.recommendMovies("Finding Dory");
		//display results
		System.out.println(Arrays.toString(result));

	}
}
```

Now `Filter` is a dependency of `RecommenderImplementation`.We still have to create an object of `RecommenderImplementation` and an object of `Filter` and pass the objects to the constructor.

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%203.png)

## Managing Beans and Dependencies

Our code is now loosely coupled as we are passing the name of the filter to be used as a constructor argument.

Spring automates the above process of creating objects and binding them together. It takes the responsibility of creating instances of classes and binding instances based on their dependencies. The instances or objects that Spring manages are called **beans**. To manage objects and dependencies, Spring requires information about three things:

1. Beans
2. Dependencies
3. Location of beans

### @Component

If we want Spring to create and manage objects, we can do so by adding the `@Component` annotation at the beginning of the class and importing `org.springframework.stereotype.Component`                                

```java
import org.springframework.stereotype.Component;

@Component
public class RecommenderImplementation {
    //...
}

import org.springframework.stereotype.Component;

@Component
public class ContentBasedFilter implements Filter {
    //...
}

```

The Spring container will have two beans, one of type `RecommenderImplementation` and the other of type `ContentBasedFilter`.

### @Autowired

The second thing Spring needs to know is the dependencies of each object. The `@Autowired` annotation is used for this purpose and we need to import `org.springframework.beans.factory.annotation.Autowired` to be able to use this annotation.

```java
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RecommenderImplementation {

  @Autowired
  private Filter filter;
  // ...
}
```

The `@Autowired` annotation tells Spring that `RecommenderImplementation` needs an object of type `Filter`. In other words, `Filter` is a dependency of `RecommenderImplementation`.

Press+to interact

![*Beans in the container and  Identifying bean dependencies*](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%204.png)

*Beans in the container and  Identifying bean dependencies*

### @ComponentScan

The third thing that Spring requires from the developer, is the location of the beans so that it can find them and autowire the dependencies. The `@ComponentScan` annotation is used for this purpose. This annotation can be used with or without arguments. It tells Spring to scan a specific package and all of its sub-packages

Since we are using Spring Boot, it uses the `@SpringBootApplication` annotation on the `MovieRecommenderSystemApplication` class. This annotation is equivalent to the following three annotations:

- `@Configuration`, which declares a class as the source for bean definitions
- `@EnableAutoConfiguration`, which allows the application to add beans using classpath definitions
- `@ComponentScan`, which directs Spring to search for components in the path specified

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%205.png)

Because of `@SpringBootApplication` annotation, we do not need to use `@ComponentScan` annotation in our code.

### @SpringBootApplication

`@SpringBootApplication` tells Spring to scan all the files in the package where the class with this annotation is present. It also scans any sub-packages of the package where it is placed.

When we use the `@Component`, `@Autowired`, and `@SpringBootApplication` annotations, the following line in our code becomes redundant as it is automatically done by Spring:

```java
RecommenderImplementation recommender = new RecommenderImplementation(new ContentBasedFilter());

```

When using annotations, we need not pass the filter when creating recommender object

The beans that Spring creates are managed by the `ApplicationContext`. We can get information about a bean from the Application Context. The `run()` method returns the `ApplicationContext`, which can be assigned to a variable `appContext`. Then, the `getBean()` method of `ApplicationContext` can be used to get the bean of a particular class. We will create a local variable `recommender` and assign the bean to it as follows

```java
public static void main(String[] args) {
  //ApplicationContext manages the beans and dependencies
  ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);

  //use ApplicationContext to find which filter is being used
  RecommenderImplementation recommender = appContext.getBean(RecommenderImplementation.class);	

  //call method to get recommendations
  String[] result = recommender.recommendMovies("Finding Dory");

  //display results 
  System.out.println(Arrays.toString(result));
}
```

If we add `@Component` to the `CollaborativeFilter` class, Spring will not know which bean of `Filter` type to autowire. It says, `RecommenderImplementation required a single bean, but 2 were found`.

## Autowiring By Type

### Dynamic Bean Selection

We will add the `@Component` annotation on the `CollaborativeFilter` class to declare it a bean. Now both implementations of the `Filter` interface are beans. Previously, when Spring searched for a dependency to be autowired in the `RecommenderImplementation` object, it only found one bean of matching type. Now when we run the application, it fails to start.

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%206.png)

Spring throws NoUniqueBeanDefinition exception as it doesn't know which bean to inject

The error message says: `Required a single bean, but two were found`.

### @Primary

One way Spring can choose between two beans of the same type is by giving one bean priority over the other. The `@Primary` annotation is used for making a component the default choice when multiple beans of the same type are found.

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%207.png)

Using `@Primary` is called *autowiring by type*. We are looking for instances of type `Filter`. 

If we make both beans primary by adding the `@Primary` annotation to both implementations of the `Filter` interface, we will get an error. The error message states `more than one 'primary' bean found among candidates`.

## Autowiring By Name

Another approach is autowiring by name where we specify the bean that is to be used by name. In this approach, while creating an object, the dependency is injected by matching the name of the reference variable to the bean name. The developer has to ensure that the variable name is the same as its bean name.

```java
public class RecommenderImplementation {
  @Autowired
  private Filter contentBasedFilter;

  public String [] recommendMovies (String movie) {		
    System.out.println("\nName of the filter in use: " + contentBasedFilter + "\n");
    String[] results = contentBasedFilter.getRecommendations("Finding Dory");
    return results;
  }
}
```

`Note: Omitting @Primary annotation from code`

Now, when the application is run, it chooses the `ContentBasedFilter` bean for autowiring. When Spring finds two beans of the same type (`Filter`), it determines that the bean to inject is the one whose name matches the bean with the `@Component` annotation. 

![Bean with matching name is injected](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%208.png)

Bean with matching name is injected

### Autowiring by type vs autowiring by name

 To see which autowiring approach takes precedence, we will use the `@Primary` annotation on `CollaborativeFilter` class and use autowiring by name by using `contentBasedFilter` as the name of the variable of type `Filter` in `RecommenderImplementation` class

> The application chooses the `CollaborativeFilter` bean, showing that `@Primary` has a higher priority.
> 

This is because `@Autowired` annotation tries to resolve dependency by type first. If it fails to resolve a conflict and finds more than one bean of the same type then it tries to resolve it by name.

## @Qualifier

Like `@Primary`, the `@Qualifier` annotation gives priority to one bean over the other if two beans of the same type are found. The bean whose name is specified in the `@Qualifier` annotation qualifies to be injected as a dependency. The `@Qualifier` annotation can be used in a scenario when we have multiple objects of the same type and autowiring by name cannot be used because the variable name doesn’t match any bean name.

Difference between approach1 and approach2:

In approach 1, we **explicitly provide** the bean name `"CBF"`, this becomes the **default qualifier**.

In approach2, The `@Qualifier("CF")` annotation **does not affect bean naming**; it only applies when injecting.

```java
//Approach 1
@Component("CBF")
public  class ContentBasedFilter implements Filter{
  //...
}

//Approach2
@Component
@Qualifier("CF")
public  class CollaborativeFilter implements Filter{
  //...
}

@Component
public class RecommenderImplementation {

	@Autowired
	@Qualifier("CF")
	private Filter filter;
			
	//use a filter to find recommendations
	public String [] recommendMovies (String movie) {
		
		//print the name of interface implementation being used
		System.out.println("\nName of the filter in use: " + filter + "\n");
		String[] results = filter.getRecommendations("Finding Dory");
		return results;
	}
}
```

### Autowiring by type vs autowiring by Qualifier

The `@Qualifier` annotation takes precedence over the `@Primary` annotation. To show this, we add the `@Primary` annotation to the `ContentBasedFilter` class and run the application.

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%209.png)

## Constructor and Setter Injection

Spring framework gives the developer control over how beans are wired in. There are a variety of options to choose from.

### Constructor Injection

Autowiring the dependency using a constructor is called **constructor injection**. 

```java
@Component
public class CollaborativeFilter implements Filter {
    public String[] getRecommendations(String movie) {
        //logic of collaborative filter
        return new String[] {"My name is Khan", "Pathaan", "Jawan"};
    }
}
@Component
public class ContentBasedFilter implements Filter {

    public String[] getRecommendations(String movie){

        return new String[]{"Aashiqui 2", "Drishyam", "Kal Ho Na Ho"};
    }
}

@Component
public class RecommenderImplConstructor {

    private Filter filter;

    @Autowired//Optional with constructor injection
    public RecommenderImplConstructor(@Qualifier("contentBasedFilter") Filter filter) {
        super();
        this.filter = filter;
        System.out.println("constructor invoked");
    }
}

```

Since we have two implementations of the `Filter` interface, we need to specify which one to use. If we use the `@Primary` annotation, then Spring will use the primary bean as the default choice. However, if we want different beans to be used in different scenarios, then the `@Qualfier` annotation with the bean name can be used to give a hint to Spring about which bean to inject. Since, we have not specified bean names with the `@Component` annotation, their default names (`contentBasedFilter` and `collaborativeFilter`) will be used.

> Default bean name is the class name with the first letter in lowercase.
> 

The `@Qualifier` annotation cannot be used on the constructor (as it results in an error message: `The annotation @Qualifier is disallowed for this location`), rather, it should be used in the argument list right in front of the property that we want to be autowired 

Constructor injection ensures that all dependencies are injected because an object cannot be constructed until all its dependencies are available. It also ensures immutability as the state of the bean cannot be modified after creation.

### Setter Injection

Setter injection is used to avoid the `BeanCurrentlyInCreationException` raised in case of a circular dependency, because unlike constructor injection where dependencies are injected at the time when context is loaded, setter injection injects dependencies when they are needed.

```java

@Component
public class RecommenderImplSetter {

    private Filter filter;

    @Autowired
    @Qualifier("collaborativeFilter")
    void setFilter(Filter filter) {
        this.filter = filter;
        System.out.println("Setter method  the component default bean becomes CBFinvoked");
    }
    //...
}
```

### Field Injection

Spring can perform dependency injection without a constructor or setter method.We have been using `@Autowired` annotation directly on the field. This is called **field injection**.

Using field injection keeps the code simple and readable, but it is unsafe because Spring can set private fields of the objects

![image.png](Spring%20Basics%201a976e26cece805bb2cfdebe986625fa/image%2010.png)