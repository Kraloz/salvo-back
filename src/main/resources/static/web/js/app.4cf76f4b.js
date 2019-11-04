(function(e){function t(t){for(var n,c,i=t[0],s=t[1],l=t[2],u=0,m=[];u<i.length;u++)c=i[u],Object.prototype.hasOwnProperty.call(r,c)&&r[c]&&m.push(r[c][0]),r[c]=0;for(n in s)Object.prototype.hasOwnProperty.call(s,n)&&(e[n]=s[n]);d&&d(t);while(m.length)m.shift()();return o.push.apply(o,l||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],n=!0,i=1;i<a.length;i++){var s=a[i];0!==r[s]&&(n=!1)}n&&(o.splice(t--,1),e=c(c.s=a[0]))}return e}var n={},r={app:0},o=[];function c(t){if(n[t])return n[t].exports;var a=n[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,c),a.l=!0,a.exports}c.m=e,c.c=n,c.d=function(e,t,a){c.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},c.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},c.t=function(e,t){if(1&t&&(e=c(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(c.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var n in e)c.d(a,n,function(t){return e[t]}.bind(null,n));return a},c.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return c.d(t,"a",t),t},c.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},c.p="/web/";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],s=i.push.bind(i);i.push=t,i=i.slice();for(var l=0;l<i.length;l++)t(i[l]);var d=s;o.push([0,"chunk-vendors"]),a()})({0:function(e,t,a){e.exports=a("56d7")},"034f":function(e,t,a){"use strict";var n=a("85ec"),r=a.n(n);r.a},"1e55":function(e,t,a){"use strict";var n=a("a49c"),r=a.n(n);r.a},"3a5f":function(e,t,a){"use strict";var n=a("3d66"),r=a.n(n);r.a},"3d66":function(e,t,a){},"56d7":function(e,t,a){"use strict";a.r(t);a("e260"),a("e6cf"),a("cca6"),a("a79d");var n=a("2b0e"),r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("router-view")],1)},o=[],c=(a("034f"),a("2877")),i={},s=Object(c["a"])(i,r,o,!1,null,null,null),l=s.exports,d=a("8c4f"),u=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"h-full flex justify-center items-center"},[e.$store.state.gameViews?e._e():a("login-form"),e.$store.state.gameViews?a("game-picker",{attrs:{games:e.$store.state.gameViews}}):e._e()],1)},m=[],p=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"w-full max-w-xs my-auto mx-auto"},[a("form",{staticClass:"bg-white shadow-xl rounded px-8 pt-6 pb-8"},[a("div",{staticClass:"mb-4"},[a("label",{staticClass:"block left-align text-gray-700 text-sm font-bold mb-4",attrs:{for:"username"}},[e._v(" NickName ")]),a("input",{directives:[{name:"model",rawName:"v-model",value:e.nickName,expression:"nickName"}],staticClass:"shadow border-gray-400 appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline",attrs:{id:"username",type:"text",placeholder:"Enter your nickname"},domProps:{value:e.nickName},on:{input:function(t){t.target.composing||(e.nickName=t.target.value)}}})]),a("div",{staticClass:"flex items-center justify-end"},[a("VueLoadingButton",{staticClass:"button",attrs:{loading:e.isLoading,styled:!1,type:"button","aria-label":"Login"},nativeOn:{click:function(t){return e.login(t)}}},[e._v("Go!")])],1)])])},f=[],h=a("0a4b"),g={name:"LoginForm",components:{VueLoadingButton:h["a"]},data:function(){return{nickName:"",isLoading:!1}},methods:{login:function(){var e=this;this.isLoading=!0,setTimeout((function(){e.$store.dispatch("setNickname",e.nickName),e.$store.dispatch("fetchGameViews").then((function(){e.isLoading=!1}))}),1e3)}}},y=g,v=Object(c["a"])(y,p,f,!1,null,null,null),x=v.exports,b=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:" w-full max-w-3xl my-auto mx-auto"},[a("div",{staticClass:"bg-white flex flex-col shadow-xl rounded px-8 pt-6 pb-8"},[a("button",{staticClass:"button self-start",attrs:{type:"button"},on:{click:function(t){return e.$store.dispatch("logOut")}}},[e._v("Logout ")]),e._v(" Select your game: "),e._l(e.games,(function(t,n){return a("game-card",{key:n,attrs:{created:t.created,gamePlayers:t.gamePlayers},nativeOn:{click:function(a){return e.setCurrentGame(t)}}})}))],2)])},w=[],E=(a("4160"),a("159b"),function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("button",{staticClass:"block border border-black p-4 my-2 max-w-lg mx-auto"},[a("div",{staticClass:"my-2 grilla"},[a("div",{staticClass:"p1"},[e._v(e._s(e.gamePlayers[0].player.nickName))]),a("div",{staticClass:"vs"},[e._v(" VS ")]),e.gamePlayers[1]?a("div",{staticClass:"p2"},[e._v(e._s(e.gamePlayers[1].player.nickName))]):e._e(),e.gamePlayers[1]?e._e():a("div",{staticClass:"p2"},[e._v(" -- ")])]),a("div",{staticClass:"text-xs text-gray-700"},[e._v(" "+e._s(Date(e.created))+" ")])])}),C=[],_={name:"GameCard",props:["created","gamePlayers"],data:function(){return{}}},S=_,I=(a("3a5f"),Object(c["a"])(S,E,C,!1,null,"c0dfa5e6",null)),k=I.exports,T={name:"GamePicker",components:{GameCard:k},props:["games"],methods:{retrieveId:function(e){var t,a=this;return e.gamePlayers.forEach((function(e){e.player.nickName==a.$store.state.nickName&&(t=e.id)})),t},setCurrentGame:function(e){this.$store.dispatch("setGpIndex",this.retrieveId(e)),this.$store.dispatch("setCurrentGame",e),this.$router.push({name:"game"})}}},N=T,L=Object(c["a"])(N,b,w,!1,null,null,null),j=L.exports,O={name:"home",components:{LoginForm:x,GamePicker:j},methods:{}},G=O,P=Object(c["a"])(G,u,m,!1,null,null,null),q=P.exports,$=function(){var e=this,t=e.$createElement;e._self._c;return e._m(0)},V=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"flex-container"},[a("div",{attrs:{id:"dock"}},[a("div",{attrs:{id:"display"}},[a("p",[e._v("Welcome...")])])]),a("div",{attrs:{id:"grid"}})])}],A=a("e587"),B=(a("99af"),a("a15b"),a("e25e"),a("4d63"),a("ac1f"),a("25f0"),a("466d"),a("841c"),function(e,t){var a=t.id.match(new RegExp("[^".concat(t.dataset.y,"|^").concat(t.dataset.x,"]"),"g")).join(""),n=t.dataset.y.charCodeAt()-64,r=parseInt(t.dataset.x);document.querySelectorAll(".".concat(e.id,"-busy-cell")).forEach((function(t){t.classList.remove("".concat(e.id,"-busy-cell"))}));for(var o=0;o<e.dataset.length;o++)"horizontal"==e.dataset.orientation?document.querySelector("#".concat(a).concat(String.fromCharCode(n+64)).concat(r+o)).classList.add("".concat(e.id,"-busy-cell")):document.querySelector("#".concat(a).concat(String.fromCharCode(n+64+o)).concat(r)).classList.add("".concat(e.id,"-busy-cell"))}),D=function(e,t,a){var n=document.createElement("DIV");n.classList.add("grid-wrapper");for(var r=0;r<e;r++){var o=document.createElement("DIV");o.classList.add("grid-row"),o.id="".concat(a,"-grid-row").concat(r),n.appendChild(o);for(var c=0;c<e;c++){var i=document.createElement("DIV");if(i.classList.add("grid-cell"),r>0&&c>0&&(i.id="".concat(a).concat(String.fromCharCode(r-1+65)).concat(c),i.dataset.y=String.fromCharCode(r-1+65),i.dataset.x=c,i.addEventListener("drop",(function(e){u(e)})),i.addEventListener("dragover",(function(e){d(e)}))),0===c&&r>0){var s=document.createElement("SPAN");s.innerText=String.fromCharCode(r+64),i.appendChild(s)}if(0===r&&c>0){var l=document.createElement("SPAN");l.innerText=c,i.appendChild(l)}o.appendChild(i)}}function d(e){e.preventDefault()}function u(e){if(e.preventDefault(),document.querySelector("#display p").innerText="",e.target.classList.contains("grid-cell")){var t=e.dataTransfer.getData("ship"),a=document.getElementById(t),n=e.target,r=n.dataset.y.charCodeAt()-64,o=parseInt(n.dataset.x);if("horizontal"==a.dataset.orientation){if(parseInt(a.dataset.length)+o>11)return void(document.querySelector("#display p").innerText="movement not allowed");for(var c=1;c<a.dataset.length;c++){var i=n.id.match(new RegExp("[^".concat(n.dataset.y,"|^").concat(n.dataset.x,"]"),"g")).join(""),s="".concat(i).concat(n.dataset.y).concat(parseInt(n.dataset.x)+c);if(-1!=document.getElementById(s).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}else{if(parseInt(a.dataset.length)+r>11)return void(document.querySelector("#display p").innerText="movement not allowed");for(var l=1;l<a.dataset.length;l++){var d=n.id.match(new RegExp("[^".concat(n.dataset.y,"|^").concat(n.dataset.x,"]"),"g")).join(""),u="".concat(d).concat(String.fromCharCode(n.dataset.y.charCodeAt()+l)).concat(n.dataset.x);if(-1!=document.getElementById(u).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}a.dataset.y=String.fromCharCode(r+64),a.dataset.x=o,e.target.appendChild(a),B(a,e.target)}else document.querySelector("#display p").innerText="movement not allowed"}t.appendChild(n)},z=function(e,t,a,n,r){var o=document.createElement("DIV"),c=document.createElement("DIV"),i=document.createElement("DIV");function s(e){e.dataTransfer.setData("ship",e.target.parentNode.id)}function l(e){o.classList.add("absolute");var t=e.targetTouches[0];o.style.left=t.pageX-25+"px",o.style.top=t.pageY-25+"px",event.preventDefault()}function d(e){o.style.left="-1000px",o.style.top="-1000px";var t=document.elementFromPoint(event.changedTouches[0].pageX,event.changedTouches[0].pageY);if(o.classList.remove("absolute"),o.style.left="",o.style.top="",t.classList.contains("grid-cell")){var a=t.dataset.y.charCodeAt()-64,n=parseInt(t.dataset.x);if("horizontal"==o.dataset.orientation){if(parseInt(o.dataset.length)+n>11)return void(document.querySelector("#display p").innerText="movement not allowed");for(var r=1;r<o.dataset.length;r++){var c=t.id.match(new RegExp("[^".concat(t.dataset.y,"|^").concat(t.dataset.x,"]"),"g")).join(""),i="".concat(c).concat(t.dataset.y).concat(n+r);if(-1!=document.getElementById(i).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}else{if(parseInt(o.dataset.length)+a>11)return void(document.querySelector("#display p").innerText="movement not allowed");for(var s=1;s<o.dataset.length;s++){var l=t.id.match(new RegExp("[^".concat(t.dataset.y,"|^").concat(t.dataset.x,"]"),"g")).join(""),d="".concat(l).concat(String.fromCharCode(t.dataset.y.charCodeAt()+s)).concat(n);if(-1!=document.getElementById(d).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}t.appendChild(o),o.dataset.x=n,o.dataset.y=String.fromCharCode(a+64),B(o,t)}else document.querySelector("#display p").innerText="movement not allowed"}function u(e){document.querySelector("#".concat(e)).addEventListener("click",(function(e){document.querySelector("#display p").innerText="";var t=e.target.parentNode,a=t.dataset.orientation,n=t.parentElement.classList.contains("grid-cell")?t.parentElement:null;if(null!=n)if("horizontal"==a){if(parseInt(t.dataset.length)+(n.dataset.y.charCodeAt()-64)>11)return void(document.querySelector("#display p").innerText="careful");for(var r=1;r<t.dataset.length;r++){var o=n.id.match(new RegExp("[^".concat(n.dataset.y,"|^").concat(n.dataset.x,"]"),"g")).join(""),c="".concat(o).concat(String.fromCharCode(n.dataset.y.charCodeAt()+r)).concat(n.dataset.x);if(-1!=document.getElementById(c).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}else{if(parseInt(t.dataset.length)+parseInt(n.dataset.x)>11)return void(document.querySelector("#display p").innerText="careful");for(var i=1;i<t.dataset.length;i++){var s=n.id.match(new RegExp("[^".concat(n.dataset.y,"|^").concat(n.dataset.x,"]"),"g")).join(""),l="".concat(s).concat(n.dataset.y).concat(parseInt(n.dataset.x)+i);if(-1!=document.getElementById(l).className.search(/busy-cell/))return void(document.querySelector("#display p").innerText="careful")}}"horizontal"==a?(t.dataset.orientation="vertical",t.style.transform="rotate(90deg)"):(t.dataset.orientation="horizontal",t.style.transform="rotate(360deg)"),null!=n&&B(t,n)}))}o.classList.add("grid-item"),o.dataset.length=t,o.dataset.orientation=a,o.id=e,"vertical"==a&&(o.style.transform="rotate(90deg)"),window.innerWidth>=768?(o.style.width="".concat(45*t,"px"),o.style.height="45px"):window.innerWidth>=576?(o.style.width="".concat(35*t,"px"),o.style.height="35px"):(o.style.width="".concat(30*t,"px"),o.style.height="30px"),window.addEventListener("resize",(function(){window.innerWidth>=768?(o.style.width="".concat(45*t,"px"),o.style.height="45px"):window.innerWidth>=576?(o.style.width="".concat(35*t,"px"),o.style.height="35px"):(o.style.width="".concat(30*t,"px"),o.style.height="30px")})),r||(c.classList.add("grip"),c.draggable="true",c.addEventListener("dragstart",s),o.addEventListener("touchmove",l),o.addEventListener("touchend",d),o.appendChild(c)),i.classList.add("grid-item-content"),o.appendChild(i),n.appendChild(o),r?B(o,n):u(e)},R=function(){z("carrier",5,"horizontal",document.getElementById("dock"),!1),z("battleship",4,"horizontal",document.getElementById("dock"),!1),z("submarine",3,"horizontal",document.getElementById("dock"),!1),z("destroyer",3,"horizontal",document.getElementById("dock"),!1),z("patrol_boat",2,"horizontal",document.getElementById("dock"),!1)},M={name:"game",mounted:function(){var e=this;D(11,document.getElementById("grid"),"ships"),this.$store.getters.ships?this.$store.getters.ships.forEach((function(t){z(t.type.toLowerCase(),5,e.whatOrientation(t.locations),document.getElementById("ships".concat(t.locations[0])),!1)})):R()},methods:{whatOrientation:function(e){var t=Object(A["a"])(e,2),a=t[0],n=t[1];return a[0]==n[0]?"horizontal":"vertical"}}},W=M,X=(a("1e55"),Object(c["a"])(W,$,V,!1,null,"8d7f0292",null)),F=X.exports;n["a"].use(d["a"]);var J=[{path:"/",name:"home",component:q},{path:"/game",name:"game",component:F}],K=new d["a"]({base:"/web/",routes:J}),U=K,Y=(a("96cf"),a("89ba")),H=a("2f62"),Q=a("bc3a"),Z=a.n(Q);n["a"].use(H["a"]);var ee=new H["a"].Store({state:{nickName:null,gameViews:null,gpIndex:null,currentGame:null},mutations:{SET_NICK_NAME:function(e,t){e.nickName=t},SET_GAME_VIEWS:function(e,t){e.gameViews=t},SET_CURRENT_GAME:function(e,t){e.currentGame=t},SET_GP_INDEX:function(e,t){e.gameIndex=t}},getters:{ships:function(e){var t=e.currentGame;return t?t.ships:null}},actions:{setNickname:function(e,t){var a=e.commit;a("SET_NICK_NAME",t)},setGpIndex:function(e,t){var a=e.commit;a("SET_GP_INDEX",t)},setCurrentGame:function(e,t){var a=e.commit;a("SET_CURRENT_GAME",t)},logOut:function(e){var t=e.commit;t("SET_GAME_VIEWS",null)},fetchGameViews:function(){var e=Object(Y["a"])(regeneratorRuntime.mark((function e(t){var a,n,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(a=t.state,n=t.commit,a.nickName){e.next=3;break}return e.abrupt("return");case 3:return e.prev=3,e.next=6,Z.a.get("/api/player/".concat(a.nickName,"/game_views"));case 6:r=e.sent,n("SET_GAME_VIEWS",r.data),e.next=13;break;case 10:e.prev=10,e.t0=e["catch"](3),e.t0.response||e.t0.request;case 13:case"end":return e.stop()}}),e,null,[[3,10]])})));function t(t){return e.apply(this,arguments)}return t}()}});a("def6");n["a"].config.productionTip=!1,new n["a"]({router:U,store:ee,render:function(e){return e(l)}}).$mount("#app")},"85ec":function(e,t,a){},a49c:function(e,t,a){},def6:function(e,t,a){}});
//# sourceMappingURL=app.4cf76f4b.js.map