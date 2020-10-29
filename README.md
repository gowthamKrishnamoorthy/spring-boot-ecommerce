# Simple eCommerce Application with SpringBoot and React

This example app shows a simple eCommerce Application with Spring Boot API and CRUD (create, read, update, and delete) its data with a React app.

**Tools:** For this project it was necessary to use tools such as those shown below.

- **Language:** Java
- **Framework backend:** SpringBoot with Maven


**Objective:** Make an application for a product catalog with the backend applying micro service patterns.


## Getting Started

To install this application, run the following commands:

```bash
git clone https://github.com/gowthamKrishnamoorthy/spring-boot-ecommerce
cd spring-boot-ecommerce
```

This will get a copy of the project installed locally. To configure all of its dependencies and start each app, follow the instructions below.

### Configure Database

Once MySQL is installed you must configure a username and password. By default the user and password should be `root` . If not, you must configure in the file `application.configure` located in the path `src/main/resources/`.

In the file `application.configure` you must edit the parameters `spring.datasource.username` and `spring.datasource.password` with the values you defined.

### Create Database & Tables


```SQL
CREATE DATABASE ecommerce;
```
