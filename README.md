# Microservices Trainstations Place [![Build Status](https://travis-ci.org/knuthp/ns-trainstations-place.png?branch=master)](https://travis-ci.org/knuthp/ns-trainstations-place)
This is part of the microservices architecture experiment.

This microservice is an adapter for places (Station location). It has a list of known stations and provides a REST API to get the list of stations, add new stations and to get data about a specific station.


# Technology
1. Language: Java 8 Spart (Sinatra for Java)
2. Build: Maven
3. Deployment: Heroku
4. Tools:
  1. Jackson for JSON transformations
  2. Apache HttpClient as REST client
  3. Dozer for class mapping.
  4. Postgres database to store list of places
  5. Freemarker for HTML templating
5. Scaling:
  1. Adding dynos (state is shared between instances in Postgres database)

# Resources
1. Deployed to Heroku [ns-trainstations-place](https://ns-trainstations-place.herokuapp.com/)

# Notes
## Local deployment
###Environment:

```
DATABASE_URL="postgresql://postgres:Postgre1234@localhost:5432/ns"
```

* Can be run locally using foreman and a .env file with a local Postgres database. 
* Can be started from Eclipse and debugged from Eclipse.
