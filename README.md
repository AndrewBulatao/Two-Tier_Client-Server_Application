# Enterprise Assignment
**Enterprise Computing - Project Three: A Two-Tier Client-Server Application Using MySQL and JDBC**  

## Description  
A Java-based GUI front-end application that connects to a MySQL server via JDBC. The application allows clients with various permissions to execute SQL commands against different databases.  

## Clone the Repository  
To clone this repository, run the following command:  

```sh
git clone git@github.com:AndrewBulatao/Two-Tier_Client-Server_Application.git
```

## Prerequisites
Ensure you have the following installed before running the application:
* Java (JDK 17 or later)
* MySQL Server
* MySQL Workbench or another SQL client
* JDBC Driver

## Database Setup
Using your SQL Client, run the SQL scripts found in the **sqlScripts** folder. This will create the users and their permissions, and the data 

## Running the application
To run the application, you will need to modify the classpath to point to the provided .jar file. You can use the following commands to do so:

For Windows:
```
java -cp "path\to\your\file.jar" main
```
For  macOS/Linux:
```
java -cp "/path/to/your/file.jar" main
```


