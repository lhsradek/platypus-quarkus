# Platypus Quarkus Project

```
================= STOP =================
Network platel is external, skipping
================= START =================
[INFO] Scanning for projects...
[INFO]
[INFO] --------------< local.intranet.quarkus:platypus-quarkus >---------------
[INFO] Building platypus-quarkus 1.0.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ platypus-quarkus ---
[INFO] Deleting /home/lhs/src/platypus-quarkus/target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ platypus-quarkus ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 230 resources
[INFO]
[INFO] --- quarkus-maven-plugin:2.16.7.Final:generate-code (default) @ platypus-quarkus ---
[INFO]
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ platypus-quarkus ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 63 source files to /home/lhs/src/platypus-quarkus/target/classes
[INFO]
[INFO] --- jandex-maven-plugin:3.1.1:jandex (make-index) @ platypus-quarkus ---
[INFO] Saving Jandex index: /home/lhs/src/platypus-quarkus/target/classes/META-INF/jandex.idx
[INFO]
[INFO] --- quarkus-maven-plugin:2.16.7.Final:generate-code-tests (default) @ platypus-quarkus ---
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ platypus-quarkus ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO]
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ platypus-quarkus ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 7 source files to /home/lhs/src/platypus-quarkus/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:3.0.0:test (default-test) @ platypus-quarkus ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running local.intranet.quarkus.ConversionTest
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/home/lhs/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.2.0.Final/slf4j-jboss-logmanager-1.2.0.Final.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/home/lhs/.m2/repository/ch/qos/logback/logback-classic/1.2.11/logback-classic-1.2.11.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Slf4jLoggerFactory]
01:39:34,372 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [local.intranet] to DEBUG
01:39:34,375 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [io.quarkus] to INFO
01:39:34,375 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.SQL] to INFO
01:39:34,376 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.type.descriptor.sql.BasicBinder] to INFO
01:39:34,376 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to INFO
01:39:34,376 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
2023-07-09 01:39:37,832 INFO  [io.agr.pool] (main) Datasource '<default>': Initial size smaller than min. Connections will be created when necessary
2023-07-09 01:39:37,921 INFO  [org.fly.cor.int.lic.VersionPrinter] (main) Flyway Community Edition 9.11.0 by Redgate
01:39:37,924 |-WARN in Logger[org.flywaydb.core.internal.license.VersionPrinter] - No appenders present in context [null] for logger [org.flywaydb.core.internal.license.VersionPrinter].
2023-07-09 01:39:37,924 INFO  [org.fly.cor.int.lic.VersionPrinter] (main) See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#9.11.0
2023-07-09 01:39:37,925 INFO  [org.fly.cor.int.lic.VersionPrinter] (main)
2023-07-09 01:39:38,252 INFO  [org.fly.cor.int.dat.bas.BaseDatabaseType] (main) Database: jdbc:postgresql://localhost:5432/lhs_platypus_fpt (PostgreSQL 14.3)
2023-07-09 01:39:38,328 INFO  [org.fly.cor.int.com.DbValidate] (main) Successfully validated 3 migrations (execution time 00:00.045s)
2023-07-09 01:39:38,390 INFO  [org.fly.cor.int.com.DbMigrate] (main) Current version of schema "public": 1.01.001
2023-07-09 01:39:38,391 INFO  [org.fly.cor.int.com.DbMigrate] (main) Schema "public" is up to date. No migration necessary.
2023-07-09 01:39:39,391 INFO  [io.quarkus] (main) fpt-platypus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.7.Final) started in 11.367s. Listening on: http://0.0.0.0:8081 and https://0.0.0.0:8444
2023-07-09 01:39:39,392 INFO  [io.quarkus] (main) Profile test activated.
2023-07-09 01:39:39,393 INFO  [io.quarkus] (main) Installed features: [agroal, cache, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-gelf, logging-logback, micrometer, narayana-jta, opentelemetry, opentelemetry-otlp-exporter, qute, redis-client, resteasy-reactive, resteasy-reactive-jackson, resteasy-reactive-jaxb, resteasy-reactive-jsonb, resteasy-reactive-qute, scheduler, security, security-jpa, servlet, smallrye-context-propagation, smallrye-health, smallrye-openapi, smallrye-reactive-type-converters, spring-data-jpa, spring-di, swagger-ui, vertx]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 19.029 s - in local.intranet.quarkus.ConversionTest
[INFO] Running local.intranet.quarkus.AhojResourceTest
2023-07-09 01:39:41,106 INFO  [io.quarkus] (main) fpt-platypus stopped in 0.067s
01:39:46,826 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [local.intranet] to DEBUG
01:39:46,829 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [io.quarkus] to INFO
01:39:46,829 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.SQL] to INFO
01:39:46,829 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.type.descriptor.sql.BasicBinder] to INFO
01:39:46,829 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to INFO
01:39:46,830 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
2023-07-09 01:39:49,209 INFO  [io.agr.pool] (main) Datasource '<default>': Initial size smaller than min. Connections will be created when necessary
01:39:49,274 |-WARN in Logger[io.agroal.pool] - No appenders present in context [null] for logger [io.agroal.pool].
2023-07-09 01:39:49,349 INFO  [org.fly.cor.int.lic.VersionPrinter] (main) Flyway Community Edition 9.11.0 by Redgate
2023-07-09 01:39:49,349 INFO  [org.fly.cor.int.lic.VersionPrinter] (main) See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#9.11.0
2023-07-09 01:39:49,349 INFO  [org.fly.cor.int.lic.VersionPrinter] (main)
2023-07-09 01:39:49,370 INFO  [org.fly.cor.int.dat.bas.BaseDatabaseType] (main) Database: jdbc:postgresql://localhost:5432/lhs_platypus_fpt (PostgreSQL 14.3)
2023-07-09 01:39:49,407 INFO  [org.fly.cor.int.com.DbValidate] (main) Successfully validated 3 migrations (execution time 00:00.019s)
2023-07-09 01:39:49,442 INFO  [org.fly.cor.int.com.DbMigrate] (main) Current version of schema "public": 1.01.001
2023-07-09 01:39:49,443 INFO  [org.fly.cor.int.com.DbMigrate] (main) Schema "public" is up to date. No migration necessary.
2023-07-09 01:39:49,771 INFO  [io.qua.sch.run.SimpleScheduler] (main) Simple scheduler is disabled by config property and will not be started
2023-07-09 01:39:49,947 INFO  [io.quarkus] (main) fpt-platypus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.7.Final) started in 8.340s. Listening on: http://0.0.0.0:8081 and https://0.0.0.0:8444
2023-07-09 01:39:49,947 INFO  [io.quarkus] (main) Profile test activated.
2023-07-09 01:39:49,947 INFO  [io.quarkus] (main) Installed features: [agroal, cache, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-gelf, logging-logback, micrometer, narayana-jta, opentelemetry, opentelemetry-otlp-exporter, qute, redis-client, resteasy-reactive, resteasy-reactive-jackson, resteasy-reactive-jaxb, resteasy-reactive-jsonb, resteasy-reactive-qute, scheduler, security, security-jpa, servlet, smallrye-context-propagation, smallrye-health, smallrye-openapi, smallrye-reactive-type-converters, spring-data-jpa, spring-di, swagger-ui, vertx]
2023-07-09 01:39:51,031 WARN  [io.qua.ver.htt.run.sec.HttpSecurityRecorder] (vert.x-eventloop-thread-2) Encryption key was not specified for persistent FORM auth, using temporary key G4IvJtKENzO0eWuADkshJs0s72eiM1p4XmN0fAv25dU=
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 11.513 s - in local.intranet.quarkus.AhojResourceTest
[INFO] Running local.intranet.quarkus.HelloResourceTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.088 s - in local.intranet.quarkus.HelloResourceTest
[INFO] Running local.intranet.quarkus.JobCounterResourceTest
2023-07-09 01:39:52,757 INFO  [loc.int.qua.api.sch.PlatypusJob] (main) Hello from job of Platypus Quarkus on kibana.platel status:OK level:[DEBUG=6675, ERROR=6, INFO=4659, WARN=269]
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.106 s - in local.intranet.quarkus.JobCounterResourceTest
[INFO] Running local.intranet.quarkus.StatusResourceTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.17 s - in local.intranet.quarkus.StatusResourceTest
2023-07-09 01:39:53,003 INFO  [io.quarkus] (main) fpt-platypus stopped in 0.051s
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ platypus-quarkus ---
[INFO] Building jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT.jar
[INFO]
[INFO] --- quarkus-maven-plugin:2.16.7.Final:build (default) @ platypus-quarkus ---
[INFO] [org.hibernate.Version] HHH000412: Hibernate ORM core version 5.6.15.Final
[INFO] [io.quarkus.deployment.pkg.steps.JarResultBuildStep] Building native image source jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar/platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Building native image from /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar/platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildContainerRunner] Using docker to run the native image builder
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildContainerRunner] Checking image status quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
22.3-java17: Pulling from quarkus/ubi-quarkus-mandrel-builder-image
Digest: sha256:[PROTECTED]
Status: Image is up to date for quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Running Quarkus native-image plugin on native-image 22.3.2.1-Final Mandrel Distribution (Java Version 17.0.7+7)
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildRunner] docker run --env LANG=C --rm --user 1000:1000 -v /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar:/project:z --name build-native-JzIyz quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 -J-Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true -J-Dio.quarkus.caffeine.graalvm.recordStats=true -J-Dlogging.initial-configurator.min-level=500 -J-Dsun.nio.ch.maxUpdateArraySize=100 -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-DCoordinatorEnvironmentBean.transactionStatusManagerEnable=false -J-Dvertx.logger-delegate-factory-class-name=io.quarkus.vertx.core.runtime.VertxLogDelegateFactory -J-Dvertx.disableDnsResolver=true -J-Dio.netty.leakDetection.level=DISABLED -J-Dio.netty.allocator.maxOrder=3 -J-Duser.language=en -J-Duser.country=US -J-Dfile.encoding=UTF-8 --features=io.quarkus.hibernate.orm.runtime.graal.DisableLoggingFeature,io.quarkus.jdbc.postgresql.runtime.graal.SQLXMLFeature,org.hibernate.graalvm.internal.QueryParsingSupport,io.quarkus.hibernate.validator.runtime.DisableLoggingFeature,org.hibernate.graalvm.internal.GraalVMStaticFeature,io.quarkus.caffeine.runtime.graal.CacheConstructorsFeature,io.quarkus.runner.Feature,io.quarkus.runtime.graal.ResourcesFeature,io.quarkus.runtime.graal.DisableLoggingFeature -J--add-exports=java.security.jgss/sun.security.krb5=ALL-UNNAMED -J--add-opens=java.base/java.text=ALL-UNNAMED -J--add-opens=java.base/java.io=ALL-UNNAMED -J--add-opens=java.base/java.lang.invoke=ALL-UNNAMED -J--add-opens=java.base/java.util=ALL-UNNAMED -H:+CollectImageBuildStatistics -H:ImageBuildStatisticsFile=platypus-quarkus-1.0.0-SNAPSHOT-runner-timing-stats.json -H:BuildOutputJSONFile=platypus-quarkus-1.0.0-SNAPSHOT-runner-build-output-stats.json -H:+AllowFoldMethods -J-Djava.awt.headless=true --no-fallback --link-at-build-time -H:+ReportExceptionStackTraces -H:-AddAllCharsets --enable-url-protocols=http,https -H:-UseServiceLoaderFeature -H:+StackTrace -J--add-exports=org.graalvm.sdk/org.graalvm.nativeimage.impl=ALL-UNNAMED -J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk=ALL-UNNAMED --exclude-config io\.netty\.netty-codec /META-INF/native-image/io\.netty/netty-codec/generated/handlers/reflect-config\.json --exclude-config io\.netty\.netty-handler /META-INF/native-image/io\.netty/netty-handler/generated/handlers/reflect-config\.json platypus-quarkus-1.0.0-SNAPSHOT-runner -jar platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
========================================================================================================================
GraalVM Native Image: Generating 'platypus-quarkus-1.0.0-SNAPSHOT-runner' (executable)...
========================================================================================================================
[1/7] Initializing...                                                                                   (34.2s @ 0.48GB)
 Version info: 'GraalVM 22.3.2.1-Final Java 17 Mandrel Distribution'
 Java version info: '17.0.7+7'
 C compiler: gcc (redhat, x86_64, 8.5.0)
 Garbage collector: Serial GC
 10 user-specific feature(s)
 - com.oracle.svm.thirdparty.gson.GsonFeature
 - io.quarkus.caffeine.runtime.graal.CacheConstructorsFeature
 - io.quarkus.hibernate.orm.runtime.graal.DisableLoggingFeature: Disables INFO logging during the analysis phase for the [org.hibernate.Version, org.hibernate.annotations.common.Version, org.hibernate.dialect.Dialect] categories
 - io.quarkus.hibernate.validator.runtime.DisableLoggingFeature: Disables INFO logging during the analysis phase for the [org.hibernate.validator.internal.util.Version] categories
 - io.quarkus.jdbc.postgresql.runtime.graal.SQLXMLFeature
 - io.quarkus.runner.Feature: Auto-generated class by Quarkus from the existing extensions
 - io.quarkus.runtime.graal.DisableLoggingFeature: Disables INFO logging during the analysis phase for the [org.jboss.threads] categories
 - io.quarkus.runtime.graal.ResourcesFeature: Register each line in META-INF/quarkus-native-resources.txt as a resource on Substrate VM
 - org.hibernate.graalvm.internal.GraalVMStaticFeature: Hibernate ORM's static reflection registrations for GraalVM
 - org.hibernate.graalvm.internal.QueryParsingSupport: Hibernate ORM's support for HQL Parser in GraalVM
23:40:52,484 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.classic.db.DBAppender$$delayed]
23:40:52,533 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [DB]
23:40:52,561 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.classic.AsyncAppender$$delayed]
23:40:52,562 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [DB_ASYNC]
23:40:52,563 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [DB] to ch.qos.logback.classic.AsyncAppender$$delayed[DB_ASYNC]
23:40:52,563 |-INFO in ch.qos.logback.classic.AsyncAppender$$delayed[DB_ASYNC] - Attaching appender named [DB] to AsyncAppender.
23:40:52,566 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [local.intranet] to DEBUG
23:40:52,567 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [DB_ASYNC] to Logger[local.intranet]
23:40:52,567 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [io.quarkus] to INFO
23:40:52,568 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [DB_ASYNC] to Logger[io.quarkus]
23:40:52,568 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [local.intranet] to DEBUG
23:40:52,568 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [io.quarkus] to INFO
23:40:52,569 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.SQL] to INFO
23:40:52,570 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.type.descriptor.sql.BasicBinder] to INFO
23:40:52,571 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to INFO
23:40:52,571 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
23:40:52,800 INFO  [org.hib.env.boo.int.EnversServiceImpl] Envers integration enabled? : true
[2/7] Performing analysis...  [*******]                                                                (156.3s @ 2.28GB)
  25,304 (90.43%) of 27,982 classes reachable
  37,269 (62.86%) of 59,292 fields reachable
 119,891 (55.41%) of 216,358 methods reachable
     919 classes,   262 fields, and 4,691 methods registered for reflection
      64 classes,    68 fields, and    58 methods registered for JNI access
       6 native libraries: dl, m, pthread, rt, stdc++, z
[3/7] Building universe...                                                                              (19.8s @ 2.89GB)
[4/7] Parsing methods...      [****]                                                                    (18.3s @ 2.78GB)
[5/7] Inlining methods...     [****]                                                                     (8.8s @ 2.74GB)
[6/7] Compiling methods...    [***********]                                                            (122.2s @ 3.96GB)
[7/7] Creating image...                                                                                 (22.7s @ 2.23GB)
  44.81MB (40.66%) for code area:    76,643 compilation units
  64.93MB (58.92%) for image heap:  544,438 objects and 508 resources
 477.17KB ( 0.42%) for other data
 110.20MB in total
------------------------------------------------------------------------------------------------------------------------
Top 10 packages in code area:                               Top 10 object types in image heap:
   1.64MB sun.security.ssl                                    19.13MB byte[] for embedded resources
   1.10MB java.util                                           10.05MB byte[] for code metadata
 759.93KB java.lang.invoke                                     6.08MB java.lang.Class
 719.65KB com.sun.crypto.provider                              5.02MB java.lang.String
 689.11KB io.quarkus.runtime.generated                         4.56MB byte[] for java.lang.String
 624.66KB jdk.proxy4                                           4.07MB byte[] for general heap data
 579.77KB org.hibernate.hql.internal.antlr                     2.12MB com.oracle.svm.core.hub.DynamicHubCompanion
 497.54KB org.postgresql.jdbc                                  1.25MB byte[] for reflection metadata
 487.99KB com.sun.org.apache.xerces.internal.impl           1020.48KB java.lang.String[]
 485.80KB java.util.concurrent                               968.63KB java.util.HashMap$Node
  36.77MB for 1274 more packages                               9.61MB for 6002 more object types
------------------------------------------------------------------------------------------------------------------------
                       30.3s (7.5% of total time) in 116 GCs | Peak RSS: 5.70GB | CPU load: 6.11
------------------------------------------------------------------------------------------------------------------------
Produced artifacts:
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner (executable)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner-build-output-stats.json (json)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner-timing-stats.json (raw)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner.build_artifacts.txt (txt)
========================================================================================================================
Finished generating 'platypus-quarkus-1.0.0-SNAPSHOT-runner' in 6m 39s.
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildRunner] docker run --env LANG=C --rm --user 1000:1000 -v /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar:/project:z --entrypoint /bin/bash quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 -c objcopy --strip-debug platypus-quarkus-1.0.0-SNAPSHOT-runner
[INFO] [io.quarkus.deployment.QuarkusAugmentor] Quarkus augmentation completed in 423890ms
[INFO]
[INFO] --- maven-javadoc-plugin:3.2.0:jar (attach-javadocs) @ platypus-quarkus ---
[INFO] No previous run data found, generating javadoc.
[INFO] Building jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-javadoc.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  08:03 min
[INFO] Finished at: 2023-07-09T01:47:09+02:00
[INFO] ------------------------------------------------------------------------
Start lhsradek/platypus-quarkus:v1 (platypus-quarkus-v1.img)
Building quarkus
[+] Building 4.2s (9/9) FINISHED                                                                                                                                                                    docker:default
 => [internal] load build definition from Dockerfile.native                                                                                                                                                   0.0s
 => => transferring dockerfile: 895B                                                                                                                                                                          0.0s
 => [internal] load .dockerignore                                                                                                                                                                             0.0s
 => => transferring context: 115B                                                                                                                                                                             0.0s
 => [internal] load metadata for registry.access.redhat.com/ubi8/ubi-minimal:8.5                                                                                                                              0.5s
 => [1/4] FROM registry.access.redhat.com/ubi8/ubi-minimal:8.5@sha256:[PROTECTED]                                                                                                                             0.0s
 => [internal] load build context                                                                                                                                                                             1.8s
 => => transferring context: 115.44MB                                                                                                                                                                         1.8s
 => CACHED [2/4] WORKDIR /work/                                                                                                                                                                               0.0s
 => CACHED [3/4] RUN chown 1000 /work     && chmod "g+rwX" /work     && chown 1000:root /work                                                                                                                 0.0s
 => [4/4] COPY --chown=1001:root target/*-runner /work/application                                                                                                                                            0.6s
 => exporting to image                                                                                                                                                                                        1.2s
 => => exporting layers                                                                                                                                                                                       1.2s
 => => writing image sha256:[PROTECTED]                                                                                                                                                                       0.0s
 => => naming to docker.io/lhsradek/platypus-quarkus:v1                                                                                                                                                       0.0s
Creating fpt-platypus-quarkus-db ... done
Creating fpt-platypus-quarkus       ... done
```
