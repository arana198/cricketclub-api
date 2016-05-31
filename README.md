cricketclub-api
================

cricketclub-api (CCL) is a service that provides REST api for cricket club management related data. The majority of the service is a RESTFUL,
JSON api; however, there are a number of tools to aid with the exploration of the data.

Dependencies
------------

This section lists all of the external dependencies of cricketclub-api.
The address, port and other information required to connect to each dependency can be found in the environment-specific external configuration
(i.e. application.properties in the application-configuration repository).

### Data Store
* MySql Database

Endpoints
--------

See ``{address}:{port}/swagger-ui.html`` for a list of endpoints exposed by this service.
The UI endpoint lives under the root directory and can be accessed locally in browser via localhost:8002/

Profiles
--------

The following profiles can be used:

* api: the profile for the swagger configuration

Installation
------------

The project requires Java 8

To create the jars for the web service run ``gradlew build``.

Running
-------

The service can be started using spring-boot gradle tasks i.e ``gradlew bootRun``,
manually running the .jar file ``java -jar cricketclub-api.jar``
or by using the deployment process scripts.

Contributing
------------

**Owners**

arana198
