"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[9589],{4137:(e,t,n)=>{n.d(t,{Zo:()=>l,kt:()=>m});var r=n(7294);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function a(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function c(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?a(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):a(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,o=function(e,t){if(null==e)return{};var n,r,o={},a=Object.keys(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var i=r.createContext({}),u=function(e){var t=r.useContext(i),n=t;return e&&(n="function"==typeof e?e(t):c(c({},t),e)),n},l=function(e){var t=u(e.components);return r.createElement(i.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},p=r.forwardRef((function(e,t){var n=e.components,o=e.mdxType,a=e.originalType,i=e.parentName,l=s(e,["components","mdxType","originalType","parentName"]),p=u(n),m=o,b=p["".concat(i,".").concat(m)]||p[m]||d[m]||a;return n?r.createElement(b,c(c({ref:t},l),{},{components:n})):r.createElement(b,c({ref:t},l))}));function m(e,t){var n=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=n.length,c=new Array(a);c[0]=p;var s={};for(var i in t)hasOwnProperty.call(t,i)&&(s[i]=t[i]);s.originalType=e,s.mdxType="string"==typeof e?e:o,c[1]=s;for(var u=2;u<a;u++)c[u]=n[u];return r.createElement.apply(null,c)}return r.createElement.apply(null,n)}p.displayName="MDXCreateElement"},2368:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>i,contentTitle:()=>c,default:()=>d,frontMatter:()=>a,metadata:()=>s,toc:()=>u});var r=n(7462),o=(n(7294),n(4137));const a={id:"custom-subject",title:"Custom Subject",sidebar_label:"Custom Subject"},c=void 0,s={unversionedId:"advanced/custom-subject",id:"advanced/custom-subject",title:"Custom Subject",description:"Subject contains the user information in our request.",source:"@site/docs/advanced/custom-subject.md",sourceDirName:"advanced",slug:"/advanced/custom-subject",permalink:"/docs/advanced/custom-subject",draft:!1,editUrl:"https://github.com/dromara/sureness/edit/master/home/docs/advanced/custom-subject.md",tags:[],version:"current",frontMatter:{id:"custom-subject",title:"Custom Subject",sidebar_label:"Custom Subject"},sidebar:"docs",previous:{title:"Extend Point",permalink:"/docs/advanced/extend-point"},next:{title:"Custom Subject Creator",permalink:"/docs/advanced/custom-subject-creator"}},i={},u=[],l={toc:u};function d(e){let{components:t,...n}=e;return(0,o.kt)("wrapper",(0,r.Z)({},l,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("p",null,"Subject contains the user information in our request.\nSureness has built-in ",(0,o.kt)("inlineCode",{parentName:"p"},"PasswordSubject")," based on account password, ",(0,o.kt)("inlineCode",{parentName:"p"},"JwtSubject")," based on jwt, etc.\nOf course, we can customize the subject we need to expand our user information.    "),(0,o.kt)("p",null,"Before customization, it is recommended to understand the sureness's process and the extended interface provided, see ",(0,o.kt)("a",{parentName:"p",href:"/docs/advanced/extend-point"},"Advanced Extension")," for details."),(0,o.kt)("p",null,"-",(0,o.kt)("inlineCode",{parentName:"p"},"Subject"),": Authentication and authorization object interface, providing access to the object's account info, requesting resources, roles and other information.  "),(0,o.kt)("p",null,"Customizing the subject requires the following process:    "),(0,o.kt)("ol",null,(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("inlineCode",{parentName:"li"},"Implment Subject, add custom subject content"),"  "),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("inlineCode",{parentName:"li"},"Implment SubjectCreate to create custom subject"),", see ",(0,o.kt)("a",{parentName:"li",href:"/docs/advanced/custom-subject-creator"},"Custom SubjectCreator"),"  "),(0,o.kt)("li",{parentName:"ol"},(0,o.kt)("inlineCode",{parentName:"li"},"Implment Processor to support custom subject"),", see ",(0,o.kt)("a",{parentName:"li",href:"/docs/advanced/custom-processor"},"Custom Processor"),"  ")),(0,o.kt)("p",null,"Detail please refer to  ",(0,o.kt)("a",{parentName:"p",href:"/docs/integrate/sample-tom"},"Sureness integration springboot sample(database scheme)")))}d.isMDXComponent=!0}}]);