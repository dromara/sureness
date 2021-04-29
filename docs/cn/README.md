<p align="center">
  <a href="https://su.usthe.com">
    <img alt="sureness" src="../_media/icon128.svg">
  </a>
</p>

# <font size="14p">sureness</font>

> 面向`REST API`的高性能认证鉴权框架    

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.3-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core)
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks)
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness)
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red)


## 📫 背景

在主流的前后端分离架构中，如何通过有效快速的认证鉴权来保护后端提供的`REST API`变得尤为重要。对现存框架，不原生支持`rest`的`apache shiro`，
还是深度绑定`spring`，学习曲线陡峭的`spring security`，或多或少都不是我们的理想型。   
于是乎`sureness`诞生了，我们希望能解决这些，提供一个面向**REST API**，**无框架依赖**，可以**动态修改权限**，**多认证策略**，**更快速度**，**易用易扩展**的认证鉴权框架。

## 🎡 <font color="green">介绍</font>

> `sureness` 是我们在深度使用权限框架 `apache shiro` 之后,吸取其优点全新设计开发的一个认证鉴权框架  
>  面向 `REST API` 的认证鉴权,基于 `RBAC` (用户-角色-资源)主要关注于对 `API` 的安全保护  
>  无特定框架依赖(本质就是过滤器处拦截判断,已有 `Springboot,Quarkus,Javalin,Ktor` 等集成样例)  
>  支持动态修改权限配置(动态修改配置每个 `API` 谁有权访问)
>  支持 `Websocket` ,主流 `HTTP` 容器 `Servlet` 和 `JAX-RS`  
>  支持多种认证策略, `JWT, Basic auth, Digest auth` ... 可扩展自定义支持的认证方式   
>  基于改进的字典匹配树拥有的高性能    
>  良好的扩展接口, 样例和文档助急速理解扩展

>`sureness`的低配置，易扩展，不耦合其他框架，希望能帮助开发者对自己的项目多场景快速安全的进行保护

##### 🔍 框架对比

| ~         | sureness | shiro | spring security |
| ---       | ---      | ---   | ---  |
| **多框架支持**  | 支持      | 需改动支持   | 不支持 |
| **REST API** | 支持 | 需改动支持   | 支持 |
| **Websocket** | 支持 | 不支持   | 不支持 |
| **过滤链匹配**  | 优化的字典匹配树 | ant匹配 | ant匹配 |
| **注解支持**    | 支持      | 支持      | 支持 |
| **Servlet**    | 支持      | 支持      | 支持|
| **JAX-RS**     | 支持      | 不支持    | 不支持|
| **权限动态修改** | 支持 | 需改动支持 | 需改动支持|
| **性能速度** | 较快 | 较慢 | 较慢|
| **学习曲线** | 简单 | 简单 | 陡峭|  

##### 📈 基准性能测试    

![benchmark](../_images/benchmark_cn.png)  

**基准测试显示sureness对比无权限框架应用损耗0.026ms性能，shiro损耗0.088ms,spring security损耗0.116ms，
相比之下sureness基本不消耗性能，且性能(TPS损耗)是shiro的3倍，spring security的4倍**     
**性能差距会随着api匹配链的增加而进一步拉大**     
详见[基准测试](https://github.com/tomsun28/sureness-shiro-spring-security)    


##### ✌ 框架支持样例  

- [x] sureness集成springboot样例(配置文件方案) [sample-bootstrap](cn/sample-bootstrap.md)   
- [x] sureness集成springboot样例(数据库方案) [sample-tom](cn/sample-tom.md)  
- [x] sureness集成quarkus样例 [sample-quarkus](cn/sample-quarkus.md)  
- [x] sureness集成javalin样例 [sample-javalin](cn/sample-javalin.md)    
- [x] sureness集成ktor样例 [sample-ktor](cn/sample-ktor.md)    
- [x] sureness集成spring webflux样例 [spring-webflux-sureness](cn/sample-spring-webflux.md)    
- [x] sureness集成micronaut样例 [sample-micronaut](cn/sample-micronaut.md)
- [x] sureness使用session样例 [sureness-session](https://github.com/usthe/sureness/tree/master/samples/sureness-session)
- [x] sureness分布式缓存session样例 [sureness-redis-session](https://github.com/usthe/sureness/tree/master/samples/sureness-redis-session)
- [x] more samples todo   
