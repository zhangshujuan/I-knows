!function(){function a(b,c){return a.init(b,c)}function b(a,b){var d=[];return l(a,function(a,e){(b?b(a,e):!c(e))&&d.push(e)}),d}function c(a){return void 0===a||null===a}function d(a){return c(a)||e(a)&&("undefined"==a||"null"==a||""==a.trim())}function e(a){return"string"==typeof a}function f(a){return a&&1==a.nodeType}function g(a){return a instanceof Array}function h(b){return!e(b)&&(g(b)||a.isQmik&&a.isQmik(b)||function(){return b+="","[object Arguments]"==b||/^\[object \w*((List)|(Collection)|(Map))\]$/.test(b)}())}function i(a){return"function"==typeof a}function j(a){return a instanceof Error}function k(a){return a+""=="[object Object]"}function l(a,b){var c;if(h(a))for(c=0;c<a.length;c++)b.call(a[c],c,a[c]);else if(k(a))for(c in a)b.call(a[c],c,a[c]);return a}function m(a){return"number"==typeof a}function n(a){return"boolean"==typeof a}function o(a){return n(a)||e(a)||m(a)?a+"":i(a)?a.toString():JSON.stringify(a)}function p(a){return d(a)?"":JSON.parse(a)}function q(a){return y.Event&&a instanceof y.Event||a==y.event}function r(a){return i(a)?a():a}function s(){for(var a=arguments,b=a[0],c=g(b),d=1;d<a.length;d++)l(a[d],function(a,d){c?b.push(d):b[a]=d});return b}function t(b,c,d,e){c=a.url(c);var f="css"==b,g="js"==b,h=f?"link":g?"script":"iframe",i=z.createElement(h),j=a(i).attr({_src:c,async:"async"});return f?j.attr("rel","stylesheet"):g&&j.attr("type","text/javascript"),j.on({load:function(){d&&d(i)},error:function(){j.remove(),e&&e(i)}}),f?i.href=c:i.src=c,a("head").append(i),i}function u(a,b){try{delete a[b]}catch(c){a[b]=null}}function v(a,b){var c=G.call(b);B&&(B[a].apply?B[a].apply(B,c):B[a](a+":",c))}function w(a,b,c){var d=this;d.pid=setTimeout(function(){a.apply(a,c)},b)}function x(a,b,d,e){function f(){(c(d)||d>h*b)&&(a.apply(a,e),g._p=new w(f,b,e)),h++}var g=this,h=1;g._p=new w(f,b,e)}var y=this,z=y.document||{},A=y.navigator||{},B=y.console,C=A.appVersion||A.userAgent,D=y.location,E=encodeURIComponent,F=decodeURIComponent,G=[].slice,H={base:""};a.extend=function(){var a=arguments,b=a[0]||{},d=1;switch(a.length){case 0:return;case 1:b=this,d=0}return l(G.call(a,d),function(a,d){d&&l(d,function(a,d){c(d)||(b[a]=d)})}),b};var I=String.prototype;a.extend(I,{trim:function(){return this.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"")},toLower:I.toLowerCase,toUpper:I.toUpperCase}),a.extend(w.prototype,{stop:function(){clearTimeout(this.pid)}}),a.extend(x.prototype,{stop:function(){this._p&&this._p.stop()}}),a.extend({encode:E,decode:F,isDom:f,isBool:n,isString:e,isFun:i,isFunction:i,isNum:m,isNumber:m,isArray:g,isNull:c,isError:j,each:l,stringify:o,parseJSON:p,isEvent:q,likeArray:h,isDate:function(a){return a instanceof Date},isObject:k,isPlainObject:function(a){if(c(a)||a+""!="[object Object]"||a.nodeType||a==y)return!1;var b;for(b in a);return c(b)||Object.prototype.hasOwnProperty.call(a,b)},likeNull:d,inherit:function(a,b){function c(){}var d=a.prototype;c.prototype=b.prototype,a.prototype=new c,a.prototype.constructor=a,b.prototype.constructor==Object.prototype.constructor&&(b.prototype.constructor=b);for(var e in d)a.prototype[e]=d[e];for(var e in b)a[e]=b[e]},trim:function(a){return c(a)?"":e(a)?a.trim():a.toString().trim()},toLower:function(a){return a?a.toLower():a},toUpper:function(a){return a?a.toUpper():a},merge:s,inArray:function(b,c){if(a.likeArray(c))for(var d=0;d<c.length;d++)if(c[d]===b)return d;return-1},unique:function(b){var c=[];return l(b,function(b,d){a.inArray(d,c)<0&&c.push(d)}),c},map:function(a,b){for(var d=[],e=0;a&&e<a.length;e++)c(a[e])||d.push(b(e,a[e]));return d},getScript:function(a,b,c){return t("js",a,b,c)},getCss:function(a,b,c){return t("css",a,b,c)},grep:b,param:function(a){var b=[];return l(a,function(a,c){e(a)?b.push(E(a)+"="+E(r(c))):c.name&&b.push(E(c.name)+"="+E(r(c.value)))}),b.join("&")},contains:function(a,b){return f(b)&&(a==z||a.contains(b))},now:function(a){return(a||0)+(new Date).getTime()},delay:function(a,b){return new w(a,b,G.call(arguments,2))},cycle:function(a,b,c){return new x(a,b,c,G.call(arguments,3))},log:function(){v("log",arguments)},warn:function(){v("warn",arguments)},error:function(){v("error",arguments)},isIphone:function(){return/iPhone OS/.test(C)},isAndroid:function(){return/Android/.test(C)},isWP:function(){return/Windows Phone/.test(C)},isIE:function(){return/MSIE/.test(C)||/Trident/.test(C)},isFF:function(){return/Firefox/.test(C)},isWK:function(){return/WebKit/.test(C)},isOpera:function(){return/Opera/.test(C)},isRetinal:function(){return(y.devicePixelRatio||2)>=1.5},config:function(b,d){d=arguments.length<=1?H:d||{};var e=d;return arguments.length<1||c(b)||(k(b)?l(b,function(b,c){k(c)&&d[b]?a.extend(d[b],c):d[b]=c}):e=d[b]),e},url:function(b){return b=a.trim(b),b?b:D.pathname},cssPrefix:function(b){var c={};return e(b)?c=(a.isWK()?"-webkit-":a.isIE()?"-ms-":a.isFF()?"-moz-":a.isOpera()?"-o-":"")+b:l(a.extend({},b),function(b,d){c[a.cssPrefix(b)]=d,c[b]=d}),c},execCatch:function(a,b,c){try{return i(a)&&a.apply(a,b||[])}catch(d){return console.error(d.stack||d,"\r\n",a,b),c&&c(d)}}}),l([a.url,a.now],function(a,b){b.toString=b}),a._in={createEvent:function(a){return z.createEvent?z.createEvent(a):z.createEventObject(a)},isSE:function(){return!c(z.addEventListener)},_delete:u},a.version="2.2.22",a.global=y,y.Qmik=a,y.$=y.$||a}(),function(Q){function Query(a,b){var c,d=this;if(d.context=b=b||doc,d.selector=a,d.length=0,d.__lives={},isString(a)){if(!rNode.test(a.replace(/\n+/g,"")))return each(find(a,b),function(a,b){d._push(b)}),d;var e=doc.createElement("div");e.innerHTML=a,c=e.children,compileScript(e)}else c=likeArray(a)?a:[a];for(var f=0;f<c.length;f++)d._push(c[f]);return d}function init(a,b){return b=b||doc,isFun(a)?Q(doc).ready(a):isQmik(a)?a:new Query(a,b)}function isQmik(a){return a instanceof Query}function find(a,b){var c=[];return likeNull(a)||(b=isString(b)?Q(b):b,isQmik(b)?each(b,function(b,d){isDom(d)&&(c=arrayConcat(c,d.querySelectorAll(a)))}):c=b.querySelectorAll(a)||[]),c}function muchValue2Qmik(a){return a=execObject(a),isString(a)&&rNode.test(a.replace(/\n+/g,""))?Q(a):a}function execObject(a){return isFun(a)?a():a}function arrayConcat(a,b){if(isArray(b))a=a.concat(b);else for(var c=0;c<b.length;c++)a.push(b[c]);return a}function at(a,b){return SE()?a.getAttribute(b)||a[b]:a[b]}function hasClass(a,b){if(!isDom(a))return!1;var c=a.className.split(" "),d=0;for(b=trim(b);d<c.length;d++)if(c[d]==b)return!0;return!1}function formateClassName(a){return replace(a,/[A-Z]/g,function(a){return"-"+toLower(a)})}function createTextNode(a){return doc.createTextNode(a)}function append(a,b){b=muchValue2Qmik(b),likeArray(a)?each(a,function(a,c){append(c,b)}):isDom(a)&&(likeArray(b)?each(b,function(b,c){append(a,c)}):a.appendChild(isDom(b)?b:createTextNode(b)))}function before(a,b){b=muchValue2Qmik(b),likeArray(a)?each(a,function(a,c){before(c,b)}):isDom(a)&&(likeArray(b)?each(b,function(b,c){before(a,c)}):a.parentNode.insertBefore(isDom(b)?b:createTextNode(b),a))}function after(a,b){if(isDom(a)){var c=GN(a);c?before(c,b):append(a.parentNode,b)}else likeArray(a)&&each(a,function(a,c){after(c,b)})}function setValue(a,b,c){return a[b]=c,a}function formateClassNameValue(a,b){for(var c in addUints)if(a.indexOf(addUints[c])>=0){/[^\d\.-]/.test(b)||(b+="px");break}return b}function getStyle(a,b){return a.currentStyle?a.currentStyle[b]:doc.defaultView.getComputedStyle(a,!1)[b]}function css(a,b,c){if(b=isString(b)&&!isNull(c)?setValue({},b,execObject(c)):b,likeArray(a)){if(isString(b))return css(a[0],b);each(a,function(a,c){css(c,b)})}else if(isDom(a)){if(isString(b))return getStyle(a,formateClassName(b));c="",each(b,function(a,b){c+=formateClassName(a)+":"+formateClassNameValue(a,b)+";"}),a.style.cssText+=";"+c}}function attr(a,b,c,d){if(likeArray(a)){if(isString(b)&&isNull(c))return attr(a[0],b,c,d)||"";each(a,function(a,e){attr(e,b,c,d)})}else if(!isNull(a)){if(isString(b)&&isNull(c))return at(a,b);if(isPlainObject(b))each(b,function(b,c){attr(a,b,c,d)});else if(isDom(c))attr(a,b,"",d),Q(a).append(c);else{var c=execObject(c);d||!SE()?a[b]=c:a.setAttribute(b,c)}}}function clone(a,b){if(isDom(a))return Q(a.cloneNode(1==b));var c=[];return each(a,function(a,d){isDom(d)&&c.push(clone(d,b))}),Q(c)}function data(a,b,c){if(likeArray(a))return isString(b)&&isNull(c)?data(a[0],b,c):(each(a,function(a,d){data(d,b,c)}),a);if(!isNull(a)){if(isNull(a[dn])&&(a[dn]={}),isNull(c)&&isString(b))return a[dn][b];isString(b)?a[dn][b]=c:each(b,function(b,c){a[dn][b]=c})}}function GN(a,b){return a?(a="prev"==b?a.previousSibling:a.nextSibling,isDom(a)?a:GN(a,b)):void 0}function uponSelector(a,b,c,d){var e,f,g=Q(a.parentNode).children(b);if("prev"==c)for(e=g.length-1;e>=0;e--)for(f=a;(f=GN(f,c))&&f==g[e];){d.push(f);break}else for(e=0;e<g.length;e++)for(f=a;(f=GN(f,c))&&f==g[e];){d.push(f);break}}function upon(a,b,c){var d=[];return each(a,function(a,e){isNull(b)?d.push(GN(e,c)):uponSelector(e,b,c,d)}),new Query(d,a)}function matchesSelector(a,b){return a?(a._matchesSelector=a.matchesSelector||a.msMatchesSelector||a.mozMatchesSelector||a.webkitMatchesSelector,a._matchesSelector&&a._matchesSelector(b)):void 0}function parents(a,b,c,d){a=a?trim(a):a;var e=[],f=0;c=0!=c,d=1==d;var g=!isNull(a);return each(b,function(b,h){for(var i,j=h.parentNode;isDom(j)&&(f=0,g?(i=j.parentNode,i&&Q.inArray(j,Q(i).children(a))>-1&&(f=1)):f=1,!f||(e.push(j),c))&&!d;)j&&(j=j.parentNode)}),Q(e)}function compileScript(context){Q("script",context).each(function(i,dom){Q.execCatch(function(){eval(dom.text||"")})})}function getHeight(){return win.innerHeight||screen.availHeight}function getMaxX(){return win.pageXOffset+win.innerWidth+120}function getMaxY(){return win.pageYOffset+getHeight()+120}var win=Q.global,doc=win.document,_in=Q._in,SE=_in.isSE,isNull=Q.isNull,isDom=Q.isDom,each=Q.each,likeArray=Q.likeArray,isArray=Q.isArray,likeNull=Q.likeNull,isString=Q.isString,isFun=Q.isFun,isPlainObject=Q.isPlainObject,trim=Q.trim,toLower=Q.toLower,toUpper=Q.toUpper,replace=function(a,b,c){return a.replace(b,c)},rNode=/^\s*(<.+>.*<\/.+>)+|(<.+\/\s*>)+\s*$/,addUints="height width top right bottom left".split(" ");Q.extend(Query.prototype,{_push:function(a){a&&(this[this.length++]=a)}});var dn="Qmikdata:";Q.init=init;var fn=Q.fn=Query.prototype;fn.extend=function(a){each(a,function(a,b){Query.prototype[a]=b})},fn.extend({last:function(){return Q(this[this.length-1])},eq:function(a){return Q(this[a])},first:function(){return Q(this[0])},filter:function(a){var b=new Query;return each(this,function(c,d){a(c,d)&&b._push(d)}),b},even:function(){return this.filter(function(a){return a%2==0})},odd:function(){return this.filter(function(a){return a%2!=0})},gt:function(a){for(var b=new Query,c=a+1;c<this.length&&c>=0;c++)b._push(this[c]);return b},lt:function(a){for(var b=new Query,c=0;a>c&&c<this.length;c++)b._push(this[c]);return b},find:function(a){return new Query(a,this)},each:function(a){return each(this,a),this},append:function(a){return append(this,a),this},appendTo:function(a){return Q(a).append(this),this},remove:function(){return each(this,function(a,b){isDom(b.parentNode)&&b.parentNode.removeChild(b)}),this},before:function(a){return before(this,a),this},after:function(a){return after(this,a),this},beforeTo:function(a){return before(a,this),this},afterTo:function(a){return after(a,this),this},html:function(a){var b=this;return arguments.length<1?attr(b,"innerHTML"):(attr(b,"innerHTML",isQmik(a)?a.html():a,!0),compileScript(b),this)},empty:function(){return this.html("")},text:function(a){var b=attr(this,"innerText",isQmik(a)?a.text():a,!0);return isNull(a)?b:this},addClass:function(a){return each(this,function(b,c){isDom(c)&&!hasClass(c,a)&&(c.className+=" "+trim(execObject(a)))}),this},rmClass:function(a){var b=new RegExp(replace(execObject(a),/\s+/g,"|"),"g");return each(this,function(a,c){c.className=replace(trim(replace(c.className,b,"")),/[\s]+/g," ")}),this},show:function(){return css(this,"display","block"),this},hide:function(){return css(this,"display","none"),this},inViewport:function(){var a=this,b=a.offset(),c=!1;if(b){var d=b.top,e=b.left,f=d+a.height(),g=e+a.width(),h=win.pageXOffset,i=getMaxX(),j=win.pageYOffset,k=getMaxY();j=0>j?0:j,c=k>=d&&f>=j,c=c&&i>=e&&g>=h}return c},animate:function(a,b,c,d){var e=this;isFun(c)&&(d=c,c=" ");var f={transition:0},g=Q.extend({},f);return each(a,function(a){g[a]=parseFloat(css(e,a))||0}),css(e,g),Q.delay(function(){function g(a){h&&(css(e,f),d&&d(a),h=!1)}var h=!0;a.transition=(c||" ease-in-out ")+" "+(b||1)+"ms",css(e,Q.cssPrefix(a)),e.once({webkitTransitionEnd:g,msTransitionEnd:g,oTransitionEnd:g,transitionend:g})},10),e},toggle:function(){return each(this,function(a,b){"none"==css(b,"display")?Q(b).show():Q(b).hide()}),this},map:function(a){return Q.map(this,a)},css:function(a,b){var c=css(this,a,b);return isPlainObject(a)||isString(a)&&!isNull(b)?this:c},attr:function(a,b){var c=attr(this,a,b);return arguments.length>1||isPlainObject(a)?this:c},rmAttr:function(a){return each(this,function(b,c){isDom(c)&&c.removeAttribute(a)}),this},data:function(a,b){return data(this,a,b)},rmData:function(a){return each(this,function(b,c){c.$Qmikdata&&delete c.$Qmikdata[a]}),this},val:function(a){var b=this;return isNull(a)?b.attr("value")||"":(each(b,function(b,c){c.value=execObject(a)}),b.emit("change"),b)},next:function(a){return upon(this,a,"next")},prev:function(a){return upon(this,a,"prev")},clone:function(a){return clone(this,a)},hasClass:function(a){return hasClass(this[0],a)},closest:function(a){var b=this,c=new Query;return b.each(function(b,d){Q(a,d.parentNode).each(function(a,b){d===b&&c._push(d)})}),c.length>0?c:parents(a,b,!1)},parents:function(a){return parents(a,this,!0)},parent:function(a){return parents(a,this,!0,!0)},children:function(a){var b=new Query,c=this,d=isNull(a);return c.each(function(c,e){for(var f,g=e.children||e.childNodes,h=0;h<g.length;)f=g[h++],isDom(f)&&(d||matchesSelector(f,a))&&b._push(f)}),b}}),fn.extend({removeClass:fn.rmClass,removeData:fn.rmData,removeAttr:fn.rmAttr}),Q.isQmik=isQmik}(Qmik),function(a){function b(a,b){a.onreadystatechange=a.onload=a.onDOMContentLoaded=b}function c(b,c,d,e){var g=a(b),h=g.data(p)||{},i=h[c];g.data(p,h),i||(h[c]=i=[],r(b["on"+c])&&(i.push({fun:b["on"+c],param:[]}),u(b,"on"+c)),n()?b.addEventListener(c,f,!1):b["on"+c]=f),r(d)&&i.push({fun:d,param:e||[]})}function d(b,c,d){var e=a(b).data(p)||{},g=e[c]||[],h=g.length-1;if(d)for(;h>=0;h--)g[h].fun==d&&g.splice(h,1);else n()?b.removeEventListener(c,f,!1):u(b,"on"+c),u(e,c)}function e(a,b){var c;n()?(c=m.createEvent("MouseEvents"),c.initEvent(b,!0,!0),a.dispatchEvent(c)):a.fireEvent("on"+b)}function f(b){b=g(b||j.event);var c,d,e,f=this,h=a(f).data(p)||{};s(h[b.type],function(g,h){a.execCatch(function(){d=h.fun,e=h.param||[],r(d)&&(c=d.apply(f,[b].concat(e)),q(c)||(b.returnValue=c,j.event&&(j.event.returnValue=c)))})})}function g(a){return a.preventDefault||(a.preventDefault=function(){this.returnValue=!1,j.event&&(j.event.returnValue=!1)}),a.stopPropagation||(a.stopPropagation=function(){this.cancelBubble=!0}),a.target||(a.target=a.srcElement),a}function h(a,b){return a+(b||"").toString()}function i(a,b,c){var d={};return t(a)?d=a:d[a]=b,c&&s(d,c),d}var j=a.global,k=j.document,l=a.fn,m=a._in,n=m.isSE,o=/complete|loaded|interactive|loading/i,p="QEvents",q=a.isNull,r=a.isFun,s=a.each,t=a.isPlainObject,u=m._delete;a.ready=l.ready=function(c,d){function e(c){f=g.readyState,"loading"!=f&&!q(g.$$handls)&&(o.test(f)||q(f)&&"load"==c.type)&&(b(g,null),s(g.$$handls,function(b,c){a.delay(function(){c.call(g,a)},5)}),u(g,"$$handls"))}var f,g=d||this[0]||k;if(o.test(g.readyState))a.delay(function(){c.call(g,a)},10);else{var h=g.$$handls=g.$$handls||[];h.push(c),b(g,e)}return this},l.extend({on:function(a,b){return s(this,function(d,e){i(a,b,function(a,b){c(e,a,b)})}),this},off:function(a,b){return s(this,function(c,e){d(e,a,b)}),this},once:function(a,b){var c=this,d={};return i(a,b,function(a,b){d[a]=function(e){c.off(a,d[a]),b(e)}}),c.on(d)},emit:function(a){return s(this,function(b,c){e(c,a)}),this},live:function(b,c){var d=this;return i(b,c,function(b,c){var e=d.__lives[h(b,c)]=function(b){var e=b.target,f=(a(e),a.isString(d.selector)?a(d.selector,d.context):d);s(f,function(d,f){a.contains(f,e)&&c.call(e,b)})};a("body").on(b,e)}),d},die:function(a,b){var c=this,e=c.__lives[h(a,b)];return(arguments.length<2||e)&&d(k.body,a,e),c}}),l.extend({bind:l.on,unbind:l.off,trigger:l.emit}),s("click blur focus scroll resize".split(" "),function(a,b){l[b]=function(a){var c,d=this;a?d.on(b,a):(["focus","blur"].indexOf(b)>=0&&(c=d.last()[0],c&&r(c[b])&&c[b]()),d.emit(b))}})}(Qmik),function(a){function b(a,b){var c=a.length;0==c?b():function d(f,g){e(a[f],function(a,e){a?b(a):f==c-1?b(a,e):d(f+1,e)},g)}(0,null)}function c(a,b){var c=a.length;0==c?b():function d(e){f(a[e],function(){e==c-1?b():d(e+1)})}(0)}function d(b,d){var e=b.length,f=(new Array(e),parseInt((e-1)/h)+1),g=new Array(e>h?h:e);a.each(g,function(a){g[a]=b.slice(a*f,(a+1)*f)});var i=0;a.each(g,function(a,b){c(b,function(){i++,i==g.length&&d()})})}function e(a,b,c){g(a,[b,c],b)}function f(a,b){g(a,[b],b)}var g=a.execCatch,h=3,i={};i.series=function(c,d){b(c,function(b,c){b&&a.log(b,b.stack),g(d,[b,c])})},i.parallel=function(a,b){d(a,function(){g(b)})},a.task=i,a.series=i.series,a.parallel=i.parallel}(Qmik),function(a){function b(b,c,d){a.extend(this,{id:b,url:b,dependencies:c,factory:d,state:3,type:a.inArray(b,y.preload)>=0?2:1,exports:{}})}function c(b){var c=[];return a.each(b.replace(/(\/\/[^\n]*)|(\/\*[^\n]*\*\/)|(["'][^"'\n]*["'])/g,function(a){return/[\(\)\*]/.test(a)?"":a}).replace(/\/\*.*\*\//g,"").split(/\*\//),function(a,b){c.push(b.replace(/\/\*[\s\S]+/,""))}),c.join("")}function d(b){b=c(b.toString());var d=b.replace(/^\s*function\s*\w*\s*/,"").match(/^\([\w ,]*\)/)[0].replace("(","").replace(")",""),e=[],f=d.indexOf(","),g=d.substring(0,f>0?f:d.length),h=new RegExp(g+"s*[(]s*[\"']([^\"')]+)[\"']s*[)]","g");return g&&(e=a.map(b.match(h),function(a,b){return b.replace(new RegExp("^"+g+"s*[(]s*[\"']"),"").replace(/\s*[\"']\s*[)]$/,"")})),e}function e(a){var b=this;b._deal=a,b.l=b.p=0,b.state=1,b.deal()}function f(a){var b=g(a);return b?b.exports:x}function g(a){return z[a]||z[k(o(a))]}function h(b,c,d,e){var f=[],g=[];a.each(c,function(a,b){f.push(function(a){i(b,function(b,c){c&&console.error(c),g.push(b),a()},d)})}),a.series(f,function(a){try{a||b&&b.apply(b,g)}catch(c){console.error(c.stack||c)}e&&e()})}function i(a,b,c){var d=g(a);d?m(d,f,b,c):n(a,function(){d=g(a),d?m(d,f,b,c):j(a,b)},function(){j(a,b)})}function j(a,b){b(x,new Error("load module is error "+a))}function k(a){return a.replace(/[\?#].*$/g,"")}function l(b){return b.isMake||(b.isMake=!0,exports=b.factory(f,b.exports,b),a.isNull(exports)||(b.exports=exports)),b.exports}function m(a,b,c,d){switch(a.state){case 1:c(a.exports);break;case 2:d&&c(l(a));break;case 3:a.state=2,h(function(){a.state=1,c(l(a))},a.dependencies,a)}}function n(b,c,d){u=o(b),/\/.+\.css(\?.*)?$/i.test(b)?a.getCss(b,d,d):a.getScript(u,c,d)}function o(a){var b=q(a);return b==a?a:(b=r(b),p(b))}function p(a){return/\?/.test(a)||/\.(css|js)\s*$/.test(a)?a:a+".js"}function q(a){return y.alias[a]||a}function r(a){return a.replace(/\$\{[0-9a-zA-Z._-]+\}/g,function(a){var b=y.vars[a.substring(2,a.length-1).trim()]||a;return v(b)?b():b})}function s(a){if(/[\)\(\*]/.test(a))throw new Error("define id:"+a+" is Illegal,not contain )(*")}function t(a,c,d,e){return s(a),s(c),z[a]=z[c]=new b(a,d,e)}var u,v=a.isFun,w=a.global,x=null,y={alias:{},vars:{},preload:[]},z={},A=!1,B={};a.extend(e.prototype,{pause:function(){return this.state=2,this},size:function(){return this.l-this.p},push:function(a){this[this.l++]=a},pop:function(){var a=this,b=a[a.p];return delete a[a.p++],b},deal:function(){var a=this;return 1==a.state&&a.size()>0&&a._deal(a.pause().pop(),function(){a.state=1,a.deal()}),a}});var C=new e(function(a,b){var c=a.callback;h(c,a.ids,x,b)});a.extend(B,{use:function(b,c){a.delay(function(){b=a.isArray(b)?b:[b],A||(C.push({ids:y.preload}),A=!0);var d=[];a.each(b,function(a,b){var c=g(b)||{};1==c.state&&d.push(f(b))}),d.length==b.length?c&&c.apply(c,d):C.push({ids:b,callback:c}),C.deal()},1)},define:function(b,c,e){var f;u&&(f=u),(v(b)||a.isArray(b))&&(e=c,c=b,b=""),v(c)&&(e=c,c=[]),c=c.concat(d(e)),c=a.unique(c),t(b,k(f||b),c,e),u=x},config:function(b){return a.config(b,y)},modules:function(){return a.extend({},z)}}),B.define.cmd={},a.sun=B,a.define=B.define,w.define=w.define||a.define,a.use=B.use}(Qmik),function(a){function b(){return!f.XMLHttpRequest||"file:"===f.location.protocol&&f.ActiveXObject?new f.ActiveXObject("Microsoft.XMLHTTP"):new f.XMLHttpRequest}function c(b,c,d){function e(){1==m&&(m=0,i(f,p),a("script[jsonp='"+p+"']").remove(),d&&d())}var g,h=b.timeout,m=1,n=b.url,o=a.param(b.data),p=l+k++,q=n.match(j);/\?/.test(n)||(n+="?"),q?(q=q[0].split("=")[0],n=n.replace(j,q+"="+p)):n+="&callback="+p,n+="&"+o,f[p]=function(b){i(f,p),a("script[jsonp='"+p+"']").remove(),g&&g.stop(),1==m&&c&&c(b)},a(a.getScript(n,null,e)).attr("jsonp",p),h>0&&(g=a.delay(e,h))}function d(d){var e,f=a.extend({},m,d),h=f.dataType,i=f.timeout,j=b(),k=a.url(f.url),l="GET"==a.toUpper(f.type),n=f.success,o=f.error,p=a.param(d.data);return"jsonp"==h?void c(f,n,o):(j.onreadystatechange=function(){4==j.readyState&&(200==j.status?(e&&e.stop(),n&&n("xml"==h?j.responseXML:"json"==h?g(j.responseText):j.responseText)):o&&o())},j.onprogress=d.progress,l&&(k+=(/\?/.test(k)?"&":"?")+p),a.extend(j,f.xhrFields||{}),j.open(f.type,k,f.async),j.setRequestHeader("Cache-Control","no-cache"),j.setRequestHeader("X-Requested-With","XMLHttpRequest"),!l&&j.setRequestHeader("Content-Type","application/x-www-form-urlencoded"),j.send(l?null:p),i>0&&(e=a.delay(function(){j.abort(),o&&o(j.status||j.responseText)},i)),j)}function e(a,b,c,e,f){return h(b)&&(e=c,c=b,b=null),d({url:a,data:b,success:c,dataType:e,type:f})}var f=a.global,g=a.parseJSON,h=a.isFun,i=a._in._delete,j=/[\w\d_$-]+\s*=\s*\?/,k=1,l="qjsonp",m={type:"GET",async:!0,dataType:"text",xhrFields:{}};a.extend({ajax:d,get:e,getJSON:function(a,b,c){h(b)&&(c=b,b={}),e(a,b,c,"json")},post:function(a,b,c,d){h(b)&&(d=c,c=b,b={}),e(a,b,c,d,"post")}})}(Qmik),function(a){function b(a){return a.offsetParent?a.offsetLeft+b(a.offsetParent):a.offsetLeft}function c(a){return a.offsetParent?a.offsetTop+c(a.offsetParent):a.offsetTop}function d(a){return a.parentNode==a.offsetParent?a.offsetLeft:b(a)-b(a.parentNode)}function e(a){return a.parentNode==a.offsetParent?a.offsetTop:c(a)-c(a.parentNode)}var f=a.global,g=(f.document,a.isNull,a.isDom);a.fn.extend({width:function(){var a=this[0];return g(a)?a.offsetWidth:screen.availWidth},height:function(){var a=this[0];return g(a)?a.offsetHeight:screen.availHeight},offset:function(){if(!this[0])return null;var a=this[0].getBoundingClientRect();return{left:a.left+f.pageXOffset,top:a.top+f.pageYOffset}},position:function(){var a=this[0];return a?{left:d(a),top:e(a)}:null}})}(Qmik),function(a){function b(){var c=a.now();if(_>c-ba)return void N(b,_+2);ba=c;var d=L({},aa);M(d,function(b,c){var d=c.scope?c.scope.context:c.context,e=a(d);e.inViewport()&&(delete aa[b],a.delay(function(){K(c.callback),e.emit("viewport")},11))})}function c(){a(H).emit("scroll")}function d(a,b,c){var d=a.target,e=c?a.name:d.name||"",b=y(d)[U]||b;if(b=x(b,e),e&&(o(d)||c)){v(b,e,c?a.value:q(d));var f=w(b,e);if(!p(d)){if(d.__oldValue==f)return;d.__oldValue=f}l(b,e),M(n(b[V],e),function(a,c){K(c,[{name:e,value:f,source:b[u(e)[0]],target:d}])}),B(e,b)}}function e(a){this.config(a),this._rootCtrls=[]}function f(b,c){var d=this;d[V]={},d[X]=b=b||a(W)[0],d.scopes=P,d.__name=a(b).attr("q-ctrl")||"root",d[Z]={},d.__cmd={},d[Y]={},P[d.__name]=d,b[U]=d,d[S]=c,j(d[X],d)}function g(a){if(a==ca)for(var b in a)da[b]==a[b]||h(b)||(da[b]=a[b])}function h(a){return/^__/.test(a)||new RegExp(a).test(R)}function i(a,b){1==a.nodeType&&$("input,select,textarea",a).each(function(a,c){b&&b(c)})}function j(b,c){i(b,function(d){var e=a(d).closest("[q-ctrl]")[0];(I(e)||e==b)&&k(d,c)})}function k(b,c){var d=b.name;if(o(b)&&d){if(h(d))return a.error("set scope["+c.__name+"] name["+d+"] is illegal");c=x(c,d);var e=q(b);p(b)&&(e=e||v(c,d)),v(c,d,e),c[Y][d]=c[Y][d]||[],c[Y][d].push(b),l(c,d)}}function l(a,b){if("root"==a.__name){var c=u(b)[0];da[c]=a[c]}}function m(a,b){if(a.length<1)return[];for(var c,d=a.concat(b||[]).sort(),e=[],f=0;f<d.length;f++){for(c=f+1;c<d.length;c++)new RegExp("^"+d[f]).test(d[c])&&(d.splice(c,1),c--);e[f]=d[f]}return e}function n(a,b){if(""!=b){for(var c=[],d=0,e=u(b).length;e>d;d++){var f=a[b];f&&(c=f.concat(c)),b=b.replace(/\.?[^\.]*$/,"")}return c}}function o(a){var b=a?a.tagName:"";return"INPUT"==b||"SELECT"==b||"TEXTAREA"==b}function p(a){var b=a.type;return"checkbox"==b||"radio"==b||"select-multiple"==b}function q(b){var c=(b.name,b.type),d=[];return"radio"==c?d[0]=b.checked?b.value:"":"checkbox"==c?a("input[name='"+b.name+"']",y(b)).each(function(a,b){b.checked&&d.push(b.value)}):"select-multiple"==c?a(b).children("option").each(function(a,b){b&&b.selected&&d.push(b.value)}):d.push(b.value),d.join("&")}function r(a,b,c){F(a,b,c,s)}function s(a,b,c){a&&a!=H&&M(a.childNodes,function(a,d){F(d,b,c,s)})}function t(a){return(a||"").replace(fa,"")}function u(a){return a.split(".")}function v(b,c,d){var e=a.isArray(c)?c:u(c),f=e[0];return e.length<2?(I(d)||(b[f]=I(d)?I(b[f])?"":b[f]:d),b[f]):(b[f]=b[f]||{},e.shift(),v(b[f],e,d))}function w(a,b){var c=v(x(a,b),b);return I(c)?"":c}function x(b,c){var d=u(c)[0],e=b[d],f=da[d];return I(f)||!a.isObject(e)&&!a.isArray(e)||e!=f?b:b[S]?b[S]:b}function y(b){return a(b).closest("[q-ctrl],"+W)[0]}function z(a,b,c){a=x(a,b),a&&A(a,b,c)}function A(a,b,c){for(var d=[],e=0,f=u(b).length;f>e;e++){var g=a[Z][b]=a[Z][b]||[];g.indexOf(c)<0&&g.push(c),b=b.replace(/\.?[^\.]*$/,"")}return d}function B(b,c){var d=[];M(c[Z][b],function(b,e){a(e).parents("html").length>0&&d.push(e),F(e,c)}),c[Z][b]=d}function C(a){var b=y(a);return b?a[T]||{attr:{},vars:[],ctrl:b,event:{},fors:{},scope:b[U]||ca}:void 0}function D(a,b){M(a[Y],function(c){(c==b||0==c.indexOf(b+"."))&&E(a,c)})}function E(b,c){var d=w(b,c)+"",e=b[Y][c];b[Y][c]=[],M(e,function(e,f){if(a.contains(b.context,f)){var g=f.type;"radio"==g?f.checked=f.value==d:"checkbox"==g?(f.checked=!1,d.replace(/[^&]+/g,function(a){f.value==a&&(f.checked=!0)})):"select-multiple"==g?M(f.options,function(a,b){b.selected=!1,d.replace(/[^&]+/g,function(a){b.value==a&&(b.selected=!0)})}):f.value=d,b[Y][c].push(f)}})}function F(c,d,e,g){var h=C(c),i=a(c);if(h){switch(c.nodeType){case 1:"SCRIPT"!=c.tagName&&M(c.attributes,function(g,j){var k=j.name,l=h.attr[k]=h.attr[k]||(j.value||"").trim();if("q-ctrl"==k)""!=l&&(P[l]?d=P[l]:(d=new f(c,d.parent||d),K(function(){a.isFun(O[l])?O[l].call(d,d):a.warn("q-ctrl:["+l+"]is not define")}),d.apply("")));else if("q-include"==k){var m="q-include-"+l+Math.random(),n={context:c,callback:function(){"none"!=a(c).css("display")?a.get(l,function(a){i.rmClass("loading").html("<div>"+a+"</div>")}):(aa[m]=n,a("body").once({click:b,touchstart:b}))}};aa[m]=n,i.rmAttr("q-include")}else if("q-for"==k){var o=l.replace(/(\s){2,}/g," ").split(" "),p=h.html=h.html||c.innerHTML.replace(ga,"&lt;script"),q=[],r=w(d,o[2])||[];3==o.length&&"in"==o[1]?(M(r,function(a,b){var c=p.replace(ea,function(a){var c=new RegExp("^"+o[0]+"."),d=t(a).replace(c,""),e=v(b,d);return e=I(e)?"":e+"",e.replace(ga,"&lt;script")});q.push(c)}),i.html(q.join("")),s(c,d,e),G(c),c[T]=h,e&&z(d,o[2],c)):a.warn("q-for[",l,"] is error")}else if(/^q-on/.test(k)){var u=k.replace(/^q-on/,""),x=l.replace(/\(.*\)$/,""),y=d[X];if(!h.event[u]){c[T]=h,["focus","blur"].indexOf(u)>=0?y=c:h.event[u]=!0;var A=function(b){return a.contains(y,c)?void(a.contains(c,b.target)&&d[x]&&d[x].call(c,b)):a(y).off(u,A)};a(y).on(u,A)}}else ea.test(l)&&(j.value=l.replace(ea,function(a){c[T]=h,a=t(a),h.vars.push(a);var b=w(d,a);return e&&z(d,a,c),b}))});break;case 3:var j=h.text;j=I(j)?c.textContent:j,ea.test(j)&&(c[T]=h,h.text=j,c.textContent=j.replace(ea,function(a){a=t(a),h.vars.push(a);var b=w(d,a);return e&&z(d,a,c),b}),G(c.parentNode))}h.scope=d,g&&g(c,d,e)}}function G(b){var c=a(b);c.css("visibility","visible"),"none"==c.css("display")&&(a.isIE()?c.css("display",c.attr("q-for")?"inline-block":"inline"):c.css("display","initial"))}var H=a.global,I=a.isNull,J=a.isPlainObject,K=a.execCatch,L=a.extend,M=a.each,N=a.delay,O=(console,{}),P={},Q={section:24},R="scopes context parent get set on off once app watch apply $",S="parent",T="qmik-mvc-space",U="qmik-mvc-space-scope",V="__watchs",W="html",X="context",Y="__input",Z="__map",_=10,aa={},ba=a.now();a(H).on({scroll:b,touchstart:b,touchmove:b});var ca;L(e.prototype,{__init:function(b){function e(a){var b=a.target,c=b.name,d=o(b),e=C(b);if(e){var f=e.scope;M(e.vars,function(a,e){var g=[];d&&c==e||(M(f[Z][e],function(a,c){c!=b&&g.push(c)}),f[Z][e]=g)}),d&&(/^\s*$/.test(b.value||"")||E(f,c))}}function g(a){d(a,j)}var h=this,j=new f,l=a(W)[0];ca=h.scope=j,l[T]=C(l),a.each(h._rootCtrls,function(a,b){b.call(j,j)}),h._rootCtrls=[],a.isFun(b)&&b.call(j,j),a("body").on({change:g,keyup:g}),a(H).on({DOMNodeInserted:function(b){var c=b.target,d=C(c),e=d.scope;if(d){if(o(c)&&/^\s*$/.test(c.value||""))return;N(function(){var b=a(c).closest("[q-ctrl]")[0];k(c,b?e:ca),i(c,function(a){o(a)&&(k(a,b?e:ca),b||ca.apply(a.name))}),r(c,e,!0)},30)}},DOMNodeRemoved:e}),j.apply(""),r(l,j,!0),c()},config:function(a){return L(Q,a),Q},ctrl:function(a,b){return arguments.length<1?O:(J(a)?L(O,a):O[a]=b,this)}});var da=f.prototype;L(da,{watch:function(a,b){var c=this,d={};return J(a)?d=a:d[a]=b,M(d,function(a,b){c[V][a]=c[V][a]||[],c[V][a].push(b)}),c},$:function(b){return I(b)?a(this[X]):a(b,this[X])},on:function(b,c){a(this[X]).on(b,c)},off:function(b,c){a(this[X]).off(b,c)},once:function(b,c){a(this[X]).once(b,c)},set:function(b,c,d){var e=[],f=this;return a.isPlainObject(b)?(d=c,a.each(b,function(a,b){e.push(a),v(f,a,b)})):(e=[b],v(f,b,c)),f.apply(e,d),f},get:function(a){return v(this,a)},apply:function(c,e){var f=this;a.isFun(c)?(e=c,c=[]):c=a.isArray(c)?c:a.isString(c)?[c]:[],c=m(c,(aa[f.__name]||{}).names),g(f);var h=(aa[f.__name]||{}).tasks||[];h.push(function(b){a.execCatch(e),b&&b()}),aa[f.__name]={scope:f,names:c,tasks:h,callback:function(){function b(b,c){var e,g=a.likeArray(b);M(b,function(a,b){e=g?b:a,D(f,e),d({target:f[X],name:e,value:f[e]},f,!0),c&&c(e,f)})}c.length<1&&(c=[],M(f[Z],function(a){c.push(a)})),b(c,B),a.series(h)}},N(b,_+2)}});var ea=/(\$\{\s*[\w\._-]*\s*\})|(\{\{\s*[\w\._-]*\s*\}\})/g,fa=/\s*((^(\$|\{)\{)|(\}?\}$))\s*/g,ga=/<\s*script/g;ea.compile(ea),fa.compile(fa),ga.compile(ga);var ha;a.app=function(b,c){return c=a.isFun(b)?c:b,c=a.isObject(c)?c:{},ha=ha||new e(c),0==c.compile||ha.compile?a.isFun(b)&&(ha.scope?K(b,[ha.scope]):ha._rootCtrls.push(b)):(ha.compile=!0,a(function(){ha.__init(b)})),ha}}(Qmik),Qmik("[data-href]").live({click:function(a){window.location.href=$(this).attr("data-href")}});