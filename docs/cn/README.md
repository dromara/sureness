<p align="center">
  <a href="https://su.usthe.com">
    <img alt="sureness" src="../_media/hat-128.svg">
  </a>
</p>

# <font size="14p">sureness - 面向restful api的认证鉴权</font>

> A simple and efficient open-source java security framework that focus on the protection of restful api.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks) 
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness) 
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red) 


## Background  
现在很多网站都进行了前后端分离，后端提供rest api，前端调用接口获取数据渲染。这种架构下如何保护好后端所提供的rest api使得更加重视。  
认证-请求携带的认证信息是否校验通过，鉴权-认证通过的用户拥有指定api的权限才能访问此api。然而不仅于此，什么样的认证策略, jwt, basic,digest,oauth还是多支持, 权限配置是写死代码还是动态配置赋权，云原生越来越火用的框架是quarkus而不是spring生态，http实现不是servlet而是jax-rs规范咋办。   

我们希望能解决这些，提供一个面向**restful api**，**无框架依赖**，可以**动态修改权限**，**多认证策略**，**更快速度**，**易用易扩展**的认证鉴权框架    

## <font color="green">Introduction</font>

> `sureness` 是我们在使用 `java` 权限框架 `shiro` 之后,吸取其良好设计加上一些想法实现的全新认证鉴权项目  
>  面对 `restful api` 的认证鉴权,基于 `rbac` (用户-角色-资源)主要关注于对 `restful api` 的安全保护  
>  无特定框架依赖(本质就是过滤器处拦截判断,已有`springboot,quarkus,javalin,ktor`等demo)  
>  支持动态修改权限配置(动态修改哪些`api`需要被认证，可以被谁访问)    
>  支持主流`http`容器  `servlet` 和 `jax-rs`  
>  支持多种认证策略, `jwt, basic auth, digest auth` ... 可扩展自定义支持的认证方式   
>  基于改进的字典匹配树拥有的高性能    
>  良好的扩展接口, demo和文档  

>`sureness`的低配置，易扩展，不耦合其他框架，能使开发者对自己的项目多场景快速安全的进行保护   

##### Compare     

| ~         | sureness | shiro | spring security |
| ---       | ---      | ---   | ---  |
| **多框架支持**  | 支持      | 需改动支持   | 不支持 |
| **restful api** | 支持 | 需改动支持   | 支持 |
| **过滤链匹配**  | 优化的字典匹配树 | ant匹配 | ant匹配 |
| **注解支持**    | 支持      | 支持      | 支持 |
| **servlet**    | 支持      | 支持      | 支持|
| **jax-rs**     | 支持      | 不支持    | 不支持|
| **权限动态修改** | 支持 | 需改动支持 | 需改动支持|  

##### Framework Sample Support  

- [x] spring [sample-bootstrap](cn/sample-bootstrap.md)   
- [x] springboot [sample-tom](cn/sample-tom.md)  
- [x] quarkus [sample-quarkus](cn/sample-quarkus.md)  
- [x] javalin [sample-javalin](cn/sample-javalin.md)    
- [x] ktor [sample-ktor](cn/sample-ktor.md)    
- [x] spring webflux [spring-webflux-sureness](cn/sample-spring-webflux.md)    
- [x] more samples todo   
