/**
 * 图片轮播的公共方法js
 * @param imgZoomSlide
 */
(function($){
	$.fn.imgZoomSlide = function(param1, param2){
		if (typeof param1 == 'string') {
	        var isMethod = $.fn.imgZoomSlide.methods[param1];
	        if (isMethod) {
	            return isMethod(this, param2);
	        }
	    }
		param1 = param1 || {};
		return this.each(function(){
			var eleJson = $.data(this, 'imgZoomSlide');
			if(eleJson){
				$.extend(eleJson.options, param1);
			}else{
				eleJson = $.data(this, 'imgZoomSlide', {
					options: $.extend(true, $.fn.imgZoomSlide.defaults, param1)
				});
			}
			
			domInit.call(this,eleJson.options);
			
			function domInit(options){
				if(!!eleJson.options.width){
					$(this).width(eleJson.options.width);
				}
				eleJson.options.width = $(this).width()-64;
				
				if(!!eleJson.options.height){
					$(this).height(eleJson.options.height);
				}
				eleJson.options.height = $(this).height()-15;

				$(this).css({
					width : options.width,
					height : options.height,
					left : 32
				});
				
				$(this).addClass('slideBox').empty(); 
				var $hd = $("<div class='hd'><ul></ul></div>").hide();  
				var $bd = $("<div class='bd'><ul></ul></div>");
				var $prev = $("<a class='prev' href='javascript:void(0)'></a>"); //上一条
				var $next = $("<a class='next' href='javascript:void(0)'></a>"); //下一条
				for(var i=0;i<options.imgUrls.length;i++){
					$hd.find("ul").append("<li>"+(i+1)+"</li>"); //计算li的值
					var $li = $("<li>").appendTo($bd.find("ul"));
					var $zoom = $("<div>");
					$zoom.appendTo($li);
					
				}
				$(this).append($hd).append($bd).append($prev).append($next);
				$(this).slide(options);
				
				for(var i=0;i<options.imgUrls.length;i++){
					var $zoom = $bd.find("ul li").eq(i).find("div");
					var zoomparam = {
							width:options.width,
							height:options.height,
							imgUrl:options.imgUrls[i]
					};
					var $img = $("<img src="+ options.imgUrls[i] +">");  //实际的图片链接
					$img.css({
						'height':zoomparam.height,
						'width':zoomparam.width,
						"text-align":"center",
						"position":"relative"
					}); //图片样式
					$zoom.append($img).append(zoomparam);
					
				}
				$hd.css({
					"margin-left": -$hd.width()/2
				}).show();
			} 
		});
	}
})(jQuery);




$(document).ready(function() {
	layout();
	a();
	
/*	$(".slideBox").slide({
		mainCell : ".bd ul",
		autoPlay : false
	});	*/
});


function layout(){
	var winWidth  = $(window).width();
	var winHeight  =  $(window).height();
	
	$('#topDiv').height(80);
	$('#topDiv').width(winWidth);
	$('#centerDiv').height(winHeight-$('#topDiv').outerHeight(true));
	$('#centerDiv').width(winWidth);
}

function a(){
	var imgArray = [];
		imgArray.push("bigbin/carousel/images/11.jpg");
		imgArray.push("bigbin/carousel/images/12.jpg");
		imgArray.push("bigbin/carousel/images/13.jpg");
		imgArray.push("bigbin/carousel/images/14.jpg");
		imgArray.push("bigbin/carousel/images/15.jpg");
		imgArray.push("bigbin/carousel/images/16.png");
		imgArray.push("bigbin/carousel/images/17.png");
	
	$("#image").imgZoomSlide({
		width: $("#image").parent("div").innerWidth() - 2,
		height: $("#image").parent("div").innerHeight() - 2,
		mainCell : ".bd ul",
		autoPlay : true,
		trigger:'mouseover',
		pnLoop:false, //是否支持轮播
		interTime:4000, //轮播的间隔时间
		imgUrls : imgArray //图片来源
	});
}