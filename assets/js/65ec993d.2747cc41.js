(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[4882],{3905:function(e,t,r){"use strict";r.d(t,{Zo:function(){return p},kt:function(){return m}});var o=r(7294);function n(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function s(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,o)}return r}function a(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?s(Object(r),!0).forEach((function(t){n(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):s(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function c(e,t){if(null==e)return{};var r,o,n=function(e,t){if(null==e)return{};var r,o,n={},s=Object.keys(e);for(o=0;o<s.length;o++)r=s[o],t.indexOf(r)>=0||(n[r]=e[r]);return n}(e,t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);for(o=0;o<s.length;o++)r=s[o],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(n[r]=e[r])}return n}var u=o.createContext({}),i=function(e){var t=o.useContext(u),r=t;return e&&(r="function"==typeof e?e(t):a(a({},t),e)),r},p=function(e){var t=i(e.components);return o.createElement(u.Provider,{value:t},e.children)},l={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},d=o.forwardRef((function(e,t){var r=e.components,n=e.mdxType,s=e.originalType,u=e.parentName,p=c(e,["components","mdxType","originalType","parentName"]),d=i(r),m=n,f=d["".concat(u,".").concat(m)]||d[m]||l[m]||s;return r?o.createElement(f,a(a({ref:t},p),{},{components:r})):o.createElement(f,a({ref:t},p))}));function m(e,t){var r=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var s=r.length,a=new Array(s);a[0]=d;var c={};for(var u in t)hasOwnProperty.call(t,u)&&(c[u]=t[u]);c.originalType=e,c.mdxType="string"==typeof e?e:n,a[1]=c;for(var i=2;i<s;i++)a[i]=r[i];return o.createElement.apply(null,a)}return o.createElement.apply(null,r)}d.displayName="MDXCreateElement"},8086:function(e,t,r){"use strict";r.r(t),r.d(t,{frontMatter:function(){return a},metadata:function(){return c},toc:function(){return u},default:function(){return p}});var o=r(2122),n=r(9756),s=(r(7294),r(3905)),a={id:"custom-processor",title:"Custom Processor",sidebar_label:"Custom Processor"},c={unversionedId:"advanced/custom-processor",id:"advanced/custom-processor",isDocsHomePage:!1,title:"Custom Processor",description:"A subject also can support by different processors, so we can custom processor to support custom subject.",source:"@site/docs/advanced/custom-processor.md",sourceDirName:"advanced",slug:"/advanced/custom-processor",permalink:"/sureness/docs/advanced/custom-processor",editUrl:"https://github.com/dromara/sureness/edit/master/home/docs/advanced/custom-processor.md",version:"current",sidebar_label:"Custom Processor",frontMatter:{id:"custom-processor",title:"Custom Processor",sidebar_label:"Custom Processor"},sidebar:"docs",previous:{title:"Custom Subject Creator",permalink:"/sureness/docs/advanced/custom-subject-creator"},next:{title:"Custom Datasource",permalink:"/sureness/docs/advanced/custom-datasource"}},u=[],i={toc:u};function p(e){var t=e.components,r=(0,n.Z)(e,["components"]);return(0,s.kt)("wrapper",(0,o.Z)({},i,r,{components:t,mdxType:"MDXLayout"}),(0,s.kt)("p",null,"A subject also can support by different processors, so we can custom processor to support custom subject.  "),(0,s.kt)("p",null,"The processor is the authentication and authentication processor for the requested user account information subject. We need to implement the BaseProcessor interface to implement our custom authentication and authentication method.",(0,s.kt)("br",{parentName:"p"}),"\n","Sureness has built-in PasswordProcessor that processes PasswordSubject based on account password authentication, and JwtProcessor that processes JwtSubject based on jwt authentication.  "),(0,s.kt)("p",null,"Before customization, it is recommended to understand the sureness's process and the extended interface provided, see ",(0,s.kt)("a",{parentName:"p",href:"/docs/advanced/extend-point"},"Advanced Extension")," for details."),(0,s.kt)("p",null,"-",(0,s.kt)("inlineCode",{parentName:"p"},"Processor"),": ",(0,s.kt)("inlineCode",{parentName:"p"},"Subject")," processing interface, according to Subject information, perform authentication  "),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre"},"public abstract class BaseProcessor implements Processor{\n\n    public abstract boolean canSupportSubjectClass(Class<?> var);\n\n    public abstract Subject authenticated (Subject var) throws SurenessAuthenticationException;\n\n    public abstract void authorized(Subject var) throws SurenessAuthorizationException;\n}\n\n")),(0,s.kt)("p",null,"The above are some important interface methods of BaseProcessor. The custom processor requires us to implement these methods.  "),(0,s.kt)("p",null,"-",(0,s.kt)("inlineCode",{parentName:"p"},"canSupportSubjectClass")," judges whether to support this subject class type of input, for example, JwtProcessor only supports JwtSubject, PasswordProcessor only supports PasswordSubject.",(0,s.kt)("br",{parentName:"p"}),"\n","-",(0,s.kt)("inlineCode",{parentName:"p"},"authenticated")," authenticates the subject, and performs the account authentication of the requesting user based on the incoming subject information and system information.",(0,s.kt)("br",{parentName:"p"}),"\n","-",(0,s.kt)("inlineCode",{parentName:"p"},"authorized")," authenticates the subject, and the authentication determines whether the user has the access right to access the api.  "),(0,s.kt)("p",null,"Sureness uses an exception process model. The above authentication failures or authentication failures will throw different types of exceptions. The user captures and judges at the outermost point to implement the next process.  "),(0,s.kt)("p",null,"Detail please refer to  ",(0,s.kt)("a",{parentName:"p",href:"/docs/integrate/sample-tom"},"Sureness integration springboot sample(database scheme)")))}p.isMDXComponent=!0}}]);