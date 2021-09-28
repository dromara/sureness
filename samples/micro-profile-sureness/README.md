# MicroProfile generated Application

## Introduction

MicroProfile Starter has generated this MicroProfile application for you.

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package     
    mvn clean install -Pwildfly 

This will create an executable jar file **demo-wildfly.jar** within the _target_ maven folder. This can be started by executing the following command

    java -jar target/demo-wildfly.jar



To launch the test page, open your browser at the following URL

    http://localhost:8080/index.html  



## Specification examples

By default, there is always the creation of a JAX-RS application class to define the path on which the JAX-RS endpoints are available.

Also, a simple Hello world endpoint is created, have a look at the class **HelloController**.

More information on MicroProfile can be found [here](https://microprofile.io/)


















