# `sureness`  

> A simple and efficient open-source security framework that focus on the protection of restful api.

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) 
![GitHub pull request check contexts](https://img.shields.io/github/status/contexts/pulls/tomsun28/sureness/8?label=pull%20checks) 
[![Gitter](https://img.shields.io/gitter/room/usthe/sureness?label=sureness&color=orange&logo=gitter&logoColor=red)](https://gitter.im/usthe/sureness) 
![GitHub Release Date](https://img.shields.io/github/release-date/tomsun28/sureness?color=blue&logo=figshare&logoColor=red) 

**Please use the space [su.usthe.com](https://su.usthe.com/) when [usthe.com/sureness](https://usthe.com/sureness) cannot accessed.**  

[中文文档](README.md)  

## <font color="green">`Introduction`</font>


> Sureness is a new, permission project which author learn from apache shiro and add some ideas to create it  
> Authentication for restful api, based on RBAC, Mainly focused on the protection of restful api  
> Native supports  restful api, websocket's protection  
> Native supports dynamic permissions  
> Native supports JWT, Basic Auth... Can extend custom supported authentication methods  
> [High performance due dictionary matching tree](#Why Is High Performance)  
> Sorry about google english.   

### Components of Repository:  
- [sureness's kernel code--sureness-core](core)  
- [10 Minute Tutorial's Program--sample-bootstrap](sample-bootstrap)  
- [30 Minute Tutorial's Program--sample-tom](sample-tom)  

## Quick Start 

### <font color="red">Some Conventions</font>  

- Based RBAC, but only has role-resource, no permission action    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belongs some Role -- Role owns Resource -- User can access the resource  

### Add sureness In Project  

1. When use maven build project, add maven coordinate  
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.7</version>
</dependency>
```
2. When use gradle build project, add gradle coordinate  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.7'
```
3. When not java build project, add sureness-core.jar to classPath  
```
download this jar at mvnrepository  
https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core
```

### Add an Interceptor Intercepting All Requests  

The interceptor can be a filter or a spring interceptor.  
The interceptor intercepts all request to check them.  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```


### Implement Exception Flow When Exception Throw  
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

sureness exception                              | exception note
---                                                             | ---
SurenessAuthenticationException     |  basic authenticated exception,Authentication related extend it
SurenessAuthorizationException       | basic authorized exception,Authorization related extend it
ProcessorNotFoundException            | authenticated,not found process support this subject
UnknownAccountException                | authenticated,unknown account
UnSupportedSubjectException           | authenticated,unSupport request
DisabledAccountException                  | authenticated,account disable
ExcessiveAttemptsException                | authenticated,excessive attempts
IncorrectCredentialsException             | authenticated, incorrect credential
ExpiredCredentialsException               | authenticated,expired credential
UnauthorizedException                        | authorized,no permission access this resource

Custom exception should extend SurenessAuthenticationException or SurenessAuthorizationException  

### Load Config DataSource   

Sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
The dataSource can load from txt, dataBase or no dataBase etc.
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.
Also, we provide default dataSource implement which load dataSource from txt(sureness.yml), user can defined their data in sureness.yml. 
eg:  
```
## -- sureness.yml txt dataSource-- ##

# load api resource which need be protected.
# eg: /api/v2/host===post===[role2,role3,role4] means /api/v2/host===post is be role2,role3,role4 supported access
# eg: /api/v1/getSource3===get===[] means /api/v1/getSource3===get is be all role or no role supported access
resourceRole:
  - /api/v2/host===post===[role2,role3,role4]
  - /api/v2/host===get===[role2,role3,role4]
  - /api/v2/host===delete===[role2,role3,role4]
  - /api/v2/host===put===[role2,role3,role4]
  - /api/mi/**===put===[role2,role3,role4]
  - /api/v1/getSource1===get===[role1,role2]
  - /api/v2/getSource2/*/*===get===[role2]
  - /api/v1/source1===get===[role2]
  - /api/v1/source1===post===[role1]
  - /api/v1/source1===delete===[role3]
  - /api/v1/source1===put===[role1,role2]
  - /api/v1/source2===get===[]

# load api resource wich do not need be proetcted, means them need be filtering.
# these api resource can be access by everyone
excludedResource:
  - /api/v3/host===get
  - /api/v3/book===get
  - /api/v1/account/auth===post

# account info
# there are three account: admin root tom
# eg: admin has [role1,role2] ROLE, encrypted password is 0192023A7BBD73250516F069DF18B500
# eg: root has no ROLE, no encrypted password is 23456
account:
  - appId: admin
    # if add salt, the password is encrypted password - the result MD5(password+salt)
    # if no salt, the password is no encrypted password
    credential: 0192023A7BBD73250516F069DF18B500
    salt: 123
    role: [role1,role2]
  - appId: root
    credential: 23456
    role: [role1]
  - appId: tom
    credential: 32113
    role: [role2]

```

If the configuration resource data comes from text, please refer to  [10 Minute Tutorial's Program--sample-bootstrap](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
If the configuration resource data comes from dataBase, please refer to  [30 Minute Tutorial's Program--sample-tom](https://github.com/tomsun28/sureness/tree/master/sample-tom)   

**Have Fun**      

## Advanced Use  

If know sureness [Process flow](#Process Flow), maybe know the extend point  

Sureness supports custom subject, custom subjectCreator, custom processor and more.  

Suggest look these interface before extending:  

- `Subject`:  Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: create subject interface, provider create method   
- `Processor`:  process subject interface, where happen authentication and authorization 
- `PathTreeProvider`: resource data provider, it can load data from txt or database,etc
- `SurenessAccountProvider`: account data provider, it can load data from txt or database,etc   


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

### Why Is High Performance  

![pathRoleMatcher](docs/_images/PathRoleMatcher.svg)  

### Process Flow  

![sureness-core](docs/_images/sureness-core.svg)  

### License  
[`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)