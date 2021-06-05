---
id: custom-subject-creator  
title: Custom Subject Creator  
sidebar_label: Custom Subject Creator  
---


Custom subject creator is our most frequently used extension. 
When the request body object is not a servlet or jax-rs standard api, we need to customize the subject creator, 
make it obtain the request information we need (request path, request method, authentication information, etc.) through the request object, thereby creating the corresponding subject.  

Sureness has built-in `BasicSubjectServletCreator` that can create `PasswordSubject` based on account password,
and create the `JwtSubjectServletCreator` of the jwt type `JwtSubject`, etc.    
Of course, we can customize the subjectCreator we need to create the subject.  

Before customization, it is recommended to understand the sureness's process and the extended interface provided, see [Advanced Extension](/docs/advanced/extend-point) for details.

- `SubjectCreate`: Create a `Subject` interface and create different types of `Subject` objects according to the request content.   

Implement the `SubjectCreate` interface method, and create the corresponding `subject` according to different request objects.    

Detail please refer to [sample-spring-webflux-sureness](/docs/integrate/sample-spring-webflux)    