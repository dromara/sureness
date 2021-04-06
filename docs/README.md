<p align="center">
  <a href="https://su.usthe.com">
    <img alt="sureness" src="_media/hat-128.svg">
  </a>
</p>

# <font size="14p">sureness</font>

> A simple and efficient open-source jvm security framework that focus on the protection of restful api.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.2-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core)
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks)
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness)
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red)

## 📫 Background  

In the mainstream web architecture, how to protect the restful api provided by the back-end through effective and fast authentication has become particularly important.     
For existing frameworks, whether it is apache shiro which does not natively support rest, or deeply bound spring and steep learning curve of spring security are not our ideal framework.   
Ever since sureness was born, we hope to solve these, provide a **restful api**, **no framework dependency**, can **dynamically modify permissions**, **multiple authentication policies**, **faster**, **easy to use and extend** security framework.

## 🎡 <font color="green">Introduction</font>  

> Sureness is a new, permission project which we learn from apache shiro and add some ideas to create it.  
> Authentication for restful api, based on RBAC, mainly focused on the protection of restful api.  
> No specific framework dependency(support springboot, quarkus, javalin, ktor and more).    
> Support dynamic modification of permissions.   
> Support websocket, mainstream http container(servlet and jax-rs).    
> Supports JWT, Basic Auth, Digest Auth... Can extend custom supported authentication methods.    
> High performance due dictionary matching tree.      
> Good extension interface, demo and document.    

> The low configuration of sureness, easy to expand, and not coupled with other frameworks, enables developers to quickly and safely protect their projects in multiple scenarios.   

##### 🔍 Compare     

| ~         | sureness | shiro | spring security |
| ---       | ---      | ---   | --- |
| **multi framework support**  | support      | support need modify   | not support |
| **restful api** | support | support need modify   | support |
| **websocket** | support | not support   | not support |
| **path match**  | dictionary matching tree | ant match | ant match |
| **annotation support**    | support      | support      | support |
| **servlet**    | support      | support      | support |
| **jax-rs**     | support      | not support    | not support |
| **dynamic modification of permissions** | support | support need modify | support need modify |
| **performance** | fast | slower | slower|
| **learning curve** | simple | simple | steep|

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
- [x] sureness integration session sample [sureness-session](https://github.com/usthe/sureness/tree/master/samples/sureness-session)
- [x] sureness integration redis cache session sample [sureness-redis-session](https://github.com/usthe/sureness/tree/master/samples/sureness-redis-session)
- [x] more samples todo   
