Stream Event Processing System
===================================
Introduction 
-----------------------
This is an open source event stream processing system based on [s4.io](http://s4.io). S4 is a general-purpose, distributed, scalable, partially fault-tolerant, plug-gable platform that allows programmers to easily develop applications for processing continuous unbounded streams of data. This event processing project is modeled on the event processing model depicted by Opher Etzion et al.

NB: Names used in this project does not imply that they endorse this project in anyway. Source code is in its early stages so changes could be made to some implementations without issuing a notice.

Features
--------------------------
 * classic event operators (e.g., sequence, concurrent conjunction, disjunction, negation etc.).
 * event aggregation for count, avg, sum, min, max, mode etc.
 * pattern matching for avg, min, max etc (making it more stable).
 * temporal-based grouping (e.g. fixed & sliding interval, fixed & sliding event interval, etc) will be supported.
 * spatial-based grouping (e.g. fixed location, entity distance location and event distance location) will be supported.
 * event filtering, enrichment, projection and translation will be supported

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

