## Annotation Auth DataSource   

Sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
The dataSource can load from txt, dataBase, no dataBase or annotation etc.  
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.  
`SurenessAccountProvider` - Account datasource provider interface.   
`PathTreeProvider` - Resource uri-role datasource provider interface.  

The way of `sureness` to implement the annotation dataSource is not to judge the aop before calling the method, 
but to scan the data in the annotation as the auth dataSource when startup, 
which facilitates the unification of the process and the independence of the framework.    

Here is an introduction to show how to use annotation config auth data.

1. First of all, we need to configure the annotation dataSource as the auth dataSource in the sureness startup configuration.  

```
@Bean
TreePathRoleMatcher pathRoleMatcher() {
    // Instantiate the resource permission path matcher, which will match the required role information 
    // according to the requested path and existing resource permission data.
    DefaultPathRoleMatcher pathRoleMatcher = new DefaultPathRoleMatcher();
    // Instantiate the resource permission data loader - `AnnotationLoader`, 
    // which implements the PathTreeProvider interface.    
    AnnotationLoader annotationLoaderProvider = new AnnotationLoader();
    // Set the package path to be scanned by AnnotationLoader, which will scan the 
    // @RequiresRoles, @WithoutAuth annotations on all class methods under the package path to obtain data.
    annotationLoaderProvider.setScanPackages(Arrays.asList("com.usthe.sureness.sample.tom.controller"));
    // Set the AnnotationLoader dataSource as the sureness auth dataSource. 
    pathRoleMatcher.addPathTreeProvider(annotationLoaderProvider);
    pathRoleMatcher.buildTree();
    return pathRoleMatcher;
}
```

2. Use annotations in the provided interface methods, eg:  
```
@RequiresRoles(roles = {"role1", "role2"}, mapping = "/resource", method = "post")  
Means that the resource /resource===post requires role role1 or role2 to access
```
```
@WithoutAuth(mapping = "/resource/*", method = "put")  
Means that the resource /resource/*===put can be accessed by any request
```

3. Suggest    
Although the annotation auth data is more convenient for us to develop, it is hard-coded in the code and cannot 
dynamically modify the permission role configuration data. It is not very suitable for large projects.   
`sureness` provides the function of loading multiple data sources at the same time, that is, 
we can simultaneously use the annotation dataSource and database auth dataSource in the sureness configuration.   
For the permission configuration that is not frequently modified, we can configure it to annotations, 
and for other permission data that needs to be dynamically modified, we configure it in the database.   