## Custom Datasource  

Before customization, it is recommended to understand the `sureness` process and the extended interface provided, see [Advanced Extension](extend-point.md) for details.  

First, let's get to know the two user information and resource permission information interfaces provided by Sureness. Users can customize these interfaces to provide data to Sureness from different data sources.  

- `PathTreeProvider`: Path resource provider interface, which can load data from database, text, etc., and load it into the resource permission matcher `DefaultPathRoleMatcher`.   
- `SurenessAccountProvider`: Account information provider interface, to load data from database, text, etc., and load it into the `processor` that needs account data.   

When we switched the project from the configuration file mode to the database mode, we simply replaced the implementation classes of these interfaces.  

1. `PathTreeProvider`  

````
public interface PathTreeProvider {

    Set<String> providePathData();

    Set<String> provideExcludedResource();
}

````  

This interface mainly needs to implement the above two methods.   
`ProvidePathData` is to load resource permission configuration information, which is the resourceRole information column of sureness.yml in our configuration file mode.  
`ProvideExcludedResource` is to load which resources can be filtered without authentication, that is, the excludedResource information column under sureness.yml, as follows.   

````
resourceRole:
  - /api/v2/host===post===[role2,role3,role4]
  - /api/v2/host===get===[role2,role3,role4]
  - /api/v2/host===delete===[role2,role3,role4]
  - /api/v2/host===put===[role2,role3,role4]
  - /api/mi/**===put===[role2,role3,role4]
  - /api/v1/getSource1===get===[role1,role2]
  - /api/v2/getSource2/*/*===get===[role2]

excludedResource:
  - /api/v1/source3===get
  - /api/v3/host===get
  - /**/*.css===get
  - /**/*.ico===get
  - /**/*.png===get
````

When we use the database mode, it is ok to realize that this information is read from the database association. The specification returns eg: `/api/v2/host===post===[role2,role3,role4]` format data column.  

Database implementation reference class - [DatabasePathTreeProvider](https://github.com/tomsun28/sureness/blob/master/sample-tom/src/main/java/com/usthe/sureness/sample/tom/sureness/provider/DatabasePathTreeProvider.java)  

2. `SurenessAccountProvider`  

````
public interface SurenessAccountProvider {
    SurenessAccount loadAccount(String appId);
}
````

This interface mainly needs to implement the above loadAccount method, and the user's account information can be found from the database or redis cache through the user's unique identification and returned.    
Default account information class `SurnessAccount` is as follows:   

````
public class DefaultAccount implements SurenessAccount {

    private String appId;
    private String password;
    private String salt;
    private List<String> ownRoles;
    private boolean disabledAccount;
    private boolean excessiveAttempts;
}
```` 

Database implementation reference class - [DatabaseAccountProvider](https://github.com/tomsun28/sureness/blob/master/sample-tom/src/main/java/com/usthe/sureness/sample/tom/sureness/provider/DatabaseAccountProvider.java)


Detail please refer to  [Sureness integration springboot sample(database scheme)](sample-tom.md)      
