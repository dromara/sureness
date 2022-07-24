"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[3048],{4137:(e,t,r)=>{r.d(t,{Zo:()=>u,kt:()=>m});var n=r(7294);function a(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function o(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function s(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?o(Object(r),!0).forEach((function(t){a(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):o(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function i(e,t){if(null==e)return{};var r,n,a=function(e,t){if(null==e)return{};var r,n,a={},o=Object.keys(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||(a[r]=e[r]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(n=0;n<o.length;n++)r=o[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(a[r]=e[r])}return a}var c=n.createContext({}),l=function(e){var t=n.useContext(c),r=t;return e&&(r="function"==typeof e?e(t):s(s({},t),e)),r},u=function(e){var t=l(e.components);return n.createElement(c.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},p=n.forwardRef((function(e,t){var r=e.components,a=e.mdxType,o=e.originalType,c=e.parentName,u=i(e,["components","mdxType","originalType","parentName"]),p=l(r),m=a,f=p["".concat(c,".").concat(m)]||p[m]||d[m]||o;return r?n.createElement(f,s(s({ref:t},u),{},{components:r})):n.createElement(f,s({ref:t},u))}));function m(e,t){var r=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=r.length,s=new Array(o);s[0]=p;var i={};for(var c in t)hasOwnProperty.call(t,c)&&(i[c]=t[c]);i.originalType=e,i.mdxType="string"==typeof e?e:a,s[1]=i;for(var l=2;l<o;l++)s[l]=r[l];return n.createElement.apply(null,s)}return n.createElement.apply(null,r)}p.displayName="MDXCreateElement"},2185:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>c,contentTitle:()=>s,default:()=>d,frontMatter:()=>o,metadata:()=>i,toc:()=>l});var n=r(7462),a=(r(7294),r(4137));const o={id:"custom-datasource",title:"Custom Datasource",sidebar_label:"Custom Datasource"},s=void 0,i={unversionedId:"advanced/custom-datasource",id:"advanced/custom-datasource",title:"Custom Datasource",description:"Before customization, it is recommended to understand the sureness's processor and the extended interface provided, see Advanced Extension for details.",source:"@site/docs/advanced/custom-datasource.md",sourceDirName:"advanced",slug:"/advanced/custom-datasource",permalink:"/docs/advanced/custom-datasource",draft:!1,editUrl:"https://github.com/dromara/sureness/edit/master/home/docs/advanced/custom-datasource.md",tags:[],version:"current",frontMatter:{id:"custom-datasource",title:"Custom Datasource",sidebar_label:"Custom Datasource"},sidebar:"docs",previous:{title:"Custom Processor",permalink:"/docs/advanced/custom-processor"},next:{title:"SpringBoot-Sureness Sample(file scheme)",permalink:"/docs/integrate/sample-bootstrap"}},c={},l=[],u={toc:l};function d(e){let{components:t,...r}=e;return(0,a.kt)("wrapper",(0,n.Z)({},u,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("p",null,"Before customization, it is recommended to understand the sureness's processor and the extended interface provided, see ",(0,a.kt)("a",{parentName:"p",href:"/docs/advanced/extend-point"},"Advanced Extension")," for details.  "),(0,a.kt)("p",null,"First, let's get to know the two user information and resource permission information interfaces provided by Sureness. Users can customize these interfaces to provide data to Sureness from different data sources.  "),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"PathTreeProvider"),": Path resource provider interface, which can load data from database, text, etc., and load it into the resource permission matcher ",(0,a.kt)("inlineCode",{parentName:"li"},"DefaultPathRoleMatcher"),".   "),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("inlineCode",{parentName:"li"},"SurenessAccountProvider"),": Account information provider interface, to load data from database, text, etc., and load it into the ",(0,a.kt)("inlineCode",{parentName:"li"},"processor")," that needs account data.   ")),(0,a.kt)("p",null,"When we switched the project from the configuration file mode to the database mode, we simply replaced the implementation classes of these interfaces.  "),(0,a.kt)("ol",null,(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("inlineCode",{parentName:"li"},"PathTreeProvider"),"  ")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre"},"public interface PathTreeProvider {\n\n    Set<String> providePathData();\n\n    Set<String> provideExcludedResource();\n}\n\n")),(0,a.kt)("p",null,"This interface mainly needs to implement the above two methods.",(0,a.kt)("br",{parentName:"p"}),"\n",(0,a.kt)("inlineCode",{parentName:"p"},"ProvidePathData")," is to load resource permission configuration information, which is the resourceRole information column of sureness.yml in our configuration file mode.",(0,a.kt)("br",{parentName:"p"}),"\n",(0,a.kt)("inlineCode",{parentName:"p"},"ProvideExcludedResource")," is to load which resources can be filtered without authentication, that is, the excludedResource information column under sureness.yml, as follows.   "),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre"},"resourceRole:\n  - /api/v2/host===post===[role2,role3,role4]\n  - /api/v2/host===get===[role2,role3,role4]\n  - /api/v2/host===delete===[role2,role3,role4]\n  - /api/v2/host===put===[role2,role3,role4]\n  - /api/mi/**===put===[role2,role3,role4]\n  - /api/v1/getSource1===get===[role1,role2]\n  - /api/v2/getSource2/*/*===get===[role2]\n\nexcludedResource:\n  - /api/v1/source3===get\n  - /api/v3/host===get\n  - /**/*.css===get\n  - /**/*.ico===get\n  - /**/*.png===get\n")),(0,a.kt)("p",null,"When we use the database mode, it is ok to realize that this information is read from the database association. The specification returns eg: ",(0,a.kt)("inlineCode",{parentName:"p"},"/api/v2/host===post===[role2,role3,role4]")," format data column.  "),(0,a.kt)("p",null,"Database implementation reference class - ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/tomsun28/sureness/blob/master/sample-tom/src/main/java/com/usthe/sureness/sample/tom/sureness/provider/DatabasePathTreeProvider.java"},"DatabasePathTreeProvider"),"  "),(0,a.kt)("ol",{start:2},(0,a.kt)("li",{parentName:"ol"},(0,a.kt)("inlineCode",{parentName:"li"},"SurenessAccountProvider"),"  ")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre"},"public interface SurenessAccountProvider {\n    SurenessAccount loadAccount(String appId);\n}\n")),(0,a.kt)("p",null,"This interface mainly needs to implement the above loadAccount method, and the user's account information can be found from the database or redis cache through the user's unique identification and returned.",(0,a.kt)("br",{parentName:"p"}),"\n","Default account information class ",(0,a.kt)("inlineCode",{parentName:"p"},"SurnessAccount")," is as follows:   "),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre"},"public class DefaultAccount implements SurenessAccount {\n\n    private String appId;\n    private String password;\n    private String salt;\n    private List<String> ownRoles;\n    private boolean disabledAccount;\n    private boolean excessiveAttempts;\n}\n")),(0,a.kt)("p",null,"Database implementation reference class - ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/tomsun28/sureness/blob/master/sample-tom/src/main/java/com/usthe/sureness/sample/tom/sureness/provider/DatabaseAccountProvider.java"},"DatabaseAccountProvider")),(0,a.kt)("p",null,"Detail please refer to  ",(0,a.kt)("a",{parentName:"p",href:"/docs/integrate/sample-tom"},"Sureness integration springboot sample(database scheme)")))}d.isMDXComponent=!0}}]);