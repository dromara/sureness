<p align="center">
  <a href="https://su.usthe.com">
    <img alt="sureness" src="./docs/_media/hat-128.svg">
  </a>
</p>

# <font size="14p">sureness</font>

> A simple and efficient open-source jvm security framework that focus on the protection of restful api.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks) 
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness) 
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red) 

**Please use the space [su.usthe.com](https://su.usthe.com/) when [usthe.com/sureness](https://usthe.com/sureness) cannot accessed.**  

[Chinese Documentation -> 中文文档](README_CN.md)  

## <font color="green">`Introduction`</font>


> Sureness is a new, permission project which we learn from apache shiro and add some ideas to create it  
> Authentication for restful api, based on RBAC, Mainly focused on the protection of restful api  
> No specific framework dependency(support springboot, quarkus, javalin, ktor and more)  
> Support dynamic modification of permissions
> Support mainstream http container(servlet and jax-rs)    
> Supports JWT, Basic Auth... Can extend custom supported authentication methods  
> [High performance due dictionary matching tree](#Why Is High Performance)  
> Good extension interface, demo and document.   

> The low configuration of sureness, easy to expand, and not coupled with other frameworks, enables developers to quickly and safely protect their projects in multiple scenarios.   

##### Framework Sample Support  

- [x] spring [sample-bootstrap](sample-bootstrap)   
- [x] springboot [sample-tom](sample-tom)  
- [x] quarkus [sample-quarkus](samples/quarkus-sureness)  
- [x] javalin [sample-javalin](samples/javalin-sureness)    
- [x] ktor [sample-ktor](samples/ktor-sureness)   
- [x] spring webflux [sample-spring-webflux](samples/spring-webflux-sureness)   
- [x] more samples todo  

## Quick Start 

##### <font color="red">Some Conventions</font>  

- Based RBAC, only has role-resource, no permission action    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belongs some Role -- Role owns Resource -- User can access the resource  

Resource path matching see: [Uri Match](docs/path-match.md)  

##### Add sureness In Project  

1. When use maven build project, add maven coordinate  
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.8</version>
</dependency>
```
2. When use gradle build project, add gradle coordinate  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.8'
```
3. When not java build project, add sureness-core.jar to classPath  
```
download this jar at mvnrepository  
https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core
```

##### Add an Interceptor Intercepting All Requests  

The interceptor can be a filter or a spring interceptor.  
The interceptor intercepts all request to check them.  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

##### Implement Exception Flow When Exception Throw  
Authentication passed directly, failure throw exception, catch exception and do something:   

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
        } catch (RuntimeException e) {
            // other sureness exception
        }
```

Detail sureness exception see: [Default Sureness Exception](docs/default-exception.md)  

### Load Config DataSource   

Sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
The dataSource can load from txt, dataBase or no dataBase etc.
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.
Also, we provide default dataSource implement which load dataSource from txt(sureness.yml), user can defined their data in sureness.yml. 

Default Document DataSource Config - sureness.yml, see: [Default DataSource](docs/default-datasource.md)  

If the configuration resource data comes from text, please refer to  [10 Minute Tutorial's Program--sample-bootstrap](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
If the configuration resource data comes from dataBase, please refer to  [30 Minute Tutorial's Program--sample-tom](https://github.com/tomsun28/sureness/tree/master/sample-tom)   

**Have Fun**      

## Advanced Use  

If know sureness Process flow, maybe know the extend point  

Sureness supports custom subject, custom subjectCreator, custom processor and more.  

Suggest look these interface before extending:  

- `Subject`:  Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: create subject interface, provider create method   
- `Processor`:  process subject interface, where happen authentication and authorization 
- `PathTreeProvider`: resource data provider, it can load data from txt or database,etc
- `SurenessAccountProvider`: account data provider, it can load data from txt or database,etc   

Sureness Process Flow: 

![flow](/docs/_images/flow-en.png)  

1. **Custom Datasource**  

`Implment PathTreeProvider, load in DefaultPathRoleMatcher`   
`Implment SurenessAccountProvide, load in processor`  

2. **Custom Subject**  

`Implment Subject, add custom subject content`  
`Implment SubjectCreate to create custom subject`  
`Implment Processor to support custom subject`    

3. **Custom Processor**  

`A subject also can support by different processor, so we can custom processor to support custom subject`
`Implment Processor, set which subject can support and implment processing details`  

Detail please refer to  [30 Minute Tutorial's Program--sample-tom](sample-tom)   

## Contributing  

Very welcome to Contribute this project, help sureness go further and better. If you have any questions or suggestions about the project code, please contact @tomsun28 directly.

Components of Repository:  
- [sureness's kernel code--sureness-core](core)  
- [10 Minute Tutorial's Program--sample-bootstrap](sample-bootstrap)  
- [30 Minute Tutorial's Program--sample-tom](sample-tom)  
- [Sample projects using sureness in each framework(javalin,ktor,quarkus)--samples](samples)  


##### Why Is High Performance  

![pathRoleMatcher](docs/_images/PathRoleMatcher.svg)  
 

### License  
[`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)