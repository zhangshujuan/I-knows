window.mobileUtil=function(a,b){var c=navigator.userAgent,d=/android|adr/gi.test(c),e=/iphone|ipod|ipad/gi.test(c)&&!d,f=d||e;return{isAndroid:d,isIos:e,isMobile:f,isNewsApp:/NewsApp\/[\d\.]+/gi.test(c),isWeixin:/MicroMessenger/gi.test(c),isQQ:/QQ\/\d/gi.test(c),isYixin:/YiXin/gi.test(c),isWeibo:/Weibo/gi.test(c),isTXWeibo:/T(?:X|encent)MicroBlog/gi.test(c),tapEvent:f?"tap":"click",fixScreen:function(){function c(a){return"initial-scale="+a+",maximum-scale="+a+",minimum-scale="+a}var d=b.querySelector('meta[name="viewport"]'),g=d?d.content:"",h=g.match(/initial\-scale=([\d\.]+)/),i=g.match(/width=([^,\s]+)/);if(d){if(f&&!h&&i&&"device-width"!=i[1]){var j=parseInt(i[1]),k=a.innerWidth||j,l=a.outerWidth||k,m=a.screen.width||k,n=a.screen.availWidth||k,o=a.innerHeight||j,p=a.outerHeight||o,q=a.screen.height||o,r=a.screen.availHeight||o,s=Math.min(k,l,m,n,o,p,q,r),t=s/j;1>t&&(d.content=g+","+c(t))}}else{var u,v=b.documentElement,w=v.dataset.mw||750,x=e?Math.min(a.devicePixelRatio,3):1,t=1/x;v.removeAttribute("data-mw"),v.dataset.dpr=x,d=b.createElement("meta"),d.name="viewport",d.content=c(t),v.firstElementChild.appendChild(d);var y=function(){var a=v.getBoundingClientRect().width;a/x>w&&(a=w*x);var b=a/16;v.style.fontSize=b+"px"};a.addEventListener("resize",function(){clearTimeout(u),u=setTimeout(y,300)},!1),a.addEventListener("pageshow",function(a){a.persisted&&(clearTimeout(u),u=setTimeout(y,300))},!1),y()}},getSearch:function(b){b=b||a.location.search;var c={},d=new RegExp("([^?=&]+)(=([^&]*))?","g");return b&&b.replace(d,function(a,b,d,e){c[b]=e}),c}}}(window,document),mobileUtil.fixScreen();