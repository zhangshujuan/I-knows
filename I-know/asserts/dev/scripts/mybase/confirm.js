define(function(require, exports, module){
  		var $ = require("jquery");
  		var Modal = require("modal");
  		var str =['<div class="gd-modal modal-confirm" tabindex="-1" id="my-confirm">',
						'<div class="gd-modal-dialog">',														'<div class="gd-modal-bd">{{contain}}</div>',
							'<div class="gd-modal-footer">',
								'<span class="gd-modal-btn" data-gd-modal-cancel="">取消</span>',
								'<span class="gd-modal-btn" data-gd-modal-confirm="">确定</span>',
							'</div>',
						'</div>',
					'</div>'].join("");
		var confirm = function(contain){

			var sth = $(str.replace(/\{\{contain\}\}/g,contain));
				sth.appendTo("body");
			$("#my-confirm").modal("open").on("closed.modal.amui",function(){
				this.remove();
			});			
		}
		window.confirm = confirm;
		return confirm;
	});