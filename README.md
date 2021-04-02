<p align="center">
  <a href="https://su.usthe.com">
    <img alt="sureness" src="./docs/_media/hat-128.svg">
  </a>
</p>

# <font size="14p">sureness</font> <font size="5p">  | [中文文档](README_CN.md)</font>

> A simple and efficient open-source jvm security framework that focus on the protection of restful api.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
[![Maven](https://img.shields.io/badge/Maven%20Central-1.0.0-blue.svg)](https://search.maven.org/artifact/com.usthe.sureness/sureness-core) 
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks) 
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness) 
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red)
[![star](https://gitee.com/tomsun28/sureness/badge/star.svg?theme=gray)](https://gitee.com/tomsun28/sureness/stargazers)
[![star](https://img.shields.io/github/stars/usthe/sureness?style=social)](https://github.com/usthe/sureness)

<a href="https://www.producthunt.com/posts/sureness?utm_source=badge-featured&utm_medium=badge&utm_souce=badge-sureness" target="_blank"><img src="https://api.producthunt.com/widgets/embed-image/v1/featured.svg?post_id=287707&theme=light" alt="sureness - Jvm security framework that focus on protection of rest api | Product Hunt"/></a>

**Home Page: [usthe.com/sureness](https://usthe.com/sureness) or [su.usthe.com](https://su.usthe.com/)**  

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

![benchmark](docs/_images/benchmark_en.png)  

**Benchmark test shows sureness to lose 0.026ms performance compared to frameless application, shiro lose 0.088ms, spring security lose 0.116ms.**    
**In contrast, sureness basically does not consume performance, and the performance (TPS loss) is 3 times that of shiro and 4 times that of spring security.**      
**The performance gap will be further widened as the api matching chain increases.**      

Detail see [Benchmark Test](https://github.com/tomsun28/sureness-shiro-spring-security-benchmark)       

##### ✌ Framework Sample Support  

- [x] sureness integration springboot sample(configuration file scheme) [sample-bootstrap](sample-bootstrap)   
- [x] sureness integration springboot sample(database scheme) [sample-tom](sample-tom)  
- [x] sureness integration quarkus sample [sample-quarkus](samples/quarkus-sureness)  
- [x] sureness integration javalin sample [sample-javalin](samples/javalin-sureness)    
- [x] sureness integration ktor sample [sample-ktor](samples/ktor-sureness)   
- [x] sureness integration spring webflux sample [sample-spring-webflux](samples/spring-webflux-sureness)   
- [x] more samples todo  

## 🔨 Quick Start 

#### 🐕 <font color="red">Some Conventions</font>  

- Based RBAC, only has role-resource, no permission action.    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
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
    <version>1.0.1</version>
</dependency>
```
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.1'
```

#### 🐵 Use the Default Configuration to Configure Sureness  

The default configuration -`DefaultSurenessConfig` uses the document datasource `sureness.yml` as the auth datasource.  
It supports jwt, basic auth, digest auth authentication.  
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

If the configuration resource data comes from text, please refer to  [Sureness integration springboot sample(configuration file scheme)](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
If the configuration resource data comes from dataBase, please refer to  [Sureness integration springboot sample(database scheme)](https://github.com/tomsun28/sureness/tree/master/sample-tom)   


#### 🐐 Add an Interceptor Intercepting All Requests  

The essence of sureness is to intercept all rest requests for authenticating and Authorizing.     
The interceptor can be a filter or a spring interceptor, it intercepts all request to check them.  
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

Detail sureness auth exception see: [Default Sureness Auth Exception](docs/default-exception.md)   

**Have Fun**      

## 🥐 Advanced Use

Sureness supports custom subject, custom subjectCreator, custom processor and more.  

Before advanced custom extension, let's first understand the general process of sureness:  

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

Detail please refer to  [Sureness integration springboot sample(database scheme)](sample-tom)   

## 🙋 Contributing  

Very welcome to Contribute this project, go further and better with sureness. If you have any questions or suggestions about the project code, please contact @tomsun28 directly.  

Components of Repository:  
- [sureness's kernel code--sureness-core](core)  
- [sureness integration springboot sample(configuration file scheme)--sample-bootstrap](sample-bootstrap)  
- [sureness integration springboot sample(database scheme)-sample-tom](sample-tom)  
- [sample projects using sureness in each framework(javalin,ktor,quarkus)--samples](samples)  


#### 💪 Why Is High Performance  

![pathRoleMatcher](docs/_images/PathRoleMatcher.svg)  


## 🛡️ License  
[`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)