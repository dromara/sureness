## URI Path Match    
We treat restful requests as a resource, resource format like `requestUri===httpMethod`.   
That is the request uri + request method(`post,get,put,delete...`) is considered as a resource as a whole.  
`eg: /api/v2/book===get`    
The `requestUri` here support url path match: `str*str`, `*`, `**`     

 Wildcard                   | Describe              
 ---                        | ---               
 `str*str`                    | the * in string match 0 or more characters  
 `*`                          | Match 0 or 1 directories   
 `**`                         | Match 0 or more directories    


 Sample                      | Note  
 ---                       | ---
 `*.html`                    | can match `content.html`, `user-ui.html` etc    
 `/api/*/book`               | can match `/api/user/book` or `/api/book` etc  
 `/**`                       | can match any path  
 `/**/foo`                   | can match `/api/user/book/foo` etc    
 

Match priority: Raw string > `*.html` > `*` > `**`  
Longest path matching principle：  
eg: when `requestUri` is `/app/book/foo`，If there are two matching patterns - `/app/**` and `/app/book/*`，will match`/app/book/*`  