## Default Sureness Exception    
`sureness` uses the exception handling process, we need to customize the corresponding exceptions thrown by the authentication failure or unauthorized access in the authentication process of `checkIn`.  

`sureness`使用异常处理流程,我们需要对`checkIn`的认证鉴权流程中发生的认证失败或无权限访问等抛出的相应异常做自定义处理,  
If the authentication is successful, it will pass directly, if it fails, a specific exception will be thrown, and the exception will be caught, eg:  
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