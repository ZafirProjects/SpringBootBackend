# Spring Boot Backend

This repository was made to highlight my ability to make APIs and tests in Java Spring. This backend was written using the Java Spring framework. It uses an embedded h2 database to store data.

## Prerequisites
1. Ensure java is installed on your machine (preferably Java 17 [Bellsoft Liberica](https://bell-sw.com/pages/downloads/#jdk-17-lts)) \
2. Install maven on to your computer using the following [guide](https://www.baeldung.com/install-maven-on-windows-linux-mac). Or if you're on MacOS with [homebrew](https://brew.sh) installed you can just do:
```
brew install maven
```
3. Install the repository
```
git clone https://github.com/ZafirProjects/SpringBootBackend.git
```
## Running the project
#### Method 1:
Step 1: CD into the 'streaming' folder in the repo you have just cloned\
Step 2: install dependencies by running:\
```
mvn clean install
```
Step 3: Run the tests by running:
```
mvn clean test
```
Step 4: Run the server by running:
```
mvn spring-boot:run
```
Step 5: Make requests to the server using Postman or Curl (or whatever you want)\

#### Method 2:
Open the project in IntelliJ and run the StreamingApplication class to get the server running.\
Run the tests in the tests folder.

## Example Http Requests
```
POST localhost:8080/users
{
    "username" : "User1" ,
    "password : "Password1" ,
    "email" : "email1@address.com" ,
    "doB" : "2001-11-20" ,
    "creditCardNumber" : "4242424242424242"
}
```
```
POST localhost:8080/users
{
    "username": "User2",
    "password": "Password2",
    "email": "email2@address.com",
    "doB": "2002-11-20",
    "creditCardNumber": ""
}
```
```
GET localhost:8080/users?CreditCard=Yes
```
```
GET localhost:8080/users?CreditCard=No
```
```
POST localhost:8080/payments\cf19 \strokec20 \
{
    "creditCardNumber" : "4242424242424242",
    "amount" : 100
}
```
