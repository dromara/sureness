## Default support auth type    

Now sureness supports JWT, Basic Auth, Digest Auth... also can extend custom supported authentication methods(by extend interface `Processor`,`Subject`,`SubjectCreate`).   

#### `bearer jwt`  
`jwt` - `json web token`, is a very popular cross-domain, stateless, security authentication solution. 
why we call `bearer jwt` here is because `jwt` is put into the `bearer token` in the http request header, eg: `Authorization: Bearer jsonWebTokenValue`  
eg: 
```
GET /api/v1/source1 HTTP/1.1
Host: localhost:8088
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNocjEEOwiAQRe8y65IwCBQ4hlvjotAhVqs1DBoT492l7F5e_vtfuNYFAliUPs3aCrIuCW1nFDHlUaBVqJOLJpkIA_ArtnHd7o0X5s43egim8qayy6lCQOOUd15JHIA-zy4OUo5dlG2lFp46KDjvR0fKhfgCIU8r0-8PAAD__w.f-3klWWDpEO3uDLlx2S53DV2cYernwVEDwcC6z1JexocbZoxRKmASTOuky1qMCxy_hV8-RbuMjDmI3ASa_FQOw
```  

we can use it in postman: add jwt in Bearer Token.  
![jwtPostmanUse](_images/jwtPostmanUse.png)  

#### `basic auth`  
`basic auth` - `Basic access authentication`, is a classic `http` basic authentication method.  
This authentication method encrypts the string `base64` composed of the account password and puts it in the `Authorization` of the request header, eg: `Authorization: Basic base64encode(username+":"+password)`  
eg:  
```
GET /api/v1/source1 HTTP/1.1
Host: localhost:8088
Content-Type: application/json
Authorization: Basic dG9tOjMyMTEz
```  

we can use it in postman: add username password in `Basic Auth` type's `Authorization`, postman would encrypt it automatically.  
![basicAuthPostmanUse](_images/basicAuthPostmanUse.png)  

#### `digest auth`  
`digest auth` - `Digest access authentication`, is a classic `http` digest authentication method. It is used to protect the transmitted password.   
The following is the authentication process flow of `digest auth`, which come from [internet](https://www.cnblogs.com/xiaoxiaotank/p/11078571.html)    
![digestFlow](_images/digestFlow.png)  

We can use it directly in the chrome browser: visit the url, enter the account password in the pop-up dialog box, 
and the chrome browser will automatically perform the authentication process.     
![digestAuthChromeUse](_images/digestAuthUse.png)    


#### other auth type   
Sureness currently supports these three mainly auth types for the time being, and will continue to expand other basic authentication methods in the future.
Of course, you can easily customize the authentication method, detail: [Custom Subject](custom-subject.md)    

We provide the demo to use default authentication method, detail please refer to  [10 Minute Tutorial's Program--sample-bootstrap](sample-bootstrap.md)     
Also we provide the demo to custom authentication method, detail please refer to  [30 Minute Tutorial's Program--sample-tom](sample-tom.md)     
    

