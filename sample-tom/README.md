## sample-tom  

[中文文档](README_CN.md) 

[sureness 30 Minute Tutorial](https://github.com/tomsun28/sureness/tree/master/sample-tom)  

- based on `springboot`, [db_sql](src/main/resources/db)    
- load data from mysql dataBase dataSource, then user can modify their access control data dynamically(see `AccountProvider ResourceProvider`)   
- apart from having default `jwt,basic auth`, it adds custom `subject subjectCreator processor` to define new auth type(custom `subject subjectCreator processor...`).
- project's protected entrance is `SurenessFilterExample`
- suggest use postman to test, test case is in `sample-tom-postman.json`, user can load it in postman  

The sample-tom contains two custom authentication methods  

1. Customize a separate subjectCreator, see `CustomPasswdSubjectCreator`  
The demo function is to customize the account password of the request body from different places to create the default PasswordSubject and follow the default account password authentication process.  

2. Customize a whole set of processes (including subject subjectCreator processor), see `CustomTokenSubject CustomTokenSubjectCreator CustomTokenProcessor`  
The demo function is to customize a simple token as the subject object, and customize its creation and acquisition method-creator and custom authentication processing flow-processor.  
This custom process also demonstrates a simple token refresh process.  