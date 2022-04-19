## Objective

This project is to prolong life of Elasticsearch 1.x by allowing it to run on newer JDKs until we need that.

## Milestones

* Java 11
* ...

## Versioning

Follow the format: `<base-version>-<build>`, where `build` is incremented starting `001`

## Distributing

Via FUGA internal Maven repository as follows:
```xml
<dependency>
    <groupId>com.iip</groupId>
    <artifactId>elasticsearch-java11</artifactId>
    <version>1.7.5-001</version>
</dependency>
```

### Deploying to the FUGA internal repository

Make sure that your `settings.xml` contains credentials for
the registry `fuga-nexus`.

```shell
$ mvn package org.apache.maven.plugins:maven-deploy-plugin:2.8:deploy \ 
  -DskipTests \
  -DaltReleaseDeploymentRepository=fuga-nexus::default::https://nexus.tools.fuga.com/repository/maven-releases/
```