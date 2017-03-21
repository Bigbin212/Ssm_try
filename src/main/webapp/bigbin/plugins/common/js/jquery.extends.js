(function($){
	/**
	 * 所有插件都要依赖util.js
	 *
	 */
	
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
			
			//window从未加载过放大镜，才绑定离开事件
			if(!window.imgZoom){
				var newUnload = function(){
					//放大镜组件删除
					$(".slideBox.imgZoomSlidePlugin").each(function(){
						$(this).imgZoomSlide("destory");
					});
					
					//放大镜删除
					$(".thumbnails").parent(".zoomPlugin").each(function(){
						$(this).parent("div").imgZoom("destory");
					});
					
					$("body").empty();
				};
				var func = window.onbeforeunload;
				var newFunc = undefined;
				if(!!func){
					newFunc = function(){
						var rtn = func();
						if(!!rtn){
							return rtn;
						}else{
							newUnload();
						}
					}
				}else{
					newFunc = newUnload;
				}
				window.onbeforeunload = newFunc; 
				
				if(document.all){
					$("a[href='javascript:void(0)']").bind("click",function (e){
						e.preventDefault();
					});
				}
			}
			window.imgZoom = true;
			
			var eleJson = $.data(this, 'imgZoom');
			if(eleJson){
				eleJson.options = $.extend(true,{},eleJson.options, param1);
			}else{
				eleJson = $.data(this, 'imgZoom', {
					options: $.extend(true, {},$.fn.imgZoom.defaults, param1),
					zoomView: undefined			//外置dom,需要删除
				});
			}
			$(this).imgZoom("destory");
			
			domInit.call(this,eleJson.options);
			
			function domInit(options){
				
				//如果图片还未加载完毕
				if(!!options.onloading){
					options.nextImgUrl = options.imgUrl;
					return;
				}
				
				//标记放大镜组件正在加载
				options.onloading = true;
				
				$(this).empty();
				
				if(!!options.width)
					$(this).width(options.width);
				if(!!options.height)
					$(this).height(options.height);
				
				if(!!options.loading)
					$(this).showLoading();
			
				var $innerZoom = $("<div></div>");
				$innerZoom.css({
					"position": "relative",
					"width": "100%",
					"height": "100%",
					"text-align": "left"
				}).appendTo($(this));

				var $img = $("<div class='thumbnails'>").data("background-image", options.imgUrl);
				
				var imgCss = {ysCss:{}, slCss:{}};
				var $this = $(this);
				//后面追加的是callback函数
				setImgLayOut(imgCss, options.imgUrl, $(this).width(), $(this).height(), function(){
					
					//如果放大镜组件已经移除。
					if(!$this.data("imgZoom")){
						$this.hideLoading();
						return false;
					}
					
					var options = $this.data("imgZoom").options;
					if(!!options.loading)
						$this.hideLoading();
					options.onloading = false;
					if(!!options.nextImgUrl){
						options.nextImgUrl = undefined;
						$this.imgZoom(options);
					}else{
						
						$this.data("imgZoom").slCss = imgCss.slCss;
						
						//如果加载失败
						if(!!imgCss.slCss.error){
							var defaultImgErrorUrl = webSiteRoot + basePath + "/znjt/common/images/imgLoadError2.png";
							if(options.imgUrl != defaultImgErrorUrl){
								$this.imgZoom({
									isZoom : false,
									imgUrl : defaultImgErrorUrl
								});
							}
						}else{
							var $cloneImg = $img.clone(true);
							$img.css(imgCss.slCss).appendTo($innerZoom);
							$cloneImg.css(imgCss.ysCss);
							var zoomParam = {
								width: options.zoomWidth,			//放大镜的宽度
								height: options.zoomHeight,			//放大镜的高度
								zoom: options.zoom,					//和原图的比例(范围(0-1])
								isZoom: options.isZoom,				//是否开启放大镜功能
								img:{
									dom: $img[0],					//缩放图对象
									ysImg: $cloneImg[0],			//原始图对象
									imgWidth: imgCss.slCss.imgWidth,		//图片的原始宽度
									imgHeight: imgCss.slCss.imgHeight,		//图片的原始高度
									divWidth: $this.width(),		//图片外框当前宽度
									divHeight: $this.height()		//图片外框当前高度
								}
							};
							getZoom.call($this, zoomParam);
						}
					}
					//如果有回调函数
					if(typeof(options.callback) == "function"){
						options.callback();
					}
				});
			} 

			function getZoom(){
				var $div = undefined;									//图片的父div
				var $zoom = $("<div class='zoom'></div>");				//要放大的区域
				var $zoomView = $("<div class='zoomPlugin_zoomView'></div>");		//显示区域
				var blc = 1;
				$(this).data("imgZoom").zoomView = $zoomView;
				
				var tempOptions = null;
				
				var initDom = function(opts){
					tempOptions = $.extend(true,{},$.fn.imgZoom.defaults,opts);
					$div = $(tempOptions.img.dom).parent("div").addClass("zoomPlugin");
					
					if(!!opts.isZoom){
						$zoom.css({
							width:tempOptions.width,
							height:tempOptions.height
						}).appendTo($div).hide();
						
						blc = (tempOptions.img.imgWidth/$(tempOptions.img.dom).width())*tempOptions.zoom;
						$zoomView.css({
							width:tempOptions.width*blc,
							height:tempOptions.height*blc
						}).appendTo("body").hide().append($(tempOptions.img.ysImg));
					}
				};

				var initialize = function(opts){
					initDom(opts);
					
					if(!!opts.isZoom){
						$div.bind("mouseenter",this,function(e){
							$zoom.show();
							$zoomView.show();
							$div.mousemove();
						});
						
						$div.bind("mousemove",this,function(e){
							var divRect = getRect($div[0]);
							var imgRect = getRect(tempOptions.img.dom);
							var zoomLeft = e.pageX - $(window).scrollLeft() - divRect.left - tempOptions.width/2;
							var zoomTop = e.pageY - $(window).scrollTop() - divRect.top - tempOptions.height/2;
							if(zoomLeft < imgRect.left - divRect.left)
								zoomLeft = imgRect.left - divRect.left;
							if(zoomTop < imgRect.top - divRect.top)
								zoomTop = imgRect.top - divRect.top;
							if(zoomLeft > imgRect.right - tempOptions.width - divRect.left)
								zoomLeft = imgRect.right - tempOptions.width - divRect.left;
							if(zoomTop > imgRect.bottom - tempOptions.height - divRect.top)
								zoomTop = imgRect.bottom - tempOptions.height - divRect.top;
							var zoomCssJson = {
									left:zoomLeft,
									top:zoomTop
							};
							$zoom.css(zoomCssJson);
							
							var zoomRect = getRect($zoom[0]);
							//放大区域内的图片定位
							$zoomView.children("div").css({
								left: -(zoomRect.left - imgRect.left)*blc ,
								top: -(zoomRect.top - imgRect.top)*blc ,
								width: tempOptions.img.imgWidth,
								height: tempOptions.img.imgHeight
							});
							//放大区域的定位
							var zoomviewLeft = divRect.left + zoomLeft + tempOptions.width + 10;
							var zoomviewTop = divRect.top + zoomTop;
							//如果超出屏幕高度
							if(zoomviewTop + $zoomView.height() > $("body").height()){
								zoomviewTop = divRect.top + zoomTop + tempOptions.height 
									- $zoomView.outerHeight();
							}
							if(zoomviewLeft + $zoomView.width() > $("body").width()){
								zoomviewLeft = divRect.left + zoomLeft - $zoomView.outerWidth() - 10;
							}
							
							$zoomView.css({
								left: zoomviewLeft,
								top: zoomviewTop
							});
						});
						
						$div.bind("mouseleave",this,function(e){
							$zoom.hide();
							$zoomView.hide();
						});
						
						$zoom.bind("dblclick",this,function(e){
							
							var imgMsg = tempOptions.img;
							
							var imgUrl = $(e.target).prev().data("background-image");
							
							window.open(imgUrl, "_blank");
							return;
						});
					}
				}
				
				initialize(arguments[0]);
			}
		});
	}

	$.fn.imgZoom.methods ={
		destory: function(jq){
			if(!!$(jq).data("imgZoom").zoomView){
				$(jq).empty();
				$(jq).data("imgZoom").zoomView.remove();
			}
		}
	};
	
	//号牌号码插件默认参数
	$.fn.imgZoom.defaults = {
			width:undefined,
			height:undefined,
			isZoom:true,
			loading:true,
			zoomWidth:60,
			zoomHeight:60,
			zoom:1,
			imgUrl:undefined
	};
	
	//图片放大镜插件
	//依赖：util.js，superSlide.js
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
				eleJson.options.width = $(this).innerWidth();
				
				if(!!eleJson.options.height){
					$(this).height(eleJson.options.height);
				}
				eleJson.options.height = $(this).innerHeight();

				$(this).css({
					width : options.width,
					height : options.height
				});
				
				//组件需要删除之前的内容
				$(this).imgZoomSlide("destory");
				
				$(this).addClass('slideBox').addClass('imgZoomSlidePlugin').empty();
				var $hd = $("<div class='hd'><ul></ul></div>");
				var $bd = $("<div class='bd'><ul></ul></div>").css({
					"width": $(this).innerWidth()-70,
					"height": $(this).innerHeight()-70,
					"overflow":"hidden"
				});
				var $prev = $("<a class='prev' href='#'></a>");
				var $next = $("<a class='next' href='#'></a>");

				$(this).append($hd).append($bd).append($prev).append($next);

				for(var i=0;i<options.imgUrls.length;i++){

					var $li = $("<li>").appendTo($bd.find("ul"));
					
					//如果是字符串,则直接默认为图片的url
						//放大镜
						var $zoom = $("<div>").appendTo($li);
						var zoomparam = {};
						if(typeof(options.imgUrls[i])=="string"){
							zoomparam = {
									width:options.width - 70,
									height:options.height - 70,
									imgUrl:options.imgUrls[i],
									isZoom:true,
									loading:i==0?true:false
							}
						}else{
							zoomparam = options.imgUrls[i];
						}
						
						if(!!options.zoomWidth)
							zoomparam.zoomWidth = options.zoomWidth;
						if(!!options.zoomHeight)
							zoomparam.zoomHeight = options.zoomHeight;
						if(!!options.zoom)
							zoomparam.zoom = options.zoom;
						
						zoomparam.callback = function(index){
							return function(){
								//缩略图
								var $zoomPlugin = $bd.find("ul li").eq(index).children("div");
								var $span = $hd.find("ul li").eq(index).find("div.thumbnails");
								var slCss = $zoomPlugin.data("imgZoom").slCss;
								$span.css({
									"background-size": slCss["background-size"],
									"filter": slCss["filter"],
									"background-image": slCss["background-image"],
								});
							};
						}(i);
						$zoom.imgZoom(zoomparam);
						
						var $span = $("<div class='thumbnails' style='border:0px;position:relative;float:left;cursor:pointer;width:100%;height:100%;'>");
						$hd.find("ul").append($("<li>").append($span));
					}
				
				$(this).slide(options);
			} 
		});
	}
	
	$.fn.imgZoomSlide.methods ={
		destory: function(jq){
			//组件需要删除之前的内容
			$(jq).find("div.bd div.zoomPlugin, div.bd div.vedioContent").each(function(){
				if($(this).hasClass("zoomPlugin")){
					$(this).parent("div").imgZoom("destory");
				}
			});
			$(jq).find(".hd .thumbnails").remove();
		}
	};
	

})(jQuery);
