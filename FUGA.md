## Objective

This project is to prolong life of Elasticsearch 1.x by allowing it to run on newer JDKs until we need that.

## Milestones

* Java 11
* ...

## Versioning

Follow the format: `<base-version>-javaXX.<build>-<SNAPSHOT(OPTIONAL)>`

## Distributing

Via FUGA internal Maven registry. For instance:
```xml
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>1.7.5-java11.1-SNAPSHOT</version>
</dependency>
```