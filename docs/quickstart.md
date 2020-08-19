## 快速开始  

### <font color="red">使用前一些约定</font>  

- `sureness`尽量简洁,基于`RBAC`,但只有(角色-资源)的映射,没有(权限)动作映射
- 我们将`restful api`请求视作一个资源,资源格式为: `requestUri===httpMethod`  
  即请求的路径加上其请求方式(`post,get,put,delete...`)作为一个整体被视作一个资源  
  `eg: /api/v2/book===get` `get`方式请求`/api/v2/book`接口数据     
- 角色资源映射: 用户所属角色--角色拥有资源--用户拥有资源(用户就能访问此`api`)   

资源路径匹配详见 [url路径匹配](path-match.md)  

### 项目中加入sureness  

1. 项目使用`maven`构建,加入`maven`坐标  
```
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.7</version>
</dependency>
```

2. 项目使用`gradle`构建,`gradle`坐标  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.7'
```

3. 项目为普通工程,加入`sureness-core.jar`依赖  

```
在 mvnrepository 下载jar  
https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core
```

### 添加拦截所有请求的过滤器入口  

入口拦截器器实现一般可以是 `filter or spring interceptor`  
在拦截器加入sureness的安全过滤器，如下:  
入口,一般放在拦截所有请求的`filter`:  
  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

### 实现相关异常处理  

`sureness`使用异常处理流程,我们需要对`checkIn`抛出的异常做自定义处理,  
安全过滤器,认证鉴权成功直接通过,失败抛出特定异常,捕获异常,如下: 

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
        } catch (RuntimeException e) {
            // 其他自定义异常
        }
```
异常详见 [sureness 异常](exception.md)

### 加载配置数据  

`sureness`认证鉴权当然也需要我们自己的配置数据:账户数据，角色权限数据等  
这些配置数据可能来自文本，关系数据库，非关系数据库  
我们提供了配置数据接口`SurenessAccountProvider`, `PathTreeProvider`, 用户可以实现此接口实现自定义配置数据源  
当前我们也提供默认文本形式的配置数据实现 `DocumentResourceDefaultProvider`, 用户可以配置`sureness.yml`来配置数据  

默认文本数据源配置详见 [默认数据源](default-datasource.md)  

我们提供了默认文本数据源使用`DEMO`，默认文本数据源具体实现，请参考[使用sureness10分钟搭建权限项目--sample-bootstrap](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
若权限配置数据来自数据库,请参考[使用sureness30分钟搭建权限项目--sample-tom](https://github.com/tomsun28/sureness/tree/master/sample-tom)  

**HAVE FUN**  