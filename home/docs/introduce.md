---
id: introduce  
title: Introduce  
sidebar_label: Introduce  
slug: /
---

# Sureness

> A simple and efficient jvm security framework that focus on the protection of REST API.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.5-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core)
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks)
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness)
![GitHub Release Date](https://img.shields.io/github/release-date/dromara/sureness?color=blue&logo=figshare&logoColor=red)
[![star](https://gitee.com/dromara/sureness/badge/star.svg?theme=gray)](https://gitee.com/dromara/sureness/stargazers)
[![star](https://img.shields.io/github/stars/dromara/sureness?style=social)](https://github.com/dromara/sureness)

## 🎡 <font color="green">Introduction</font>

> [Sureness](https://github.com/dromara/sureness) is a simple and efficient open-source security framework that focus on the protection of REST API.  
> Provide authentication and authorization, based on RBAC.   
> No specific framework dependency (supports Javalin, Spring Boot, Quarkus, Ktor, Micronaut and more).    
> Supports dynamic modification of permissions.   
> Supports WebSockets and HTTP containers (Servlet and JAX-RS).    
> Supports JWT, Basic Auth, Digest Auth, and can custom auth methods.    
> High performance with Dictionary Matching Tree.      
> Good extension interface, demos and documentation.

> Sureness has a sensible default configuration, is easy to customize, and is not couple to any one framework, which enables developers to quickly and safely protect their projects in multiple scenarios.

### 🔍 Compare

| ~         | Sureness | Shiro | Spring Security |
| ---       | ---      | ---   | --- |
| **Multi Framework Support**  | support      | support need modify   | not support |
| **REST API** | support | support need modify   | support |
| **Websocket** | support | not support   | not support |
| **Path Match**  | dictionary matching tree | ant match | ant match |
| **Annotation Support**    | support      | support      | support |
| **Servlet**    | support      | support      | support |
| **JAX-RS**     | support      | not support    | not support |
| **Dynamic Permissions** | support | support need modify | support need modify |
| **Performance** | fast | slower | slower|
| **Learning Curve** | simple | simple | steep|

### 📈 Benchmark  

![benchmark](/img/docs/benchmark_en.png)  

**Benchmark test shows Sureness to lose 0.026ms performance compared to frameless application, Shiro lose 0.088ms, Spring Security lose 0.116ms.**    
**In contrast, Sureness basically does not consume performance, and the performance (TPS loss) is 3 times that of Shiro and 4 times that of Spring Security.**      
**The performance gap will be further widened as the api matching chain increases.**

Detail see [Benchmark Test](https://github.com/tomsun28/sureness-shiro-spring-security)    

### ✌ Framework Sample Support  

- [x] Sureness integration **Spring Boot** sample(configuration file scheme) [sample-bootstrap](docs/integrate/sample-bootstrap)   
- [x] Sureness integration **Spring Boot** sample(database scheme) [sample-tom](docs/integrate/sample-tom)  
- [x] Sureness integration **Quarkus** sample [sample-quarkus](docs/integrate/sample-quarkus)  
- [x] Sureness integration **Javalin** sample [sample-javalin](docs/integrate/sample-javalin)    
- [x] Sureness integration **Ktor** sample [sample-ktor](docs/integrate/sample-ktor)    
- [x] Sureness integration **Spring Webflux** sample [spring-webflux-sureness](docs/integrate/sample-spring-webflux)   
- [x] Sureness integration **Micronaut** sample [sample-micronaut](docs/integrate/sample-micronaut)  
- [x] Sureness integration Session sample [sureness-session](https://github.com/usthe/sureness/tree/master/samples/sureness-session)
- [x] Sureness integration Redis Session cache sample [sureness-redis-session](https://github.com/usthe/sureness/tree/master/samples/sureness-redis-session)
- [x] More samples todo   
