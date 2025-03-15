# Data JPA

JDBC requires the developer to write the queries, map values to the query, pass a set of parameters to execute the query, and map rows of the result set to a bean. For simple queries, this task is manageable, but in large applications with hundreds of tables, the queries become complex. Writing and maintaining those queries requires expertise beyond the skillset of a Java developer. Java Persistence API (JPA) is designed to ease that task.

## How JPA works?

JPA challenges the notion of writing queries and mapping the data back. It creates entities that are Java objects which map to a row in a database table. JPA creates a schema based on the entities and defines relationships between entities. The Object-Relational Mapping (ORM) layer maps the objects to a database table.

![image.png](Data%20JPA%201b476e26cece80b9ac39ef9455fccb65/image.png)

ORM maps Java objects to table rows

Using JPA, we can map a Java class or bean to a table. The members of the class map columns in the table. When this mapping is defined, JPA can write queries on its own. It takes the responsibility of creating and executing queries for CRUD operations. This is due to the fact that the operations performed on a database are identical and can be generalized. The types of objects change based on the database schema but the operations remain the same.

![image.png](Data%20JPA%201b476e26cece80b9ac39ef9455fccb65/image%201.png)

Class members are mapped to columns in a database table

## JPA Implementations

Hibernate, the most popular ORM framework in the last decade, prompted the creation of the JPA standard. JPA is a standard of Object Relational Mapping. It is an interface that defines a set of annotations for creating the object relational mapping. There are numerous implementations of the JPA interface like Hibernate, EclipseLink, Apache OpenJPA, etc. Hibernate is by far the most popular implementation of JPA. It is a lightweight framework and can easily be integrated with Spring.

The benefit of using JPA instead of Hibernate is that JPA is a standard and one can switch to any other implementation later.

To use Spring Data JPA, we will add the starter JPA dependency to the `pom.xml` file

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

```

Hibernate is an implementation of the JPA API which automatically gets configured in our application. The `hibernate-core` jar can be seen in the Maven Dependencies folder.

# Defining an Entity

To map objects to database tables, we need to mark our class with JPA annotations. JPA will automatically create a table matching the class name and generate columns corresponding to each class member.

For example,  Every instance of the `Player` class will become a row in the `Player` table. We will use the `@Entity` annotation to map this class to the `Player` table.

```java
@Entity
@Table(name="jpa_player")
Public class Player {

}
```

## @Table

In case we want to map this class to a table with a different name, we can use the `@Table` annotation and provide the name of the table to which the bean maps to, as shown in the code above. Since the name of the entity and table match, we do not need the `@Table` annotation.

## **@Id and @GeneratedValue**

Every table in a relational database has a primary key. In our case, the `Id` attribute uniquely identifies each object. The `@Id` annotation is used to indicate the primary key. We can also let JPA generate the primary key value when we insert rows. The `@GeneratedValue` annotation will automatically generate `Id` values.

```java
@Entity
public class Player {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private String nationality;
  private Date birthDate;
  private int titles;
  // ...
}
```

Now whenever a new row is inserted, we do not need to supply the `Id` value. We need another constructor that initializes all attributes except `Id`.

```java
//constructor without Id attribute
public Player(String name, String nationality, Date birthDate, int titles) {
  super();
  this.name = name;
  this.nationality = nationality;
  this.birthDate = birthDate;
  this.titles = titles;
}
```

The `Player` class now has three constructors; a no-arg constructor, one that initializes all five attributes, and one that initializes all attributes except the primary key.

## @Column

`@Column` annotation is used to define column mappings. `@Column` annotation mentions the name of the column that matches an attribute of the class. For example:

```java
@Entity
public class Player {
  @Id
  @GeneratedValue
  private int id;
  private String name;

  @Column(name="nationality")
  private String nationality;

  private Date birthDate;
  private int titles;
  //...
}
```

## **JPA schema creation**

The Spring Boot autoconfiguration triggers a schema update and creates a table by the same name as the class marked with the `@Entity` annotation. 

With JPA, we don't need to manually create tables. Instead, we can omit the table creation queries from `schema.sql`.

# Creating a Repo

## @Repository

Classes can be marked with the `@Repository` annotation.

```java
@Repository
public class PlayerRepository {

}
```

## **Enabling transaction management**

Database queries contain multiple steps. We will also enable transaction management to allow all steps in a query to succeed. In case of an error or runtime exception, all steps will be rolled back. Transactions are implemented at the business layer, but in this example, we will implement them at the repository level. Spring provides all the boilerplate code to start, commit, and roll back a transaction, which can also be integrated with JPA’s transaction management. This is enabled using the `@Transactional` annotation on a method or a class.

```java
@Repository
@Transactional
public class PlayerRepository{

}
```

Using this annotation on the `PlayerRepository` class, all the methods will be executed in a transactional context. So if we have `INSERT` and `UPDATE` in a single method, something called an `EntityManager` will keep track of both of them. If one of them fails, the whole operation will be rolled back.

## **EntityManager and @PersistenceContext annotation[#](https://www.educative.io/courses/guide-to-spring-and-spring-boot/creating-a-repository#EntityManager-and-PersistenceContext-annotation)**

A JPA `EntityManager` manages connection to a database as well as to database operations. `EntityManager` is associated with a `PersistenceContext`. All operations that are performed in a specific session are stored inside the `PersistenceContext`.

`EntityManager` is the interface to the `PersistenceContext`. All operations on the entity go through the `EntityManager`.We will declare an `EntityManager` object in our class and mark it with the `@PersistenceContext` annotation.

```java

@Repository
@Transactional
public class PlayerRepository{
  @PersistenceContext
  EntityManager entityManager;
  //...
}
```

`EntityManager` provides a number of methods that perform `SELECT`, `INSERT`, `UPDATE`, and `DELETE` queries.

# CRUD Operations

The `EntityManager` offers a large number of methods to perform various queries. We will write methods to insert, fetch, update, and delete data in the `PlayerRepository`.

## **merge() method for INSERT and UPDATE queries**

The `EntityManager` offers a `merge()` method for  both `INSERT` and `UPDATE` operations. `merge()` checks if the primary key value is being passed to it or not. If it finds the primary key, it updates the corresponding record. If the primary key is not passed, it generates a value and inserts a new record in the table.

Here, the `merge()` method returns a `Player` object.

```java
public Player insertPlayer(Player player) 
{
  return entityManager.merge(player);
}

public Player updatePlayer(Player player) 
{
  return entityManager.merge(player);
}
```

The `merge()` method will create an SQL `INSERT` or `UPDATE` query and map values to it from the `Player` object on its own. It also converts the row returned to a `Player` object on its own.

**The show-sql property**

To see the queries that Hibernate has generated, we can enable the `show-sql` property in the `application.properties` file as follows:

```java
spring.jpa.show-sql=true
```

## **find() method for SELECT query**

`EntityManager` offers a number of `find()` methods. We will pick the one that takes the name of the class and the primary key as arguments.

```java
public Player getPlayerById(int id) {
    return entityManager.find(Player.class, id);			    
}
```

Like the `merge()` method, the `find()` method also creates the SQL query and maps values to it on its own. It also converts the row returned to a `Player` object.

## **remove() method for DELETE query**

The `remove()` method of `EntityManager` is used to delete a row from the table. This is a two step process: first, we will get the object using the `find()` method by passing the `id` of the player. Then, we will pass the object to the `remove()` method. `remove()` is a void method and does not return anything.

```java
public void deletePlayerById(int id){
  Player player = entityManager.find(Player.class, id);
  entityManager.remove(player);
}
```

JPA makes interacting with databases really simple. All it needs is entity definition and mapping, and it takes care of the rest.

# **JPQL Named Query**

Java Persistence Query Language (JPQL).

A named query in JPA is a pre-defined query that is given a unique name and associated with an entity. Named queries allow us to encapsulate commonly used queries directly within our entity classes, promoting code reuse, readability, and maintainability.

A named query is defined on an entity, which in our case is the `Player` class. We will use the named query in a method called `getAllPlayers()` in the `PlayerRepository`.

## @NamedQuery

To create a named query, we will use the `@NamedQuery` annotation on the `Player` class. This annotation requires two parameters: the name of the query and the query itself. When using JPA, we will write the query in JPQL instead of SQL. **JPQL** uses entities in place of tables.

```java
@Entity
@NamedQuery(
      name="get_all_players", 
      query="select p from Player p"
)
public class Player {
    //...
}
```

To execute, the named query, we make use of the `createNamedQuery` method. This method creates a typed query object for the named query. We pass the name of the named query as a parameter to `createNamedQuery`. It returns a `TypedQuery` object, which represents the named query and allows us to set parameters, execute the query, and retrieve results.

```java
public List<Player> getAllPlayers() {
  TypedQuery<Player> getAll = entityManager.createNamedQuery("get_all_players", Player.class);
  return getAll.getResultList();
}
```

# Spring Data JPA

We have written methods to perform CRUD operations on the `Player` entity. If we add more entities to the project like **Coach** and **Tournament**, we will have to write the same code for CRUD operations and plug a different entity type.

The methods that we implemented as part of the CRUD operations are all generic methods. The logic of the methods remains the same, and only the entity changes.

Spring Data identified this duplication of code when writing repositories and created some predefined repositories. The developer provides the entity type and its primary key and Spring Data comes up with the CRUD methods for the entity. Spring Data JPA adds a layer of abstraction over the JPA provider (Hibernate in our case).

The `JpaRepository` interface extends the `Repository` interface. It contains the API provided by `CrudRespository` as well as the `PagingAndSortingRepository` for CRUD operations along with pagination and sorting of records.

![image.png](Data%20JPA%201b476e26cece80b9ac39ef9455fccb65/image%202.png)

new app: tennis-player-spring-data

**JpaRepository interface**

We will create an interface that extends the `JpaRepository`. We will call this interface `PlayerSpringDataRepository`. We need to specify the entity that will be managed by this repository, as well as the primary key of the entity as follows:

```java
public interface PlayerSpringDataRepository extends JpaRepository<Player, Integer>{

  //no implementation required!
}
```

Simply by extending the `JpaRepository`, we get all basic CRUD operations without having to write any implementation.

```java
public class TennisPlayerSpringDataApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PlayerSpringDataRepository repo;
}
```

### **save() method**

To insert and update an object, Spring Data has a `save()` method that works in the same manner as the `merge()` method of the `EntityManager`.

```java
//Inserting rows
logger.info("Inserting Player: {}", repo.save(new Player("Djokovic", "Serbia", 
                                                Date.valueOf("1987-05-22"), 81)));
logger.info("Inserting Player: {}", repo.save(new Player("Thiem", "Austria", 
                                                new Date(System.currentTimeMillis()), 17)));
                                                
 //Updating row
 logger.info("Updating Player with Id 3: {}", repo.save(new Player(3, "Thiem", "Austria", 
                                                        Date.valueOf("1993-09-03"), 17)));
}
```

### **findById() method**

Spring Data has a method called `findById()`, which takes in the primary key and returns an object.

```java
logger.info("Player with Id 2: {}", repo.findById(2));

```

### **findAll() method**

It also has a `findAll()` method which returns all entity objects.

```java
logger.info("All Players Data: {}", repo.findAll());

```

### **deleteById() method**

For deletion, Spring Data has a `deleteById()` method that takes the primary key of the record to be deleted.

```java
repo.deleteById(2);

```

Using Spring Data, we can run the same application again without writing an implementation for any of the CRUD operations. The `JpaRepository` provides us with methods needed to perform those operations. This results in a significant reduction in boilerplate code.

The CRUD methods in Spring Data are annotated with `@Transactional`. 

Spring Data can parse a method name and create a query from it. In the code widget below, we have a method `findByNationality` in the `PlayerSpringDataRepository` for which we have not provided any implementation. This method name is converted to the following JPQL query using the JPA Criteria API:

```java
select p from jpa_player p where p.nationality = ?

```

# **Connecting to Other Databases**

Using Spring Boot, switching databases is very straightforward. We just need to add some dependencies and change some property values.

The first step is to remove the H2 dependency from `pom.xml` and replace it with the dependency of the database we wish to connect to. Suppose we wish to connect to MySQL. The dependency for MySQL is given below. Dependencies for other databases are available online.

```java
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
</dependency>
```

### Configuring **property value**

For any database, we need to configure its URL, username, and password. These values are specified in the `application.properties` file.

```java
spring.jpa.hibernate.ddl-auto=none 
spring.datasource.url = jdbc:mysql://localhost:3306/movie_example
spring.datasource.username = demo
spring.datasource.password = demo
```

Since we already have a schema and do not want Hibernate to create it for us, we have set `spring.jpa.hibernate.ddl-auto` setting to `none`. 

Since we already have a schema and do not want Hibernate to create it for us, we have set `spring.jpa.hibernate.ddl-auto` setting to `none`. Other options for this setting are `create-only`, `drop`, `create`, `validate`, and `update`. If we use `create` instead of `none`, Hibernate would look at the entities and create the schema itself (after dropping any already existing schema).The `username` and `password` are used to connect to the data source.

Some databases may require additional configuration properties