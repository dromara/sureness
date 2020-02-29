# `sureness`  

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)  

## <font color="green">`Introduction`</font>

> `sureness` 是作者在使用`java`权限框架`shiro`之后,吸取其良好的设计加上一些自己想法实现的全新认证鉴权项目  
>  面对`restful api`的认证鉴权,基于`RABC`主要关注于对`restful api`的保护  
>  原生支持 `restful api`  
>  原生支持动态权限(权限配置的动态加载)  
>  原生支持 `jwt`, `Basic Auth` ... 可扩展自定义支持的认证方式  
>  基于改进的字典匹配树大大提高性能  
  
>`sureness`的低配置，易扩展，不耦合其他框架，能使开发者对自己的项目多场景快速安全的进行保护  

### 仓库的组成部分:  
- [sureness的核心代码--sureness-core](core)
- [使用sureness10分钟搭建权限项目--sample-bootstrap](sample-bootstrap)
- [使用sureness30分钟搭建权限项目--sample-tom,开发中](sample-tom)

### <font color="red">一些约定</font>  

- 基于`RABC`,但只有(角色-资源)的映射,没有(权限)动作
- 我们将`restful api`请求视作一个资源,资源格式为: `requestUri===httpMethod`  
  即请求的路径加上其请求方式(`post,get,put,delete...`)作为一个整体被视作一个资源  
  `eg: /api/v2/book===get` `get`方式请求`/api/v2/book`接口数据     
- 用户所属角色--角色拥有资源--用户拥有资源(用户就能访问此`api`)   
- `have fun`  

### 使用  

`maven`坐标  
```
<!-- https://mvnrepository.com/artifact/com.usthe.sureness/sureness-core -->
<dependency>
    <groupId>com.usthe.sureness</groupId>
    <artifactId>sureness-core</artifactId>
    <version>0.0.1.1</version>
</dependency>
```

`gradle`坐标  
```
compile group: 'com.usthe.sureness', name: 'sureness-core', version: '0.0.1.1'
```

若权限配置数据来自文本,请参考[使用sureness10分钟搭建权限项目--sample-bootstrap](sample-bootstrap)  

若权限配置数据来自数据库,请参考[使用sureness30分钟搭建权限项目--sample-tom,开发中](sample-tom)  


### 进阶扩展  



### 处理流程  

![sureness-core](/img/sureness-core.svg)  

### License  
`sureness is licensed under` [`Apache License, Version 2.0`](https://www.apache.org/licenses/LICENSE-2.0.html)