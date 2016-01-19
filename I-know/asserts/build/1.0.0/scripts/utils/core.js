define(function(require, exports, module){
	var $ = require("jquery");
	var UI = {};
		UI.utils = {};
	var $win = $(window);
	var doc = window.document;
	var $html = $('html');
	UI.support = {};
	UI.support.transition = (function() {
	  var transitionEnd = (function() {
	    // https://developer.mozilla.org/en-US/docs/Web/Events/transitionend#Browser_compatibility
	    var element = doc.body || doc.documentElement;
	    var transEndEventNames = {
	      WebkitTransition: 'webkitTransitionEnd',
	      MozTransition: 'transitionend',
	      OTransition: 'oTransitionEnd otransitionend',
	      transition: 'transitionend'
	    };

	    for (var name in transEndEventNames) {
	      if (element.style[name] !== undefined) {
	        return transEndEventNames[name];
	      }
	    }
	  })();

	  return transitionEnd && {end: transitionEnd};
	})();

	UI.support.animation = (function() {
	  var animationEnd = (function() {
	    var element = doc.body || doc.documentElement;
	    var animEndEventNames = {
	      WebkitAnimation: 'webkitAnimationEnd',
	      MozAnimation: 'animationend',
	      OAnimation: 'oAnimationEnd oanimationend',
	      animation: 'animationend'
	    };

	    for (var name in animEndEventNames) {
	      if (element.style[name] !== undefined) {
	        return animEndEventNames[name];
	      }
	    }
	  })();

	  return animationEnd && {end: animationEnd};
	})();

	/* jshint -W069 */
	UI.support.touch = (
	('ontouchstart' in window &&
	navigator.userAgent.toLowerCase().match(/mobile|tablet/)) ||
	(window.DocumentTouch && document instanceof window.DocumentTouch) ||
	(window.navigator['msPointerEnabled'] &&
	window.navigator['msMaxTouchPoints'] > 0) || //IE 10
	(window.navigator['pointerEnabled'] &&
	window.navigator['maxTouchPoints'] > 0) || //IE >=11
	false);

	// https://developer.mozilla.org/zh-CN/docs/DOM/MutationObserver
	UI.support.mutationobserver = (window.MutationObserver ||
	window.WebKitMutationObserver || null);

	// https://github.com/Modernizr/Modernizr/blob/924c7611c170ef2dc502582e5079507aff61e388/feature-detects/forms/validation.js#L20
	UI.support.formValidation = (typeof document.createElement('form').
	  checkValidity === 'function');

	UI.utils.parseOptions = UI.utils.options = function(string) {
	  if ($.isPlainObject(string)) {
	    return string;
	  }

	  var start = (string ? string.indexOf('{') : -1);
	  var options = {};

	  if (start != -1) {
	    try {
	      options = (new Function('',
	        'var json = ' + string.substr(start) +
	        '; return JSON.parse(JSON.stringify(json));'))();
	    } catch (e) {
	    }
	  }

	  return options;
	};

	/* jshint +W054 */
	UI.utils.generateGUID = function(namespace) {
	  var uid = namespace + '-' || 'gd-';

	  do {
	    uid += Math.random().toString(36).substring(2, 7);
	  } while (document.getElementById(uid));

	  return uid;
	};
	// via http://davidwalsh.name/detect-scrollbar-width
	UI.utils.measureScrollbar = function() {
	  if (document.body.clientWidth >= window.innerWidth) {
	    return 0;
	  }
	}
	// http://blog.alexmaccaw.com/css-transitions
	$.fn.emulateTransitionEnd = function(duration) {
	  var called = false;
	  var $el = this;

	  $(this).one(UI.support.transition.end, function() {
	    called = true;
	  });

	  var callback = function() {
	    if (!called) {
	      $($el).trigger(UI.support.transition.end);
	    }
	    $el.transitionEndTimmer = undefined;
	  };
	  this.transitionEndTimmer = setTimeout(callback, duration);
	  return this;
	};

	$.fn.redraw = function() {
	  return this.each(function() {
	    /* jshint unused:false */
	    var redraw = this.offsetHeight;
	  });
	};

	/* jshint unused:true */

	$.fn.transitionEnd = function(callback) {
	  var endEvent = UI.support.transition.end;
	  var dom = this;

	  function fireCallBack(e) {
	    callback.call(this, e);
	    endEvent && dom.off(endEvent, fireCallBack);
	  }

	  if (callback && endEvent) {
	    dom.on(endEvent, fireCallBack);
	  }

	  return this;
	};
	return UI;
})