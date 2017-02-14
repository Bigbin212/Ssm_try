(function($){
	
	//图片放大镜插件
	//依赖：util.js
	$.fn.imgZoom = function(param1, param2){
		if (typeof param1 == 'string') {
	        var isMethod = $.fn.imgZoom.methods[param1];
	        if (isMethod) {
	            return isMethod(this, param2);
	        }
	    }
		param1 = param1 || {};
		return this.each(function(){
			var eleJson = $.data(this, 'imgZoom');
			if(eleJson){
				eleJson.options = $.extend(true,{},eleJson.options, param1);
			}else{
				eleJson = $.data(this, 'imgZoom', {
					options: $.extend(true, {},$.fn.imgZoom.defaults, param1)
				});
			}
			
			domInit.call(this,eleJson.options);
			
			function domInit(options){
				$(this).empty().css({
					'text-align':'left'
				});
				if(!!options.width)
					$(this).width(options.width);
				if(!!options.height)
					$(this).height(options.height);
				
				if(!!options.loading)
					$(this).showLoading();
				try{
					var imgCss = getImgLayOut(options.imgUrl,$(this).width(),$(this).height());
					var $img = $("<img src="+ options.imgUrl +">");
					$img.css(imgCss).appendTo($(this));
					var zoomParam = {
						width:options.zoomWidth,			//放大镜的宽度
						height:options.zoomHeight,			//放大镜的高度
						zoom:options.zoom,					//和原图的比例(范围(0-1])
						isZoom: options.isZoom,				//是否开启放大镜功能
						img:{
							dom:$img[0],					//被放大的图片对象
							imgWidth:imgCss.imgWidth,		//图片的原始宽度
							imgHeight:imgCss.imgHeight,		//图片的原始高度
							divWidth:$(this).width(),		//图片当前宽度
							divHeight:$(this).height()		//图片当前高度
						}
					};
					getZoom(zoomParam);
					if(!!options.loading)
						$(this).hideLoading();
				}catch(e){
					if(!!options.loading)
						$(this).hideLoading();
					$(this).imgZoom({
						isZoom : false,
						imgUrl : "znjt/common/images/imgLoadError.png"
					});
				}
			} 

			function getZoom(){
				var $div = undefined;									//图片的父div
				var $zoom = $("<div class='zoom'></div>");				//要放大的区域
				var $zoomView = $("<div class='zoomView'></div>");		//显示区域
				var blc = 1;		
				
				var Zoom = function(options){
					this.initialize(options);
				};
				
				Zoom.fn = Zoom.prototype = {
						initDom:function(opts){
							this.options = $.extend(true,{},$.fn.imgZoom.defaults,opts);
							$div = $(this.options.img.dom).parent("div").addClass("zoomPlugin");
//							$div.data('imgZoom').options = this.options;
							$zoom.css({
								width:this.options.width,
								height:this.options.height
							}).appendTo($div).hide();
							blc = (this.options.img.imgWidth/$(this.options.img.dom).width())*this.options.zoom;
							$zoomView.css({
								width:this.options.width*blc,
								height:this.options.height*blc
							}).appendTo($div).hide().append($(this.options.img.dom).clone(true));
						},
						initialize:function(opts){
							this.initDom(opts);
							
							if(!!opts.isZoom){
								$(this.options.img.dom).add($zoom).bind("mouseenter",this,function(e){
									e.data.onmouseenter(e);
								});
								
								$(this.options.img.dom).add($zoom).bind("mousemove",this,function(e){
									e.data.onmousemove(e);
								});
								
								$(this.options.img.dom).add($zoom).bind("mouseleave",this,function(e){
									e.data.onmouseleave(e);
								});
							}
						},
						onmouseleave:function(e){
							$zoom.hide();
							$zoomView.hide();
						},
						onmouseenter:function(e){
							$zoom.show();
							$zoomView.show();
						},
						onmousemove:function(e){
							var divRect = getRect($div[0]);
							var imgRect = getRect(this.options.img.dom);
							var zoomLeft = e.pageX - $(window).scrollLeft() - divRect.left - this.options.width/2;
							var zoomTop = e.pageY - $(window).scrollTop() - divRect.top - this.options.height/2;
							if(zoomLeft < imgRect.left - divRect.left)
								zoomLeft = imgRect.left - divRect.left;
							if(zoomTop < imgRect.top - divRect.top)
								zoomTop = imgRect.top - divRect.top;
							if(zoomLeft > imgRect.right - this.options.width - divRect.left)
								zoomLeft = imgRect.right - this.options.width - divRect.left;
							if(zoomTop > imgRect.bottom - this.options.height - divRect.top)
								zoomTop = imgRect.bottom - this.options.height - divRect.top;
							var zoomCssJson = {
									left:zoomLeft,
									top:zoomTop
							};
							$zoom.css(zoomCssJson);
							
							var zoomRect = getRect($zoom[0]);
							//放大区域内的图片定位
							$zoomView.children("img").css({
								left: -(zoomRect.left - imgRect.left)*blc ,
								top: -(zoomRect.top - imgRect.top)*blc ,
								width: this.options.img.imgWidth,
								height: this.options.img.imgHeight
							});
							//放大区域的定位
							var zoomviewLeft = zoomLeft + this.options.width + 10;
							var zoomviewTop = zoomTop;
							//如果超出屏幕高度
							if(zoomviewTop + $zoomView.height() + divRect.top > divRect.bottom){
								zoomviewTop = divRect.bottom - divRect.top - $zoomView.outerHeight();
							}
							if(zoomviewLeft + $zoomView.width() + divRect.left > divRect.right){
								zoomviewLeft = zoomLeft - $zoomView.outerWidth() - 10;
							}
							
							$zoomView.css({
								left: zoomviewLeft,
								top: zoomviewTop
							});
						}
				}
				
				return new Zoom(arguments[0]);
			}
		});
	}
	
	
	//图片放大镜插件
	//依赖：util.js,superSlide.js
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
				
				$(this).addClass('slideBox').addClass('imgZoomSlidePlugin').empty();
				var $hd = $("<div class='hd'><ul></ul></div>").hide();
				var $bd = $("<div class='bd'><ul></ul></div>");
				var $prev = $("<a class='prev' href='javascript:void(0)'></a>");
				var $next = $("<a class='next' href='javascript:void(0)'></a>");
				for(var i=0;i<options.imgUrls.length;i++){
					$hd.find("ul").append("<li>"+(i+1)+"</li>");
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
							imgUrl:options.imgUrls[i],
							isZoom:true,
							loading:i==0?true:false
					};
					if(!!options.zoomWidth)
						zoomparam.zoomWidth = options.zoomWidth;
					if(!!options.zoomHeight)
						zoomparam.zoomHeight = options.zoomHeight;
					if(!!options.zoom)
						zoomparam.zoom = options.zoom;
					$zoom.imgZoom(zoomparam);
				}
				$hd.css({
					"margin-left": -$hd.width()/2
				}).show();
			} 
		});
	}
	
})(jQuery);
