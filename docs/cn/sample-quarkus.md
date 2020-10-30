## Sample-quarkus-sureness  

[quarkus-sureness例子项目仓库地址](https://github.com/tomsun28/sureness/tree/master/samples/quarkus-sureness)    

- 基于`quarkus, jax-rs`
- 从默认的配置文件`sureness.yml`加载账户信息,资源角色,过滤资源等信息  
- 使用默认的`sureness-config`  
- 使用默认的`jwt,basic auth,digest auth`方式认证鉴权
- 例子中包含`restful api`  
- 保护入口: `SurenessFilterExample`  
- 推荐使用`postman`测试
