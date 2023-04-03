* Change use-case.  
  * Food = Ticket
  * Restaurant = Airline

* AVRO backward compatibility

* Static code analysis
  * Security scanner
  * FindBugs
  * Checkstyle

* Code style 

* Externalised Configs: Move Spring configs to config server

* split application modules in to 
  * spec (containing openapi.yaml)
  * rest-controller (containing spring rest controller)

* Where to put @Transactional operations?
  * SQL transaction should be rolled back if Kafka publish fails

* Dockerfile for the containers/services

* Gradle: 
  * hide redundant tasks
  * clean up lint output
