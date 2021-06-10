---
id: sample-spring-webflux  
title: Spring-Webflux-Sureness Sample  
sidebar_label: Spring-Webflux-Sureness Sample  
---

[spring-webflux-sureness sample repository](https://github.com/tomsun28/sureness/tree/master/samples/spring-webflux-sureness)      

- Based on `spring-webflux`
- Custom subject creator (BasicSubjectReactiveCreator, JwtSubjectReactiveCreator, NoneSubjectReactiveCreator) adapt to ServerHttpRequest request body
- Load data from default dataSource - `sureness.yml`
- Use default `JWT, Basic Auth, Digest auth` to authenticate and authorize
- Contain `REST API`  
- Project's protected entrance is `SurenessFilterExample`  
- Suggest use postman to test
