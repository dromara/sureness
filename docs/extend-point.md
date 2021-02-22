## Advanced Use  

Sureness supports custom subject, custom subjectCreator, custom processor and more.

Before advanced custom extension, let's first understand the general process of sureness:

```mermaid
graph TD
A(request in) --> B(s)
B(subjectCreate creates different key - subjects based on the request header content,every key can be tried once) --> C(s)
C(Different key authentication methods<differnet lock - processors> to process incoming key - subjects) --> D(s)
D(Once successful is successful and ends, failure means the next key lock attempt until the end of all attempts)
```

As in the above process, Subject is created by SubjectCreate according to the request body, and different authentication processors process the supported Subjects.

Sureness provides the following common interfaces as extension points:

- `Subject`: Authenticated authorized  user's account interface, provide the account's username,password, request resources, roles, etc.  
- `SubjectCreate`: Create subject interface, provider create method.    
- `Processor`: Process subject interface, where happen authentication and authorization.   
- `PathTreeProvider`: Resource data provider, it can load data from txt or database,etc.  
- `SurenessAccountProvider`: Account data provider, it can load data from txt or database,etc.     
