# Using Sureness to protect the security of micronaut REST API  

Using Sureness to secure micronaut REST API by providing authentication(JWT,Basic,Digest) and authorization(RBAC)  


## What You Will Learn

* Creating a simple REST API using micronaut
* Learn how to integrate Sureness into a micronaut application
* Test API authentication - use JWT Auth, Basic Auth, Digest Auth to test the security of the REST API
* Test API authorization - use different users to verify that they can access the REST API


The tutorial assumes that you know what  JWT, Basic Auth, Digest Auth, RBAC are. If you
do not, then you can check [jwt](https://jwt.io/introduction/), [basic auth](https://docs.oracle.com/cd/E50612_01/doc.11122/user_guide/content/authn_http_basic.html) , [digest auth](https://docs.oracle.com/cd/E50612_01/doc.11122/user_guide/content/authn_http_digest.html), [rbac](https://en.wikipedia.org/wiki/Role-based_access_control) for an introduction.

## Setting Up Dependencies

First, you will need to create a maven project and add micronautn, Sureness dependencies coordinate

````
        <dependency>
            <groupId>io.micronautn</groupId>
            <artifactId>micronautn</artifactId>
            <version>{{site.micronautionversion}}</version>
        </dependency>
        <dependency>
            <groupId>com.usthe.sureness</groupId>
            <artifactId>sureness-core</artifactId>
            <version>1.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.30</version>
        </dependency>
````


- [User Guide](https://docs.micronaut.io/2.4.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

We need to create a simple micronautn app and provide some  REST API for test.



## Setting Up Sureness

#### 1.Run Micronaut Application

```
  
import io.micronaut.runtime.Micronaut;


public class Application{

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);

    }
}

```

####  2. Config Document Datasource - `sureness.yml`

Sureness authentication requires us to provide our own account data, role permission data. These data may come from document, databases,, annotations, etc. When we use sureness default configuration above, the datasource is document - `sureness.yml`.

Create a file named `sureness.yml` in the `resource` directory. Configure account data, role permission data in the `sureness.yml`.  eg:

````yaml
## -- sureness.yml document dataSource-- ##

# load api resource which need be protected, config role who can access these resource.
# resources that are not configured are also authenticated and protected by default, but not authorized
# eg: /api/v2/host===post===[role2,role3] means /api/v2/host===post can be access by role2,role3
# eg: /api/v1/source2===get===[] means /api/v1/source2===get can not be access by any role
resourceRole:
  - /api/v1/source1===get===[role2]
  - /api/v1/source1===post===[role1]
  - /api/v1/source1===delete===[role3]
  - /api/v1/source1===put===[role1,role2]
  - /api/v1/source2===get===[]
  - /api/v2/host===post===[role2,role3]
  - /api/v2/host===get===[role2,role3]
  - /api/v2/host===delete===[role2,role3]
  - /api/v2/host===put===[role2,role3]
  - /api/v3/*===*===[role1,role2,role3]

# load api resource which do not need be protected, means them need be excluded.
# these api resource can be access by everyone
excludedResource:
  - /api/v3/host===get
  - /**/*.html===get
  - /**/*.js===get
  - /**/*.css===get
  - /**/*.ico===get

# account info
# there are three account: admin, root, tom
# eg: admin has [role1,role2] ROLE, unencrypted password is admin, encrypted password is 0192023A7BBD73250516F069DF18B500
# eg: root has role1, unencrypted password is 23456
# eg: tom has role3, unencrypted password is 32113
account:
  - appId: admin
    # if add salt, the password is encrypted password - the result: MD5(password+salt)
    # digest auth not support encrypted password
    # if no salt, the password is unencrypted password
    credential: 0192023A7BBD73250516F069DF18B500
    salt: 123
    role: [role1,role2]
  - appId: root
    credential: 23456
    role: [role1,role2]
  - appId: tom
    credential: 32113
    role: [role3]

````



####  3. Add an Interceptor Intercepting All Requests

The essence of sureness is to intercept all rest requests for authenticating and authorizing.     The interceptor can be a filter or interceptor, it intercepts all request to check them. In Micronaut, we use Filter
```java
@Filter("/**")
public class MicronautSurenessFilterExample  implements HttpServerFilter {

    private static final Logger logger = LoggerFactory.getLogger(MicronautSurenessFilterExample.class);

    @Inject
    private SurenessSecurityManager securityManager ;


    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                             ServerFilterChain chain) {
        Integer statusCode = null;
        String errorMsg = null;
        try {
            SubjectSum subject =securityManager.checkIn(request);
            if (subject != null) {
                SurenessContextHolder.bindSubject(subject);
            }
        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
            logger.debug("this request is illegal");
            statusCode = HttpStatus.BAD_REQUEST.getCode();
            errorMsg = e4.getMessage();
        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
            logger.debug("the account is disabled");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e2.getMessage();
        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
            logger.debug("this account credential is incorrect or expired");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e3.getMessage();
        } catch (UnauthorizedException e5) {
            logger.debug("this account can not access this resource");
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e5.getMessage();
        } catch (RuntimeException e) {
            logger.error("other exception happen: ", e);
            statusCode = HttpStatus.FORBIDDEN.getCode();
            errorMsg = e.getMessage();
        }finally {
            SurenessContextHolder.unbindSubject();
        }
        if (statusCode != null && errorMsg != null) {
            String finalErrorMsg = errorMsg;
            Integer finalStatusCode = statusCode;
            logger.info(statusCode+"--->"+errorMsg);
            try {
                URI location = new URI("/auth/error");
                request = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                    httpHeaders.add("errorMsg", finalErrorMsg);
                }).uri(location);
            }catch (URISyntaxException e){
                logger.error("uri error");
            }
        }
        return chain.proceed(request);
    }



    @Override
    public int getOrder() {
        return ServerFilterPhase.SECURITY.order();
    }


}
```
SurenessSecurityManager configuration
```java
@Singleton
public class SurenessConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SurenessConfiguration.class);

    @Singleton
    public SurenessSecurityManager init() {
        SurenessAccountProvider accountProvider = new DocumentAccountProvider();
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(accountProvider);
        processorList.add(passwordProcessor);
        DefaultProcessorManager processorManager = new DefaultProcessorManager(processorList);
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultProcessorManager init");
        }
        PathTreeProvider pathTreeProvider = new DocumentPathTreeProvider();
        DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
        pathRoleMatcher.setPathTreeProvider(pathTreeProvider);
        pathRoleMatcher.buildTree();
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultPathRoleMatcher init");
        }

        // SubjectFactory init
        SubjectFactory subjectFactory = new SurenessSubjectFactory();
        List<SubjectCreate> subjectCreates = Arrays.asList(
                new NoneSubjectReactiveCreator(),
                new BasicSubjectReactiveCreator());
        subjectFactory.registerSubjectCreator(subjectCreates);
        if (logger.isDebugEnabled()) {
            logger.debug("SurenessSubjectFactory init");
        }

        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        if (logger.isDebugEnabled()) {
            logger.debug("SurenessSecurityManager init");
        }
        return securityManager;
    }

}


```

#### 4. Last, Implement Auth Exception Handling Process

Sureness uses exception handling process:

- If auth success, method - `checkIn()` will return a `SubjectSum` object containing user information.
- If auth failure, method - `checkIn()` will throw different types of auth exceptions.

We need to continue the subsequent process based on these exceptions.(eg: return the request response)

Here we need to customize the exceptions thrown by `checkIn`, passed directly when auth success, catch exception when auth failure and do something:

````
// when auth error , add error msg to HttpRequest
   if (statusCode != null && errorMsg != null) {
            String finalErrorMsg = errorMsg;
            Integer finalStatusCode = statusCode;
            logger.info(statusCode+"--->"+errorMsg);
            try {
                URI location = new URI("/auth/error");
                request = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("statusCode", String.valueOf(finalStatusCode));
                    httpHeaders.add("errorMsg", finalErrorMsg);
                }).uri(location);
            }catch (URISyntaxException e){
                logger.error("uri error");
            }
        } 

````


**All done, we can test now!**

## Test

Through the above steps, a complete auth function project is completed. Someone maybe think that with only these few steps, where is its complete function and what can it support?   
This built project is based on the RBAC permission model and supports Baisc authentication, Digest authentication and JWT authentication. It can fine-grained control the user's access to the restful api provided by the Javalin. That is to control which users can access which api.

Let's test it. (we use postman and chrome to test.)

### Test Authentication

####  1. Basic Auth Test

Use postman Basic auth, as shown below:

* success - input username: admin, password: admin

![success](../../docs/_images/micronaut/success.png)


* fail - input username: admin, password: admin1234

![fail](../../docs/_images/micronaut/error.png)


## Conclusion

micronaut is a framework dedicated to simplicity and ease of use, and so is Sureness.  
We hope you enjoy this tutorial. Of course, the tutorial only introduces a simple introduction. Our account data, role permission data can not only be written in `sureness.yml`, but also loaded and obtained from the database and annotations. We can also customize the authentication method, data source, etc.   
Finally, thank you again for reading.

[DEMO SOURCE CODE ON GITHUB](https://github.com/usthe/sureness/tree/master/samples/javalin-sureness)