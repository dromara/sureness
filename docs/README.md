<p align="center">
  <a href="https://github.com/usthe/sureness">
    <img alt="sureness" src="_media/icon128.svg">
  </a>
</p>

# <font size="14p">sureness</font>

> A simple and efficient jvm security framework that focus on the protection of REST API.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.3-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core)
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks)
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness)
![GitHub Release Date](https://img.shields.io/github/release-date/dromara/sureness?color=blue&logo=figshare&logoColor=red)
[![star](https://gitee.com/dromara/sureness/badge/star.svg?theme=gray)](https://gitee.com/dromara/sureness/stargazers)
[![star](https://img.shields.io/github/stars/dromara/sureness?style=social)](https://github.com/dromara/sureness)

## 🎡 <font color="green">Introduction</font>

> [Sureness](https://github.com/usthe/sureness) is a simple and efficient open-source security framework that focus on the protection of REST API.  
> Provide authentication and authorization, based on RBAC.   
> No specific framework dependency (supports Javalin, Spring Boot, Quarkus, Ktor, and more).    
> Supports dynamic modification of permissions.   
> Supports WebSockets and mainstream HTTP containers (Servlet and JAX-RS).    
> Supports JWT, Basic Auth, Digest Auth, and can be extended to support custom authentication methods.    
> High performance due to dictionary matching tree.      
> Good extension interface, demos and documentation.

> Sureness has a sensible default configuration, is easy to customize, and is not couple to any one framework, which enables developers to quickly and safely protect their projects in multiple scenarios.

##### 🔍 Compare

| ~         | sureness | shiro | spring security |
| ---       | ---      | ---   | --- |
| **Multi Framework Support**  | support      | support need modify   | not support |
| **REST API** | support | support need modify   | support |
| **Websocket** | support | not support   | not support |
| **Path Match**  | dictionary matching tree | ant match | ant match |
| **Annotation Support**    | support      | support      | support |
| **Servlet**    | support      | support      | support |
| **JAX-RS**     | support      | not support    | not support |
| **Dynamic Modification of Permissions** | support | support need modify | support need modify |
| **Performance** | fast | slower | slower|
| **Learning Curve** | simple | simple | steep|

##### 📈 Benchmark  

![benchmark](_images/benchmark_en.png)  

**Benchmark test shows sureness to lose 0.026ms performance compared to frameless application, shiro lose 0.088ms, spring security lose 0.116ms.**    
**In contrast, sureness basically does not consume performance, and the performance (TPS loss) is 3 times that of shiro and 4 times that of spring security.**      
**The performance gap will be further widened as the api matching chain increases.**      

Detail see [Benchmark Test](https://github.com/tomsun28/sureness-shiro-spring-security)    

##### ✌ Framework Sample Support  

- [x] sureness integration springboot sample(configuration file scheme) [sample-bootstrap](sample-bootstrap.md)   
- [x] sureness integration springboot sample(database scheme) [sample-tom](sample-tom.md)  
- [x] sureness integration quarkus sample [sample-quarkus](sample-quarkus.md)  
- [x] sureness integration javalin sample [sample-javalin](sample-javalin.md)    
- [x] sureness integration ktor sample [sample-ktor](sample-ktor.md)    
- [x] sureness integration spring webflux sample [spring-webflux-sureness](sample-spring-webflux.md)   
- [x] sureness integration micronaut sample [sample-micronaut](sample-micronaut.md)  
- [x] sureness integration session sample [sureness-session](https://github.com/usthe/sureness/tree/master/samples/sureness-session)
- [x] sureness integration redis cache session sample [sureness-redis-session](https://github.com/usthe/sureness/tree/master/samples/sureness-redis-session)
- [x] more samples todo   
