# REST

Spring framework is a very good choice for creating REST applications. Spring provides specialized annotations that make RESTful application development easy. Some examples include `@RestController`, `@ResponseBody` and `@ResponseStatus` etc.

Spring also automatically handles Jackson integration which makes conversion of data from JSON to POJO and vice versa a breeze. Any JSON data received by the controller is automatically converted to a POJO and any POJO returned by the controller is converted to JSON.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image.png)

# REST

**REST** stands for the **RE**presentational **S**tate **T**ransfer. It provides a mechanism for communication between applications. In the REST architecture, the client and server are implemented independently and they do not depend on one another. REST is language independent, so the client and server applications can use different programming languages. This gives REST applications a lot of flexibility.

The REST architecture is stateless meaning that the client only manages the session state and the server only manages the resource state. The communication between the client and server is such that every request contains all the information necessary to interpret it without any previous context.

Both the client and server know the communication format and are able to understand the message sent by the other side. REST calls can be made over **HTTP**. The client can send an HTTP request message to the server where it is processed, and an HTTP response is sent back.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%201.png)

The request message has three parts:

1. The request line contains the HTTP method like (GET or POST etc.)
2. The request header contains data with additional information about the request.
3. The request body contains the contents of the message, e.g., if it is a POST request, the request body will contain the contents of the entity to be created.

The response message also has three parts:

1. The response line contains the status code for success or redirection etc.
2. The response header contains additional information about the response like the content type and the size of the response. The client can render the data based on the content type so if it is text/html, it is displayed according to the HTML tags and if it is application/json or application/xml, it is processed accordingly.
3. The response body contains the actual message sent in response to the request.

The HTTP methods for CRUD operations are:

- **POST** for creating a resource
- **GET** for reading a resource
- **PUT** for updating a resource
- PATCH  for updating a resource partially
- **DELETE** for deleting a resource

# JSON Data binding

The most commonly used data formats in a REST application are JSON and XML. **JSON** stands for **J**ava**S**cript **O**bject **N**otation. It is a plain text data format used for exchanging data between applications.

JSON is a collection of name-value pairs, which the application processes as a string. So, instead of using HTML or JSP to send data, it is passed as a string, and the application can process and render the data accordingly. JSON is language-independent and can be used with any programming language.

- A JSON object is defined between curly braces ({ }).
- The object consists of members in the form of comma-separated name-value pairs.
- The names and values are separated by a colon (:).
- Names are provided in double quotes and are on the left side of the colon.
- The values are on the right side of the colon.
- If the value is a string, it is written in double quotes.
- JSON also supports arrays written within square brackets ([ ]) that contain a comma-separated list of values.
- An object can contain nested objects.
- JSON objects can have a *null* value.
- Boolean values true and *false* are also allowed.

## **Java - JSON data binding**

A Java object (**POJO**) can be converted into a JSON object and vice versa through a process called data binding. We can read JSON and use it to populate a Java object. In the same manner, we can use a Java object to create JSON.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%202.png)

## **Jackson Project**

Jackson Project handles data binding between Java and JSON. It also provides support for data binding with XML. Spring framework uses the Jackson project for data binding. The Jackson data binding API is present in the `com.fasterxml.jackson.databind` package.

The following maven dependency adds Jackson support to the project:

```java
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.4</version>
</dependency>
```

Jackson handles the conversion between JSON and POJOs by making use of the getter and setter methods of a class. To convert a JSON object to POJO, the setter methods are called. Vice versa, to create a JSON object from a POJO, the getters methods are used. Jackson has access to the getters and setters only, not the private fields of the class.

The figure below illustrates how Jackson converts JSON data to a Java object. It calls the corresponding setter methods to populate the class members. If a setter method matching a JSON property is not found, an exception is thrown.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%203.png)

The Jackson annotation `@JsonIgnoreProperties` can be used to bypass the exception by setting the `IgnoreUnknown` attribute to `true`. This feature is useful when the JSON file contains more properties but we are only interested in a few of them.

To create a REST API in Spring, we need the `spring-webmvc` dependency. This dependency supports web as well as RESTful applications. It loads all the supporting dependencies like `spring-core`, `spring-context`, `spring-web` etc., and comes with the embedded Tomcat server. Secondly, we need the `jackson-databind` dependency for JSON data binding. By having the Jackson dependency on the classpath, Spring will automatically handle the conversion of JSON data to POJO and vice versa.

# **Creating a REST Controller**

## **@RestController**

 This annotation is an extension of `@Controller` annotation. The `@RestController` annotation has support for REST requests and responses and automatically handles the data binding between the Java POJOs and JSON.

```java
@RestController
public class PlayerController{

}
```

## **Adding request mapping**

```java
@GetMapping("/welcome")
public String welcome() {
    return "Tennis Player REST API";
}

```

The above code creates a RESTful endpoint `/welcome` which can be accessed from the REST client and returns the message string to the client. The `@GetMapping` annotation is discussed in detail in the next lesson.

# @GetMapping

The client sends an HTTP request to the REST service. The dispatcher servlet handles the request and if the request has JSON data, the `HttpMessageConverter` converts it to Java objects. The request is mapped to a controller which calls service layer methods. The service layer delegates the call to repository and returns the data as POJO. The `MessageConverter` converts the data to JSON and it is sent back to the client. The flow of request is shown below:

![REST service architecture](REST%201b976e26cece80af9de1da3b628e07d1/image%204.png)

REST service architecture

The `@GetMapping` annotation maps HTTP GET requests to controller methods. It is a shortcut for:

```java
@RequestMapping(method = RequestMethod.GET)

```

```java
@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Tennis Player REST Api!";
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }
}

```

# @PathVariable

Path variables are a way of parameterizing the path or endpoint to accept data. Path variables are written in curly braces. When the client sends a request, it passes a value in place of the path variable. For example, we could say `/players/1` to give us the player with Id `1`, or `/players/3` for the player with Id `3`.

The REST client will send a request to `/players/{playerId}`, where `playerId` is a path variable. So the actual call may be `/players/2`. The REST service will return the player with `id` `2` from the `Player` table, which is Monfils.

![Path Variable](REST%201b976e26cece80af9de1da3b628e07d1/image%205.png)

Path Variable

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%206.png)

GET request to /players/2

`JpaRepository` interface provides us with methods for all basic CRUD operations. We need to write a service method to call the repository. We will call this method `getPlayer`. It takes an integer `id` as input and returns a `Player` object.

To retrieve an entity based on the id, `JpaRepository` provides the `findById()` method. This method has a return type of `Optional`. Optional is a design pattern introduced in Java 8, where instead of writing code to check for null values, we can see if a value is present in the `Optional`.

Since `findById()` does not return a `Player` object, we will get a type mismatch error when we try to return the result. The way to go about it, is to create a temporary variable to store the result of the method call.

```java
@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;
		 
		//....

    public Player getPlayerById(int id){
        Optional<Player> tempPlayer = playerRepository.findById(id);

        Player p = null;

        if(tempPlayer.isPresent()){
            p = tempPlayer.get();
        }else{
            throw new RuntimeException("Player with id: "+id+" not found");
        }

        return p;
    }

}

```

```java
@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;
		
		//....

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable int id){
        return playerService.getPlayerById(id);
    }
}
```

Since there is a path variable in the endpoint, we need to bind it with a method parameter. The `@PathVariable` annotation binds the path variable `{id}` from the URL to the method parameter `id`. By default, both the names must be the same for the binding to work.

If a GET request is sent for a player record which is not present in the list or the user enters a character in place of an int for the path variable, the application will throw an error.

# @PostMapping

In this lesson, we will create an endpoint for the REST API which creates a new player and adds it to the database.

The REST client will send a POST request to `/players`. The body of the request contains the JSON data for a player. Since this is a new player, the client will not pass the ID (primary key). The backend system will generate the key for the new record.

The REST service will convert JSON data to POJO and add it to the database. The primary key of the added player is automatically generated by Hibernate, which is the default ORM used by Spring Data JPA. The response to the client is an echo of the player details along with the newly generated ID value.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%207.png)

### @save

The `JpaRepository` interface inherits a method from the `CrudRepository` called `save()`. This method handles both inserts and updates. To distinguish between an INSERT and UPDATE operation, the `save()` method checks the primary key of the object that is being passed to it. If the primary key is empty or null, an INSERT operation is performed, otherwise an UPDATE to an existing record is performed.

```java
public Player addPlayer(Player p) {
    return repo.save(p);
}
```

### @PostMapping

The `@PostMapping` annotation maps HTTP POST requests to controller methods. It is a shortcut annotation for:

```java
@RequestMapping(method = RequestMethod.POST)
```

### **@RequestBody**

The client will send the player data in the request body as JSON. Jackson will convert the incoming JSON data to POJO. The `@RequestBody` annotation handles this conversion and binds the data in the request body to a method parameter.

```java
@PostMapping("/players")
public Player addPlayer(@RequestBody Player player) {
    player.setId(0);
    return service.addPlayer(player);
}
```

Inside the `addPlayer()` method, we will set the primary key to zero. This is done to ensure that if the client accidentally passes the id of a player to be added, we remove it from the request before delegating the call to the service layer. The `save()` method offered by the `JpaRepository` works for both INSERT and UPDATE requests by checking the primary key and performs an INSERT or UPDATE operation depending upon its value.

We are explicitly setting the `Id` to zero to ensure the insertion of a new player instead of an update to an existing player. By overwriting the `Id` with zero, we are effectively setting it to null or empty.

# @PutMapping

The HTTP PUT request is used for updates. The REST client will send a PUT request to `/players/{playerId}` with JSON data containing the information to be updated. The player’s ID is a path variable.

The REST service will convert the JSON data to a `Player` object, and using the id of the player; it will overwrite the player’s record in the database with the one sent in the PUT request. On success, the REST service will respond with the updated player record (which is an echo of the request).

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%208.png)

To handle the update based on the player’s id, we will create a method called `updatePlayer()` in the `PlayerService` class. This method takes in two arguments: a player’s Id and a `Player` object. It returns the updated `Player` object.

The primary key passed in the method will be used to fetch the existing record from the database. We will use the `getOne()` method as follows:

The last step is to call repository methods to save the updated information

```java
public Player updatePlayer(int id, Player p) {
		
  //get player object by ID
  Player player = repo.getOne(id);

  //update player details
  player.setName(p.getName());
  player.setNationality(p.getNationality());
  player.setBirthDate(p.getBirthDate());
  player.setTitles(p.getTitles());

  //save updates
  return repo.save(player);
}
```

### @PutMapping

The `@PutMapping` is a shortcut annotation for mapping a PUT request to a controller method. It is the same as:

```java
@RequestMapping(method=RequestMethod.PUT)
```

The `updatePlayer()` method accepts JSON data in the request body. The `@RequestBody` annotation binds the JSON data to the Player object `player`. It handles the conversion from JSON to POJO.

We have also used the `@PathVariable` annotation which will extract the path variable `id` from the incoming request `/players/{id}` and bind it with the `id` method parameter.

The controller method delegates the call to the service layer by calling its update method and passing the `id` and the `Player` object containing the information to be updated:

```java
@PutMapping("/players/{id}")
public Player updatePlayer(@RequestBody Player player, @PathVariable int id) {
  return service.updatePlayer(id, player);
}
```

# @PatchMapping

## **Partial update**

The PUT method updates the whole record. There may be a scenario when only one or two fields needs to be updated. In that case, sending the whole record does not make sense. The HTTP PATCH method is used for partial updates.

Sometimes we may need to update a single field. For example, once we enter a player in our database, the field that will most likely change is his `titles` count. The player entity only has a few fields and PUT can be used for update. But if the entity is large and contains nested objects, it will have a performance impact to send the whole entity only to update a single field.

So, in our example, partial request means that we only send the `titles` in the request body instead of the whole `Player` object. If we use PUT to send a partial request, all other fields are set to null. The code widget below illustrates the point if a PUT request with the following request body is sent to `/players/1`:

```java
{
    "titles": 161
}
```

We get the following response:

```java
{
    "id": 1,
    "name": null,
    "nationality": null,
    "birthDate": null,
    "titles": 161
}
```

The titles have been modified but the rest of the values are now null which is not the desired outcome. The PUT method requires the entire object to be sent, even when we want to modify a single field. If partial data is sent, then all other fields are set to null. PATCH comes in handy in such situations. It allows a list of changes to be applied to the entity,

![Patch Request](REST%201b976e26cece80af9de1da3b628e07d1/image%209.png)

Patch Request

## Service layer for patching

In the `PlayerService` class, we will implement a method to handle partial updates to the `Player` object. The method `patch()` will have two arguments. The first is the `id` of the player on which the patch is to be applied. The second argument is the `Map` containing the key-value pairs of the fields that will be updated. The key (field name) is a `String` while the value is an `Object` as it can have different datatypes.

Inside the method, we will use the `id` to fetch the existing `Player` object from the database using the `findById()` method of the `JpaRepository`.The `findById()` returns an `Optional` and we need to check if a `Player` object is returned using the `isPresent()` method.

```java
public Player patch( int id, Map<String, Object> playerPatch) {

  Optional<Player> player = repo.findById(id);
  if (player.isPresent()){
      //update fields using Map
      }
  return repo.save(player);				
}
```

Next, we will loop through the `Map`, find the field that will be updated, and then change the value of that field in the existing `Player` object that we retrieved from the database in the previous step

### Using Reflection

Next, we will loop through the `Map`, find the field that will be updated, and then change the value of that field in the existing `Player` object that we retrieved from the database in the previous step. The Reflection API is used to examine and modify fields, methods, and classes at runtime. It allows access to the private fields of a class and can be used to access the fields irrespective of their access modifiers. Spring provides the `ReflectionUtils` class for handling reflection and working with the Reflection API.

### **Finding the desired field**

The `ReflectionUtils` class has a `findField` method to identify the field of an object using a `String` name. The `findField()` method takes two arguments, the class having the field and the name of the field which in our case is contained in the variable `key`. This method will return a `Field` object.

```java
Field field = ReflectionUtils.findField(Player.class, key);
```

### **Making the field accessible**

To set a value for this field, we need to set the field’s accessible flag to true. `ReflectionUtils` `setAccessible()` method, when called on a field, toggles its accessible flag. We can also use another method called `makeAccessible()`. This method makes the given field accessible by calling the `setAccessible(true)` method if necessary.

```java
ReflectionUtils.makeAccessible(field);
```

### **Setting the updated value**

Lastly, we will call the `setField()` method and use the `value` from the `Map` to set the field in the player object. The `setField()` method takes three arguments:

1. The reference of the field
2. The object in which the field is to be set. We have used the `get()` method on the `Optional` player object to retrieve it.
3. The value to set

This method requires that the given field is accessible.

```java
ReflectionUtils.setField(field, player.get(), value);
```

## Controller

```java
@PatchMapping("/players/{id}")
public Player partialUpdate( @PathVariable int id, 
                             @RequestBody Map<String, Object> playerPatch) {
    return service.patch(id, playerPatch);	        
}
```

# @DeleteMapping

The `@DeleteMapping` annotation is a shortcut annotation. It is the same as:

```java
@RequestMapping(method = RequestMethod.DELETE)
```

The HTTP DELETE request deletes a record. The primary key of the record to be deleted can be sent as part of the request URI or the record itself can be sent as part of the request body.

The client will send a DELETE request to our REST service with the id of the player to be deleted. The REST Service deletes the record and responds with the 200 (OK) status code to the client.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%2010.png)

`JpaRepository` inherits two methods of the `CrudRepository` for deleting a record. One is the `delete()` method and the other is `deleteById()` method. Both are used to remove entities from the database. The `delete` method takes an entity as its parameter and deletes it from the database. While, `deleteById` accepts the primary key of the entity to be deleted and removes the corresponding entity. They both have the same function, and internally, the `deleteById()` method typically calls the `delete()` method after finding the entity by its ID using the `findById()` method. This ensures that the entity corresponding to the provided ID is deleted from the database.

The difference lies in the way both methods respond when the entity to be deleted is not found. The `deleteById()` method throws the `EmptyResultDataAccessException`, while `delete(entity)` behaves differently based on the entity state. If `delete(entity)` is called with a reference to an entity that is managed and the entity does not exist in the database, it typically results in the entity manager attempting to remove the entity without raising an exception if it's already absent. If `delete(entity)` is called with a reference to a non-managed entity or an entity with an ID that does not exist in the database, it might raise an `IllegalArgumentException`.

## **Service layer method for deletion**

```java
public  String deletePlayer(int id){
      Optional<Player> player = playerRepository.findById(id);
      if(player.isEmpty()) {
          return "Player with id: "+id+" not found" ;
      }
      playerRepository.delete(player.get());
      return "Player with id: "+id+" deleted successfully";
  }
```

 In the `PlayerService` class, create a method called `deletePlayer()`. This method takes an `int` argument which is the primary key of the player to be deleted.  It returns a `String` message to indicate success or failure of the delete operation.

Inside the method, we will call the repository method `deleteById()` and pass the player’s `id`. As mentioned above, the `deleteById()` method internally calls the `findById()` method, and then deletes the record.

To handle the exception thrown when the player record is not found, we can enclose the method call in a `try` `catch` block:

## **Controller method for deletion**

we will create a method `deletePlayer` to handle the DELETE request. It will map to the endpoint `/players/{id}`, with `id` being the path variable. The REST controller will respond with a message informing the client of the success or failure. The method is annotated with `@DeteleMapping`. The `@PathVariable` annotation binds the `id` path variable to the method parameter `id`.

```java
@DeleteMapping("/players/{id}")
public String deletePlayer(@PathVariable int id) {
  return service.deletePlayer(id);		
}
```

The `deletePlayer()` method will delegate the call to the service layer and pass the id received in the DELETE request. On success, the method will return the deleted player’s id by plugging the id received in the request. If the player is not found in the database, the method will return with a message informing the client that the player was not found.

# Exception Handling

When the client sends a request to fetch, update or delete a player record not found in the database, an internal server error occurs. The information contained in the response is verbose and of interest to developers only.

![Custom error reponse](REST%201b976e26cece80af9de1da3b628e07d1/image%2011.png)

Custom error reponse

## Custom Error Response Class

In order to send a custom response to the client, we will create a class with fields like status code, error message, path/URI, and timestamp. An object of this class will be created when an exception occurs and sent back to the client as the response. Jackson will automatically handle data binding and send this object as a JSON response.

```java
public class PlayerErrorResponse {
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private ZonedDateTime timestamp;
  private int statusCode;
  private String path;
  private String message;

  //constructor
  //getters and setters
}
```

## **Custom exception class**

When the REST service receives a bad request, we want it to throw an exception of our custom type. Since the exception is thrown when the player is not found , we will call the class `PlayerNotFoundException`. This class extends the `RunTimeException` class.

```java
public class PlayerNotFoundException extends RuntimeException {

    // Constructor and other class members...
}
```

The `RunTimeException` class contains multiple constructors and we will use one of them to throw an exception when the player is not found. These constructors allow us to create instances of `RuntimeException` with different configurations based on our requirements.

```java
//Different types of exception classes
public PlayerNotFoundException() {
    super();
}
public PlayerNotFoundException(String message) {
    super(message);
}
public PlayerNotFoundException(String message, Throwable cause) {
    super(message, cause);
}
public PlayerNotFoundException(Throwable cause) {
    super(cause);
}

```

```java
public String deletePlayer(int id) {
  Optional<Player> tempPlayer = repo.findById(id);

  if(tempPlayer.isEmpty()) {
    throw new PlayerNotFoundException("Player with id "+ id + " not found.");
  }

  repo.delete(tempPlayer.get());
  return "Player with id "+ id +" deleted";
}
```

## **@ControllerAdvice**

A best practice in exception handling is to have centralized exception handlers that can be used by all controllers in the REST API. Since exception handling is a cross-cutting concern, Spring provides the `@ControllerAdvice` annotation. This annotation intercepts requests going to the controller and responses coming from controllers.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%2012.png)

The `@ControllerAdvice` annotation can be used as an interceptor of exceptions thrown by methods annotated with `@RequestMapping` or any of its shortcut annotations. The exception handling logic is contained in the global exception handler which handles all exceptions thrown by the `PlayerController`.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%2013.png)

We will create a new class `PlayerExceptionHandler`, and annotate it with the `@ControllerAdvice` annotation so it can act as a global exception handler.

This class will have methods to handle different types of exceptions. We will write a handler method to catch the `PlayerNotFoundException` exception thrown by the methods in `PlayerService` class. This handler method will create an appropriate response for the client.

```java
@ControllerAdvice
public class PlayerExceptionHandler {

}
```

Create a method `playerNotFoundHandler()` in the `PlayerExceptionHandler` class. The input to this method is the type of the exception it will handle as well as the `HttpServletRequest` object. In our case, the exception will be of type `PlayerNotFoundException`. The method returns a `ResponseEntity` object containing the HTTP response when the exception occurs.

```java
@ExceptionHandler
public ResponseEntity<PlayerErrorResponse> playerNotFoundHandler (
                                                            PlayerNotFoundException ex, 
                                                            HttpServletRequest req) {
    PlayerErrorResponse error = new PlayerErrorResponse(
                                                  ZonedDateTime.now(),
                                                  HttpStatus.NOT_FOUND.value(),
                                                  req.getRequestURI(),
                                                  ex.getMessage());
    return new ResponseEntity<> (error, HttpStatus.NOT_FOUND);
}
```

An HTTP response message has three parts: response line, header and body. We can set these attributes in our handler method and configure the HTTP response. The `ResponseEntity` object is generic and we can send any type as the response body. In our case, the response body will contain an object of the `PlayerErrorResponse` class.

## @ExceptionHandler

The `@ExceptonHandler` annotation on a method, marks it as a method that will handle exceptions. Spring automatically checks all methods marked with this annotation when an exception is thrown. If it finds a method whose input type matches the exception thrown, the method will be executed.

Inside the handler method, we will create an object of the `PlayerErrorResponse` class and set its fields, then return it as a `ResponseEntity` object.

In addition to the body of the response, we will also return the appropriate status code with the response. The status code for `NOT_FOUND` is `404`. The last step is to create and return a `ResponseEntity` object as follows:

The `ResponseEntity` class provides a variety of constructors to create an object using the status code, header and body or a combination of the three. Here, we have used the constructor which creates a `ResponseEntity` object with a given body and status code. The other constructor variants for creating the `ResponseEntity` object are:

- `ResponseEntity(HttpStatus status)`
- `ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status)`
- `ResponseEntity(T body, MultiValueMap<String, String> headers, HttpStatus status)`

## Generic Exception Handler

It is always a good idea to create a handler to catch all exceptions and send a custom response. We will define another exception handler method called `genericHandler()`. The method signature is the same as the previous handler except for the input type, which is the parent class, `Exception` (as opposed to `PlayerNotFoundException` used in the previous handler method).

```java
@ExceptionHandler
public ResponseEntity<PlayerErrorResponse> genericHandler (
                                                    Exception ex, 
                                                    HttpServletRequest req){

    PlayerErrorResponse error = new PlayerErrorResponse(
                                              ZonedDateTime.now(),
                                              HttpStatus.BAD_REQUEST.value(),
                                              req.getRequestURI(),
                                              ex.getMessage());

    return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);
}
```

Since this method is a generic exception handler, it will set the status code to `400`, which corresponds to the HTTP status code for `BAD_REQUEST`.

# Spring Data REST

Spring Data REST is a project similar to Spring Data JPA which aims to eliminate boilerplate code. With Spring Data JPA, we get the basic CRUD functionality without writing any code simply by specifying the entity and the type of primary key. Similarly, Spring Data REST provides a REST API based on the repository and entity without us having to write any code in the controller and service layer.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%2014.png)

Spring Data REST uses the repository to expose endpoints to perform GET, POST, PUT, PATCH and DELETE on every entity in the application. Spring Data REST works with data sources implementing the repository programming model. It supports Spring Data JPA, Spring Data MongoDB, Spring Data Cassandra as well as other Spring Data projects. In the image below, it eliminates the need for `PlayerController` and `PlayerService` classes.

![image.png](REST%201b976e26cece80af9de1da3b628e07d1/image%2015.png)

Spring Data REST provides a basic REST API which can be customized. Custom queries can be added using JPQL and Query DSL.

We had a `Player` entity and created a `PlayerRepository` implementing the `JpaRespository` interface. Spring Data REST can provide us a similar API simply by scanning our repository and exposing the `/players` endpoint.

Spring Data REST creates endpoints using the entity name by making the first letter lowercase and adding an ‘s’ to the end of the name. For the case shown below, Spring Data REST will convert the entity name `Player` to its uncapitalized, pluralized form `players` and expose the REST endpoints at `/players`. It also exposes `/players/{id}` for each item managed by the repository.

To create a Spring Data REST application using Spring Boot, we need the following:

1. `spring-boot-starter-data-rest` dependency in `pom.xml`
2. An entity (e.g., `Player`)
3. A repository interface (e.g., `JpaRepository` or `CrudRepository`)

## **HATEOAS**

Spring Data REST uses the HATEOAS application architecture and provides metadata in the response including the information about the current page, as well as the total number of pages and the number of records per page. This is different from the REST API we created in the previous lessons. **HATEOAS** stands for **H**ypermedia **A**s **T**he **E**ngine **O**f **A**pplication **S**tate which basically is metadata about the REST data. The server includes hypermedia links in the response and the client can navigate those links to the data. For a collection of items, in addition to the JSON array of items, the metadata information includes the page size and total number of elements etc. This can be seen in the response to GET request to `/players`:

```java
{
  "_embedded" : {
    "players" : [ {
      "name" : "Djokovic",
      "nationality" : "Serbia",
      "birthDate" : "21-05-1987",
      "titles" : 81,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/players/1"
        },
        "player" : {
          "href" : "http://localhost:8080/players/1"
        }
      }
    }, {
      "name" : "Monfils",
      "nationality" : "France",
      "birthDate" : "31-08-1986",
      "titles" : 10,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/players/2"
        },
        "player" : {
          "href" : "http://localhost:8080/players/2"
        }
      }
    }, {
      "name" : "Isner",
      "nationality" : "USA",
      "birthDate" : "25-04-1985",
      "titles" : 15,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/players/3"
        },
        "player" : {
          "href" : "http://localhost:8080/players/3"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/players?page=0&size=20"
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/players"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 3,
    "totalPages" : 1,
    "number" : 0
  }
}
```

When we send a POST request to `/players`, the HATEOAS response shows the hyperlink to the newly created resource (`/players/4`).

## Customization

Spring Data REST allows for customization of the REST API through a number of properties. It is possible to change the path of the API using the `basePath` property. Similarly, we can choose how many items to display per page using the `defaultPageSize` and `maxPageSize` properties. The `returnBodyOnCreate` and `returnBodyOnUpdate` properties can be used to change the response after a POST and PUT request. We can also specify which repositories are exposed in the REST API by using the `detectionStrategy` property. We will explore some of the properties in the following steps.

### **Base path**

We can change the base path of our API. To do so, open the `application.properties` file and add the following property:

```java
spring.data.rest.basePath=/api
```

With this change, the application is available at `/api/players` and accessing it at `/players` will result in a `404` error.

### **Sorting**

We can sort the results returned by the field names of the entity using the `sort` property in the request URL. The `Player` entity had the `id`, `name`, `nationality`, `brithDate` and `titles` fields. We can sort the players based on their date of birth by sending a GET request to `/api/players?sort=birthDate`.The default sort order is ascending.

To sort in descending order, we need to specify the `desc` keyword. A GET request to `/api/players?sort=titles,desc` will sort the players with the most number of titles on top.

We can also sort by multiple fields, say, nationality and name as `/api/players?sort=nationality,name`.

### **Paging**

By default, Spring Data REST returns 20 records per page. If there are more than 20 records, they are moved to the next page. This default behavior can be changed using properties.

When we send a GET request to `/api/players`, we get a JSON response of all players, and at the bottom, we get the meta-data about the page, which shows that the number of items per page or size is 20. The metadata also contains information about the total number of elements, and the total number of pages in the response, as well as the current page number.

```java
spring.data.rest.defaultPageSize = 2
```

### **Resource name**

Spring Data Rest uses the up-capitalized, pluralized form of the entity name as the resource name for the endpoints. From the `Player` entity it created the `/players` endpoint. If we want to change the resource name, we can do so by using the `@RepositoryRestResource` annotation on the repository and providing the desired resource name as a path:

```java
@RepositoryRestResource(path="athletes")
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
```

Spring Data REST will now expose the `/api/athletes` endpoint instead of `/api/players`.