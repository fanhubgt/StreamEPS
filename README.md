Stream Event Processing System
==============================

Introduction 
-----------------------
This is an open source event stream processing system based on [s4.io](http://s4.io). S4 is a general-purpose, distributed, scalable, partially fault-tolerant, plug-gable platform that allows programmers to easily develop applications for processing continuous unbounded streams of data. This project is based on the event processing language depicted by Opter Etzion et al.

Features
--------------------------
 * classic event operators (e.g., sequence, concurrent conjunction, disjunction, negation etc.).
 * event aggregation for count, avg, sum, min, max etc.
 * event filtering, enrichment, projection and translation will be supported

Requirement
---------------------------

 * OS : Linux
 * JDK : Java 1.6
 * Tool : Maven

Build Instructions 
---------------------------

* s4_core, comm and other libraries must be installed to your local Maven repository manually. 
 The jars are located under lib/*.jar 

* Build and install using Maven
     mvn install