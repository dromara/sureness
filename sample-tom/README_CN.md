## sample-tom  

[English Documentation](README.md) 

sureness + springboot项目集成-数据库方案代码  

- 基于`springboot,jpa...`，[数据源sql](src/main/resources/db)  
- 自定义数据源,使用从数据库加载账户信息,资源角色,过滤资源等信息,这样便于动态调整(见`AccountProvider ResourceProvider`)        
- 除了使用了默认的`jwt,basic auth`方式认证鉴权,新增自定义认证鉴权(自定义`subject subjectCreator processor...`)
- 推荐使用`postman`测试,测试样例为`sample-tom-postman.json`,导入`postman`即可  

样例中包含2种自定义认证鉴权方式:  

1. 自定义了一个单独的subjectCreator 见 `CustomPasswdSubjectCreator`     
演示功能就是自定义的从不同地方获取请求体的账户密码，来创建默认的PasswordSubject，走默认的账户密码认证流程  

2. 自定义了一整套流程(包含subject subjectCreator processor) 见 `CustomTokenSubject CustomTokenSubjectCreator CustomTokenProcessor`  
演示功能就是自定义一个简单的token作为subject对象，对其自定义创建获取方式-creator和自定义认证鉴权处理流程-processor.  
此自定义流程也演示了一个简单的token刷新流程  