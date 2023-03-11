/**
 * This module is for {@link local.intranet.quarkus}
 * 
 * <li>LoggingEvent</li>
 * <p>
 * <pre>
 * timestmp    |formatted_message                                                                                                                                                                                                                                              |logger_name                                                       |level_string|thread_name           |reference_flag|arg0                                                                                                                                                                                                                                                          |arg1          |arg2                                                                                                                                               |arg3        |caller_filename                    |caller_class                                                      |caller_method     |caller_line|event_id|
 * ------------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+------------------------------------------------------------------+------------+----------------------+--------------+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--------------+---------------------------------------------------------------------------------------------------------------------------------------------------+------------+-----------------------------------+------------------------------------------------------------------+------------------+-----------+--------+ * 678524145968|Nazdar from RESTEasy Reactive                                                                                                                                                                                                                                  |local.intranet.quarkus.api.controller.IndexController             |DEBUG       |executor-thread-0     |             0|Nazdar from RESTEasy Reactive                                                                                                                                                                                                                                 |              |                                                                                                                                                   |            |IndexController.java               |local.intranet.quarkus.api.controller.IndexController             |nazdar            |55         |     153|
 * 678524179572|Nazdar from RESTEasy Reactive                                                                                                                                                                                                                                  |local.intranet.quarkus.api.controller.IndexController             |DEBUG       |executor-thread-0     |             0|Nazdar from RESTEasy Reactive                                                                                                                                                                                                                                 |              |                                                                                                                                                   |            |IndexController.java               |local.intranet.quarkus.api.controller.IndexController             |nazdar            |55         |     154|
 * 678524185998|GetUserRoles ret:[local.intranet.quarkus.api.info.RolePlain@18e7361b, local.intranet.quarkus.api.info.RolePlain@4bd59898, local.intranet.quarkus.api.info.RolePlain@5f3d537d]                                                                                  |local.intranet.quarkus.api.service.RoleService                    |DEBUG       |executor-thread-0     |             0|[local.intranet.quarkus.api.info.RolePlain@18e7361b, local.intranet.quarkus.api.info.RolePlain@4bd59898, local.intranet.quarkus.api.info.RolePlain@5f3d537d]                                                                                                  |              |                                                                                                                                                   |            |RoleService.java                   |local.intranet.quarkus.api.service.RoleService                    |getUsersRoles     |52         |     155|
 * 678524186002|local.intranet.quarkus.api.info.RoleInfo@25a9a40b                                                                                                                                                                                                              |local.intranet.quarkus.api.controller.InfoController              |DEBUG       |executor-thread-0     |             0|local.intranet.quarkus.api.info.RoleInfo@25a9a40b                                                                                                                                                                                                             |              |                                                                                                                                                   |            |InfoController.java                |local.intranet.quarkus.api.controller.InfoController              |getRoleInfo       |58         |     156|
 * 678524352708|Restarting quarkus due to changes in InfoController.class.                                                                                                                                                                                                     |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-0|             0|InfoController.class                                                                                                                                                                                                                                          |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |532        |     157|
 * 678524353848|platypus-quarkus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.4.Final) started in 1.068s. Listening on: http://0.0.0.0:8080                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|platypus-quarkus                                                                                                                                                                                                                                              |1.0.0-SNAPSHOT|on JVM                                                                                                                                             |2.16.4.Final|Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |106        |     158|
 * 678524353849|Profile dev activated. Live Coding activated.                                                                                                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|                                                                                                                                                                                                                                                              |dev           |Live Coding activated.                                                                                                                             |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |109        |     159|
 * 678525772695|Hello from RESTEasy Reactive                                                                                                                                                                                                                                   |local.intranet.quarkus.api.controller.IndexController             |DEBUG       |executor-thread-0     |             0|Hello from RESTEasy Reactive                                                                                                                                                                                                                                  |              |                                                                                                                                                   |            |IndexController.java               |local.intranet.quarkus.api.controller.IndexController             |hello             |42         |     203|
 * 678524353850|Installed features: [agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-ja|io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-jackson, security, sma|              |                                                                                                                                                   |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |111        |     160|
 * 678524353851|Live reload total time: 1.335s                                                                                                                                                                                                                                 |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-0|             0|1.335                                                                                                                                                                                                                                                         |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |539        |     161|
 * 678524526827|Restarting quarkus due to changes in UserService.class.                                                                                                                                                                                                        |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-1|             0|UserService.class                                                                                                                                                                                                                                             |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |532        |     162|
 * 678524527767|platypus-quarkus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.4.Final) started in 0.902s. Listening on: http://0.0.0.0:8080                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|platypus-quarkus                                                                                                                                                                                                                                              |1.0.0-SNAPSHOT|on JVM                                                                                                                                             |2.16.4.Final|Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |106        |     163|
 * 678524527770|Profile dev activated. Live Coding activated.                                                                                                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|                                                                                                                                                                                                                                                              |dev           |Live Coding activated.                                                                                                                             |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |109        |     164|
 * 678524527771|Installed features: [agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-ja|io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-jackson, security, sma|              |                                                                                                                                                   |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |111        |     165|
 * 678524527772|Live reload total time: 1.106s                                                                                                                                                                                                                                 |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-1|             0|1.106                                                                                                                                                                                                                                                         |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |539        |     166|
 * 678524801629|Restarting quarkus due to changes in UserService.class, InfoController.class.                                                                                                                                                                                  |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-1|             0|UserService.class, InfoController.class                                                                                                                                                                                                                       |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |532        |     167|
 * 678524802649|platypus-quarkus 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.16.4.Final) started in 0.977s. Listening on: http://0.0.0.0:8080                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|platypus-quarkus                                                                                                                                                                                                                                              |1.0.0-SNAPSHOT|on JVM                                                                                                                                             |2.16.4.Final|Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |106        |     168|
 * 678524802650|Profile dev activated. Live Coding activated.                                                                                                                                                                                                                  |io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|                                                                                                                                                                                                                                                              |dev           |Live Coding activated.                                                                                                                             |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |109        |     169|
 * 678524802651|Installed features: [agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-ja|io.quarkus                                                        |INFO        |Quarkus Main Thread   |             0|agroal, cdi, flyway, hibernate-envers, hibernate-orm, hibernate-orm-panache, hibernate-validator, jdbc-postgresql, logging-logback, micrometer, narayana-jta, reactive-pg-client, reactive-routes, resteasy-reactive, resteasy-reactive-jackson, security, sma|              |                                                                                                                                                   |            |Timing.java                        |io.quarkus.bootstrap.runner.Timing                                |printStartupTime  |111        |     170|
 * 678524802652|Live reload total time: 1.250s                                                                                                                                                                                                                                 |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-1|             0|1.250                                                                                                                                                                                                                                                         |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |539        |     171|
 * 678525056303|Restarting quarkus due to changes in InfoController.class.                                                                                                                                                                                                     |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |INFO        |vert.x-worker-thread-1|             0|InfoController.class                                                                                                                                                                                                                                          |              |                                                                                                                                                   |            |RuntimeUpdatesProcessor.java       |io.quarkus.deployment.dev.RuntimeUpdatesProcessor                 |doScan            |532        |     172|
 * </pre>
 * </p>
 * 
 * 
 *
 * @since 11
 * @author  Radek Kádner
 * @version 1.0.0-SNAPSHOT
 */
package local.intranet.quarkus.api.model.entity;
