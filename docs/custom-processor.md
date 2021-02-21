## Custom Processor  

A subject also can support by different processors, so we can custom processor to support custom subject.  

The processor is the authentication and authentication processor for the requested user account information subject. We need to implement the BaseProcessor interface to implement our custom authentication and authentication method.  
Sureness has built-in PasswordProcessor that processes PasswordSubject based on account password authentication, and JwtProcessor that processes JwtSubject based on jwt authentication.  

Before customization, it is recommended to understand the sureness's process and the extended interface provided, see [Advanced Extension](extend-point.md) for details.

-`Processor`: `Subject` processing interface, according to Subject information, perform authentication  

```
public abstract class BaseProcessor implements Processor{

    public abstract boolean canSupportSubjectClass(Class<?> var);

    public abstract Subject authenticated (Subject var) throws SurenessAuthenticationException;

    public abstract void authorized(Subject var) throws SurenessAuthorizationException;
}

```

The above are some important interface methods of BaseProcessor. The custom processor requires us to implement these methods.  

-`canSupportSubjectClass` judges whether to support this subject class type of input, for example, JwtProcessor only supports JwtSubject, PasswordProcessor only supports PasswordSubject.  
-`authenticated` authenticates the subject, and performs the account authentication of the requesting user based on the incoming subject information and system information.  
-`authorized` authenticates the subject, and the authentication determines whether the user has the access right to access the api.  

Sureness uses an exception process model. The above authentication failures or authentication failures will throw different types of exceptions. The user captures and judges at the outermost point to implement the next process.  

Detail please refer to  [Sureness integration springboot sample(database scheme)](sample-tom.md)        
