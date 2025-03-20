/*
	日期: 2009/09/14
	作者: http://www.unwrongest.com/projects/limit/
	修改: abgne.tw

	修改內容:
	1.修改空白及換行均算一字元的功能判斷
*/
(function($){ 
     $.fn.extend({  
         limit: function(limit,element) {
			
			var interval, f;
			var self = $(this);
					
			$(this).focus(function(){
				interval = window.setInterval(substring,100);
			});
			$(this).blur(function(){
				interval;
				clearInterval(interval);
				substring();
			});
			
			substringFunction = "function substring(){ " +
					"var val = $(self).val();" +
					//"var length = val.replace(/|\\n/gim, '').length;" +
					//"var length = getByteLength(val);"+
					"var length = val.length;"+
					"if(length > limit){" +
					"	$(self).val($(self).val().substring(0,limit));" +
					"}";
			if(typeof element != 'undefined')
				substringFunction += "if($(element).html() != limit-length){" +
						"$(element).html((limit-length>=0)? '剩'+(limit-length)+'個字':'字數過長');" +
						"}"
				
			substringFunction += "}";
			eval(substringFunction);
			substring();
        } 
    }); 
})(jQuery);