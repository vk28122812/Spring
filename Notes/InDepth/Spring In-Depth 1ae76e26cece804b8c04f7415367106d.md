# Spring In-Depth

# Bean Scope

The Spring container manages beans. The term bean scope refers to the lifecycle and the visibility of beans. It tells how long the bean lives, how many instances of the bean are created, and how the bean is shared.

## **Types of bean scopes**

There are six types of scopes: singleton, prototype, request, session, application, and websocket.

The singleton and prototype scopes can be used in any application while the last four scopes are only available for a web application

### Singleton Scope

The default scope of a bean is **singleton**, in which only one instance of the bean is created and cached in memory. Multiple requests for the bean return a shared reference to the same bean. In contrast, **prototype** scope results in the creation of new beans whenever a request for the bean is made to the application context.

In our movie recommendation system example, we have two implementations of the `Filter` interface, namely `ContentBasedFilter` and `CollaborativeFilter`. We will use them to show the differences between singleton and prototype bean scope.

Application context manages the beans and we can retrieve a bean using the `getBean()` method. If we request the application context for the `ContentBasedFilter` bean three times as shown, we get the same bean:

```java
@SpringBootApplication
public class MovieRecommenderSystemApplication {

	public static void main(String[] args) {
		
		//ApplicationContext manages the beans and dependencies
		ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);

		//Retrieve singleton bean from application context thrice
		ContentBasedFilter cbf1 = appContext.getBean(ContentBasedFilter.class);	
		ContentBasedFilter cbf2 = appContext.getBean(ContentBasedFilter.class);	
		ContentBasedFilter cbf3= appContext.getBean(ContentBasedFilter.class);	
					
		System.out.println(cbf1);
		System.out.println(cbf2);
		System.out.println(cbf3);
	}
}
```

![Singleton Bean](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image.png)

Singleton Bean

As can be verified from the output, all beans are the same. The application context did not create a new bean when we requested it the second and third time. Rather, it returned the reference to the bean already created. 

Singleton bean scope is the default scope. It is used to minimize the number of objects created. Beans are created when the context is loaded and cached in memory. All requests for a bean are returned with the same memory address.This type of scope is best suited for cases where stateless beans are required. 

### Prototype Beans

 prototype bean scope is used when we need to maintain the state of the beans.

```java
//Option 1
@Scope("prototype")

//Option 2 
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
```

![Prototype Bean](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%201.png)

Prototype Bean

Spring creates a singleton bean even before we ask for it while a prototype bean is not created till we request Spring for the bean.

> Note: The singleton design pattern as specified by the GoF means one bean per JVM. However, in Spring it means one bean per application context. By the GoF definition, even if there were more than one application contexts running on the same JVM, there would still be only one instance of the singleton class.
> 

## Mixing Bean Scopes

Sometimes, a bean has singleton scope but its dependency has prototype scope. 

> `Note`: When a prototype bean is injected into a singleton bean, it loses its prototype behavior and acts as a singleton.
> 

```java

@Component 
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Movie {
  //for keeping track of instances created
  private static int instances = 0;

  private int id;
  private String name;
  private String genre;  
  private String producer; 

  public Movie() {
      instances++;
      System.out.println("Movie constructor called");
  }

  public static int getInstances() {
      return Movie.instances;
  }

  //...
}

@Component
public class ContentBasedFilter implements Filter {
  //for keeping track of instances created
  private static int instances= 0;

  @Autowired
  private Movie movie;

  public ContentBasedFilter() {
      instances++;      
      System.out.println("ContentBasedFilter constructor called");
  }

  public Movie getMovie() {
      return movie;
  }

  public static int getInstances(){
      return ContentBasedFilter.instances;
  }
}
public static void main(String[] args) {
  ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);

  //Retrieve singleton bean from application context
  ContentBasedFilter filter = appContext.getBean(ContentBasedFilter.class);	
  System.out.println("\nContentBasedFilter bean with singleton scope");
  System.out.println(filter);

  //Retrieve prototype bean from the singleton bean thrice
  Movie movie1 = filter.getMovie(); 	
  Movie movie2 = filter.getMovie(); 	
  Movie movie3 = filter.getMovie();

  System.out.println("\nMovie bean with prototype scope");
  System.out.println(movie1);
  System.out.println(movie2);
  System.out.println(movie3);

  //Print number of instances of each bean
  System.out.println("\nContentBasedFilter instances created: " + ContentBasedFilter.getInstances());
  System.out.println("Movie instances created: "+ Movie.getInstances());
}

```

the same `Movie` bean is returned every time. Moreover, the number of instances of the prototype bean created is one instead of three.

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%202.png)

### Proxy

Spring cannot inject the prototype bean into the singleton bean after it has been created.

To solve this, We declare the bean with prototype scope as a proxy using the `proxyMode` element inside the `@Scope` annotation.

```java

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Movie {
	//...
}
```

The prototype bean doesn’t get autowired into the singleton bean at the time of its creation. Instead, a proxy or placeholder object is autowired. The proxy adds a level of indirection. When the developer requests the prototype bean from Spring, a proxy is created and is returned by the application context. The proxy mode allows Spring container to inject a new object into the singleton bean when a method on the proxy object is called.

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%203.png)

# @ComponentScan

Spring does a component scan to search for the beans that it manages. In a Spring application, the `@ComponentScan` annotation without any argument tells Spring to scan the current package as well as any sub-packages that might exist. Spring detects all classes marked with the `@Component`, `@Repository`, `@Service`, and `@Controller` annotations during component scan.

In a Spring application, `@ComponentScan` is used along with the `@Configuration` annotation. In a Spring Boot application, component scan happens implicitly.

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%204.png)

`@SpringBootApplication` by default, searches the package where it is present, as well as all the sub-packages. If a bean is present in a package other than the base package or its sub-packages, it will not be found. If we want Spring to find beans defined in other packages, we need to use the `@ComponentScan` annotation and provide the path of the package where we want Spring to look for the beans.

 The `containsBean()` method, which returns a Boolean value, is used to check if the bean is found during component scanning.

```java
//put collaborative filter in other package

@SpringBootApplication
public class MovieRecommenderSystemApplication {

  public static void main(String[] args) {
    ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);
    System.out.println("ContentBasedFilter bean found = " + appContext.containsBean("contentBasedFilter"));
    //Will give true
    System.out.println("CollaborativeFilter bean found = " + appContext.containsBean("collaborativeFilter"));
    //Will give false
  }
}
```

To get files present outside of base package, We will use the `@ComponentScan` annotation with `basePackages` argument as follows:

```java

@SpringBootApplication
@ComponentScan(basePackages="io.datajek.spring.basics.movierecommendersystem.lesson10")
// @ComponentScan(includeFilters = @ComponentScan.Filter (type= FilterType.REGEX, pattern=
public class MovieRecommenderSystemApplication {

  public static void main(String[] args) {
    ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);
    System.out.println("ContentBasedFilter bean found = " + appContext.containsBean("contentBasedFilter"));
    //Will give true
    System.out.println("CollaborativeFilter bean found = " + appContext.containsBean("collaborativeFilter"));
    //Will give false
  }

```

## **Include and exclude filters**

`@ComponentScan` can be used to include or exclude certain packages from being scanned. Include filters are used to include certain classes in component scan. Exclude filters are used to stop Spring from auto-detecting classes in component scan.

Spring also allows the creation of custom filters, e.g., find only those beans whose names are a certain length. `FilterType` can have the following values:

- `FilterType.ANNOTATION`
- `FilterType.ASPECTJ`
- `FilterType.ASSIGNABLE_TYPE`
- `FilterType.REGEX`
- `FilterType.CUSTOM`

```java
@ComponentScan(basePackages = "io.datajek.spring.basics.movierecommendersystem.lesson10")
@ComponentScan(includeFilters = @ComponentScan.Filter (
                                          type= FilterType.REGEX, 
                                          pattern="io.datajek.spring.basics.movierecommendersystem.lesson9.*"))

```

# **Bean Lifecycle:  @PostConstruct and @PreDestroy**

Spring manages the entire lifecycle of beans from the time they are created till the time they are destroyed. It provides post-initialization and pre-destruction callback methods on the beans. The developer can tap into these callbacks and write custom initialization and cleanup code.

### @PostConstruct

When Spring creates a bean, it autowires the dependencies. If the developer wants to perform a task after the dependencies have been populated, it can be done by calling a method annotated with the `@PostConstruct` annotation. A method with this annotation works like the `init()` method.  Its return type is always `void`.After the bean is created, we can initialize the contents of the bean, load data, establish a database connection, or connect to a web server. The post construct method is only called after all the dependencies have been populated.

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%205.png)

### @PreDestruct

The callback method that is executed just before the bean is destroyed is annotated using `@PreDestroy.` A method with the `@PreDestroy` annotation can be used to release resources or close a database connection.

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%206.png)

# **Bean Lifecycle: Prototype Scoped Beans**

## Lifecycle of Prototype Beans

Spring manages the entire lifecycle of singleton beans but it does not completely manage the lifecycle of prototype beans. This is because there might be a large number of prototype instances and the container can become overwhelmed keeping track of them.

> Note: The Spring container creates the prototype beans and hands them over when requested. Thereafter, it is the responsibility of the application to destroy the bean and free up any resources that it has acquired.
> 

> Note: For singleton beans, @PostConstruct and @PreDestroy methods are called automatically. For prototype beans, only @PostConstruct is called, and this happens only when a new bean instance is created.
> 

```java

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Movie {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
  //for keeping track of instances created
  private static int instances = 0;

  private int id;
  private String name;
  private String genre;  
  private String producer; 

  public Movie() {
	  super();
	  logger.info("In Movie constructor method");		
	}

	@PostConstruct
	private void postConstruct() {
	  //initialization code
	  logger.info("In Movie postConstruct method");
	}
	
	@PreDestroy
	private void preDestroy() {
	  //cleanup code
	  logger.info("In Movie preDestroy method");
	}

  public static int getInstances() {
      return Movie.instances;
  }

  //...
}

public interface Filter {
	public String[] getRecommendations(String movie);
}
@Component
public class ContentBasedFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ContentBasedFilter() {  
      super();
      logger.info("In ContentBasedFilter constructor method");		
    }

    @PostConstruct
    private void postConstruct() {
        //load movies into cache
        logger.info("In ContentBasedFilter postConstruct method");
    }
    
    @PreDestroy
    private void preDestroy() {
        //clear movies from cache
        logger.info("In ContentBasedFilter preDestroy method");
    }
	
	public String[] getRecommendations(String movie) {
      //logic of content based filter
      return new String[] {"Happy Feet", "Ice Age", "Shark Tale"};
    }
}

@Component
public class RecommenderImplementation {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Filter filter;
	
	@Autowired
	public void setFilter(Filter filter) {
	  logger.info("In RecommenderImplementation setter method..dependency injection");
	  this.filter = filter;
	}
	
	@PostConstruct
    public void postConstruct() {
        //initialization code goes here
        logger.info("In RecommenderImplementation postConstruct method");
    }
	
	@PreDestroy
	public void preDestroy() {
	   //cleanup code
	  logger.info("In RecommenderImplementation preDestroy method");
	}
  
	public String[] recommendMovies(String movie) {
		
	  //print the name of interface implementation being used
	  System.out.println("Name of the filter in use: " + filter + "\n");
	 
	  String[] results = filter.getRecommendations("Finding Dory");
	 
	  return results;
	}
  
}
@SpringBootApplication
public class MovieRecommenderSystemApplication {

	public static void main(String[] args) {
	 
		//ApplicationContext manages the beans and dependencies
		ApplicationContext appContext = SpringApplication.run(MovieRecommenderSystemApplication.class, args);

		//use ApplicationContext to get recommender object
		RecommenderImplementation recommender = appContext.getBean(RecommenderImplementation.class);	
		System.out.println(recommender);
		  
		//Retrieving prototype bean from application context twice
		Movie m1 = appContext.getBean(Movie.class);
		System.out.println(m1);

		Movie m2 = appContext.getBean(Movie.class);
		System.out.println(m2);

	}

}

```

When the application is run, we can see that the constructor and post construct methods of the singleton `RecommenderImplementation` bean (and its dependency, `ContentBasedFilter` bean) are called when the bean is created, before the application starts.

The prototype bean is not created beforehand and the constructor and post construct methods for the `Movie` bean are only called when we request the application context for the `Movie` bean.

When the application terminates, the `preDestroy()` method is called for the singleton `RecommenderImplementation` bean (and its dependency `ContentBasedFilter` bean) but not for the prototype scoped `Movie` bean.

# **Contexts and Dependency Injection Framework**

CDI is an interface that standardizes dependency injection for Java EE. It defines different annotations for dependency injection like `@Named`, `@Inject`, `@Scope`, `@Singleton`, etc. 

`@Named` is used to define a bean and `@Inject` is used for autowiring one bean into another. Spring supports most of the annotations defined by CDI.

```xml
<dependency>
    <groupId>jakarta.inject</groupId>
    <artifactId>jakarta.inject-api</artifactId>
    <version>2.0.1</version>
</dependency>
//CDI annotation dependency
```

### @Named

In Spring framework, a bean is declared using the `@Component` annotation. However, it also supports the `@Named` annotation.

### @Inject

Spring has the `@Autowired` annotation for dependency injection but it also supports the equivalent CDI annotation, `@Inject`

Other annotations provided by CDI are `@Qualifier`, `@Scope`, and `@Singleton`. The `@Qualifier` annotation is used to break ties if two beans of the same type qualify to be injected in a dependency.

`@Scope` is used to set the scope of the bean, similar to the `@Scope` annotation in Spring. The `@Singleton` annotation is used to explicitly set the scope to singleton in CDI annotation. In Spring, we can specify singleton scope using the `@Scope` annotation.

Both Spring and CDI annotations provide the same functionality. The only difference is that if the application is migrated to another framework, the CDI annotations can still be used, whereas Spring annotations are specific to the Spring framework.

# Spring Application Configuration

Running applicatoin using the core features of Spring.

## **spring-core dependency**

`spring-core` provides the fundamental features of Spring framework like dependency injection and Inversion of Control.

`spring-core` defines the bean factory and forms the base of the Spring framework.

```xml
<!-- remove the following dependency:
   <dependency>
   <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot- starter</artifactId>
   </dependency>   
   -->

   <!-- replace it with this dependency: -->
   <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
   </dependency>
```

## **spring-context dependency**

To be able to use `ApplicationContext`, we need to add another dependency called `spring-context` as follows:

```
<dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-context</artifactId>
</dependency>
```

## **@Configuration**

`@SpringBootApplication` cannot be used in the Java application file anymore as we have replaced the `spring-boot-starter` dependency with `spring-core` above. This annotation defined the application configuration in Spring Boot. In the Java realm, we use `@Configuration` and import the `org.springframework.context.annotation.Configuration` jar

### **AnnotationConfigApplicationContext**

If we run the application now, the following compilation error is encountered:

> Note: Unresolved compilation problem: SpringApplication cannot be resolved.
> 

The `SpringApplication` class creates the application context. It belongs to the `org.springframework-boot` package. When using the `spring-core`, the application context is created using `AnnotationConfigApplicationContext` class as follows:

```xml

/*Change this:
ApplicationContext appContext =              
    SpringApplication.run(MovieRecommenderSystemApplication.class, args);
*/

//to this:
AnnotationConfigApplicationContext appContext = 
  new AnnotationConfigApplicationContext(MovieRecommenderSystemApplication.class);
```

Spring Boot automatically closes the application context in the end, but now we need to close the context as follows:

```

//close the application context
appContext.close();

```

- Removing the `spring-boot starter`(don’t remove actually it contains many related dependencies like annotation, logger) dependency and replacing it with `spring-core` and `spring-context`.
- Replacing `@SpringBootApplication` with `@Configuration` and `@ComponentScan`.
- Replacing the `SpringApplication` class with the `AnnotationConfigApplicationContext` class.

# **XML Application Configuration**

## **XML configuration file**

The first step is creating an XML file that contains the bean definitions. Spring will read this file and know which beans to create and manage. We will create an XML file in `src/main/resources` and call it `appContext.xml`.

First, we need to provide some metadata for validating the tags which will be used in this file. The metadata defines the schema location of the tags, as shown:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans                    
  http://www.springframework.org/schema/beans/spring-beans.xsd">

<!-- bean definitions -->
</beans>
```

### **<bean> tag**

Next, we will define the beans inside this metadata using the `<bean> </bean>` tag. For every bean, we need to specify the fully-qualified class name as well as a reference id. The fully-qualified class name is the class name along with its package name. If we used `@Component` at three places in our application. Now we will declare three beans as follows:

```xml
<bean id="contentBasedFilter" 
  class="io.datajek.spring.basics.movierecommendersystem.lesson14.ContentBasedFilter">
</bean>

<bean id="collaborativeFilter" 
  class="io.datajek.spring.basics.movierecommendersystem.lesson14.CollaborativeFilter"> 
</bean>

<bean id="recommenderImplementation"     
  class="io.datajek.spring.basics.movierecommendersystem.lesson14.RecommenderImplementation">
</bean>
```

The IOC container will read the `appContext.xml` file and create objects of the classes mentioned in it. It will call the constructor of the class to create the object by giving it the name that we specified as the `id`. Hence, the following line:

```xml
<bean id="contentBasedFilter" 
class="io.datajek.spring.basics.movierecommendersystem.lesson14.ContentBasedFilter">   
</bean >
```

translates to: 

```xml
ContentBasedFilter contentBasedFilter = new ContentBasedFilter();
```

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%207.png)

After reading `appContext.xml` file, the IOC container creates the following beans:

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%208.png)

### **ClassPathXmlApplicationContext[#](https://www.educative.io/courses/guide-to-spring-and-spring-boot/xml-application-configuration#ClassPathXmlApplicationContext)**

Inside the `main()` method, the application context will be created using `ClassPathXmlApplicationContext` by providing the name of the XML config file as an argument:

```java

ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("appContext.xml");

//check the beans which have been loaded
System.out.println("\nBeans loaded:");
System.out.println(Arrays.toString(appContext.getBeanDefinitionNames()));
//retrieve bean from the application context
RecommenderImplementation recommender = appContext.getBean("recommenderImplementation", RecommenderImplementation.class);

```

### Dependency Injection

1. Using Setters: 

    We can create getter and setter methods for the any dependency and then :

```java
public class RecommenderImplementation {
	
  private Filter filter;

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) 
  {
    this.filter = filter;
  }
  //...
}
```

```java

//using property tag
<bean id="recommenderImplementation"     
  class="io.datajek.spring.basics.movierecommendersystem.lesson14.RecommenderImplementation">
  <property name="filter" ref="collaborativeFilter"/> 
</bean>
```

1. Using Constructor

```java
public class RecommenderImplementation {
	
  private Filter filter;

  public RecommenderImplementation(Filter filter) {
    this.filter = filter;
  }
  //...
}

```

```java
<bean id="recommenderImplementation"     
  class="io.datajek.spring.basics.movierecommendersystem.lesson14.RecommenderImplementation">
  <constructor-arg ref="collaborativeFilter"/> 
</bean>
```

# 

# **XML Configuration with Java Annotations**

if we want to detect beans defined by the `@Component` annotation and inject the dependencies using `@Autowired` annotation while using XML context, we can do that too.

The `<context:component-scan>` tag is used to turn this feature on.

Just annotating the classes with `@Component` isn’t enough for Spring to detect them as beans. We need to trigger a component scan. In XML context, the `<context:component-scan>` tag is used to activate component scanning. To be able to use this tag, we will define a new schema and provide a shortcut name for it as `context` in `appContext.xml`.

> **Note:** By default, any tag that is used without any namespace (like `<bean>`) belongs to the default schema as mentioned [here](http://www.springframework.org/schema/beans) .
> 

```java

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans.xsd
						http://www.springframework.org/schema/context
						http://www.springframework.org/schema/context/spring-context.xsd">

<!-- enable component scan -->
<context:component-scan
        base-package="io.datajek.spring.basics.movie_recommender_system" />

<bean id="recommenderImplementation"
      class="io.datajek.spring.basics.movie_recommender_system.lesson11.RecommenderImplementation">
     <property name="filter" ref="collaborativeFilter"/>
</bean>
</beans>
```

Like the `<context:component-scan>` tag, the `<context:annotation-config>` tag can also detect dependency injection annotations, e.g., `@Autowired` and `@Qualifier`. But it cannot detect beans annotated with `@Component` and other stereotype annotations.

If we are using the `<context:annotation-config>` tag, then we need to declare beans in the XML config file.

# **Stereotype Annotations**

Beans can be declared using the `@Bean` annotation in a configuration class or by using the `@Controller`, `@Service`, and `@Repository` annotations. These annotations are used at different layers of an enterprise application. A typical application has the following layers:

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%209.png)

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%2010.png)

The web or UI layer interacts with the client program, the service layer provides an abstraction between the web and data access layer as well as taking care of the business logic, and the data layer interacts with a database or an external interface. `@Component` is a generic annotation. It can be used in any layer, if the developer is unsure about where the bean belongs. The other three annotations, `@Controller`, `@Service`, and `@Repository`, are specific to layers.

## **@Controller**

`@Controller` is used to define a controller in the web layer. Spring scans a class with `@Controller` to find methods that are mapped to different HTTP requests. It makes sure that the right view is rendered to the user. `@RestController` is a specialized form of `@Controller`.

## **@Service**

`@Service` is used in the business layer for objects that define the business logic. It marks a class as a service provider.

## **@Repository**

`@Respository` is used in the data layer to encapsulate storage, retrieval, and search in a typical database. This annotation can also be used for other external sources of data.

# External Files

![image.png](Spring%20In-Depth%201ae76e26cece804b8c04f7415367106d/image%2011.png)

**application-properties file**

**@Value** dynamically fetch the value from the file using the `@Value` annotation.

**@PropertySource** we will mention the name of the file from where to fetch the value using the `@PropertySource` annotation. By default, Spring loads the property file from the classpath.