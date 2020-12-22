## Default Sureness Auth Exception    
`sureness` uses exception handling process:  
1. If auth success, method - `checkIn` will return a `SubjectSum` object containing user information.  
2. If auth failure, method - `checkIn` will throw different types of auth exceptions, 
and users need to continue the subsequent process based on these exceptions.(like return the request response)  

Here we need to customize the exceptions thrown by `checkIn`, 
passed directly when auth success, catch exception when auth failure and do something:   

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

sureness exception                     | exception note
---                                    | ---
SurenessAuthenticationException        |  basic authenticated exception,Authentication related extend it
SurenessAuthorizationException         | basic authorized exception,Authorization related extend it
ProcessorNotFoundException             | authenticated,not found process support this subject
UnknownAccountException                | authenticated,unknown account
UnSupportedSubjectException            | authenticated,unSupport request
DisabledAccountException               | authenticated,account disable
ExcessiveAttemptsException             | authenticated,excessive attempts
IncorrectCredentialsException          | authenticated, incorrect credential
ExpiredCredentialsException            | authenticated,expired credential
NeedDigestInfoException                | authenticated, getAuthenticate() return digest information to client
UnauthorizedException                  | authorized,no permission access this resource

Custom exception should extend SurenessAuthenticationException or SurenessAuthorizationException  