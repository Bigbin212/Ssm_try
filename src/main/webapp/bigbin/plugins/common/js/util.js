/**      
 * 对Date的扩展，将 Date 转化为指定格式的String      
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符      
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)      
 * eg:      
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423      
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04      
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04      
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04      
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18      
 */        
Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"E"  : this.getDay(), //周几
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
}

//访问站点的根目录
var basePath = function(){
	var locationHref = window.location.href;
	if(locationHref.indexOf("/Ssm_try/") > -1){
		return "/Ssm_try";
	}else{
		return "";
	}
}();

var webSiteRoot = function(){
	var webSite = undefined;
	if(window.location.origin){
		webSite = window.location.origin;
	}else{
		var location = window.location;
		webSite = location.protocol + "//" + location.host;
	}
	return webSite;
}();

//判断是否是IE
function isIE(){
	return !!window.ActiveXObject || "ActiveXObject" in window;
}

//浏览器版本是否大于IE8
function isLgtIe8(){
	var DEFAULT_VERSION = "8.0";
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("msie")>-1;
	if(isIE){
	    var safariVersion =  ua.match(/msie ([\d.]+)/)[1];
	    if(safariVersion <= DEFAULT_VERSION){
	    	return false;
	    }
	}
    return true;
}

/**
 * 获取自适应缩放的css，兼容IE7、8
 * @param imgUrl
 */
function getBackgroundFilter(imgUri){
	var imgCss = {};
	//如果是IE8一下用滤镜
	if(!isLgtIe8()){
		imgCss["background-size"] = "cover";
		imgCss["filter"] = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+ imgUri +"',sizingMethod='scale')";
	}else{
		imgCss["background-image"] = "url('" + imgUri + "')";
		imgCss["background-size"] = "100% 100%"; 
	}
	return imgCss;
}

//设置图片的布局
/**
 * imgCss css样式!(原始图和缩略图)
 * imgUri 图片uri
 * divWidth	 外层的宽度
 * divHeight 外层的高度
 * 
 * 之所以放大镜采用背景图的方式，主要是为了解决IE8内存泄漏问题。
 */
function setImgLayOut(paramCss, imgUri, divWidth, divHeight ,callback){

	var defaultCss = {
		"text-align":"center",
		"position":"relative",
		"display": "inline-block",
		"background-repeate": "no-repeate"
	};
	
	var imgCss = paramCss.slCss;
	// 创建对象
	var img = new Image();   
	img.src = imgUri;
	//定时器触发
	var check = function(){
	    //图片已经缓存 或者  只要任何一方大于0,即可以视为大小获取成功
		//图片大小成功后，便可以立即调用回调函数
	    if(img.complete || img.width>0 || img.height>0){
	    	$(img).data("sizeloaded", true);
	    	clearInterval(set);
	    	
	    	imgCss.imgWidth = img.width;
	    	imgCss.imgHeight = img.height;
	    	
			if(img.width < divWidth && img.height < divHeight){
				imgCss.width = img.width;
				imgCss.height = img.height;
				imgCss.left = (divWidth - img.width)/2;
				imgCss.top = (divHeight - img.height)/2;
			}else{
				var imgBlc = img.width / img.height;
				var divBlc =  divWidth / divHeight;
				if(imgBlc > divBlc){
					imgCss.width = Math.round(divWidth);
					imgCss.height = Math.round((img.height*divWidth)/img.width);
					imgCss.left = 0;
					imgCss.top = Math.round((divHeight-(img.height*divWidth)/img.width)/2);
				}else{
					imgCss.width = Math.round((img.width*divHeight)/img.height);
					imgCss.height = Math.round(divHeight);
					imgCss.left = Math.round((divWidth-(img.width*divHeight)/img.height)/2);
					imgCss.top = 0;
				}
			}
			
			var imgcss_width = imgCss.width;
			var imgcss_height = imgCss.height;
			
			var tempUri = null;
			if(isIE()){
				tempUri = encodeURIComponent(encodeURIComponent(escape(imgUri)));
			}else{
				tempUri = imgUri;
			}
			
			//缩略图的url样式
			var sltUrl = webSiteRoot + basePath + "/JsPlugin/getThumbnails?width="
							+imgcss_width+"&height="+imgcss_height+"&imgUrl="+tempUri+"&isIE="+(isIE()?1:0);
			$.extend(paramCss.slCss, defaultCss, getBackgroundFilter(sltUrl));
			
			//如果原图URL IE background不能识别，则通过后来切换
			if(/[)]+/.test(imgUri)){
				var sltUrl1 = webSiteRoot + basePath + "/JsPlugin/getThumbnails?width="
				+imgCss.imgWidth+"&height="+imgCss.imgHeight+"&imgUrl="+tempUri+"&isIE="+(isIE()?1:0);
				$.extend(paramCss.ysCss, defaultCss, getBackgroundFilter(sltUrl1));
			}else{
				$.extend(paramCss.ysCss, defaultCss, getBackgroundFilter(imgUri));
			}
			
			callback();
	    }
	};
	var set = setInterval(check, 40);
	
	img.onerror = function(e1){
		clearInterval(set);
		imgCss.error = true;
		callback();
	};
	
	//加载完成事件
	img.onload = function(e1,e2,e3){};
	
}

//获取图片布局样式
function getImgLayOut(uri,divWidth,divHeight){
	var imgCss = {
			"text-align":"center",
			"position":"relative"
	};
	$.ajax({
		type:"post",
		url:basePath + "/JsPlugin/getImgLayout",
		data:{
			picUrl: uri,
			divWidth: parseInt(divWidth),
			divHeight: parseInt(divHeight)
		},
		dataType:"json",
		async:false,
		success:function(rst){
			$.extend(imgCss,rst);
		},
		error:function(xmlHttpRequest, textStatus, errorThrown){
			throw errorThrown;
		}
	});
	return imgCss;
}

function getTextFromSelect(val,$select,notFoundMsg){
	var rtnText = !!notFoundMsg?notFoundMsg:"未知";
	$select.find("option").each(function(index){
		if($(this).attr("value")==val){
			rtnText = $(this).text();
			return false;
		}
	});
	return rtnText;
}

function getRect(ele){
	var rect = undefined;
	try{
		rect = ele.getBoundingClientRect();
	}catch(e){
		rect = {top:0, bottom:0, left:0, right:0}
	}
	var top = document.documentElement.clientTop;
	var left= document.documentElement.clientLeft;
	return{
		top: rect.top - top,
		bottom: rect.bottom - top,
		left: rect.left - left,
		right : rect.right - left
	}
};
