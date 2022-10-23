自荐下我们新的全职开源项目  **[HertzBeat赫兹节拍](https://github.com/dromara/hertzbeat)** - 易用友好的高性能监控告警系统。  
网站监测，PING连通性，端口可用性，数据库监控，API监控，自定义监控，阈值告警，告警通知。

**代码仓库: [GITHUB](https://github.com/dromara/hertzbeat) | [GITEE](https://gitee.com/dromara/hertzbeat)**   
**官网: [hertzbeat.com](https://hertzbeat.com) | [tancloud.cn](https://tancloud.cn)**  
**[在线使用](https://console.tancloud.cn)** https://console.tancloud.cn

欢迎使用，点赞，推荐，灰常感谢🙏。  


<p align="center">
  <a href="https://github.com/usthe/sureness">
    <img alt="sureness" src="./docs/_media/brand128.svg">
  </a>
</p>

# <font size="14p">Sureness</font> <font size="5p">  | [中文文档](README_CN.md)</font>

> A simple and efficient jvm security framework that focus on the protection of REST API.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.5-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core)
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/dromara/sureness/8?label=pull%20checks)
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness)
![GitHub Release Date](https://img.shields.io/github/release-date/dromara/sureness?color=blue&logo=figshare&logoColor=red)
[![star](https://gitee.com/dromara/sureness/badge/star.svg?theme=gray)](https://gitee.com/dromara/sureness/stargazers)
[![star](https://img.shields.io/github/stars/dromara/sureness?style=social)](https://github.com/dromara/sureness)

<a href="https://www.producthunt.com/posts/sureness?utm_source=badge-featured&utm_medium=badge&utm_souce=badge-sureness" target="_blank"><img src="https://api.producthunt.com/widgets/embed-image/v1/featured.svg?post_id=287707&theme=light" alt="sureness - Jvm security framework that focus on protection of rest api | Product Hunt"/></a>

**Home Page: [usthe.com/sureness](https://usthe.com/sureness) | [su.usthe.com](https://su.usthe.com/)**

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

##### 🔍 Compare     

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

##### 📈 Benchmark  

![benchmark](docs/_images/benchmark_en.png)  

**Benchmark test shows Sureness to lose 0.026ms performance compared to frameless application, Shiro lose 0.088ms, Spring Security lose 0.116ms.**    
**In contrast, Sureness basically does not consume performance, and the performance (TPS loss) is 3 times that of Shiro and 4 times that of Spring Security.**      
**The performance gap will be further widened as the api matching chain increases.**      

Detail see [Benchmark Test](https://github.com/tomsun28/sureness-shiro-spring-security-benchmark)       

##### ✌ Framework Sample Support  

- [x] Sureness integration **Spring Boot** sample(configuration file scheme) [sample-bootstrap](sample-bootstrap)   
- [x] Sureness integration **Spring Boot** sample(database scheme) [sample-tom](sample-tom)  
- [x] Sureness integration **Quarkus** sample [sample-quarkus](samples/quarkus-sureness)  
- [x] Sureness integration **Javalin** sample [sample-javalin](samples/javalin-sureness)    
- [x] Sureness integration **Ktor** sample [sample-ktor](samples/ktor-sureness)   
- [x] Sureness integration **Spring Webflux** sample [sample-spring-webflux](samples/spring-webflux-sureness)
- [x] Sureness integration **Micronaut** sample [sample-micronaut](samples/micronaut-sureness)
- [x] Sureness integration **Jfinal** sample [sample-jfinal](samples/jfinal-sureness)
- [x] Sureness integration **Solon** sample [sample-solon](samples/solon-sureness)
- [x] Sureness integration **Spring Gateway** sample [sample-spring-gateway](samples/spring-gateway-sureness)  
- [x] Sureness integration **Zuul** sample [sample-zuul](samples/zuul-sureness)    
- [x] Sureness integration Session sample [sureness-session](samples/sureness-session)    
- [x] Sureness integration Redis Session cache sample [sureness-redis-session](samples/sureness-redis-session)  
- [x] More samples todo  

## 🔨 Quick Start 

#### 🐕 <font color="red">Some Conventions</font>  

- Based RBAC, User-Role-Resource.    
- We treat API requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belongs some Role -- Role owns Resource -- User can access the resource.  

Resource path matching see: [URI Match](docs/path-match.md)  

#### 🐖 Add Sureness In Your Project  

When use maven or gradle build project, add coordinate  
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>1.0.8</version>
</dependency>
```
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.8'
```

#### 🐵 Use the Default Configuration to Configure Sureness  

The default configuration -`DefaultSurenessConfig` uses the document datasource `sureness.yml` as the auth datasource.  
It supports JWT auth, Basic auth, Digest authentication.  
```
@Bean
public DefaultSurenessConfig surenessConfig() {
    return new DefaultSurenessConfig();
}
```

#### 🐮 Load Auth Config DataSource   

Sureness authentication requires us to provide our own account data, role permission data, etc. These data may come from text, relational databases, non-relational databases, annotations, etc.   
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.  

- `SurenessAccountProvider` - Account datasource provider interface.    
- `PathTreeProvider` - Resource uri-role datasource provider interface.     

Default Document DataSource Config - `sureness.yml`, see: [Default Document DataSource](docs/default-datasource.md)   
Annotation DataSource Config Detail - `AnnotationLoader`, see: [Annotation DataSource](docs/annotation-datasource.md)  

If the configuration resource data comes from text, please refer to  [Sureness integration Spring Boot sample(configuration file scheme)](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
If the configuration resource data comes from dataBase, please refer to  [Sureness integration Spring Boot sample(database scheme)](https://github.com/tomsun28/sureness/tree/master/sample-tom)   


#### 🐐 Add an Interceptor Intercepting All Requests  

The essence of Sureness is to intercept all rest requests for authenticating and authorizing.        
The interceptor can be a filter or a Spring interceptor, it intercepts all request to check them.  
```
SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

#### 🐰 Implement Auth Exception Handling Process    

Sureness uses exception handling process:  

- If auth success, method - `checkIn` will return a `SubjectSum` object containing user information.    
- If auth failure, method - `checkIn` will throw different types of auth exceptions.   

Users need to continue the subsequent process based on these exceptions.(eg: return the request response)  

Here we need to customize the exceptions thrown by `checkIn`, passed directly when auth success, catch exception when auth failure and do something:    

```
try {
    SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
} catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
    // Create subject error related execption 
} catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
    // Account disable related exception
} catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
    // Authentication failure related exception
} catch (UnauthorizedException e5) {
    // Authorization failure related exception
} catch (SurenessAuthenticationException | SurenessAuthorizationException e) {
    // other sureness exception
}
```

Detail see: [Default Sureness Auth Exception](docs/default-exception.md)   

**Have Fun**      

## 🥐 Advanced Use

Sureness supports custom subject, custom subjectCreator, custom processor and more.  

Before advanced custom extension, let's first understand the general process of Sureness:  

![flow](/docs/_images/flow-en.png)  

As in the above process, Subject is created by SubjectCreate according to the request body, and different authentication processors process the supported Subjects.  

Sureness provides the following common interfaces as extension points:  

- `Subject`:  Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: Create subject interface, provider create method.   
- `Processor`:  Process subject interface, where happen authentication and authorization. 
- `PathTreeProvider`: Resource data provider, it can load data from txt or database,etc.
- `SurenessAccountProvider`: Account data provider, it can load data from txt or database,etc.   

Refer to [Extension Point](https://usthe.com/sureness/#/extend-point) for the extended documentation.   

1. 🥊 **Custom Subject**

`Implment Subject, add custom subject content`  
`Implment SubjectCreate to create custom subject`  
`Implment Processor to support custom subject`

See [Custom Subject](docs/custom-subject.md)  

2. 🔫 **Custom SubjectCreator**

`Implment SubjectCreate to create your custom subject`   

See [Custom SubjectCreator](docs/custom-subject-creator.md)  

3. 🪓 **Custom Processor**

`A subject also can support by different processor, so we can custom processor to support custom subject`
`Implment Processor, set which subject can support and implment processing details`

See [Custom Processor](docs/custom-processor.md)  

4. 🏹 **Custom Datasource**  

`Implment PathTreeProvider, load in DefaultPathRoleMatcher`   
`Implment SurenessAccountProvide, load in processor`  

See [Custom Datasource](docs/custom-datasource.md)  

Detail please refer to  [Sureness integration Spring Boot sample(database scheme)](sample-tom)   

## 🙋 Contributing  

Very welcome to Contribute this project, go further and better with Sureness. 

Components of Repository:  
- [Sureness's kernel code--Sureness-core](core)  
- [Sureness integration Spring Boot sample(configuration file scheme)--sample-bootstrap](sample-bootstrap)  
- [Sureness integration Spring Boot sample(database scheme)-sample-tom](sample-tom)  
- [Sample projects using Sureness in each framework(Javalin,Ktor,Quarkus)--samples](samples)  

See [CONTRIBUTING](CONTRIBUTING.md)    

#### 💪 Why Is High Performance  

![pathRoleMatcher](docs/_images/PathRoleMatcher.svg)  

## 🌞 Friend's Links   

* **```JustAuth```** A Java library of third-party authorized login: [Github](https://github.com/justauth/JustAuth)    
* **```MaxKey```** Leading-Edge Enterprise-Class open source IAM Identity and Access management product: [Github](https://github.com/dromara/MaxKey)   
* **```PhalApi```** PHP Api Framework: [Website](https://www.phalapi.net/)    

## 💬 Join discussion    

[Github Discussion](https://github.com/usthe/sureness/discussions)          
[Gitter Channel](https://gitter.im/usthe/sureness)   

<img alt="tan-cloud" src="https://cdn.jsdelivr.net/gh/dromara/hertzbeat/home/static/img/wechat.png" width="400"/>       

<br/>

<img alt="planet" src="https://cdn.jsdelivr.net/gh/dromara/hertzbeat@gh-pages/img/planet.jpg" width="400"/>    



## 🛡️ License  
[`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)
