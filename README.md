### Problem

Implementing the requirements exposed in 'Silver Bars-v3.1.pdf' document


The solution is packaged as a standalone jar to be included potentially as part of a micro-service...

Main services are implemented as part of OrderService

Running ```mvn clean install``` will run the unit tests and package the jar

### Design considerations

There are few ways of implementing this problem. I would go with the simplest approach and one I think will suit this problem. Although it does start as a simple problem from my experience such problems 
get more complex and probably I would use a reactive approach with RxJava. 

These are the main concepts I will relay on

* 2- layered architecture: service api for API interaction (potentially exposed as rest) and Order Repository will abstract the Order Storage actions
* By abstracting the repository we can change the underling storage in the case the requirements change and will allow to stub it during the test
* Service layer will help to validate the requests and handle exceptions from repository (here we would be able to inject security checks input validations...)
* The code will self-documented rather adding javadocs. Each exposed API method will have well defined parameter list that will avoid miss-interpretation. Also each API call should return a result
containing the status of the result 

### Assumptions

Because this is an example implementation I made some assumptions and this could be changed when a real solution may be required

* not including any 3'rd party libraries
* coded only few edge cases (to save some time)
* order id can be derived from order fields. It may be the case that an external system can be required. In this case the code will need a little refactor 
* No Logging framework is configured, instead standard console is used. 
* Because Order repository is a boundary service that bound I decided to add checked exceptions 