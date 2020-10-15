## Quick Start 

##### <font color="red">Some Conventions</font>  

- Based RBAC, only has role-resource, no permission action    
- We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
  That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
  `eg: /api/v2/book===get`    
- User belongs some Role -- Role owns Resource -- User can access the resource  

Resource path matching see: [Uri Match](path-match.md)  

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

Detail sureness exception see: [Default Sureness Exception](default-exception.md)  

### Load Config DataSource   

Sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
The dataSource can load from txt, dataBase or no dataBase etc.
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.
Also, we provide default dataSource implement which load dataSource from txt(sureness.yml), user can defined their data in sureness.yml. 

Default Document DataSource Config - sureness.yml, see: [Default DataSource](default-datasource.md)  

If the configuration resource data comes from text, please refer to  [10 Minute Tutorial's Program--sample-bootstrap](sample-bootstrap.md)       
If the configuration resource data comes from dataBase, please refer to  [30 Minute Tutorial's Program--sample-tom](sample-tom.md)     

**Have Fun**          