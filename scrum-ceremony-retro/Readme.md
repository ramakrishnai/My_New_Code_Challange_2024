Technology Stack:
Java 17
spring 3.2.2
apache-maven-3.8.8
Spring Boot
TDD using Junit
@Slf4J used for logging


Please find the below steps to follow to run application:
--------------------------------------------------------
1. unzip source code and change director to the application root folder to run  command ->  mvn install


2. There are few Unit Test cases to cover below REST API are present in test folder. They can be executed individually
   or "mvn install" command from command line


3. To run application: created a docker "scrum-retro-docker" and image can be created using below command

   - docker build -f scrum-retro-docker -t scrum-retrospection-app .
   - docker run -p 9091:9091 scrum-retrospection-app

   - Please Postman api testing tool to run below endpoints.


Kindly find the following endpoints and json data as per the coding challenge document:
--------------------------------------------------------------------------------------

POST Request to save retrospection: http://localhost:9091/v1/api/retros

	{
     "name": "Retrospective 1",
    "summary": "Post release retrospective",
    "date": "2024-02-23",
    "participants": [
        {
            "name": "Viktor"
        },
        {
            "name": "Gareth"
        },
        {
            "name": "Mike"
        }
    ]
}

PUT request to update feedback below using - endpoint: http://localhost:9091/v1/api/retros/feedbacks/1
{
    "name": "Gareth",
    "body": "Sprint objective met",
    "feedbackType": "Positive"
}

GET Request to find all using pagination endpoint: http://localhost:9091/v1/api/retros?currentPage=0&pageSize=3

GET Request to find retrospection by given date endpoint: http://localhost:9091/v1/api/retros/date/2024-02-23

Please use H2 in memory database url to check data: http://localhost:9091/h2-console

		- Driver Class -> org.h2.Driver
		- JDBC URL     -> jdbc:h2:mem:sampledb
		- user name	   -> sa
		- click connect button to view tables. 