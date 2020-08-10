## Quick Start 

### <font color="red">Some Conventions</font>  

- Based RBAC, but only has role-resource, no permission action    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belongs some Role -- Role owns Resource -- User can access the resource  

### Add sureness in project  

1. when use maven build project, add maven coordinate  
```
<!-- https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core -->
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.6</version>
</dependency>
```
2. when use gradle build project, add gradle coordinate  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.6'
```
3. when not java build project, add sureness-core.jar to classPath  
```
download this jar at mvnrepository  
https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core
```

### Add an interceptor intercepting all requests  

the interceptor can be a filter or a spring interceptor.  
the interceptor intercepts all request to check them.  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```


### Implement exception flow when exception throw  
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

### Load config dataSource   

sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
the dataSource can load from txt, dataBase or no dataBase etc.
we provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.
also, we provide default dataSource implement which load dataSource from txt(sureness.yml), user can defined their data in sureness.yml. 
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