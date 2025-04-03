# Database Relationships

## **Primary key**

A primary key is used to uniquely identify a row in a table. The *id* column in the *player* table shown below acts as a unique identifier for all records. No two records can have the same primary key value.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image.png)

## **Foreign key**

Primary key can be used to link two tables together. When a primary key of one table is used in another table, it is known as a foreign key. To link the *player* and *player_profile* tables, the *id* column from the *player_profile* table is placed in the *player* table. The column *profile_id* is called the foreign key column and is used to point to the record in the *player_profile* table that is linked to the record in the *player* table.

# DB Relationships

The tables in a database are linked in different ways.

## **One to One relationship**

When one row in *table_a* corresponds to one row in *table_b*, then it is called a one-to-one relationship. Take the example of *player* and *player_profile* tables. One player has one player profile entry, so there is a one-to-one relationship between the tables.

![One-to-one relationship between player and player_profile tables](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%201.png)

One-to-one relationship between player and player_profile tables

## **One to Many relationship**

When one row in *table_a* corresponds to many rows in *table_b*, then it is called a one-to-many relationship. The inverse of a one-to-many relationship is a many-to-one relationship where many rows in *table_b* correspond to one row in *table_a*.For example, a player can register for many tournaments. There is a one-to-many relationship between the *player* table and the *registration* table.

![One-to-many relationship between tournament and registration tables](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%202.png)

One-to-many relationship between tournament and registration tables                          

## **Many to Many relationship**

When one row in *table_a* corresponds to many rows in *table_b* and one row in *table_b* corresponds to many rows in *table_a*, then we have what is called a many-to-many relationship.

Take the example of tournaments and playing categories (like singles, doubles etc.). One tournament features one than one playing category. In the same way, one playing category is part of many tournaments. There is a many-to-many relationship between the tournament and category tables.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%203.png)

                    Many-to-many relationship between tournament and category tables

## **Referential integrity**

When two tables are related to one another, data should reflect a consistent state. All foreign keys should refer to valid, existing primary key values. For example, a profile belongs to a player so the player must exist. If a player record is deleted, then the corresponding record in the player profile table should also be deleted. Absence of referential integrity can lead to records being lost in the database.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%204.png)

Referential integrity between player and player_profile tables

# **Cascading**

Cascading means propagating an operation from the owning entity to the related entity. When a record in the owning entity (parent table) is saved, updated or deleted, the change should be reflected in the related entity (child table) as well.

If we save a *Player* object containing a nested *Profile* object, the save operation is cascaded from the *player* table to the *player_profile* table and two records are inserted in the database. In the same manner, delete is also cascaded.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%205.png)

Representing cascading between tables

JPA offers different cascading types which are shown in the table below:

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%206.png)

# **Fetch types**

There are two ways in which data is loaded: eager and lazy. Eager fetch means that when a record is fetched from the database, all the associated records from related tables are also fetched. So if we fetch a tournament record, all the registrations for the tournament are also fetched.

Eager fetch is the default fetch type used by Hibernate but it is not always the most efficient. Lazy fetch on the other hand, fetches the records only when they are needed.

![Different types of fetching data](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%207.png)

# **Orphan records**

When we remove the relationship between a parent and child, the child record becomes an orphan record meaning that it does not have a parent record. Consider the example of tournament and registration tables where a player withdraws from a tournament. The registration gets removed from the tournament entity. The registration record becomes an orphan record. Orphan records mean that the database is in an inconsistent state.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%208.png)

Add the `Jackson` dependency for Hibernate 5. This dependency provides support for Hibernate datatypes and specifically handles aspects of lazy-loading. We are adding it manually as this dependency is not available in Spring Initializr.

```jsx
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-hibernate5</artifactId>
    <version>2.13.2</version>
</dependency>
```

In SQL, we represent relationships using primary key and foreign key. Foreign keys are used to link tables together. A foreign key is a field in one table that refers to the primary key in another table. We will see how the same can be achieved using Hibernate.

# One to One Unidirectional

 The `Player` class has a one-to-one relationship with the `PlayerProfile` class. To show this relationship, we will add a field `playerProfile` to the `Player` class and use the JPA annotation `@OneToOne` on this field. `@OneToOne` is a JPA annotation which maps a source entity to a target entity.

```java
@OneToOne
private PlayerProfile playerProfile;
```

### @cascade property

The `cascade` property ensures that changes made to a `Player` entity are reflected in the `PlayerProfile` entity. The `PlayerProfile` entity does not have a meaning of its own, rather, it defines the `Player` entity. If we delete a `Player` entity, the associated details should also be deleted. Cascading allows an operation on the `Player` entity to be propagated to the `PlayerProfile` entity.

```java
@Entity
public class Player {
    //...

    @OneToOne(cascade= CascadeType.ALL)
    private PlayerProfile playerProfile;

    //...
}
```

We have set the `CascadeType` to `ALL`. This means that all JPA and Hibernate specific operations on the `Player` entity will be propagated to the `PlayerProfile` entity.

> Note: The absence of the cascade property, results in the TransientPropertyValueException exception when Hibernate tries to save a Player object containing a nested PlayerProfile object.
> 

## @JoinColumn

In relationships, one side is the owning side. We use the `@JoinColumn` annotation on the owning side. Here, the `Player` class is the owning side of the relationship. The `@JoinColumn` annotation specifies the name of the foreign key column in the *player* table. We will call the column *profile_id*. If the name is not specified, then JPA names the column based on some rules.

In the *player_profile* table, the column that is being referenced is *id*. The name of the corresponding field in the `PlayerProfile` class is `Id`, which we specify as `referencedColumnName`.

```java
@Entity
public class Player {

    //...
    
    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="profile_id", referencedColumnName="id")
    private PlayerProfile playerProfile;

    //...

}
```

After adding the `@OneToOne` annotation, if we run the application and check the H2 database, we can see that the *player* table has changed. It now contains a *profile_Id* column, which references the *id* column in the *player_profile* table. The *player* table is called the owning table because here we store the foreign key.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%209.png)

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2010.png)

It is a unidirectional relationship because we have the reference of the `PlayerProfile` entity in the `Player` entity but we don’t have any reference of the `Player` entity in the `PlayerProfile` entity. We can retrieve the `PlayerProfile` object using the `Player` object but not the other way round.

### Creating Repositories

### Creating Service Classes

### Controllers

### Persisting Records

We can create a `Player` entity by sending a POST request to `/players` with the following request body:

```java
{
 "twitter" : "@rogerfederer"
}
```

we will create a `PlayerProfile` entity by sending a POST request to `/profiles`.

```java
{
 "twitter" : "@rogerfederer"
}
```

### **Connecting player to profile**

Right now, both entities are not connected. To assign the `PlayerProfile` to `Player`, we need to create a PUT mapping in the `PlayerController` class. This will enable us edit the `Player` entity and associate a `PlayerProfile` with it.

First, we need to write the service layer method. Create a method named `assignProfile` in the `PlayerService` class. This method is responsible for updating a `Player` record. It simply uses the setter method for the `playerProfile` field and then calls `save()` to update the record in the database.

```java
public Player assignProfile(int id, PlayerProfile profile) {
    Player player = repo.findById(id).get();
    player.setPlayerProfile(profile);
    return repo.save(player);
}
```

Now we can add a method in the `PlayerController` class which maps a PUT request to `/players/{id}/profiles/{profile_id}` and updates the `Player` entity.

```java
public class PlayerController {
	@Autowired
	PlayerService service;
	
	@Autowired
	PlayerProfileService profileService;

    //...

    @PutMapping("/{id}/profiles/{profile_id}")
    public Player assignDetail(@PathVariable int id, @PathVariable int profile_id) {
        PlayerProfile profile = profileService.getPlayerProfile(profile_id); 
        return service.assignProfile(id, profile);
    }
}
```

This is an example of a unidirectional one-to-one relationship. It is possible to retrieve a `PlayerProfile` object using a `Player` object but no way to retrieve a `Player` object using a `PlayerProfile` object, as can be seen from the GET request to `/players` and `/profiles`.

## **Hibernate implementation of @OneToOne**

Hibernate supports three variations of the `@OneToOne` mapping.

- Using foreign key with the `@JoinColumn` annotation.
- Using a common join table which has foreign keys of both tables. The `@JoinTable` annotation defines a new table name which has the foreign key from both tables. This helps in modelling optional one-to-one relationships. If a player does not have a `PlayerProfile` entry, we have to use null value in that column.
- Using a shared primary key to save space. This approach uses a common primary key (`player_id` in this case) in both tables using the `@PrimaryKeyJoinColumn`. It eliminates the need of having an `Id` column for the *player_profile* table.

The figure below illustrates the three ways in which `@OneToOne` annotation can be used.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2011.png)

# **One-to-One Bidirectional Relationship**

It is  possible to find the Twitter account, if we have the `Player` entity.

In the unidirectional one-to-one relationship, the `Player` class maintains the relationship. The `PlayerProfile` class cannot see any change in the relationship.To make this relationship bidirectional, we need to make some modifications to the `PlayerProfile` class. We will add a field to reference back to the `Player` class and add the `@OneToOne` annotation. We will also add getter and setter methods to set the `Player` value in the `PlayerProfile` class. This will enable us to fetch the entities in both directions.

## **Bi-directional relationship**

To set up a bidirectional relationship, we will add a field of `Player` class in the `PlayerProfile` class and add getter and setter methods for the field. This field holds the reference to the associated `Player` entity.

```java
public class PlayerProfile {
    @Id
    @GeneratedValue(GenertionType.IDENTITY)
    private int id;
    private String twitter;

		@OneToOne(mappedBy="playerProfile")
    private Player player;

    //...

    public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "PlayerDetail [id=" + id + ", twitter=" + twitter + ", player=" + player + "]";
	} 
}
```

### **mappedBy attribute**

we will add the `@OneToOne` annotation on the `player` field. `mappedBy` is an optional attribute of the `@oneToOne` annotation which specifies the name of the field which owns the relationship. In our case, it is the `playerProfile` field in the `Player` class. The `mappedBy` attribute is placed on the inverse side on the relationship only. The owning side cannot have this attribute.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2012.png)

To test the bidirectional relationship, we will create a `Player` with a nested `PlayerProfile` object using the following JSON POST request to `http://localhost:8080/players` :

```java
{
    "name": "Djokovic",
    "playerProfile": {
        "twitter" : "@DjokerNole"
    }
}
```

This request results in two INSERT queries in the *player* and *player_profile* tables respectively.

**JSON Infinite Recursion**

When using a bidirectional relationship, JSON throws an infinite recursion error when we try to retrieve the objects. This is because the `Player` object contains the reference of `PlayerProfile` object and the `PlayerProfile` object also contains the reference to the `Player` object.

**Solution 1: @JsonIdentityInfo**

To solve this issue, we can use the `@JsonIdentityInfo` annotation at class level. Both `Player` and `PlayerProfile` classes are annotated with `@JsonIdentityInfo` to avoid infinite recursion while converting POJOs to String.

```java
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")

```

The `property` attribute specifies the property name of the target reference.Here, `id` field is used to break out of the recursion. The first time `id` is encountered, it is replaced with the object and for subsequent occurrences of `id`, the numerical value is used instead of replacing it with the object.

**Solution 2: @JsonManagedReference and @JsonBackReference**

 use the `@JsonManagedReference` and `@JsonBackReference` annotations in the classes. As a result, only the owning side of the relationship is serialized and the inverse side is not serialized.

The `@JsonManagedReference` annotation is used on the `playerProfile` field in the owning side (`Player` class). On the inverse side (`PlayerProfile` class), the `@JsonBackReference` annotation is used to the `player` field. These annotations solve the infinite recursion problem.

```java
public class Player{
  //...
  @OneToOne(cascade=CascadeType.ALL)
  @JoinColumn(name = "profile_id", referencedColumnName = "id")
  @JsonManagedReference
  private PlayerProfile playerProfile;
  //...
}
public class PlayerProfile {
	//...
	@OneToOne(mappedBy="playerProfile")
    @JsonBackReference
    private Player player;

    //...
}

```

## Cascade

For the bidirectional relationship, we can specify the cascade type in the `PlayerProfile` class as follows:

```java
@OneToOne(mappedBy= "playerProfile", cascade= CascadeType.ALL)
private Player player;

```

`CascadeType.ALL` means that if we delete a `PlayerProfile` object, the associated `Player` object will also be deleted.

Since we do not want that to happen, we need to break the association between the two objects before calling `delete()` on the `PlayerProfile` object.

We will modify the `deletePlayerProfile` method in the `PlayerProfileService` class. The following code removes the link between the `PlayerProfile` and `Player` object by manually setting the references to `null` before deleting from the database.

```java
public void deletePlayerProfile(int id) {
    PlayerProfile tempPlayerProfile = repo.findById(id).get(); 

    //set the playerProfile field of the Player object to null
    tempPlayerProfile.getPlayer().setPlayerProfile(null);

    //set the player field of the PlayerProfile object to null
    tempPlayerProfile.setPlayer(null);

    //save changes
    repo.save(tempPlayerProfile);

    //delete the PlayerProfile object
    repo.delete(tempPlayerProfile); 
}
```

Now when the `PlayerProfile` object is deleted, the `Player` object is not affected.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2013.png)

### **optional attribute**

The `@OneToOne` annotation has an `optional` attribute. By default the value is `true` meaning that the association can be null. We can explicitly set it to `false` for the `playerProfile` attribute in the `Player` class:

```java
@OneToOne(cascade=CascadeType.ALL, optional = false) 
@JoinColumn(name = "profile_id", referencedColumnName = "id")
private PlayerProfile playerProfile;
```

If the value of the `optional` attribute is set to `false`, then we will get an error when a `Player` object is added without an associated `PlayerProfile` object.

The following POST request to `\players` now returns a `DataIntegrityViolationException` as the `playerProfile` field cannot be left blank.

```java
{
    "name": "Federer"
}

```

However, adding a `Player` with a nested `PlayerProfile` object, as shown below, does not result in an error.

```java
{
    "name": "Djokovic",
    "playerProfile": {
        "twitter" : "@DjokerNole"
    }
}

```

## **Pros and cons of bidirectional relationship**

Bidirectional relationships are better than unidirectional relationships in terms of performance as both ends of the relationship are aware of any changes.

When using bidirectional relationships, consistency must be ensured. If a `Player` object references a `PlayerProfile` object, the same `PlayerProfile` object must reference back to the `Player` object. Failure to ensure consistency can lead to unpredictable JPA behavior.

A con of having bidirectional association is that it may make the application vulnerable in terms of security since the referenced side can now be used to access the owning side (we can access the `Player` object using the `PlayerProfile` object).Infinite recursion is also an issue when using bidirectional relationships with Jackson, Hibernate JPA, and/or Elasticsearch implementations.

# One-to-many Unidirectional

Unidirectional one-to-many relationship means that only one side maintains the relationship details. So given a `Tournament` entity, we can find the `Registration`s but we cannot find the `Tournament` details from a `Registration` entity.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2014.png)

Since a player registers for a tournament, a registration object should be associated with a player object.

We will update the `Tournament` class to show the registrations. Since a tournament can have multiple registrations, we will add a `List` of `Registration`s as a new field.

### @OneToMany

The `Tournament` class has a one-to-many relationship with the `Registration` class as one tournament can have multiple registrations. This can be modelled by the `@OneToMany` annotation. In a one-to-many relationship, the primary key of the *one* side is placed as a foreign key in the *many* side.

The `@JoinColumn` annotation shows that this is the owning side of the relationship. `tournament_id` will be added as a foreign key column in the `registration` table.

```java
@OneToMany
@JoinColumn(name="tournament_id")
private List<Registration> registrations = new ArrayList<>();
```

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2015.png)

> In the absence of the `@JoinColumn` annotation, Hibernate creates a join table for the one-to-many relationship containing the primary keys of both the tables.
> 

**Creating repositories**

```java
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
}
```

```java
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
}
```

## **Creating service classes**

```java
@Service
public class TournamentService {

	@Autowired
	TournamentRepository repo;
	
	//get all tournaments
	public List<Tournament> allTournaments() {
		return repo.findAll();	    
	}
	//get tournament by ID
	public Tournament getTournament(int id){
		return repo.findById(id).get();
	}
	//add tournament
    public Tournament addTournament(Tournament tournament) {
    	tournament.setId(0);
		return repo.save(tournament);
	}
    //delete tournament	
 	public void deleteTournament(int id) {
		repo.deleteById(id);
	}
}

@Service
public class RegistrationService {

	@Autowired
	RegistrationRepository repo;
	
	//get all registrations
	public List<Registration> allRegistrations() {
		return repo.findAll();	    
	}
	//get registration by ID
	public Registration getRegistration(int id){
		return repo.findById(id).get();
	}
	//add registration
    public Registration addRegistration(Registration registration) {
    	registration.setId(0);
		return repo.save(registration);
	}
    //delete registration	
 	public void deleteRegistration(int id) {
		repo.deleteById(id);
	}
}
```

**Assigning registration to tournament:**

```java
//assign a registration to a tournament
public Tournament addRegistration(int id, Registration registration) {
    Tournament tournament = repo.findById(id).get();
    tournament.addRegistration(registration);
    return repo.save(tournament);
}
```

```java
@PutMapping("/{id}/registrations/{registration_id}")
public Tournament addRegistration(@PathVariable int id, @PathVariable int registration_id) {
    Registration registration = registrationService.getRegistration(registration_id); 
    System.out.println(registration);
    return service.addRegistration(id, registration);
}
```

```java
To test the application, first add two tournaments using the following POST requests to /tournaments:

{
    "name": "Canadian Open",
    "location": "Toronto"
}

{
    "name": "US Open",
    "location": "New York City"
}

Next, we will add four registrations by sending POST request with an empty body to /registrations four times:
{}

Out of the four registrations, we will associate one with the first tournament and three with the second tournament. This can be achieved by sending the following PUT requests:

http://localhost:8080/tournaments/1/registrations/3

http://localhost:8080/tournaments/2/registrations/1

http://localhost:8080/tournaments/2/registrations/2

http://localhost:8080/tournaments/2/registrations/4

If we delete the tournament with id 2 by sending a DELETE request to /tournaments/2, the tournament is deleted along with its three registrations. The registration table has only one registration left.

```

## **Orphan records**

An orphan record is a record with a foreign key value that points to a primary key value that no longer exists. Orphan records point to a lack of referential integrity which means that the data in the tables is not in a consistent state.

In our example, the registration record has a foreign key value of *tournament_id*. We can remove a registration from a tournament by breaking the association between the two. In such a case, the record in the registration table would become an orphan as it is no longer linked to any entry in the tournament table. The following figure shows an orphan record:

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2016.png)

we will create a method `removeRegistration` in the `Tournament` class which removes a registration from the `List` of `Registrations`.

```java
public void removeRegistration(Registration reg) {
    if (registrations != null)
        registrations.remove(reg);
}
```

 we will create a method `removeRegistration` in the `TournamentService` class which breaks the association between a `Tournament` and a `Registration` object by utilizing the method created above.

```java
public Tournament removeRegistration(int id, Registration registration) {
    Tournament tournament = repo.findById(id).get();
    tournament.removeRegistration(registration);
    return repo.save(tournament);
}
```

Finally, we will create a new PUT mapping of `/tournaments/{id}/remove_registrations/{registration_id}` in the `TournamentController` class. The `removeRegistration` method removes the registration entity having `registraion_id` as its key from the `Tournament` entity specified using `id`.

```java
@PutMapping("/{id}/remove_registrations/{registration_id}")
public Tournament removeRegistration(@PathVariable int id, @PathVariable int registration_id) {
    Registration registration = registrationService.getRegistration(registration_id); 
    return service.removeRegistration(id, registration);
}
```

Cascade type `REMOVE` only cascades the delete operation to child records which are linked to the parent. To show how it works, we will create the same scenario as before (with `2` tournaments and `4` registrations by assigning one registration to the first tournament and three registrations to the second tournament).

we will create the same scenario as before (with `2` tournaments and `4` registrations by assigning one registration to the first tournament and three registrations to the second tournament).

With the above changes in place, run the application again and create two tournaments and four registrations. Then, associate the registrations with the two tournaments as described above.

We will remove one registration from tournament with `id` `2` by sending a PUT request to `/tournaments/2/remove_registrations/4`. Now the tournament has two registrations left. Note, that we did not delete the registration, but only removed it from the tournament. The registration record is not associated with any tournament and is an orphan record.

The current state of the database is reflected from the response to GET requests to `/tournaments` and `/registrations` as shown below:

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2017.png)

Next, delete the tournament by sending a DELETE request to `/tournaments/2`.The delete operation is cascaded to the *registration* table and two records associated with the tournament are deleted. If we perform a GET on `/registrations`, we can see the orphan record with `id` `4` is still in the table.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2018.png)

### **orphanRemoval attribute**

The `@OneToMany` annotation has an `orphanRemoval` attribute, which can be used to delete records that have been orphaned.

```java
@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)    
@JoinColumn(name="tournament_id")
private List<Registration> registrations = new ArrayList<>(); 

```

we will recreate the same scenario with two tournament and four registration entries and establish one-to-many associations as mentioned above.

Remove registration with `id` `4` from tournament `2` using a PUT request to `/tournaments/2/remove_registrations/4`. The `orphanRemoval` attribute triggers a remove operation for the `Registration` object no longer associated with the `Tournament` object thereby leaving the database in a consistent state.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2019.png)

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2020.png)

Deleting registration records associated with the tournament using orphanRemoval=true attribute

# One-to-Many Bidirectional

Let’s add some real life constraints to the model.

- Every `Registration` object must be associated with a `Player` object.
- When a `Registration` object is deleted, the associated `Player` object should not be deleted.

A bidirectional association between `Player` and `Registration` means that we can get all the `Registration` objects if we have a `Player` object and vice versa, we can get a `Player` by using the `Registration`. Compare this to the unidirectional one-to-many relationship, where we could find the `Registration` objects given a `Tournament` but we could not find the `Tournament` from a `Registration` object.

The inverse of one-to-many relationship is many-to-one, where many registrations map to one player.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2021.png)

Many to One relationship: many registrations map to one player

To model a bidirectional one-to-many relationship between a player and his registrations, we will add a `player` field in the `Registration` class

```java
@Entity
public class Registration {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Player player;

	//getter and setter for player
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
    //updated toString method
	@Override
	public String toString() {
		return "Registration [id=" + id + ", player=" + player + "]";
	}
}
```

## **@ManyToOne**

There is a many-to-one relationship between the `Registration` and `Player` classes where many registrations can map to one player. The many side of a many-to-one bidirectional relationship is *always* the owning side of the relationship. In our case, it is `Registration`.

To model this relationship, we will use the `@ManyToOne` annotation with `@JoinColumn` specifying the foreign key column in the *registration* table. The *player* table has an `id` column which will become the foreign key column `player_id` in the *registration* table. This column maps the `Registration` to its `Player`.

```java
@ManyToOne
@JoinColumn(name="player_id", referencedColumnName = "id")
private Player player;
```

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2022.png)

## **Cascade type**

Next, we will choose the cascade type for this relationship. If a `Registration` object is deleted, the associated `Player` should not be deleted. This means that the delete operation should not be cascaded. Since we have fine grain control over the cascade types, we will list all of them except for `REMOVE`.

```java
@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,  CascadeType.REFRESH})
@JoinColumn(name="player_id", referencedColumnName = "id")
private Player player; 
```

Now we will update the `Player` class to show tournament registrations. Since a player can have multiple registrations, we will add a `List` of `Registration`s as a new field to the class.

```java
public class Player {
    //...
	
    private List<Registration> registrations = new ArrayList<>(); 

    //getter and setter methods
    public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
    //updated toString method
    @Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", playerProfile=" + playerProfile + ", registrations=" + registrations + "]";
	}
}
 

```

## **@OneToMany**

The `Player` class has a one-to-many relationship with the `Registration` class as one player can register for many tournaments. This can be modelled by the `@OneToMany` annotation.

Since the *many* side (`Registration`) is the owning side of a bidirectional relationship, we will use the `mappedBy` attribute here (in the `Player` class) to specify that this is the inverse side of the relationship.

```java
@OneToMany(mappedBy="player", cascade= CascadeType.ALL)
private List<Registration> registrations = new ArrayList<>(); 

```

We are using cascade type `ALL` here because we want a player’s registrations to be deleted when the player record is deleted.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2023.png)

**Assigning registration to player**

Next, we will add a method to the `Player` class that sets the bidirectional relationship. In this method, we will add a `Registration` object to the `Player` and also update the `Registration` to reflect that it belongs to this `Player`.

```java
//set up bi-directional relationship with Registration class
public void registerPlayer(Registration reg) {
    //add registration to the list
    registrations.add(reg);
    //set the player field in the registration
    reg.setPlayer(this);
}
```

Next, we need to write a service layer method `assignRegistration` in the `PlayerService` class. It takes a player’s `id` and a `Registration` object. We make use of the `registerPlayer` method written above to assign a `Registration` object to a `Player`.

```java
//assign a registration to a player
public Player assignRegistration(int id, Registration registration) {
    Player player = repo.findById(id).get();
    player.registerPlayer(registration);
    return repo.save(player);
}
```

In the `PlayerController` class, we will add a new PUT mapping to update a `Player`. The `addRegistration` method with `/{id}/registrations/{registration_id}` mapping adds a registration with `registration_id` to a player with `id` as its key. The controller class method invokes the service class `assignRegistration` method.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2024.png)

# Many-to-many Unidirectional

Every tournament has some playing categories like singles and doubles for men and/or ladies.This scenario fits well into the many-to-many relationship where many categories are part of a tournament and many tournaments have the same playing categories.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2025.png)

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2026.png)

In databases,many to many relationship is modelled using a join table which has the primary keys of both tables in the relationship.

We will also model two real life constraints.

- The first one is that one playing category should appear only once in the *category* table (we don’t want multiple entries for the same category).
- The second constraint is that when a tournament entry is deleted, the associated playing categories should not be deleted and vice versa.

```java
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(unique=true)
	private String name;

	public Category() {		
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
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
}
```

Since we do not want the same category name to appear more than once, we will impose the unique key constraint using the `unique` attribute of the `@Column` annotation.

`@Column(unique=true)` ensures that the same category name is not entered more than once.

```java
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
```

```java
@Service
public class CategoryService {

	@Autowired
	CategoryRepository repo;
	
	public List<Category> allCategories() {
		return repo.findAll();	    
	}

	public Category getCategory(int id){
		return repo.findById(id).get();
	}
	
    public Category addCategory(Category category) {
    	category.setId(0);
		return repo.save(category);
	}
    
 	public void deleteCategory(int id) {
		repo.deleteById(id);
	}
}
```

```java
@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService service;
	
	@GetMapping
	public List<Category> allCategories() {
		return service.allCategories();	    
	}

	@GetMapping("/{id}")
	public Category getCategory(@PathVariable int id) {
		return service.getCategory(id);
	}
	
    @PostMapping
	public Category addCategory(@RequestBody Category category) {
    	return service.addCategory(category);
	}
    
    @DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable int id) {
		service.deleteCategory(id);
	}
   
}
```

For a many-to-many relationship, any side can become the parent/ owner. It depends on how the business rules are defined. 

Let’s say, we cannot have a tournament without a category attached to it but we can have a category that is not associated with any tournament. Given this scenario, a category can exist on its own but a tournament needs to have one or more categories associated with it. So, `Category` becomes the owning/parent side and `Tournament` becomes the referenced/ child side.

In a unidirectional many-to-many relationship, we put the relationship on the child side. So, in the `Tournament` class we have to put a link to the `Category` class. Since a tournament can have more than one categories, we will create a `List` of categories as follows:

## **@ManyToMany**

The `@ManyToMany` annotation is used to create a many-to-many relationships between two entities.

```java
@ManyToMany
private List<Category> playingCategories = new ArrayList<>();
```

## **@JoinTable**

The many-to-many relationship is different from the relationships that we have seen so far. Here, the foreign keys are stored in a separate table called a join table instead of being placed inside the parent table or the child table. The join table connects two tables and contains the foreign keys of both tables. The tournament and category tables do not contain the keys of each other; rather the primary keys of both these tables go in the join table.

![Joining tables](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2027.png)

Joining Tables

### **joinColumns and inverseJoinColumns**

`joinColumns` attribute specifies the column(s) in the owner table that becomes a foreign key in the join table. `inverseJoinColumns` attribute specifies the foreign key column(s) from the inverse side.

```java
 @ManyToMany(Cascade = CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH)
 @JoinTable(
			name = "tournament_categories",
			joinColumns= @JoinColumn(name ="tournament_id"),  //FK of the owning side
			inverseJoinColumns=@JoinColumn(name="category_id")  //FK of inverse side
private List<Category> playingCategories = new ArrayList<>();
```

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2028.png)

## **Cascade type**

We will not use cascade type `REMOVE` as we do not want to delete tournaments when we delete a category. We will also not use cascade type `PERSIST`, because that will cause an error if we try to add a tournament with nested category values.

## **Setting up the relationship**

For a unidirectional relationship, the `Category` class does not need any information about the tournaments. Here, we are setting the category in the tournament. When a tournament is saved, it creates a join table entry with the appropriate keys.

The `addCategory()` method in the `Tournament` class assigns a category to a tournament.

```java
//set up many to many relationship
public void addCategory(Category category) {
    playingCategories.add(category);
}
```

The `addCategory` method in the `TournamentService` class makes use of the method we wrote above. It fetches the tournament from the database, assigns a category to it and saves the updated record in the database.

```java
public Tournament addCategory(int id, Category category) {
    Tournament tournament = repo.findById(id).get();
    tournament.addCategory(category);
    return repo.save(tournament);
}
```

In the `TournamentController` we will add a PUT mapping `/{id}/categories/{category_id}` to assign a `Category` with `category_id` to a `Tournament` with `id` as key.

```java
public class TournamentController {

	//...	
	@Autowired
	CategoryService categoryService;
	
	//...    
    @PutMapping("/{id}/categories/{category_id}")
    public Tournament addCategory(@PathVariable int id, @PathVariable int category_id) {
    	Category category = categoryService.getCategory(category_id); 
    	return service.addCategory(id, category);
    }   
}
```

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2029.png)

# **Many-to-Many Bidirectional Relationship**

In a bidirectional relationship, each side has a reference to the other. In our example, the `Category` class did not have any reference to the `Tournament` class. Now we will add a reference to the `Tournament` class so that the relationship can be navigated from both sides. This will have no effect on the underlying database structure. The join table *tournament_catogories* already has the foreign keys of both the tournament and category tables, and it is possible to write SQL queries to get tournaments associated with a category.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2030.png)

For a many-to-many relationship, we can choose any side to be the owner. The relationship is configured in the owner side using the `@JoinTable` annotation. On the inverse side we use the `mappedBy` attribute to specify the name of the field that maps the relationship in the owning side. From the database design point of view, there is no owner of a many-to-many relationship. It would not make any difference to the table structure if we swap the `@JoinTable` and `mappedBy`.

```java
public class Category {

    //...
	@ManyToMany(mappedBy= "playingCategories",
			cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch=FetchType.LAZY)
	private List<Tournament> tournaments = new ArrayList<>();

    //...	
	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", tournaments=" + tournaments + "]";
	}
}
```

## **mappedBy property**

On the `tournaments` field created above, use the `@ManyToMany` annotation with the `mappedBy` property. This shows the value that is used to map the relationship in the `Tournament` class.

![image.png](Database%20Relationships%201ba76e26cece80aeb4f6d391d70af2b6/image%2031.png)

We will also use the cascade property to cascade all operations except `REMOVE` because we do not want to delete all associated tournaments, if a category gets deleted.

## **Setting up bidirectional relationship**

It is the responsibility of the application to manage a bidirectional relationship. When we add a category to a tournament, we must also add the tournament to that category to preserve the relationship in both directions. Failure to do so may result in unexpected JPA behavior.

We will update the `addCategory()` method in the `Tournament` class to set up the bidirectional relationship by adding the tournament to the category.

```java
//set up many to many relationship
public void addCategory(Category category) {
    playingCategories.add(category);
    //set up bidirectional relationship
    category.getTournaments().add(this);
}
//remove category
public void removeCategory(Category category) {
if (playingCategories != null)
playingCategories.remove(category);
//update bidirectional relationship
category.getTournaments().remove(this);
}
```

/

In the `TournamentService` class, the `removeCategory` method removes the bidirectional association by calling the method created above.

```java
public Tournament removeCategory(int id, Category category) {
    Tournament tournament = repo.findById(id).get();
    tournament.removeCategory(category);
    return repo.save(tournament);
}
```

In the `TournamentController` class, we can add a PUT mapping to `/{id}/remove_categories/{category_id}` which removes the category with ID `category_id` from a tournament with ID `id`

```java
@PutMapping("/{id}/remove_categories/{category_id}")
public Tournament removeCategory(@PathVariable int id, @PathVariable int category_id) {
    Category category = categoryService.getCategory(category_id); 
    return service.removeCategory(id, category);
}
```

Lastly, we need to break the association between the `Tournament` and the `Category` before deleting the `Category` entity. We can do this by creating a filed `tournamentRepo` to `@Autowired` the associated `Tournament` entity in the `CategoryService`.

```java
@Transactional
public void deleteCategory(int id) {
    Category category = repo.findById(id).get();
    if (category != null) {
        // Break the association
        List<Tournament> tournaments = category.getTournaments();
        for (Tournament tournament : tournaments) {
            tournament.getPlayingCategories().remove(category);
            tournamentRepo.save(tournament);
        }
        repo.deleteById(id);
    }
}
```

These changes to the `CategoryService` class should ensure that we can delete a `Category` without encountering foreign key constraint violations.

However, there is a simpler way to handle the deletion of a `Category` in a many-to-many relationship by utilizing the `@PreRemove` annotation in JPA. This approach allows us to automatically break the associations before the entity is deleted.

```java
@PreRemove
private void removeCategoriesFromTournaments() {
    for (Tournament tournament : tournaments) {
        tournament.getPlayingCategories().remove(this);
    }
}
//Add a @PreRemove method in the Category class

```

This approach simplifies the code and makes use of JPA's lifecycle callbacks to handle the deletion process more elegantly. Now we no longer need to manually handle the breaking of associations in the `CategoryService` class.

## **@JsonIgnoreProperties**

JSON gets into infinite recursion when trying to de-serialize bidirectional relationships. We have seen two ways to solve this issue in the One-to-One Bidirectional Relationship lesson. Here, we will see yet another approach to avoid infinite recursion. We can use the property that we want to ignore with the `@JsonIgnoreProperties`. This annotation can be used at field level in both the `Tournament` and `Category` class.

```java
@JsonIgnoreProperties("tournaments")
private List<Category> playingCategories = new ArrayList<>();
```

> **Note:** In a many-to-many relationship, there is no owner when it comes to the table structure. This is different from a one-to-many relationship where the *many* side is always the owning side containing the key of the *one* side.
>