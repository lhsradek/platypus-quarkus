# platypus-quarkus Project


<p>
<img src="/png/Platypus_is_surprised_by_the_speed_of_quarkus_and_GraalVM_and_jumps_for_joy.png" width="400px" height="400px"/>
</p>

```
mvn io.quarkus:quarkus-maven-plugin:2.12.0.Final:create -DprojectGroupId=local.intranet.quarkus -DprojectArtifactId=platypus-quarkus -DclassName="local.intranet.quarkus.HelloResource" -Dpath="/hello" -Dextensions=agroal,resteasy,resteasy-qute,resteasy-links,resteasy-jackson,resteasy-jaxb,
resteasy-jsonb,smallrye-jwt,smallrye-health,smallrye-openapi,swagger-ui,jdbc-mysql,jdbc-h2,hibernate-orm,hibernate-orm-panache,hibernate-validator,
micrometer-registry-prometheus,flyway,spring-data-jpa,vertx,vertx-http,redis-client,hibernate-envers,logging-logback,logging-gelf,opentelemetry,scheduler
```

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/platypus-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and JPA
- Eclipse Vert.x ([guide](https://quarkus.io/guides/vertx)): Write reactive applications with the Vert.x API
- Hibernate Envers ([guide](https://quarkus.io/guides/hibernate-orm#envers)): Enable Hibernate Envers capabilities in your JPA applications
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, JPA)
- Scheduler - tasks ([guide](https://quarkus.io/guides/scheduler)): Schedule jobs and tasks
- SmallRye JWT ([guide](https://quarkus.io/guides/security-jwt)): Secure your applications with JSON Web Token
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- JDBC Driver - H2 ([guide](https://quarkus.io/guides/datasource)): Connect to the H2 database via JDBC
- Redis Client ([guide](https://quarkus.io/guides/redis)): Connect to Redis in either imperative or reactive style
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- Logging GELF ([guide](https://quarkus.io/guides/centralized-log-management)): Log using the Graylog Extended Log Format and centralize your logs in ELK or EFK
- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- Agroal - Database connection pool ([guide](https://quarkus.io/guides/datasource)): Pool JDBC database connections (included in Hibernate ORM)
- SmallRye Health ([guide](https://quarkus.io/guides/microprofile-health)): Monitor service health
- RESTEasy Classic Links ([guide](https://quarkus.io/guides/resteasy#links)): Web Links support for RESTEasy Classic. Inject web links into response HTTP headers by annotating your endpoint resources.
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC
- RESTEasy Classic Qute ([guide](https://quarkus.io/guides/qute)): Qute Templating integration for RESTEasy
- OpenTelemetry ([guide](https://quarkus.io/guides/opentelemetry)): Use OpenTelemetry to trace services
- RESTEasy Classic ([guide](https://quarkus.io/guides/resteasy)): REST endpoint framework implementing JAX-RS and more
- Swagger UI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Swagger UI
- Micrometer Registry Prometheus ([guide](https://quarkus.io/guides/micrometer)): Enable Prometheus support for Micrometer
- Quarkus Extension for Spring Data JPA API ([guide](https://quarkus.io/guides/spring-data-jpa)): Use Spring Data JPA annotations to create your data access layer
## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### RESTEasy Qute

Create your web page using Quarkus RESTEasy & Qute

[Related guide section...](https://quarkus.io/guides/qute#type-safe-templates)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)
