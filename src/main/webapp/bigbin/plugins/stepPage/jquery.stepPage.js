/**
 * CopyRight ©1995-2015: 苏州科达科技股份有限公司 
 * Project： Avatar2KITS 
 * Description： 仿百度经验 分步页面效果
 * Author： wq 
 * Create Date： 2015-09-30
 * History：
 */

(function($){
	$.fn.stepPage = function(param1, param2){
		//如果第一个参数为字符串，则请求相应的方法
		if (typeof param1 == 'string') {
	        var isMethod = $.fn.stepPage.methods[param1];
	        if (isMethod) {
	            return isMethod(this, param2);
	        }
	    }
		param1 = param1 || {};
		return this.each(function(){
			var eleJson = $.data(this, 'stepPage');
			if(eleJson){
				$.extend(eleJson.options,param2);
			} else {
				eleJson = $.data(this, 'stepPage', {
					options: $.extend({}, $.fn.stepPage.defaults, param1),
					dom:{stepPage : undefined,		//jquery对象
						stepLink : undefined,		//jquery对象
						stepHead : undefined,		//jquery对象
						stepBody : $(this),			//jquery对象
						stepList : undefined,		//jquery对象
						stepListIcon : []},			//jquery对象数组
					data: []
				});
			}
			initDom(this);
			mousemove(this);
		});
		
		function mousemove(jQ){
			var eleJson = $.data(jQ, 'stepPage');
			var $stepPage = eleJson.dom.stepPage;
			var $stepBody = eleJson.dom.stepBody;
			var $stepLink = eleJson.dom.stepLink;
			var $stepList = eleJson.dom.stepList;
			var $stepListArray = $(jQ).stepPage("getDom","stepListArray");
			var $stepListIcon = eleJson.dom.stepListIcon;
			
			if(!$stepLink){
				return;
			}
			
			//获取含滚动条的dom对象
			var $scrollDom = getScrollDom($stepPage);
			$scrollDom.hover(function(e){
				//滚动之前的判断
				if($stepLink.css("position").toLowerCase() != "fixed"){
					var abTop = 0;
					//自身不是滚轮
					if($stepPage[0] != $scrollDom[0]){
						abTop = $stepLink.position().top + $stepPage.offset().top - $stepPage.scrollTop();
					}
					//自身就是滚轮
					else{
						abTop = $stepLink.position().top + $stepPage.offset().top;
					}
					$stepLink.css({
						"position":"fixed",
						"top":abTop,
						"left":$stepBody.offset().left
					});
				}
			},function(e){
				//$stepPage.scrollTop() 用来分离scrolldom是不是stepPage的
				var abTop = $stepLink.offset().top - $stepPage.offset().top + $stepPage.scrollTop(); 
				$stepLink.css({
					"position":"absolute",
					"top":abTop,
					"left":$stepBody.position().left
				});
			});

			$scrollDom.scroll(function(e){
				//带滚动条的dom对象
				var $this = $(this); 
				$stepLink.children("a").each(function(index){
					switch($stepLink.css("position").toLowerCase()){
						case "fixed":
							//找到要操作的link元素
							if($stepLink.offset().top + $(this).height()*index >= $stepListIcon[index].offset().top
								|| ($stepListIcon[index].offset().top <= $scrollDom.offset().top + $scrollDom.height() && $stepLink.height() > $scrollDom.height())
								){
								$(this).css({"display":"inline-block"});
								$stepListIcon[index].css({"visibility":"hidden"});
								$(this).siblings().removeClass("list-icon-current");
								$(this).addClass("list-icon-current");
								
								//仅仅对真正的current做处理
								if(index < ($stepListIcon.length-1) && $stepLink.offset().top + $(this).height()*(index+1) < $stepListIcon[index+1].offset().top){
									//如果链接高度大于stepPage高度 需要进行重新定位
									if($stepLink.height() > $scrollDom.innerHeight()){
										//current超出情况下 跟随li自动走动
										if($stepLink.height()+$stepLink.offset().top >= $scrollDom.offset().top + $scrollDom.height()){
											var abTop = $($stepListIcon[index]).offset().top - ($stepLink.height() - $(this).height());
											if(abTop + $stepLink.height() < $scrollDom.offset().top + $scrollDom.height()){
												abTop = $scrollDom.offset().top + $scrollDom.height() - $stepLink.height();
												if($(this).offset().top + $(this).next().height() == $(this).next().offset().top){
													abTop = abTop + $stepListIcon[index+1].height();
												}
											}
											$stepLink.css({
												"top":abTop,
												"left":$stepBody.offset().left
											});
										}
										//current不超出 则固定在最下端
										else{
											var abTop = $scrollDom.offset().top + $scrollDom.height() - $stepLink.height();
											$stepLink.css({
												"top":abTop,
												"left":$stepBody.offset().left
											});
										}
										if($stepLink.offset().top > $scrollDom.offset().top){
											var abTop = $scrollDom.offset().top;
											$stepLink.css({
												"top":abTop,
												"left":$stepBody.offset().left
											});
										}
									}else{
										var abTop = $scrollDom.offset().top;
										$stepLink.css({
											"top":abTop,
											"left":$stepBody.offset().left
										});
									}
								}
								//否则 只需要关心最后一个 是否需要绝对定位
								else if(index == $stepListIcon.length - 1){
									if($stepLink.height()+$stepLink.offset().top > $($stepListArray[index]).offset().top){
										
										if($stepLink.height() > $scrollDom.innerHeight() 
												&& $stepLink.height() + $stepLink.offset().top >= $scrollDom.height() + $scrollDom.offset().top
												&& $stepLink.height() + $stepLink.offset().top < $($stepListArray[index]).offset().top + $($stepListArray[index]).outerHeight(false)
												){
											var abTop = $($stepListIcon[index]).offset().top - ($stepLink.height() - $(this).height());
											if(abTop + $stepLink.height() < $scrollDom.height() + $scrollDom.offset().top){
												abTop = $scrollDom.height() + $scrollDom.offset().top - $stepLink.height();
											}
											if(abTop > $scrollDom.offset().top){
												abTop = $scrollDom.offset().top;
												$(this).css({"display":"none"});
												$stepListIcon[index].css({"visibility":"visible"});
											}
											$stepLink.css({
												"top":abTop,
												"left":$stepBody.offset().left
											});
										}else if($stepLink.height() + $stepLink.offset().top > $($stepListArray[index]).offset().top + $($stepListArray[index]).outerHeight(false)
											|| 	$stepLink.height() > $scrollDom.innerHeight()
											){
											var abTop = $($stepListArray[index]).position().top + $($stepListArray[index]).outerHeight(true)+$stepPage.scrollTop();
											abTop -= $stepLink.height();
											$stepLink.css({
												"position":"absolute",
												"top":abTop,
												"left":$stepBody.position().left
											});
										}
									}
								}
							}else{
								$(this).css({"display":"none"});
								$stepListIcon[index].css({"visibility":"visible"});
							}
							break;
						case "absolute":
							//仅仅计算之后一个(前提是高度>link的高度)
							if(index == $stepListIcon.length - 1){
								$scrollDom.mouseenter();
							}
							break;
						default:
							break;
					}
				});
			});
		}
		 
		function initDom(jQ){
			var eleJson = $.data(jQ, 'stepPage');
			var options = eleJson.options;
			var data = eleJson.data;
			var dom = eleJson.dom;
			
			//DIV的标记
			if(jQ.tagName.toLowerCase() == "DIV".toLowerCase()){
				//插入父元素
				dom.stepPage = $('<div class="stepPage-content">').insertBefore(jQ);
				
				//初始化大小 width/height
				dom.stepPage.css({
					width: (!!options.width ? options.width:"auto"),
					height: (!!options.height ? options.height:"auto"),
					overflow:"auto"
				});
				
				//插入头部标题栏
				if(!!options.headText){
					dom.stepHead = $('<div class='+options.headCls+'>').appendTo(dom.stepPage);
					dom.stepHead.html(options.headText);
				}
				
				//内部初始化
				$(jQ).addClass("stepPage-content-body").appendTo(dom.stepPage);				
				dom.stepList = $('<ol class="stepPage-content-orderlist">').appendTo(jQ);
				var $children = $(jQ).children("div");
				$children.each(function(index){
					var $curlistItem = $('<li class="stepPage-content-list list-item-' + (index+1) + '">').appendTo( dom.stepList );
					$(this).appendTo($curlistItem);
					//icon插入
					var $listicon = $('<div class="list-icon">' + (index+1) + '</div>').prependTo($(this));
					dom.stepListIcon.push($listicon);
				});
				
				//link初始化
				if(!!options.stepLink){
					dom.stepLink = $('<div class="step-content-order">').appendTo(dom.stepPage);
					$children.each(function(index){
						var $linkicon = $('<a class="list-icon-link" title="点击返回第' + (index+1) + '步">'+ (index+1)+'</a>').appendTo(dom.stepLink);
						//绑定链接函数
						$linkicon.unbind("click").bind("click",function(e){
							//获取含滚动条的dom对象
							var $scrollDom = getScrollDom($(this).parents(".stepPage-content"));
							var index = parseInt($(this).text());
							var $stepListCur = $(this).parents(".stepPage-content").find("li.stepPage-content-list").eq(index-1);
							var smarginTop = $stepListCur.css("margin-top");
							smarginTop = smarginTop.substring(0,smarginTop.length-2);
							var spaddingTop = $stepListCur.css("padding-top");
							spaddingTop = spaddingTop.substring(0,spaddingTop.length-2);
							var topPy = $stepListCur.offset().top - $scrollDom.offset().top - parseInt(smarginTop) + parseInt(spaddingTop);
							$scrollDom.animate({"scrollTop":$scrollDom.scrollTop() + topPy},300);
						});
						//dom.stepLink的位置 取决于dom.stepPage的父DIV中 第一个stepPage的位置
						if((index+1) == 1){
							var $scrollDom = getScrollDom(dom.stepPage);
							var $dqDom = $scrollDom == dom.stepPage ? dom.stepPage : $scrollDom.find(".stepPage-content").eq(0);
							var offset = $dqDom.offset();
							offset.left = dom.stepPage.left;
							dom.stepLink.offset(offset);
						}
						
						//根据滚动条高度 初始化stepLink的显示情况
						if($linkicon.offset().top < dom.stepListIcon[index].offset().top){
							$linkicon.css({"display":"none"});
							dom.stepListIcon[index].css({"visibility":"visible"});
						}
					});
				}
				
				//执行onloadSuccess,参数为stepBody对象
				(options.onLoadSuccess)(dom.stepBody);
			}
			//OL的标记
			else if(jQ.tagName.toLowerCase() == "OL".toLowerCase()){
				
			}
		}
		
		function getScrollDom($stepPage){
			var $scrollDom = undefined;
			//找到施加scroll函数的对象
			
			//IE浏览器
			if(window.ActiveXObject){
				if($stepPage[0].scrollHeight > ($stepPage[0].clientHeight+1)
						&& $stepPage.css("overflow")=="auto"){
					$scrollDom = $stepPage;
				}else{
					var $temp = $stepPage.parent();
					while(!!$temp && $temp[0].scrollHeight > ($temp[0].clientHeight+1)
							&& $temp.css("overflow")!="auto"){
						$temp = $temp.parent();
					}
					$scrollDom = $temp;
				}
			}
			//火狐浏览器
			else{
				if($stepPage[0].scrollTopMax != 0){
					$scrollDom = $stepPage;
				}else{
					var $temp = $stepPage.parent();
					while(!!$temp && $temp[0].scrollTopMax == 0){
						$temp = $temp.parent();
					}
					$scrollDom = $temp;
				}
			}
			return $scrollDom;
		}
	}
	
	/**
	 * 属性说明
	 * 
	 * 属性名称			属性值类型		描述											默认值
	 * height			string/number	滚动插件的高度（包含head在内）					undefined
	 * width			string/number	滚动插件的宽度								undefined
	 * model			string			可选值 "normal"、"fullPage"					"normal"
	 * 									normal:正常滚动（类似百度经验）
	 * 									fullPage:每一个选择项 占独立的一页(暂未实现)
	 * headText			String			头部内容										undefined
	 * headCls			String			头部class(headText不为undefined时才有效)		"stepPage-content-head"
	 * stepLink			boolean			是否需要导航链接								true
	 * 									为false时，方法selectStep不可用
	 *
	 * 事件说明
	 * 事件名称			参数				描述
	 * onLoadSuccess	$stepBody		滚动插件加载完成之后执行的函数	
	 * 
	 */
	
	$.fn.stepPage.defaults = {
			height:undefined,
			width:undefined,
			model:"normal",
			headText:undefined,
			headCls:"stepPage-content-head",
			stepLink:true,
			onLoadSuccess:function($this){}
	};
	
	$.fn.stepPage.methods = {
			options: function(jq){
				return jq.data('stepPage').options;
			},
			getDom:function(jq,domType){
				var domJson = jq.data('stepPage').dom;
				if(!!domJson.stepLink){
					domJson.stepLinkArray = domJson.stepLink.children("a");
				}
				if(!!domJson.stepList){
					domJson.stepListArray = domJson.stepList.children("li"); 
				}
				if(!!domType){
					return domJson[domType];
				}else{
					return domJson;
				}
			},
			selectStep:function(jq,index){
				var $links =jq.stepPage("getDom","stepLinkArray");
				//有链接才进行操作
				if(!!$links){
					var max = $links.length - 1;
					index = index < 0 ? 0 : index;
					index = index > max ? max : index;
					$links[index].click();
				}
			},
			getStepLength:function(jq){
				var $lists =jq.stepPage("getDom","stepListArray");
				return $lists.length();
			}
	};
})(jQuery);