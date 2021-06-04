---
id: quickstart  
title: Quick Start  
sidebar_label: Quick Start  
---

### 🐕 Some Conventions

- Based RBAC, User-Role-Resource.
- We treat API requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`
- User belongs some Role -- Role owns Resource -- User can access the resource.

Resource path matching see: [URI Match](path-match)    

### Add sureness In Your Project  

When use maven or gradle build project, add coordinate  
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>1.0.3</version>
</dependency>
```
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.3'
```

### 🐵 Use the Default Configuration to Configure Sureness

The default configuration -`DefaultSurenessConfig` uses the document datasource `sureness.yml` as the auth datasource.  
It supports JWT auth, Basic auth, Digest authentication.
```
@Bean
public DefaultSurenessConfig surenessConfig() {
    return new DefaultSurenessConfig();
}
```

### Load Auth Config DataSource   

Sureness authentication requires us to provide our own account data, role permission data, etc. These data may come from text, relational databases, non-relational databases, annotations, etc.   
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.

- `SurenessAccountProvider` - Account datasource provider interface.
- `PathTreeProvider` - Resource uri-role datasource provider interface.

Default Document DataSource Config - `sureness.yml`, see: [Default Document DataSource](default-datasource)   
Annotation DataSource Config Detail - `AnnotationLoader`, see: [Annotation DataSource](annotation-datasource)

If the configuration resource data comes from text, please refer to  [Sureness integration Spring Boot sample(configuration file scheme)](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
If the configuration resource data comes from dataBase, please refer to  [Sureness integration Spring Boot sample(database scheme)](https://github.com/tomsun28/sureness/tree/master/sample-tom)

### Add an Interceptor Intercepting All Requests  

The essence of Sureness is to intercept all rest requests for authenticating and authorizing.        
The interceptor can be a filter or a Spring interceptor, it intercepts all request to check them.
```
SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

### Implement Auth Exception Handling Process    

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

Detail see: [Default Sureness Auth Exception](default-exception)   

**Have Fun**      
 