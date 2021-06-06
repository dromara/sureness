---
id: quickstart  
title: 快速开始    
sidebar_label: 快速开始    
---

#### 🐕 使用前一些约定    

- `Sureness`基于`RBAC`,即用户-角色-资源: 用户所属角色--角色拥有资源(API)--用户就能访问资源(API)
- 我们将`REST API`请求视作一个资源,资源格式为: `requestUri===httpMethod`  
  即请求的路径加上其请求方式(`post,get,put,delete...`)作为一个整体被视作资源来赋权配置  
  `eg: /api/v2/book===get` `get`方式请求`/api/v2/book`接口数据

资源路径匹配详见 [url路径匹配](/docs/start/path-match)   

#### 项目中加入Sureness

项目使用`maven`或`gradle`构建,加入坐标
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>1.0.3</version>
</dependency>
```
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '1.0.3'
```

#### 🐵 使用默认配置来配置Sureness
默认配置使用了文件数据源`sureness.yml`作为账户权限数据源  
默认配置支持了`JWT, Basic auth, Digest auth`认证
```
@Bean
public DefaultSurenessConfig surenessConfig() {
    return new DefaultSurenessConfig();
}
```

#### 配置权限账户数据源

`Sureness`认证鉴权，当然也需要我们提供自己的账户数据，角色权限数据等，这些数据可能来自文本，关系数据库，非关系数据库，注解等。  
我们提供了数据源接口：`SurenessAccountProvider`, `PathTreeProvider`，用户可以实现此接口实现自定义数据源。

当前我们也提供文本形式的数据源实现 `DocumentResourceDefaultProvider` 和 注解形式的资源权限数据源实现 `AnnotationLoader`。  
如果是使用了[默认sureness配置-DefaultSurenessConfig](#使用默认配置来配置sureness),其配置的是文本数据源，用户可以直接通过修改`sureness.yml`文件来配置数据。

文本数据源`sureness.yml`配置使用方式详见文档 [默认文本数据源](/docs/start/default-datasource)     
注解形式的资源权限数据源配置使用方式详见文档 [注解资源权限数据源](/docs/start/annotation-datasource)   

我们提供了使用代码`DEMO`：  
默认文本数据源具体实现，请参考[Sureness集成Spring Boot样例(配置文件方案)--sample-bootstrap](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
若权限配置数据来自数据库，请参考[Sureness集成Spring Boot样例(数据库方案)--sample-tom](https://github.com/tomsun28/sureness/tree/master/sample-tom)

#### 添加过滤器拦截所有请求

`Sureness`的本质就拦截所有`API`请求对其认证鉴权判断。  
入口拦截器器实现一般可以是 `filter or spring interceptor`  
在拦截器中加入`Sureness`的安全过滤器，如下:

```
SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

#### 实现认证鉴权相关异常处理流程

`Sureness`使用异常处理流程：
1. 若认证鉴权成功,`checkIn`会返回包含用户信息的`SubjectSum`对象
2. 若中间认证鉴权失败，`checkIn`会抛出不同类型的认证鉴权异常,用户需根据这些异常来继续后面的流程(返回相应的请求响应)

这里我们就需要对`checkIn`抛出的异常做自定义处理,认证鉴权成功直接通过,失败抛出特定异常进行处理,如下:

```
try {
    SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);
} catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {
    // 账户创建相关异常 
} catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {
    // 账户禁用相关异常
} catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {
    // 认证失败相关异常
} catch (UnauthorizedException e5) {
    // 鉴权失败相关异常
} catch (SurenessAuthenticationException | SurenessAuthorizationException e) {
    // 其他自定义异常
}
```

异常详见 [默认异常类型](/docs/start/default-exception)

**HAVE FUN**

> 如果这个[快速开始]对您不是很友好，可以参考下面一篇[一步一步搭建](/docs/start/step-by-step)，里面一步一步详细介绍了使用Sureness搭建一个完整功能认证鉴权项目的步骤。    