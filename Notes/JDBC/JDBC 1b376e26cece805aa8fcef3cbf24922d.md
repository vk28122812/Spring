# JDBC

Spring JDBC makes talking to databases easy by eliminating the need for establishing a connection, handling expectations, and closing connections. Spring provides a template class called `JdbcTemplate` to interact with databases which offers a wide array of methods to perform storage and retrieval of data. The `JdbcTemplate` class hides all the boilerplate code for connection management and error handling whereby the developer can focus on the SQL query.

Using the Spring `JdbcTemplate`, a Java application can store and retrieve objects of a class to a table in the database.

![image.png](JDBC%201b376e26cece805aa8fcef3cbf24922d/image.png)

JDBC with Spring Boot offers the following advantages over JDBC with Spring:

- When using Spring Boot, only one dependency (`spring-boot-starter-jdbc`) is needed in the `pom.xml` file as compared to multiple dependencies in Spring (`spring-context`, `spring-jdbc`, etc.).
- Spring Boot automatically initializes the `datasource` bean whereas it needs to be created using XML or Java configuration in Spring.
- Spring Boot also autoconfigures the `JdbcTemplate` and other template beans that need to be explicitly registered in Spring.
- Lastly, Spring Boot automatically creates the database schema specified in the `schema.sql` file. The schema needs to be explicitly configured if Spring is used.

## **In-memory database**

In-memory databases are faster because they are stored in memory and do not require disk access. The in-memory database lives as long as the application is running. When the application is stopped, the database is removed from memory. In-memory databases are easy to use as they do not require any setup like installing a server, creating a database, defining a schema, etc. that are required with databases like Oracle or MySQL. We can easily swap the in-memory database with a traditional database with a few changes in the `pom.xml` file. The in-memory database can be viewed in a web browser.

# Setting up H2

## **Configuring database connection**

- The in-memory database H2 has automatically been configured in our application. The URL of the database can be found from the console log. This value is randomly generated each time the server is restarted. To make the database URL a constant, we need to configure this in `application.properties` as follows:

```java
spring.datasource.url=jdbc:h2:mem:testdb
```

- The next task is connecting to the H2 database. One of the reasons for using Spring Boot is that its autoconfiguration feature looks at the H2 dependency and automatically configures a connection to the H2 database. The H2 console can be enabled in the `application.properties` file as follows:

```java
spring.h2.console.enabled=true
```

Enabling the H2 console in application.properties file

- When the application is run the database can be viewed in the web browser by typing `localhost:8080/h2-console`. In the login page that shows up, make sure that the JDBC URL is the same as the one that we provided in the `applications.properties` file (`jdbc:h2:mem:testdb`). If not, change it to `jdbc:h2:mem:testdb` and click “Connect” to go to the database console
- Better way is to define the query in a SQL file. For this purpose, create an SQL file in **src/main/resources** called `schema.sql`. This file will be called whenever the application is launched and will initialize the database. It contains all the DDL (Data Definition Language) queries

```java
CREATE TABLE Player
(
   ID INTEGER NOT NULL,
   Name VARCHAR(255) NOT NULL,
   Nationality VARCHAR(255) NOT NULL,
   Birth_date TIMESTAMP,
   Titles INTEGER,
   PRIMARY KEY (ID)
);
```

- To avoid inserting data every time we restart the application, we will create another SQL file in the **src/main/resources** folder and add all our `INSERT` queries to that file. This file is called `data.sql` and it contains all DML (Data Manipulation Language) queries.
- 

```java
INSERT INTO Player (ID, Name, 
Nationality, Birth_date, Titles)
VALUES(1, 'Djokovic', 'Serbia', '1987-05-22', 81);

INSERT INTO Player (ID, Name, 
Nationality, Birth_date, Titles)
VALUES(2, 'Monfils', 'France', '1986-09-01', 10);

INSERT INTO Player (ID, Name, 
Nationality, Birth_date, Titles)
VALUES(3, 'Isner', 'USA', '1985-04-26', 15);
```

# SELECT query

JDBC involves a lot of boilerplate code that is required just to get the application working. It is a tedious task to write a simple query using JDBC. There are a number of steps that are required to interact with the database.

- The first step is establishing a connection
- The second step is creating a prepared statement or query
- The third step is to execute the query
- The fourth step is looping through the result set to get the objects
- The fifth and final step is to close the connection

Spring JDBC support makes interacting with databases easy. Since we are using Spring Boot, the connection part is automatically taken care of and the data source is automatically configured. Likewise, the connection is automatically closed. Spring provides `JdbcTemplate`, which makes it easy to write and execute a query. It also provides the `BeanPropertyRowMapper` which maps rows of a table to a bean.

![image.png](JDBC%201b376e26cece805aa8fcef3cbf24922d/image%201.png)

Spring JdbcTemplate and RowMapper simplify database operations

### **Defining Player bean**

We have created a `Player` table but we need to define a class `Player` with the same fields. The rows in the `Player` table will map to this class.

```java
import java.sql.Date;

public class Player {
	private int id;
    private String name;
    private String nationality;
    private Date birthDate;
    private int titles;
	
    public Player( ) {
    
    }
    
    public Player(int id, String name, String nationality, Date birthDate, int titles) {
		super();
		this.id = id;
		this.name = name;
		this.nationality = nationality;
		this.birthDate = birthDate;
		this.titles = titles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getTitles() {
		return titles;
	}

	public void setTitles(int titles) {
		this.titles = titles;
	}

	@Override
	public String toString() {
		return "\nPlayer [id= " + id + ", name= " + name + ", nationality= " + nationality + ", birthDate= " + birthDate
				+ ", titles= " + titles + "]";
	}
}	

```

we will create two constructors (with arguments and no-argument), getters and setters, as well as a `toString()` method for the fields. The no-arg constructor is a requirement of the `BeanPropertyRowMapper`.

The `Player` class is a bean and the data coming from the `Player` table in H2 will be mapped to this class.

### Dao

```java
@Repository
public class PlayerDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Player> getAllPlayers() {
	    String sql = "SELECT * FROM PLAYER";
	    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Player>(Player.class));
	}
}
```

The `PlayerDao` class will have methods that execute various queries to manipulate rows of the `Player` table.

The first method in the `PlayerDao` class returns all rows from the `Player` table.  The return type will be `List<Player>`. This method will execute the `SELECT *` query. We will autowire the `JdbcTemplate` in the `PlayerDao` class.

`JdbcTemplate` offers a number of methods. We will make use of the `query` method of `JdbcTemplate` to execute a `SELECT` query.

## query method

The `query` method of `JdbcTemplate` is used to execute a SQL SELECT query and map the result set to Java objects. It takes two parameters;

1. The SQL query to execute.
2. An implementation of the `RowMapper` interface, which is responsible for mapping rows from the result set to Java objects. The `BeanPropertyRowMapper` is the default row mapper defined by Spring.

The `query` method returns a `List<>` containing the objects mapped from the result set by the `RowMapper`. Each element in the list represents one row of the result set, mapped to a Java object of the specified type.

To use the method, we will define a SQL query to select all rows from the `player` table. Then, we call the `query` method, passing the SQL query and a `RowMapper` implementation.

The `BeanPropertyRowMapper` internally creates a `Player` object for each row of the result set, maps values from the result set to the `Player` object, and returns the `Player` object.

## executing query

To run this query, we will use the **`CommandLineRunner`**. A `CommandLineRunner` is an interface in Spring Boot which has only one method called `run()`. This method is launched as soon as the context is loaded. Our `TennisPlayerApplication` will implement the `CommandLineRunner`.

We will autowire the `PlayerDao` class and use an object of this class to call the `getAllPlayers()` method inside the `run()` method of the `CommandLineRunner`. A logger will display the list of players returned.

```java
@SpringBootApplication
  public class TennisPlayerApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PlayerDao dao;

    public static void main(String[] args) {
        SpringApplication.run(TennisPlayerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("All Players Data: {}", dao.getAllPlayers());
    }
  }
```

## **Select with a WHERE clause**

 instead of using the query method we will use the `queryForObject` method of `JdbcTemplate`. This method accepts a list of parameters. We will create a list of objects and pass it to the method. 

```java
public Player getPlayerById(int id) {
  String sql = "SELECT * FROM PLAYER WHERE ID = ?";
  return jdbcTemplate.queryForObject(sql, 
                                    new BeanPropertyRowMapper<Player>(Player.class), 
                                    new Object[] {id});
}

//--------------------------------------------------------
@SpringBootApplication
public class TennisPlayerApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PlayerDao dao;
	
	public static void main(String[] args) {
		SpringApplication.run(TennisPlayerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//logger.info("All Players Data: {}", dao.getAllPlayers());
		logger.info("Player with Id 3: {}", dao.getPlayerById(3));
	}
}
```

# INSERT query

To insert a row, we need to send a `Player` object as input parameter.

The `update()` method is used for an `INSERT` query. This method takes the SQL query as well as an array of objects containing the values that will be inserted. The method returns an `int` value which shows the number of rows affected by the query.

```java
public int insertPlayer(Player player)
{
  String sql = "INSERT INTO PLAYER (ID, Name, Nationality,Birth_date, Titles) " + 
                                                                "VALUES (?, ?, ?, ?, ?)";
  return jdbcTemplate.update( sql, new Object[] 
                            { player.getId(), player.getName(), player.getNationality(), 
                              new Timestamp(player.getBirthDate().getTime()), 
                              player.getTitles()  
                            });
}
```

```
@Override
public void run(String... args) throws Exception 
{
  logger.info("Inserting Player 4: {}", dao.insertPlayer(
                                        new Player (4, "Thiem", "Austria", 
                                                      new Date(System.currentTimeMillis()),
                                                      17 )));	
  logger.info("All Players Data: {}", dao.getAllPlayers());
}
```

# Update Query

The `update()` method of the `JdbcTemplate` is used to execute `INSERT` as well as `UPDATE` queries.

The only difference is in the query, which will change from `INSERT` to an `UPDATE` query.

```java
public int updatePlayer(Player player){
  String sql = "UPDATE PLAYER " +
              "SET Name = ?, Nationality = ?, Birth_date = ? , Titles = ? " +
              "WHERE ID = ?";

  return jdbcTemplate.update( sql, new Object[] { 
                                player.getName(), 
                                player.getNationality(), 
                                new Timestamp(player.getBirthDate().getTime()), 
                                player.getTitles(), 
                                player.getId() }
                            );
}

```

```java
@Override
public void run(String... args) throws Exception {
  //Inserting a player
  logger.info("Inserting Player 4: {}", dao.insertPlayer( 
                      new Player(4, "Thiem", "Austria", 
                                new Date(System.currentTimeMillis()), 17)));	

  //Updating a player
  logger.info("Updating Player with Id 4: {}",  dao.updatePlayer(
                      new Player(4, "Thiem", "Austria", 
                                Date.valueOf("1993-09-03"), 17)));

  //View player by Id
  logger.info("Players with Id 4: {}", dao.getPlayerById(4));
}
```

# DELETE query

The `update()` method is also used to execute DELETE query operations.

```java
public int deletePlayerById(int id) {
  String sql="DELETE FROM PLAYER WHERE ID = ?";
  return jdbcTemplate.update(sql, new Object[] {id});
}
```

```java
@Override
public void run(String... args) throws Exception {
  logger.info("Deleting Player with Id 2: {}", dao.deletePlayerById(2));
  logger.info("All Players Data: {}", dao.getAllPlayers());
}
```

It can be seen how simple it is to retrieve, insert, update and delete rows using Spring `JdbcTemplate`. All the complexity lies in creating the query. Once the query has been created, all that remains is passing the appropriate parameters and a `RowMapper`.

**Back**

# DDL queries

Data Manipulation Language (DML) queries are the one where we manipulate the data in the table. In a rare scenario, we might want to create a table which is a Data Definition Language (DDL). In that case, we can use the `execute()` method of the `JdbcTemplate`.

```java
public void createTournamentTable() {
  String sql = "CREATE TABLE TOURNAMENT (ID INTEGER, NAME VARCHAR(50), LOCATION VARCHAR(50), PRIMARY KEY (ID))";
  jdbcTemplate.execute(sql);
  System.out.println("Table created");
}
```

```java
@Override
public void run(String... args) throws Exception {
        dao.createTournamentTable();
}
```

# Custom RowMapper

The `BeanPropertyRowMapper` can be used to map the results of the query to our bean. If the database table has a different structure or column names, we need to define our custom mapping.

```java
@Repository
public class PlayerDao {
  //...
  private static final class PlayerMapper implements RowMapper<Player> {

  }

}
```

## **RowMapper interface**

We can define our own row mapper in the `PlayerDao` class by implementing the `RowMapper` interface and providing the bean onto which the rows will be mapped. The custom row mapper, `PlayerMapper` is created as an inner class because it will only be used inside `JdbcPlayerDao`. It is best practice to make it `static` and `final`.

## mapRow() method

The `Rowmapper` interface has one method, `mapRow`, for which we will write our custom implementation to initialize a `Player` object. This method defines how a row is mapped. It takes two arguments, the first being the result set which `JdbcTemplate` gets after running the query and the second being the row number. The row number of every row in the result set is different. The `JdbcTemplate` calls the `mapRow()` method for every row in the result set and passes its row number as an argument. The method returns an object of `Player` type.

```java
@Override
public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    Player player = new Player();
    player.setId(resultSet.getInt("id"));
    player.setName(resultSet.getString("name"));
    player.setNationality(resultSet.getString("nationality"));
    player.setBirthDate(resultSet.getDate("birth_date"));
    player.setTitles(resultSet.getInt("titles"));
    return player; 
}
```

## **Using custom row mapper**

To use `PlayerMapper`, we can simply pass it in any method instead of the `BeanPropertyRowMapper`. We will create a method that finds players based on their nationality and use our custom mapper to convert the result set to objects as follows:

```java
public List<Player> getPlayerByNationality(String nationality) {
  String sql = "SELECT * FROM PLAYER WHERE NATIONALITY = ?";
  return jdbcTemplate.query(sql, new PlayerMapper(), new Object[] {nationality});
}

```

```java
@Override
public void run(String... args) throws Exception {
    logger.info("French Players: {}", dao.getPlayerByNationality("France"));
}
```