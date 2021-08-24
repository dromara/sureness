(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[5057],{3905:function(e,t,n){"use strict";n.d(t,{Zo:function(){return p},kt:function(){return m}});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function o(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},i=Object.keys(e);for(r=0;r<i.length;r++)n=i[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(r=0;r<i.length;r++)n=i[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var u=r.createContext({}),l=function(e){var t=r.useContext(u),n=t;return e&&(n="function"==typeof e?e(t):o(o({},t),e)),n},p=function(e){var t=l(e.components);return r.createElement(u.Provider,{value:t},e.children)},s={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},d=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,u=e.parentName,p=c(e,["components","mdxType","originalType","parentName"]),d=l(n),m=a,f=d["".concat(u,".").concat(m)]||d[m]||s[m]||i;return n?r.createElement(f,o(o({ref:t},p),{},{components:n})):r.createElement(f,o({ref:t},p))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,o=new Array(i);o[0]=d;var c={};for(var u in t)hasOwnProperty.call(t,u)&&(c[u]=t[u]);c.originalType=e,c.mdxType="string"==typeof e?e:a,o[1]=c;for(var l=2;l<i;l++)o[l]=n[l];return r.createElement.apply(null,o)}return r.createElement.apply(null,n)}d.displayName="MDXCreateElement"},7542:function(e,t,n){"use strict";n.r(t),n.d(t,{frontMatter:function(){return o},metadata:function(){return c},toc:function(){return u},default:function(){return p}});var r=n(2122),a=n(9756),i=(n(7294),n(3905)),o={id:"default-exception",title:"Default Sureness Auth Exception",sidebar_label:"Default Auth Exception"},c={unversionedId:"start/default-exception",id:"start/default-exception",isDocsHomePage:!1,title:"Default Sureness Auth Exception",description:"sureness uses exception handling process:",source:"@site/docs/start/default-exception.md",sourceDirName:"start",slug:"/start/default-exception",permalink:"/sureness/docs/start/default-exception",editUrl:"https://github.com/dromara/sureness/edit/master/home/docs/start/default-exception.md",version:"current",sidebar_label:"Default Auth Exception",frontMatter:{id:"default-exception",title:"Default Sureness Auth Exception",sidebar_label:"Default Auth Exception"},sidebar:"docs",previous:{title:"Default support auth type",permalink:"/sureness/docs/start/default-auth"},next:{title:"Advanced Use",permalink:"/sureness/docs/advanced/extend-point"}},u=[],l={toc:u};function p(e){var t=e.components,n=(0,a.Z)(e,["components"]);return(0,i.kt)("wrapper",(0,r.Z)({},l,n,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("p",null,(0,i.kt)("inlineCode",{parentName:"p"},"sureness")," uses exception handling process:  "),(0,i.kt)("ol",null,(0,i.kt)("li",{parentName:"ol"},"If auth success, method - ",(0,i.kt)("inlineCode",{parentName:"li"},"checkIn")," will return a ",(0,i.kt)("inlineCode",{parentName:"li"},"SubjectSum")," object containing user information.  "),(0,i.kt)("li",{parentName:"ol"},"If auth failure, method - ",(0,i.kt)("inlineCode",{parentName:"li"},"checkIn")," will throw different types of auth exceptions,\nand users need to continue the subsequent process based on these exceptions.(like return the request response)  ")),(0,i.kt)("p",null,"Here we need to customize the exceptions thrown by ",(0,i.kt)("inlineCode",{parentName:"p"},"checkIn"),",\npassed directly when auth success, catch exception when auth failure and do something:   "),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre"},"        try {\n            SubjectSum subject = SurenessSecurityManager.getInstance().checkIn(servletRequest);\n        } catch (ProcessorNotFoundException | UnknownAccountException | UnsupportedSubjectException e4) {\n            // Create subject error related execption \n        } catch (DisabledAccountException | ExcessiveAttemptsException e2 ) {\n            // Account disable related exception\n        } catch (IncorrectCredentialsException | ExpiredCredentialsException e3) {\n            // Authentication failure related exception\n        } catch (UnauthorizedException e5) {\n            // Authorization failure related exception\n        } catch (RuntimeException e) {\n            // other sureness exception\n        }\n")),(0,i.kt)("table",null,(0,i.kt)("thead",{parentName:"table"},(0,i.kt)("tr",{parentName:"thead"},(0,i.kt)("th",{parentName:"tr",align:null},"sureness exception"),(0,i.kt)("th",{parentName:"tr",align:null},"exception note"))),(0,i.kt)("tbody",{parentName:"table"},(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"SurenessAuthenticationException"),(0,i.kt)("td",{parentName:"tr",align:null},"basic authenticated exception,Authentication related extend it")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"SurenessAuthorizationException"),(0,i.kt)("td",{parentName:"tr",align:null},"basic authorized exception,Authorization related extend it")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"ProcessorNotFoundException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,not found process support this subject")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"UnknownAccountException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,unknown account")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"UnSupportedSubjectException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,unSupport request")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"DisabledAccountException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,account disable")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"ExcessiveAttemptsException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,excessive attempts")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"IncorrectCredentialsException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated, incorrect credential")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"ExpiredCredentialsException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated,expired credential")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"NeedDigestInfoException"),(0,i.kt)("td",{parentName:"tr",align:null},"authenticated, getAuthenticate() return digest information to client")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:null},"UnauthorizedException"),(0,i.kt)("td",{parentName:"tr",align:null},"authorized,no permission access this resource")))),(0,i.kt)("p",null,"Custom exception should extend SurenessAuthenticationException or SurenessAuthorizationException"))}p.isMDXComponent=!0}}]);