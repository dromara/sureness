## Default Config DataSource     

Sureness need dataSource to authenticate and authorize, eg: role data, user data etc.  
The dataSource can load from txt, dataBase or no dataBase etc.
We provide interfaces `SurenessAccountProvider`, `PathTreeProvider` for user implement to load data from the dataSource where they want.
Also, we provide default dataSource implement which load dataSource from txt(sureness.yml), user can defined their data in sureness.yml. 
eg:  
```
## -- sureness.yml txt dataSource-- ##

# load api resource which need be protected.
# eg: /api/v2/host===post===[role2,role3,role4] means /api/v2/host===post is be role2,role3,role4 supported access
# eg: /api/v1/getSource3===get===[] means /api/v1/getSource3===get is be all role or no role supported access
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

# load api resource wich do not need be proetcted, means them need be filtering.
# these api resource can be access by everyone
excludedResource:
  - /api/v3/host===get
  - /api/v3/book===get
  - /api/v1/account/auth===post

# account info
# there are three account: admin root tom
# eg: admin has [role1,role2] ROLE, encrypted password is 0192023A7BBD73250516F069DF18B500
# eg: root has no ROLE, no encrypted password is 23456
account:
  - appId: admin
    # if add salt, the password is encrypted password - the result MD5(password+salt)
    # if no salt, the password is no encrypted password
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

If the configuration resource data comes from text, please refer to  [10 Minute Tutorial's Program--sample-bootstrap](en/sample-bootstrap.md)     
If the configuration resource data comes from dataBase, please refer to  [30 Minute Tutorial's Program--sample-tom](en/sample-tom.md)     
