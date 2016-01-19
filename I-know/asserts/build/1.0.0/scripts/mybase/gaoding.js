define(function(require, exports, module){
  var $ = require("jquery");
  var Modal = require("modal");
  var str =['<div class="gd-modal modal-confirm" tabindex="-1" id="myconfirm-1-1">',
			'<div class="gd-modal-dialog">',														
			'<div class="gd-modal-bd">{{contain}}</div>',
				'<div class="gd-modal-footer">',
					'<span class="gd-modal-btn" data-gd-modal-cancel="">取消</span>',
					'<span class="gd-modal-btn" data-gd-modal-confirm="">确定</span>',
				'</div>',
			'</div>',
		'</div>'].join("");
	var unPgLoad = function(){
		sessionStorage.setItem(location.pathname+"SCROLL_TOP",$(window).scrollTop())
	}
	var pgLoad = function(){
		$(window).scrollTop(sessionStorage.getItem(location.pathname+"SCROLL_TOP"))
	}
	pgLoad();
	var gaoding = {
		modalHtml:function(){
			var str = ['<div class="float-popWrap msgMode" style="display:none;">',
				'<div class="float-modePop">',
					'<div class="warnMsg">{{msg}}</div>',
					'<div class="content">{{content}}</div>',
					'<div class="doBtn">',
						'<button class="ok">确定</button>',
						'<button class="cancel">取消</button>',
					'</div>',
				'</div>',
			'</div>'].join("");
			return str;
		},
		getSrceenWH:function(){
			return {
				w:$(window).width(),
				h:$(window).height()
			}
		},
		setSameArr:function(data,_name,_val){
			if(data[_name]){
				if(!$.isArray(data[_name])){
					data[_name] = [data[_name]];
				}
				data[_name].push(_val)
			}else{
				data[_name] = _val
			}
			return data;
		},
		eventBind:function(options){
			options && $.each(options,function(index,itemFn){
				var nbs = index.split(' ');
				var evtName = nbs.pop();
				$(document).delegate(nbs.join(' '),evtName,(typeof(itemFn) == 'function')?itemFn:$.proxy(window,itemFn));
			});
			$('[gd-event]').each(function(index,item){
				var efn = $(item).attr('gd-event');
				if(!/\:/.test(efn)){
					return;
				}
				var evtName = efn.split(":");
				$(document).delegate(item,evtName[0],$.proxy(window,evtName[1]));
			})
		},
		jumpLink:function(selector){
			$(document).delegate("["+selector+"]","click",function(e){
				unPgLoad();
				var $this = $(e.currentTarget);
				window.location.href = $this.attr(selector);
			});
		},
		getFormData:function(_form){
			var selector = [
				'[type="text"][name]:enabled',
				'select[name]:enabled',
				'textarea[name]:enabled',
				'[type="hidden"][name]'
			]
			var data = {};
			$(_form).find(selector.join(',')).each(function(index,item){
				var _this = $(item),
					_name = $(item).attr('name'),
					_type = $(item).attr('type'),
					_val = $(item).val();
				var irs = (_type == "radio" && !_this.attr("checked")) || (_type == "checkbox" && !$_this.attr("checked"))
				if(irs) return;
				if(/\./.test(_name)){
					var nArr = _name.split('.');
					if(!data[nArr[0]]) data[nArr[0]] = {};
					data[nArr[0]] = gaoding.setSameArr(data[nArr[0]],nArr[1],_val);;
				}else{
					data = gaoding.setSameArr(data,_name,_val);
				}
			})
			return data;
		},
		alert:function(msg,callback,tm){
			var sth = $(gaoding.modalHtml().replace(/\{\{msg\}\}/g,msg).replace(/\{\{content\}\}/g,''));
			var wh = gaoding.getSrceenWH();
			sth.appendTo("body").fadeIn("fast").css({
				"position": "fixed",
				"left":(wh.w-sth.width())/2+"px",
				"top":(wh.h-sth.height())/2+"px"
			});
			setTimeout(function(){
				sth.fadeOut("fast",function(){
				   sth.remove();
				   callback && typeof(callback) == "function" && callback(sth);
				});
			},tm||1500)
		},
		loadings:{},
		loading:function(type){
			type = type?type:"default";
			gaoding.loadings[type] && gaoding.loadings[type].hide();
			if(!gaoding.loadings[type]){
                gaoding.loadings[type] = $(gaoding.modalHtml().replace(/\{\{msg\}\}/g,'<span class="animation-spin animation-reverse"><i class="iconfont icon-loading ft-fff"></i></span>').replace(/\{\{content\}\}/g,''));
			}
			var wh = gaoding.getSrceenWH();
			gaoding.loadings[type].appendTo("body").css({
				"position": "fixed",
				"left":(wh.w-gaoding.loadings[type].width())/2+"px",
				"top":(wh.h-gaoding.loadings[type].height())/2+"px"
			}).hide();
			return gaoding.loadings[type];
		},
		confirm:function(contain,callback){
			var sth = $(str.replace(/\{\{contain\}\}/g,contain));
				sth.appendTo("body");
			$("#myconfirm-1-1").modal("open").on("closed.modal.amui",function(){
				this.remove();
			}).on('confirm.modal.amui',function(){
				callback && typeof(callback) == "function" && callback(this)
			});			
		}
	}
	window.gaoding = gaoding;
	gaoding.jumpLink('data-href');
	return gaoding;
})
