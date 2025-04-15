# Configuring a Web Application

# Setting Up a Web Application

## **Creating a simple Maven project**

We will create a web application using Maven as it makes development easier. Maven is a Java build tool which manages all the dependencies of an application. Create a simple Maven project using the IDE of your choice. Maven provides a set of sample projects which are called **archetypes** with predefined configuration.

We will skip the archetype selection for this project as we want to configure the application ourselves. The name of a Maven project has two parts, group Id and artifact Id. We have supplied the following values:

Group id: `io.datajek.springmvc`

Artifact id: `tennis-player-web`

Packaging: `War` (web archive)

The packaging type is set to WAR because we are creating a web project. A WAR file contains servlet, JSP, XML, CSS, HTML and JS files that can be deployed on a servlet container while a JAR file contains Java classes and associated metadata as a single file.

Once the project is created, we can see a hierarchy of folders.

- The source code is placed in src/main/java.
- Any resources like a property file or XML file goes in src/main/resources.
- All test code is placed in src/test/java.
- Any resources for the test code reside in src/test/resources.

## **Java EE Web API dependency**

The `pom.xml` shows the basic information about the group Id and artifact Id that we provided. To run a web application, we need a number of dependencies or jars. Maven downloads those jars, saving us the time to manually download them. We will add the Java EE Web API dependency to the `pom.xml` file as follows:

```xml
<dependencies>
    <dependency>
        <groupId>javax</groupId>
        <artifactId>javaee-web-api</artifactId>
		<version>6.0</version>
		<scope>provided</scope>
    </dependency>
</dependencies>
```

We will run a Java EE 6 application where servlets extend the `HttpServlet` class. We need to provide the jar to the compiler is defined. The `javaee-web-api` dependency added above downloads the API containing the `HttpServlet` among other jars.

## **Plugins for Maven compiler and Tomcat**

To compile the application, we will add the `maven-complier-plugin`. This will take care of compiling the Java classes and building the jars and wars using version 1.8. To be able to run the web application in Tomcat, we will add the `tomcat7-maven-plugin`. This plugin downloads Tomcat and runs the web application in it. The plugins are added to the `pom.xml` file as shown below:

```xml
<build>
  <pluginManagement>
    <plugins>
      <plugin>  
        <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-compiler-plugin</artifactId>
	    <version>3.2</version>
	    <configuration>
		    <verbose>true</verbose>
            <source>1.8</source>
			<target>1.8</target>
			<showWarnings>true</showWarnings>
		</configuration>
	  </plugin>
      <plugin>
		<groupId>org.apache.tomcat.maven</groupId>
		<artifactId>tomcat7-maven-plugin</artifactId>
		<version>2.2</version>
		<configuration>
			<path>/</path>
			<contextReloadable>true</contextReloadable>
		</configuration>
	  </plugin>
    </plugins>
  </pluginManagement>
</build>
```

## **web.xml file**

The `web.xml` file, also known as the deployment descriptor, is a configuration file used in Java web applications deployed on a servlet container. It is an XML file that contains configuration information about the web application, including servlet mappings, filter definitions, listener declarations, and initialization parameters. Some common elements found in a `web.xml` file are servlet declarations, servlet mappings, initialization parameters, and error page definitions. The `web.xml` file provides a way to configure the behavior of a Java web application without modifying the application code directly. It allows developers to specify the structure and behavior of the application, including how HTTP requests are processed and responded to.

`web.xml` is the starting point for any web application. This is where Tomcat, or any other Java EE implementation server, starts looking for servlets. We will create this file in `src/main/webapp/WEB-INF/` folder. `web.xml` file contains header information for the application. After the metadata, we will define the landing page for our application using the `<welcome-file>` tag.

## **<welcome-file>**

A `welcome-file` is a configuration element in the `web.xml` file of a Java web application that specifies the default file to be served when a client requests access to the root directory of the web application without specifying a particular resource or URL path. For example, if a user accesses `http://example.com/` without specifying any additional path, the servlet container looks for a file specified as the `welcome-file` in the `web.xml` of the web application and serves that file as the default resource.

We will call our welcome file, `player.do`. The following line defines a redirection for `localhost:8080` as `localhost:8080/player.do`:

```xml
<welcome-file-list>
    <welcome-file>player.do</welcome-file>
</welcome-file-list>
```

The servlet container will look for this file in the root directory of the web application and serve it.

The figure below shows how an HTTP request from the client is handled by `web.xml`.

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image.png)

# **Setting Up a Spring Web Application**

Spring Boot favors convention over configuration and provides default configurations for setting up a servlet container and configuring DispatcherServlet, which is the central servlet in Spring MVC applications.

We will add the `Spring Web dependency` and the `Spring Boot Dev Tools` dependency. Click "Generate" to download the project.

Once the project has been imported in the IDE, we can see a hierarchy of folders as follows:

- `src/main/java`: This is where the source code is placed.
- `src/main/resources`: This folder is used for resources like property or configuration files.
- `src/test/java`: All test code is placed in this folder.
- `src/main/webapp`: We will create a `webapp` folder for the JSP files, web configuration files and assets like CSS or images etc.

## **Required dependencies**

The `spring-boot-starter-web` dependency simplifies web development using Spring MVC and an embedded Tomcat server. It includes all the necessary common dependencies, such as the Spring framework, logging, validation, JSON support, and more, with compatible versions pre-configured. This eliminates the need to manage a multitude of dependencies and ensures smoother development compared to traditional Spring MVC applications.

## **Jasper dependency**

To enable JSP compilation and rendering in our application, we must include Jasper which is the JSP Engine for Tomcat. It is responsible for compiling JSP files into Java servlets, which are then executed to generate dynamic web content. It parses JSP files, translates them into Java code, compiles the Java code into servlets, and finally, executes the servlets to produce HTML content dynamically. This makes Jasper a critical component for enabling the use of JSP technology in web applications deployed on Tomcat servers. We will add the `tomcat-embed-jasper` dependency, which brings JSP support in Spring Boot as follows.

<aside>
💡

<dependency>
<groupId>org.apache.tomcat.embed</groupId>
<artifactId>tomcat-embed-jasper</artifactId>
</dependency>

</aside>

## **JSTL dependency**

We must include the JSTL (JavaServer Pages Standard Tag Library) library to ensure our JSP pages have the necessary support for JSTL tags. JSTL is a collection of custom tags and functions that simplify the development of JSP pages by providing commonly used functionalities, such as looping, conditional statements, formatting, and internationalization. By utilizing JSTL, developers can enhance the readability and maintainability of their JSP code while reducing the need for embedded Java code within JSP files.

```xml
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
</dependency>
```

# **Creating a Servlet**

A servlet is a Java class that can take in a request and return a response. The input to the servlet is a request, and the output is a response. In a web application, we are talking about HTTP requests and HTTP responses. When something is typed in the address bar of a browser, it sends a request to the web server. The web server returns a response based on the request, and the browser displays the page.

So far, we have created a `web.xml` file that defines a welcome file, `player.do`, for our web application. When `http://localhost:8080` is typed in the browser, the browser creates a GET request and sends it to `http://localhost:8080/player.do`. We want this request to be mapped to a servlet which sends some response back to the browser.

## **HttpServlet class**

We will create a servlet class in src/main/java and call it `PlayerServlet`. A servlet class extends the `HttpServlet` class defined in `javax.servlet.http.HttpServlet`.

```java
import javax.servlet.http.HttpServlet

public class PlayerServlet extends HttpServlet {

}
```

## **Mapping URL to servlet**

The next step is to define a URL that will be used to access the servlet. We will assign `/player.do` to `PlayerServlet`. This can be done using the `@WebServlet` annotation. The `urlPatterns` property can be used to assign `/player.do` to our servlet as follows:

```java
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/player.do")
public class PlayerServlet extends HttpServlet {
}
```

Now, the player servlet can be called when `http://localhost:8080/player.do` is typed.

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image%201.png)

> Note: Spring Boot offers another annotation, @ServletComponentScan to register classes annotated with @WebServlet, @WebFilter and @WebListener. In case the embedded server fails to scan the servlet defined using @WebServlet, then @ServletComponentScan can be used on the application class.
> 

## **Handling a GET request**

Our servlet class will handle HTTP requests and respond with an HTTP response. The `HttpServlet` class has several methods to handle requests. We will write the implementation of the `doGet()` method. This method has two parameters, `HttpServletRequest` and `HttpServletResponse`.

Press+to interact

```java
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/player.do")
public class PlayerServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                                                               throws IOException {
  PrintWriter out = response.getWriter();
  out.println("<html>");
  out.println("<head>" +
        "<title>Player DB</title>" +
        "</head>");
  out.println("<body>" +
        "<H2>Welcome to the Tennis Players database!</H2>" +
        "</body>");
  out.println("</html>");
}
}
```

This method defines the response to a GET request for `http://localhost:8080`. The input to this method is `request`, and the output is `response`. We will read the input provided by the client from the `request` and put information to be shown to the client in the `response`.

The `println()` method of the `PrintWriter` object is used to define HTML, which sets the title of the page and displays some text in the page body.

The image below shows what we have achieved so far. The `PlayerServlet` gets an HTTP request and sends an HTTP response back. The request/response cycle is shown below:

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image%202.png)

In your IDE, to run the application as Maven Build, specify `tomcat7:run` as goal. After Tomcat starts, you can see running war on `http://localhost:8080/`.

Type `http://localhost:8080/player.do` in the browser. On this link, the `PlayerServlet` opens up and the contents returned by the `doGet()` method are displayed.

Be sure to kill the server before attempting to re-run the application to avoid the Address already in use exception.

# **Using a JSP File**

Let’s learn how to generate dynamic content using a JSP file instead of writing static HTML content in a servlet.

Writing HTML in Java is not good practice. We have to use the `println()` method for every line of HTML code. A better approach is to use **Java Server Pages (JSP)** to write HTML content to the browser. A JSP is also compiled as a servlet so there is no performance gain in using JSPs. Rather, it is ease to use and able to send dynamic content to the browser.

## **Creating a JSP file**

Typically, JSPs reside in the views folder in src/main/webapp/WEB-INF. Right now, we only have webapp in the project hierarchy. Inside the webapp folder, we will create a folder WEB-INF which will have a subfolder, views.

In the views folder, we will create a JSP file and call it `welcome.jsp`. By default, this generates an HTML 5 JSP. This file provides the basic HTML structure of a webpage.

```java
<html>
  <head>
  <meta charset="ISO-8859-1">
  <title>Tennis Player DB</title>
  </head>
  <body>
  <h2>Welcome to the tennis player database!</h2>
  </body>
</html>
```

## **Redirecting GET request to JSP file**

To call this JSP file in the `doGet()` method, we will use the `getRequestDispatcher()` method, provide the path of the file, and then use the `forward()` method to forward the `request` and `response` to the JSP as shown in the snippet below.

The path of the JSP file starts from the WEB-INF folder.

```java
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                    throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
}
```

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image%203.png)

# **Passing Parameters with GET request**

## **Passing a parameter in the URL**

Suppose we want to pass the name of a tennis player as a parameter. The way to pass a parameter is to put a question mark at the end of the URL and provide the name of the parameter. The `?` separates the URL from the parameter:

```

http://localhost:8080?name=sampras
```

o pass multiple parameters, the `&` symbol is used as follows:

```
http://localhost:8080/?fname=novak&lname=djokovic 
```

## **Receiving parameter in servlet**

To receive the parameter passed from the browser in the `doGet()` method of the `PlayerServlet` class, we will use the `getParameter()` method and provide the name of the parameter that we wish to receive.

```

String playerName = request.getParameter("name");
```

## **Passing the attribute to JSP**

Now that our servlet has received the parameter, the next step is to forward it to the JSP. A parameter is what is passed between the browser and the web server. An attribute is what gets passed from the web server to the JSP.

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image%204.png)

We will use the `setAttribute()` method to pass the received parameter as an attribute to the JSP. The method has two arguments: the name of the attribute and its value.

```java
request.setAttribute("name", playerName);
request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
```

## **Displaying the attribute in JSP[#](https://www.educative.io/courses/guide-to-spring-and-spring-boot/passing-parameters-with-get-request#Displaying-the-attribute-in-JSP)**

To access the attribute in the JSP, we will use its name (`name`). The value of the attribute can be displayed in expression language as follows:

```java
<body>
<h2>Welcome to the tennis player database!</h2>
<h3>Player name: <i>${name}</i> </h3>

</body>
```

`${attribute_name}` is expression language syntax in which the `attribute_name` is replaced by the value of the attribute. Expression language is used to dynamically pick up content from the request.

# **Scriptlets and Scriptlet Expressions**

A JSP is compiled into a servlet, which means that anything that can be done in a servlet can also be done in a JSP. In `PlayerServlet`, we wrote Java code, while in `welcome.jsp`, we wrote HTML. Just as HTML can be written in the `PlayerServlet` class, Java code can be written in `welcome.jsp`. Java code in a JSP is called a **scriptlet**.

A disclaimer before we show how to write a scriptlet: this is not recommended practice and should be avoided.

## **Scriptlet**

In a JSP, anything written between the `<% %>` tags is a scriptlet. We can print something on the console from `welcome.jsp` as follows:

```java
<body>
  <%
  System.out.println("Hello from JSP");
  %>
</body>
```

When the JSP is executed, this message will be displayed on the console.

Any Java code can be written in a scriptlet. We can output the name of the parameter using the `request.getParameter()` method, use loops, or get the current system date and time. Let’s say we want to print the date in the JSP. Import `java.time.LocalDate` in the JSP using `@page` as shown below:

```java
<%@page import="java.time.LocalDate"%>

```

Now we can access the `now()` method to get the date as follows:

```
<%
LocalDate currentDate = LocalDate.now();
%>
```

## **Scriptlet expression**

To show the value of a Java variable in HTML, we will use a scriptlet expression. A scriptlet expression uses the `<%= variable_name %>` syntax. We can show the `currentDate` variable in HTML as follows:

```java
<p>Current Date: <%= currentDate %> </p>
<p>Today is <%= currentDate.getDayOfWeek() %> </p>
```

# **POST request**

In a GET request, parameters are passed as a query string in the URL. The routers and ISPs can see the parameters and read values as they are passed in a GET request. POST request is another type of HTTP request which is more secure. In this lesson, we will pass parameters through a form using a POST request. The POST request is not completely secure, but it is better than a GET request. Name of a player can be passed as part of the URL as well as part of the form. Now we will pass it through a form.

# **Creating a form**

The first step is to create a form which will have fields for input parameters. We will create the form in `welcome.jsp`. Forms can be created using the `<form>` tag. We need to specify who will handle the information in the form. Since we want the `PlayerServlet` to handle this request, we will specify `player.do` as `action`. In our application, any request to `player.do` is handled by the `PlayerServlet`:

```html

<form action="/player.do">
  <b>Player name:</b> 
  <input type="text" name="name"/>            
  <input type="submit" value="Enter"/>
</form>
```

# **Creating a POST request**

If you type a name in the text box and press Enter, the parameter appears in the URL. This shows that even though we are using a form, the request is still a GET request. To change it to a POST request, we will have to specify the `method` in the form element.

```html
<form action="/player.do" method="POST">

```

```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
                                                throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/views/info.jsp").forward(request, response);
}
```

The `doPost()` method redirects to `info.jsp`, which does not exist currently. We will create a simple JSP in `WEB-INF/views` as follows:

```

<body>
<h3>Searching for player... </h3>
</body>

```

![image.png](Configuring%20a%20Web%20Application%201cf76e26cece8014a3bade090d30ad49/image%205.png)

# **Forwarding the parameter as attribute**

If we want to forward the parameter `name` from the form to `info.jsp`, we can use `getParameter()` and `setAttribute()` methods on the request as follows:

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) 
                                                throws IOException, ServletException {
    String playerName = request.getParameter("name");
    request.setAttribute("name", playerName);
    request.getRequestDispatcher("/WEB-INF/views/info.jsp").forward(request, response);
}

```

Here we are getting the parameter sent from the form in `welcome.jsp` and storing it in a variable `playerName`. Then we are setting a request attribute by the name `name` and providing its value as `playerName`. Lastly, we are forwarding the request to `info.jsp`. Now the attribute `name` is available to the JSP, we will use EL to display the attribute in `info.jsp` as follows:

```java
<body>
    <h3>Searching for ${name}... </h3>
</body>
```

**## Calling a business service method**

```java
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String playerName = request.getParameter("name");

		Player player = service.getPlayerByName(playerName);

		request.setAttribute("name", playerName);
		request.setAttribute("country", player.getNationality());
		request.setAttribute("dob", player.getBirthDate());
		request.setAttribute("titles", player.getTitles());
		request.getRequestDispatcher("/WEB-INF/views/info.jsp").forward(request, response);
	}
```

#—READ ONLY and Get some idea ## errors are present