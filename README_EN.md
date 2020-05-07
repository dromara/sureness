# `sureness`  

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)  

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

### <font color="red">Some Conventions</font>  

- Based RBAC, but only has role-resource, no permission action    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belong some Role -- Role owns Resource -- User can access the resource  

### Use  

`maven`  
```
<!-- https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core -->
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.6</version>
</dependency>
```

`gradle`  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.6'
```

Portal, always is a filter intercepting all requests:  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

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

custom exception should extend SurenessAuthenticationException or SurenessAuthorizationException  

If the configuration resource data comes from text, please refer to  [10 Minute Tutorial's Program--sample-bootstrap](sample-bootstrap)   
If the configuration resource data comes from database, please refer to  [30 Minute Tutorial's Program--sample-tom](sample-tom)   

Have Fun   


### Advanced Use  

if know sureness [Process flow](#Process flow), maybe know the extend point  

sureness support custom subject, custom subjectCreator, custom processor and more.  

suggest look these interface before extending:  

- `Subject`:  Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: create subject interface, provider create method   
- `Processor`:  process subject interface, where happen authentication and authorization 
- `PathTreeProvider`: resource data provider, it can load data from txt or database,etc
- `SurenessAccountProvider`: account data provider, it can load data from txt or database,etc   


1. **custom datasource**  

`implment PathTreeProvider, load in DefaultPathRoleMatcher`   
`implment SurenessAccountProvide, load in processor`  

2. **custom subject**  

`implment Subject, add custom subject content`  
`implment SubjectCreate to create custom subject`  
`implment Processor to support custom subject`    

3. **custom processor**  

`a subject also can support by different processor, so we can custom processor to support custom subject`
`implment Processor, set which subject can support and implment processing details`  

Detail please refer to  [30 Minute Tutorial's Program--sample-tom](sample-tom)   

### Why Is High Performance  

![pathRoleMatcher](/img/PathRoleMatcher.svg)  

### Process flow  

![sureness-core](/img/sureness-core.svg)  

### License  
[`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)