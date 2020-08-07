
## 仓库的组成部分:  
- [sureness的核心代码--sureness-core](https://github.com/tomsun28/sureness)  
- [使用sureness10分钟搭建权限项目--sample-bootstrap](https://github.com/tomsun28/sureness)  
- [使用sureness30分钟搭建权限项目--sample-tom](https://github.com/tomsun28/sureness)  

## <font color="red">一些约定</font>  

- 基于`RABC`,但只有(角色-资源)的映射,没有(权限)动作
- 我们将`restful api`请求视作一个资源,资源格式为: `requestUri===httpMethod`  
  即请求的路径加上其请求方式(`post,get,put,delete...`)作为一个整体被视作一个资源  
  `eg: /api/v2/book===get` `get`方式请求`/api/v2/book`接口数据     
- 用户所属角色--角色拥有资源--用户拥有资源(用户就能访问此`api`)   


## 使用  

`maven`坐标  
```
<!-- https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core -->
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.2.2</version>
</dependency>
```

`gradle`坐标  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.2.2'
```

入口,一般放在拦截所有请求的`filter`:  
```
SurenessSecurityManager.getInstance().checkIn(servletRequest)
```

认证鉴权成功直接通过,失败抛出特定异常,捕获异常: 

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

sureness异常                              | 异常描述
---                                       | ---
SurenessAuthenticationException           | 基础认证异常,认证相关的子异常应该继承此异常 
SurenessAuthorizationException            | 基础鉴权异常,鉴权相关的子异常应该继承此异常
ProcessorNotFoundException                | 认证异常,未找到支持此subject的processor
UnknownAccountException                   | 认证异常,不存在此账户
UnSupportedSubjectException               | 认证异常,不支持的请求,未创建出subject
DisabledAccountException                  | 认证异常,账户禁用
ExcessiveAttemptsException                | 认证异常,账户尝试认证次数过多
IncorrectCredentialsException             | 认证异常,密钥错误
ExpiredCredentialsException               | 认证异常,密钥认证过期
UnauthorizedException                     | 鉴权异常,没有权限访问此资源

自定义异常需要继承`SurenessAuthenticationException`或`SurenessAuthorizationException`才能被最外层捕获  


若权限配置数据来自文本,请参考[使用sureness10分钟搭建权限项目--sample-bootstrap](https://github.com/tomsun28/sureness)  

若权限配置数据来自数据库,请参考[使用sureness30分钟搭建权限项目--sample-tom](https://github.com/tomsun28/sureness)  

HAVE FUN