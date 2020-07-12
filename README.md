# StockAPI

StockAPI provides the following capabilities
1. create a Tick.
2. Get Statistics of all Ticks across all instruments in last 60 seconds.
3. Get Statistics of a specific instruments in last 60 seconds.

## End-points

- /tick 

````
sample request body:
{
    "instrument":"IBM.N",
    "price":143.82,
    "timestamp":1478192204000
}
````

- /statistics
````
sample response body
{    
    "count":"10",
    "avg":"100.00",
    "max":"200.00",
    "min":"50.00"
}
````

- /statistics/{instrument_identifier}
````
sample response body
{    
    "count":"10",
    "avg":"100.00",
    "max":"200.00",
    "min":"50.00"
}
````

### How to start the App

#### pre-requisite:
- set the MAVEN_HOME env variable
- set the JAVA_HOME env variable


#### Invoke 

Run using Spring Boot Maven plugin. 

This will download the mvn dependencies and start the SpringBoot application at port `8084`

URL to access the application
`https://localhost:8084/tick`
`https://localhost:8084/statistics`
`https://localhost:8084/statistics/{instrument_identifier}`


### Assumptions

1. All the fields in the POST API (/tick) in the Tick JSON is mandetory and can not be empty. If any field is missing/empty then 400 BAD request will be thrown by the API.
2. All the Double Data types in the Reponse of GET APIs will be approximated to 2 decimal places.


### Further improvements 

> Logging can be added.

> Project can be dockerised. 

> Write integration testing and also shell scripts to automate Intergartion Testing.


### Did you enjoy the assignment

> Yes

The Assignemnt helped me to dive deeper in the concepts of parallel programming and helped me to grow my knowledge in concurrent programming.
