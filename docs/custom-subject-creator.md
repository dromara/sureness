## custom subject creator    

Custom subject creator is our most frequently used extension. 
When the request body object is not a servlet or jax-rs standard api, we need to customize the subject creator, 
make it obtain the request information we need (request path, request method, authentication information, etc.) through the request object, thereby creating the corresponding subject.  

Suggest take a look at the interface sureness provided, detail [Advanced Use](extend-point.md)  

- `SubjectCreate`: Create a `Subject` interface and create different types of `Subject` objects according to the request content.   

Implement the `SubjectCreate` interface method, and create the corresponding `subject` according to different request objects.    

Detail please refer to [sample-spring-webflux-sureness](sample-spring-webflux.md)    