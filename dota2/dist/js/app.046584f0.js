(function(e){function t(t){for(var a,r,u=t[0],i=t[1],s=t[2],l=0,d=[];l<u.length;l++)r=u[l],Object.prototype.hasOwnProperty.call(o,r)&&o[r]&&d.push(o[r][0]),o[r]=0;for(a in i)Object.prototype.hasOwnProperty.call(i,a)&&(e[a]=i[a]);f&&f(t);while(d.length)d.shift()();return c.push.apply(c,s||[]),n()}function n(){for(var e,t=0;t<c.length;t++){for(var n=c[t],a=!0,r=1;r<n.length;r++){var u=n[r];0!==o[u]&&(a=!1)}a&&(c.splice(t--,1),e=i(i.s=n[0]))}return e}var a={},r={app:0},o={app:0},c=[];function u(e){return i.p+"js/"+({about:"about"}[e]||e)+"."+{about:"4b8bf898","chunk-2d0d622e":"c1b48078","chunk-2d22ba1c":"ffe541a3","chunk-b098e4ec":"23b1deff","chunk-0f8da04e":"e6278257","chunk-43bf025a":"7545ea4e","chunk-cd2d25e2":"2fed70d5","chunk-4cde4dba":"4318bfb1","chunk-3a7b3b26":"61ea31aa"}[e]+".js"}function i(t){if(a[t])return a[t].exports;var n=a[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,i),n.l=!0,n.exports}i.e=function(e){var t=[],n={"chunk-b098e4ec":1,"chunk-0f8da04e":1,"chunk-cd2d25e2":1,"chunk-4cde4dba":1,"chunk-3a7b3b26":1};r[e]?t.push(r[e]):0!==r[e]&&n[e]&&t.push(r[e]=new Promise((function(t,n){for(var a="css/"+({about:"about"}[e]||e)+"."+{about:"31d6cfe0","chunk-2d0d622e":"31d6cfe0","chunk-2d22ba1c":"31d6cfe0","chunk-b098e4ec":"d7a66cbb","chunk-0f8da04e":"98346f44","chunk-43bf025a":"31d6cfe0","chunk-cd2d25e2":"cca15e4a","chunk-4cde4dba":"41e1cbbe","chunk-3a7b3b26":"c46820ed"}[e]+".css",o=i.p+a,c=document.getElementsByTagName("link"),u=0;u<c.length;u++){var s=c[u],l=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(l===a||l===o))return t()}var d=document.getElementsByTagName("style");for(u=0;u<d.length;u++){s=d[u],l=s.getAttribute("data-href");if(l===a||l===o)return t()}var f=document.createElement("link");f.rel="stylesheet",f.type="text/css",f.onload=t,f.onerror=function(t){var a=t&&t.target&&t.target.src||o,c=new Error("Loading CSS chunk "+e+" failed.\n("+a+")");c.code="CSS_CHUNK_LOAD_FAILED",c.request=a,delete r[e],f.parentNode.removeChild(f),n(c)},f.href=o;var h=document.getElementsByTagName("head")[0];h.appendChild(f)})).then((function(){r[e]=0})));var a=o[e];if(0!==a)if(a)t.push(a[2]);else{var c=new Promise((function(t,n){a=o[e]=[t,n]}));t.push(a[2]=c);var s,l=document.createElement("script");l.charset="utf-8",l.timeout=120,i.nc&&l.setAttribute("nonce",i.nc),l.src=u(e);var d=new Error;s=function(t){l.onerror=l.onload=null,clearTimeout(f);var n=o[e];if(0!==n){if(n){var a=t&&("load"===t.type?"missing":t.type),r=t&&t.target&&t.target.src;d.message="Loading chunk "+e+" failed.\n("+a+": "+r+")",d.name="ChunkLoadError",d.type=a,d.request=r,n[1](d)}o[e]=void 0}};var f=setTimeout((function(){s({type:"timeout",target:l})}),12e4);l.onerror=l.onload=s,document.head.appendChild(l)}return Promise.all(t)},i.m=e,i.c=a,i.d=function(e,t,n){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(i.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)i.d(n,a,function(t){return e[t]}.bind(null,a));return n},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="/",i.oe=function(e){throw console.error(e),e};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var d=0;d<s.length;d++)t(s[d]);var f=l;c.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";var a=n("85ec"),r=n.n(a);r.a},"56d7":function(e,t,n){"use strict";n.r(t);n("b6e5");var a=n("55f1"),r=(n("d2a2"),n("98c5")),o=(n("e1f5"),n("5efb")),c=(n("e260"),n("e6cf"),n("cca6"),n("a79d"),n("2b0e")),u=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-layout",{staticClass:"layout",attrs:{id:"components-layout-demo-top"}},[n("a-layout-header",[n("div",{staticClass:"logo"}),n("a-menu",{style:{lineHeight:"64px"},attrs:{theme:"dark",mode:"horizontal",defaultSelectedKeys:[e.route_map[this.$router.currentRoute.path]],selectedKeys:[e.route_map[this.$router.currentRoute.path]]}},[n("a-menu-item",{key:"1"},[n("router-link",{attrs:{to:"/"}},[e._v("项目介绍")])],1),n("a-menu-item",{key:"2"},[n("router-link",{attrs:{to:"/recommend"}},[e._v("阵容推荐")])],1),n("a-menu-item",{key:"3"},[n("router-link",{attrs:{to:"/rank"}},[e._v("排行榜")])],1),n("a-menu-item",{key:"4"},[n("router-link",{attrs:{to:"/history"}},[e._v("历史查询")])],1),n("a-menu-item",{key:"5"},[n("router-link",{attrs:{to:"/openApi"}},[e._v("开放平台")])],1),n("a-menu-item",{key:"6"},[n("router-link",{attrs:{to:"/dataManage"}},[e._v("数据管理平台")])],1)],1)],1),n("a-layout-content",{staticStyle:{padding:"0 0px"}},[n("div",{style:{background:"#fff",padding:"24px",minHeight:"280px"}},[n("router-view")],1)]),n("a-layout-footer",{staticStyle:{"text-align":"center"}},[e._v("Dota2阵容推荐系统 ©2020 Created by Wang Man Rong ")])],1)},i=[],s={data:function(){return{route_map:{"/":"1","/recommend":"2","/rank":"3","/history":"4","/openApi":"5","/dataManage":"6"}}},mounted:function(){console.log(this.$router.currentRoute.path)}},l=s,d=(n("034f"),n("2877")),f=Object(d["a"])(l,u,i,!1,null,null,null),h=f.exports,p=(n("d3b7"),n("8c4f")),m=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"home",style:e.home},[n("div",{staticClass:"welcome"}),n("ul",[e._m(0),n("li",[n("a-carousel",{attrs:{arrows:"",autoplay:""}},[n("div",{staticClass:"custom-slick-arrow",staticStyle:{left:"10px",zIndex:"1"},attrs:{slot:"prevArrow"},slot:"prevArrow"},[n("a-icon",{attrs:{type:"left-circle"}})],1),n("div",{staticClass:"custom-slick-arrow",staticStyle:{right:"10px"},attrs:{slot:"nextArrow"},slot:"nextArrow"},[n("a-icon",{attrs:{type:"right-circle"}})],1),n("div",{staticClass:"car"},[n("h3",[e._v("阵容推荐:")]),n("p",[e._v(" 通过对数十万场的5V5对战中的胜利方的阵容的分析,通过 "),n("code",[e._v("Apriori")]),e._v("算法, "),n("br"),e._v("挖掘对阵中的频繁集,然后生成关联规则,根据用户输入的已有阵容,推荐出最佳的5个英雄. ")]),n("br")]),n("div",{staticClass:"car"},[n("h3",[e._v("排行榜:")]),n("p",[e._v(" 输出制定日期，指定topK的查询排行榜 ")]),n("br")]),n("div",{staticClass:"car"},[n("h3",[e._v("历史查询:")]),n("p",[e._v(" 查询历史推荐的阵容，可通过时间进行筛选输出。 ")]),n("br")]),n("div",{staticClass:"car"},[n("h3",[e._v("开放平台:")]),n("p",[e._v(" 提供推荐系统使用的API接口的开放平台 ")]),n("br")]),n("div",{staticClass:"car"},[n("h3",[e._v("数据管理平台:")]),n("p",[e._v(" 提供推荐系统使用的后端数据管理平台 ")]),n("br")])])],1)])])},b=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("li",{staticStyle:{"font-size":"1.3em","font-style":"oblique 40deg"}},[n("p",[e._v("Dota2内置的基于大数据分析的阵容推荐的网页版")])])}],v=(n("805a"),n("0c63")),k=(n("05db"),n("fa07")),y={name:"home",components:{ACarousel:k["a"],AIcon:v["a"]},data:function(){return{home:{}}}},g=y,_=(n("6780"),Object(d["a"])(g,m,b,!1,null,"08854a9e",null)),w=_.exports;c["a"].use(p["a"]);var C=[{path:"/",name:"home",component:w,meta:{title:"Dota2阵容推荐首页"}},{path:"/about",name:"about",component:function(){return n.e("about").then(n.bind(null,"f820"))}},{path:"/recommend",name:"recommend",component:function(){return Promise.all([n.e("chunk-b098e4ec"),n.e("chunk-cd2d25e2"),n.e("chunk-3a7b3b26")]).then(n.bind(null,"4802"))},meta:{title:"Dota2阵容推荐"}},{path:"/history",name:"history",component:function(){return Promise.all([n.e("chunk-b098e4ec"),n.e("chunk-0f8da04e"),n.e("chunk-43bf025a")]).then(n.bind(null,"e4bb"))},meta:{title:"Dota2阵容推荐历史查询"}},{path:"/openApi",name:"openApi",component:function(){return n.e("chunk-2d22ba1c").then(n.bind(null,"f095"))},meta:{title:"Dota2阵容推荐开放平台"}},{path:"/dataManage",name:"dataManage",component:function(){return n.e("chunk-2d0d622e").then(n.bind(null,"70ed"))},meta:{title:"Dota2阵容推荐数据管理平台"}},{path:"/rank",name:"rank",component:function(){return Promise.all([n.e("chunk-b098e4ec"),n.e("chunk-0f8da04e"),n.e("chunk-cd2d25e2"),n.e("chunk-4cde4dba")]).then(n.bind(null,"789b"))},meta:{title:"Dota2阵容推荐排行榜"}}],x=new p["a"]({mode:"history",base:"/",routes:C}),A="Dota2阵容推荐";x.beforeEach((function(e,t,n){document.title=e.meta.title?e.meta.title:A,n()}));var S=x,O=n("2f62");c["a"].use(O["a"]);var P=new O["a"].Store({state:{},mutations:{},actions:{},modules:{}});c["a"].config.productionTip=!1,c["a"].use(o["a"]),c["a"].use(r["a"]),c["a"].use(a["a"]),new c["a"]({router:S,store:P,render:function(e){return e(h)}}).$mount("#app")},6780:function(e,t,n){"use strict";var a=n("8477"),r=n.n(a);r.a},8477:function(e,t,n){},"85ec":function(e,t,n){}});
//# sourceMappingURL=app.046584f0.js.map