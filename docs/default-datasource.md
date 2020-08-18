# 默认数据源   

`sureness`认证鉴权当然也需要我们自己的配置数据:账户数据，角色权限数据等  
这些配置数据可能来自文本，关系数据库，非关系数据库  
我们提供了配置数据接口`SurenessAccountProvider`, `PathTreeProvider`, 用户可以实现此接口实现自定义配置数据源  

当然我们也提供默认文本形式的配置数据实现 `DocumentResourceDefaultProvider`  
用户可以创建文件`sureness.yml`来配置数据,配置样例如下：  

```
## -- sureness.yml文本数据源 -- ##

# 加载到匹配字典的资源,也就是需要被保护的,设置了所支持角色访问的资源
# 没有配置的资源也默认被认证保护,但不鉴权
# eg: /api/v2/host===post===[role2,role3,role4] 表示 /api/v2/host===post 这条资源支持 role2,role3,role4这三种角色访问
# eg: /api/v1/getSource3===get===[] 表示 /api/v1/getSource3===get 这条资源支持所有角色或无角色访问
resourceRole:
  - /api/v2/host===post===[role2,role3,role4]
  - /api/v2/host===get===[role2,role3,role4]
  - /api/v2/host===delete===[role2,role3,role4]
  - /api/v2/host===put===[role2,role3,role4]
  - /api/mi/**===put===[role2,role3,role4]
  - /api/v1/getSource1===get===[role1,role2]
  - /api/v2/getSource2/*/*===get===[role2]
  - /api/v1/source1===get===[role2]
  - /api/v1/source1===post===[role1]
  - /api/v1/source1===delete===[role3]
  - /api/v1/source1===put===[role1,role2]
  - /api/v1/source2===get===[]

# 需要被过滤保护的资源,不认证鉴权直接访问
excludedResource:
  - /api/v3/host===get
  - /api/v3/book===get
  - /api/v1/account/auth===post

# 用户账户信息
# 下面有 admin root tom三个账户
# eg: admin 拥有[role1,role2]角色,加盐密码为0192023A7BBD73250516F069DF18B500
# eg: root 没有角色,密码为明文23456
account:
  - appId: admin
    # 如果填写了加密盐--salt,则credential为MD5(password+salt)的32位结果
    # 没有盐认为不加密,credential为明文
    credential: 0192023A7BBD73250516F069DF18B500
    salt: 123
    role: [role1,role2]
  - appId: root
    credential: 23456
    role: [role1]
  - appId: tom
    credential: 32113
    role: [role2]

```

我们提供了默认文本数据源使用`DEMO`，默认文本数据源具体实现，请参考[使用sureness10分钟搭建权限项目--sample-bootstrap](https://github.com/tomsun28/sureness/tree/master/sample-bootstrap)   
