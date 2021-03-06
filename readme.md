# Demo Villes

## Objective

This project has for objective to show some technologies 
to help te develop a client server.

## Description of the project

This is a simple project demo to display cites.
The main fonctionalities are : 
* Display a city
* Display all cities 
* Display all cities which name start with some letters.

## Technologies used

* Gradle to build the project
* Kotlin as programation language
* Groovy as programation language for testing
* Spock as a testing framework
* Spring :
    * Spring-boot to launch and develop more easily the project
    * Spring-mvc to build :
        * A Rest API
        * Client page interaction
    * Spring-boot-devtools to reload easily the project
* Thymeleaf, an html template pattern
* Html, CSS3, JavaScripts

## Prerequisite

The project need you had the Jdk8 installed. 
To check it, you can launch on a terminal :

```javac -version```

The project can also may work with a jdk6, but it has not be tested.
To try with a jdk6 you have to change in the file 'server/gradle.properties
the property `java_version` to '1.6'.

```java_version=1.6```

## How to use it.

To build the project with all test : 

```./gradlew :server:build```

Or you can go to the folder `server/` and launch :

```./gradlew build```

To run the project : 

```./gradlew :server:bootRun```

Then go to the url `http://localhost:8080` to see the home page
At this time, the web pages are very light.

In the login page, type `user` as username and `password` for the password

You can test the rest api with this urls : 
* `http://localhost:8080/cities/list` to list all cities
* `http://localhost:8080/cities/filter?startWith=[a city name]` to get all cities starting by…
* `http://localhost:8080/cities/get?name=[a city name]` to get a specify city


There is also independent client web pages using directly the Rest API.
You can launche the page `client/tp-villes.html`
* 