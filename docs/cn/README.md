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
api的保护可以认为：认证-请求携带的认证信息是否校验通过，鉴权-认证通过的用户拥有指定api的权限才能访问此api。然而不仅于此，什么样的认证策略, jwt, basic,digest,oauth还是多支持, 权限配置是写死代码还是动态配置，我想动态赋权怎么办，云原生越来越火用的框架是quarkus等新秀不是spring生态咋弄，http实现不是servlet而是jax-rs规范咋整， to be or not to be, this is a question

> 目前`java`主流的权限框架有`shiro，spring security`, 下面对于它们的探讨都是个人之见，接受纠正   
> `shiro`对于`restful api`原生支持不太友好,需要改写一些代码,2年前一个项目 [booshiro](https://gitee.com/tomsun28/bootshiro) 就是改造`shiro`,使其在过滤链就能匹配不同的`rest`请求进行权限校验,之后给`shiro commit`几次`pr`,`fix`其在过滤链匹配时的危险漏洞,总的来说`shiro`很强大但其起源并非面向`web`,对`restful`不是很友好    
> `spring security`很强大,与`spring`深度集成,离开`spring`,比如`javalin`和之前用过的`osgi`框架`karaf`就用不了了  
> 如果不用注解配置，它们都会在链式匹配这块，用请求的url和配置的链一个一个`ant`匹配(匹配过程中会有缓存等提高性能)，但匹配的链过多时还是比较耗性能(根据算法时间复杂度判断，暂未测试验证)    
> 我们希望能解决这些，提供一个**针对restful api**，**无框架依赖**，可以**动态修改权限**，**多认证策略**，**更快速度**，**易用**的认证鉴权框架    

## <font color="green">Introduction</font>

> `sureness` 是我们在使用 `java` 权限框架 `shiro` 之后,吸取其良好设计加上一些想法实现的全新认证鉴权项目  
>  面对 `restful api` 的认证鉴权,基于 `rbac` (用户-角色-资源)主要关注于对 `restful api` 的安全保护  
>  无特定框架依赖(本质就是过滤器处拦截判断,已有springboot,quarkus,javalin,ktor等demo)  
>  支持动态修改权限配置(动态修改哪些api需要被认证，可以被谁访问)    
>  支持主流http容器  servlet 和 jax-rs  
>  支持多种认证策略, `jwt, basic auth, digest auth` ... 可扩展自定义支持的认证方式   
>  基于改进的字典匹配树拥有的高性能 
>  良好的扩展接口, demo和文档  

>`sureness`的低配置，易扩展，不耦合其他框架，能使开发者对自己的项目多场景快速安全的进行保护   

##### Framework Sample Support  

- [x] spring [sample-bootstrap](cn/sample-bootstrap.md)   
- [x] springboot [sample-tom](cn/sample-tom.md)  
- [x] quarkus [sample-quarkus](cn/sample-quarkus.md)  
- [x] javalin [sample-javalin](cn/sample-javalin.md)    
- [x] ktor [sample-ktor](cn/sample-ktor.md)    
- [x] spring webflux [spring-webflux-sureness](cn/sample-spring-webflux.md)    
- [x] more samples todo   
