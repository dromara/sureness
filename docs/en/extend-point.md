## Advanced Use  

if know sureness Process flow, maybe know the extends point.  
sureness supports custom subject, custom subjectCreator, custom processor and more.  
suggest look these interface before extending:  

- `Subject`: Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: create subject interface, provider create method.    
- `Processor`:  process subject interface, where happen authentication and authorization.   
- `PathTreeProvider`: resource data provider, it can load data from txt or database,etc.  
- `SurenessAccountProvider`: account data provider, it can load data from txt or database,etc.     
