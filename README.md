Stream Event Processing System (version 0.2.2)
===================================
Introduction 
-----------------------
This is an open source event stream processing system that supports s4.io via processing element adapter. It inherits distributed, scalable, 
partially fault-tolerant properties from S4 and allow developers to easily develop applications for processing continuous unbounded streams of data. 
This event processing project is modeled on the event processing model depicted by Opher Etzion et al.

NB: Names used in this project does not imply that they endorse this project in anyway. Source code is in its early stages so changes could be made to some implementations without issuing a notice.

Features
--------------------------
 * classic event operators (e.g., sequence, concurrent conjunction, disjunction, negation etc.), modal operators (e.g. always, sometimes etc).
 * event aggregation for count, avg, sum, min, max, mode etc.
 * pattern matching for avg, min, max etc (making it more stable).
 * event filtering, enrichment, projection and translation will be supported. [FL]
 * temporal-based grouping (e.g. fixed/sliding interval, fixed/sliding event interval, etc) will be supported.[FL]
 * spatial-based grouping (e.g. fixed location, entity distance location and event distance location) will be supported.[FL]


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

Contributors
--------------------------
The StreamEPS project adopts the fork+pull model using [git](http://git-scm.com/). This means developers work on forks of the StreamEPS repo (i.e. their own clones of it). 
The team then merges these changes into the main StreamEPS repositories when requested to do so by other developers.

Check [GitHub](http://help.github.com/forking/) for more info on forking.