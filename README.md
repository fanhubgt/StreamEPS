Stream Event Processing System (version 1.5.8)
================================================
Introduction 
-----------------------
This is an open source event stream processing system that provides an engine for processing segment-oriented, temporal-oriented, state-oriented and spatial-oriented event stream.
It provides support to other processing systems via processing element adapter, PE. It supports distributed, scalable, partially fault-tolerant properties and allow developers to easily 
develop applications for processing continuous unbounded streams of data. This event processing project is modeled on an event processing model, which has a core base supporting the below features.

NB: Names used in this project does not imply that they endorse this project in anyway. Source code is in its early stages so changes could be made to some implementations without issuing a notice.

Features
--------------------------
* Classic event operators (e.g. conjunction, disjunction, negation etc.).
* Modal event operators (e.g. always, sometimes etc).
* Event aggregation for count, avg, sum, min, max, mode etc.
* Pattern matching for classic event operators(logic, modal), avg, min, max etc.
* Event filtering for event content-filter, event header-filter and event type-filter using a data structure; comparison value-set, range value-set etc.
* Enrichment, projection and translation will be supported.[FL]
* Temporal-based grouping (e.g. fixed & sliding interval, fixed & sliding event interval, etc) will be supported.[FL]
* Spatial-based grouping (e.g. fixed location, entity distance location and event distance location) will be supported.[FL]

Requirement
---------------------------

 * OS : Linux
 * JDK : Java 1.6
 * Tool : Maven

Build Instructions 
---------------------------

1. s4_core, comm and other libraries must be installed to your local Maven repository manually. 
 The jars are located under lib/*.jar 

        mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=kryo -Dversion=1.01 -Dpackaging=jar -Dfile=lib/kryo-1.01.jar
        mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=reflectasm -Dversion=0.8 -Dpackaging=jar -Dfile=lib/reflectasm-0.8.jar
        mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=minlog -Dversion=1.2 -Dpackaging=jar -Dfile=lib/minlog-1.2.jar          
        mvn install:install-file -DgroupId=io.s4.comm -DartifactId=comm -Dversion=0.2.1.0 -Dpackaging=jar -Dfile=lib/comm-0.2.1.0.jar          
        mvn install:install-file -DgroupId=io.s4  -DartifactId=s4_core -Dversion=0.2.1.0 -Dpackaging=jar -Dfile=lib/s4_core-0.2.1.0.jar        
        mvn install:install-file -DgroupId=org.apache.hadoop -DartifactId=zookeeper -Dversion=3.1.1 -Dpackaging=jar -Dfile=lib/zookeeper-3.1.1.jar                       

2. Build and install using Maven.

        mvn install

3. Tests
         
    Under the test package of the core of the StreamEPS, there are few test cases to illustrate how some functions wire together 
    if you want to use the functional components.
	
Wiki
--------------------
Read [wiki](https://github.com/fanhubgt/StreamEPS/wiki) on StreamEPS.

Author
--------------------------
Frank Appiah, PhD

Check [GitHub](http://help.github.com/forking/) for more info on forking.

Users
----------------------
If you are using this platform in any work of yours, please send me a mail appiahnsiahfrank@gmail.com to tell me about it.

==================================================================================
 (C) Copyright 2020-2022
