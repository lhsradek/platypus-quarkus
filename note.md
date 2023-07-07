# Platypus Quarkus Project

```
[lhs@phaw10kadner platypus-quarkus]$ bin/restart
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
[INFO] Copying 9 resources
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
[INFO] Not copying test resources
[INFO]
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ platypus-quarkus ---
[INFO] Not compiling test sources
[INFO]
[INFO] --- maven-surefire-plugin:3.0.0:test (default-test) @ platypus-quarkus ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ platypus-quarkus ---
[INFO] Building jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT.jar
[INFO]
[INFO] --- quarkus-maven-plugin:2.16.7.Final:build (default) @ platypus-quarkus ---
[INFO] [org.hibernate.Version] HHH000412: Hibernate ORM core version 5.6.15.Final
[INFO] [io.quarkus.deployment.pkg.steps.JarResultBuildStep] Building native image source jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar/platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Building native image from /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar/platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
[WARNING] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Cannot find the `native-image` in the GRAALVM_HOME, JAVA_HOME and System PATH. Install it using `gu install native-image` Attempting to fall back to container build.
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildContainerRunner] Using docker to run the native image builder
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildContainerRunner] Checking image status quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
22.3-java17: Pulling from quarkus/ubi-quarkus-mandrel-builder-image
Digest: sha256:720ea335a0cda883b64d4225672455ee6320f3c5a8c1d14a683a1b9b3c75b6f8
Status: Image is up to date for quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildStep] Running Quarkus native-image plugin on native-image 22.3.2.1-Final Mandrel Distribution (Java Version 17.0.7+7)
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildRunner] docker run --env LANG=C --rm --user 1000:1000 -v /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar:/project:z --name build-native-giFYG quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 -J-Dcom.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize=true -J-Dsun.nio.ch.maxUpdateArraySize=100 -J-Dlogging.initial-configurator.min-level=500 -J-Djava.util.logging.manager=org.jboss.logmanager.LogManager -J-Dio.quarkus.caffeine.graalvm.recordStats=true -J-DCoordinatorEnvironmentBean.transactionStatusManagerEnable=false -J-Dvertx.logger-delegate-factory-class-name=io.quarkus.vertx.core.runtime.VertxLogDelegateFactory -J-Dvertx.disableDnsResolver=true -J-Dio.netty.leakDetection.level=DISABLED -J-Dio.netty.allocator.maxOrder=3 -J-Duser.language=en -J-Duser.country=US -J-Dfile.encoding=UTF-8 --features=io.quarkus.hibernate.orm.runtime.graal.DisableLoggingFeature,org.hibernate.graalvm.internal.QueryParsingSupport,io.quarkus.jdbc.postgresql.runtime.graal.SQLXMLFeature,io.quarkus.hibernate.validator.runtime.DisableLoggingFeature,org.hibernate.graalvm.internal.GraalVMStaticFeature,io.quarkus.runner.Feature,io.quarkus.runtime.graal.ResourcesFeature,io.quarkus.runtime.graal.DisableLoggingFeature,io.quarkus.caffeine.runtime.graal.CacheConstructorsFeature -J--add-exports=java.security.jgss/sun.security.krb5=ALL-UNNAMED -J--add-opens=java.base/java.text=ALL-UNNAMED -J--add-opens=java.base/java.io=ALL-UNNAMED -J--add-opens=java.base/java.lang.invoke=ALL-UNNAMED -J--add-opens=java.base/java.util=ALL-UNNAMED -H:+CollectImageBuildStatistics -H:ImageBuildStatisticsFile=platypus-quarkus-1.0.0-SNAPSHOT-runner-timing-stats.json -H:BuildOutputJSONFile=platypus-quarkus-1.0.0-SNAPSHOT-runner-build-output-stats.json -H:+AllowFoldMethods -J-Djava.awt.headless=true --no-fallback --link-at-build-time -H:+ReportExceptionStackTraces -H:-AddAllCharsets --enable-url-protocols=http,https -H:-UseServiceLoaderFeature -H:+StackTrace -J--add-exports=org.graalvm.sdk/org.graalvm.nativeimage.impl=ALL-UNNAMED -J--add-exports=org.graalvm.nativeimage.builder/com.oracle.svm.core.jdk=ALL-UNNAMED --exclude-config io\.netty\.netty-codec /META-INF/native-image/io\.netty/netty-codec/generated/handlers/reflect-config\.json --exclude-config io\.netty\.netty-handler /META-INF/native-image/io\.netty/netty-handler/generated/handlers/reflect-config\.json platypus-quarkus-1.0.0-SNAPSHOT-runner -jar platypus-quarkus-1.0.0-SNAPSHOT-runner.jar
========================================================================================================================
GraalVM Native Image: Generating 'platypus-quarkus-1.0.0-SNAPSHOT-runner' (executable)...
========================================================================================================================
[1/7] Initializing...                                                                                   (38.9s @ 0.46GB)
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
20:12:15,118 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [local.intranet] to DEBUG
20:12:15,158 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [io.quarkus] to INFO
20:12:15,160 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.SQL] to DEBUG
20:12:15,162 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.hibernate.type.descriptor.sql.BasicBinder] to DEBUG
20:12:15,166 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to INFO
20:12:15,167 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
20:12:15,489 INFO  [org.hib.env.boo.int.EnversServiceImpl] Envers integration enabled? : true
[2/7] Performing analysis...  [*******]                                                                (176.8s @ 2.36GB)
  25,119 (90.37%) of 27,796 classes reachable
  37,029 (62.82%) of 58,945 fields reachable
 119,230 (55.33%) of 215,491 methods reachable
     908 classes,   244 fields, and 4,630 methods registered for reflection
      64 classes,    68 fields, and    58 methods registered for JNI access
       6 native libraries: dl, m, pthread, rt, stdc++, z
[3/7] Building universe...                                                                              (24.0s @ 2.53GB)
[4/7] Parsing methods...      [*****]                                                                   (22.7s @ 1.89GB)
[5/7] Inlining methods...     [***]                                                                     (10.6s @ 1.47GB)
[6/7] Compiling methods...    [***********]                                                            (132.0s @ 2.42GB)
[7/7] Creating image...                                                                                 (36.3s @ 2.50GB)
  44.55MB (48.51%) for code area:    76,127 compilation units
  46.82MB (50.98%) for image heap:  538,311 objects and 287 resources
 478.56KB ( 0.51%) for other data
  91.84MB in total
------------------------------------------------------------------------------------------------------------------------
Top 10 packages in code area:                               Top 10 object types in image heap:
   1.64MB sun.security.ssl                                    10.00MB byte[] for code metadata
   1.10MB java.util                                            6.04MB java.lang.Class
 759.93KB java.lang.invoke                                     4.97MB java.lang.String
 719.65KB com.sun.crypto.provider                              4.50MB byte[] for java.lang.String
 654.57KB io.quarkus.runtime.generated                         4.03MB byte[] for general heap data
 597.97KB jdk.proxy4                                           2.11MB byte[] for embedded resources
 579.99KB org.hibernate.hql.internal.antlr                     2.11MB com.oracle.svm.core.hub.DynamicHubCompanion
 496.62KB org.postgresql.jdbc                                  1.24MB byte[] for reflection metadata
 487.99KB com.sun.org.apache.xerces.internal.impl           1012.53KB java.lang.String[]
 485.35KB java.util.concurrent                               952.88KB java.util.HashMap$Node
  36.58MB for 1260 more packages                               9.50MB for 5977 more object types
------------------------------------------------------------------------------------------------------------------------
                       44.8s (9.6% of total time) in 119 GCs | Peak RSS: 4.94GB | CPU load: 5.48
------------------------------------------------------------------------------------------------------------------------
Produced artifacts:
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner (executable)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner-build-output-stats.json (json)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner-timing-stats.json (raw)
 /project/platypus-quarkus-1.0.0-SNAPSHOT-runner.build_artifacts.txt (txt)
========================================================================================================================
Finished generating 'platypus-quarkus-1.0.0-SNAPSHOT-runner' in 7m 43s.
[INFO] [io.quarkus.deployment.pkg.steps.NativeImageBuildRunner] docker run --env LANG=C --rm --user 1000:1000 -v /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-native-image-source-jar:/project:z --entrypoint /bin/bash quay.io/quarkus/ubi-quarkus-mandrel-builder-image:22.3-java17 -c objcopy --strip-debug platypus-quarkus-1.0.0-SNAPSHOT-runner
[INFO] [io.quarkus.deployment.QuarkusAugmentor] Quarkus augmentation completed in 489281ms
[INFO]
[INFO] --- maven-javadoc-plugin:3.2.0:jar (attach-javadocs) @ platypus-quarkus ---
[INFO] No previous run data found, generating javadoc.
[INFO] Building jar: /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-javadoc.jar
[INFO]
[INFO] --- maven-failsafe-plugin:3.0.0:integration-test (default) @ platypus-quarkus ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-failsafe-plugin:3.0.0:verify (default) @ platypus-quarkus ---
[INFO] Tests are skipped.
[INFO]
[INFO] --- maven-install-plugin:2.4:install (default-install) @ platypus-quarkus ---
[INFO] Installing /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT.jar to /home/lhs/.m2/repository/local/intranet/quarkus/platypus-quarkus/1.0.0-SNAPSHOT/platypus-quarkus-1.0.0-SNAPSHOT.jar
[INFO] Installing /home/lhs/src/platypus-quarkus/pom.xml to /home/lhs/.m2/repository/local/intranet/quarkus/platypus-quarkus/1.0.0-SNAPSHOT/platypus-quarkus-1.0.0-SNAPSHOT.pom
[INFO] Installing /home/lhs/src/platypus-quarkus/target/platypus-quarkus-1.0.0-SNAPSHOT-javadoc.jar to /home/lhs/.m2/repository/local/intranet/quarkus/platypus-quarkus/1.0.0-SNAPSHOT/platypus-quarkus-1.0.0-SNAPSHOT-javadoc.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  08:38 min
[INFO] Finished at: 2023-07-06T22:19:31+02:00
[INFO] ------------------------------------------------------------------------
Building quarkus
[+] Building 3.8s (9/9) FINISHED
 => [internal] load build definition from Dockerfile.native                                                                                                                                                   0.1s
 => => transferring dockerfile: 766B                                                                                                                                                                          0.0s
 => [internal] load .dockerignore                                                                                                                                                                             0.1s
 => => transferring context: 115B                                                                                                                                                                             0.0s
 => [internal] load metadata for registry.access.redhat.com/ubi8/ubi-minimal:8.5                                                                                                                              0.4s
 => [1/4] FROM registry.access.redhat.com/ubi8/ubi-minimal:8.5@sha256:3f32ebba0cbf3849a48372d4fc3a4ce70816f248d39eb50da7ea5f15c7f9d120                                                                        0.0s
 => [internal] load build context                                                                                                                                                                             1.5s
 => => transferring context: 96.18MB                                                                                                                                                                          1.5s
 => CACHED [2/4] WORKDIR /work/                                                                                                                                                                               0.0s
 => CACHED [3/4] RUN chown 1000 /work     && chmod "g+rwX" /work     && chown 1000:root /work                                                                                                                 0.0s
 => [4/4] COPY --chown=1001:root target/*-runner /work/application                                                                                                                                            0.6s
 => exporting to image                                                                                                                                                                                        1.1s
 => => exporting layers                                                                                                                                                                                       1.1s
 => => writing image sha256:d9eff7b0f20efa66f8328cd96931556caee8495ee8ccf77eedfc0e52a341f4f3                                                                                                                  0.0s
 => => naming to docker.io/lhsradek/platypus-quarkus:v1                                                                                                                                                       0.0s
Creating fpt-platypus-quarkus-db    ... done
Creating fpt-platypus-quarkus-redis ... done
Creating fpt-platypus-quarkus       ... done
```

