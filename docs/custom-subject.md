## Custom Subject

Subject contains the user information in our request. 
Sureness has built-in `PasswordSubject` based on account password, `JwtSubject` based on jwt, etc. 
Of course, we can customize the subject we need to expand our user information.    

Before customization, it is recommended to understand the sureness's process and the extended interface provided, see [Advanced Extension](extend-point.md) for details.

-`Subject`: Authentication and authorization object interface, providing access to the object's account info, requesting resources, roles and other information.  

Customizing the subject requires the following process:    

1. `Implment Subject, add custom subject content`  
2. `Implment SubjectCreate to create custom subject`, see [Custom SubjectCreator](custom-subject-creator.md)  
3. `Implment Processor to support custom subject`, see [Custom Processor](custom-processor.md)  

Detail please refer to  [Sureness integration springboot sample(database scheme)](sample-tom.md)      
