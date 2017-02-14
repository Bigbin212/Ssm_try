/**
 * @description 总的尝试些什么
 * @author Bigbin
 * @since 2016-03-09
 * 
 */
/** 使用方法:  
* 开启:MaskUtil.mask();  
* 关闭:MaskUtil.unmask();  
* MaskUtil.mask('其它提示文字...');  */
var MaskUtil = (function(){  
   var $mask,$maskMsg;  
   var defMsg = '正在处理，请稍待...';  
   function init(){
       if(!$mask){  
           $mask = $("<div></div>").css({  
             'position' : 'absolute' ,
             'left' : '0' ,
             'top' : '0' ,
             'width' : '100%' ,
             'height' : '100%' ,
             'opacity' : '0.3' ,
             'filter' : 'alpha(opacity=30)' ,
             'display' : 'none' ,
             'background-color': 'grey' ,
             'z-index':'9999'
           }).appendTo("body");  
       }  
       if(!$maskMsg){  
           $maskMsg = $("<div></div>").css({  
                 'position': 'absolute' ,
                 'top': '50%' ,
                 'margin-top': '-20px' ,
                 'padding': '5px 50px 5px 20px' ,
                 'width': 'auto' ,
                 'border-width': '0' ,
                 'border-style': 'solid' ,
                 'display': 'none' ,
                 'background-color': 'grey' ,
                 'font-size':'14px' ,
                 'z-index':'9999'  
               }).appendTo("body");  
       }  
       $mask.css({
    	   width:"100%",
    	   height:$(document).height()
       });  
       var scrollTop = $(document.body).scrollTop();  
       $maskMsg.css({  
           left:( $(document.body).outerWidth(true) - 190 ) / 2,
           top:( ($(window).height() - 45) / 2 ) + scrollTop  
       });    
   }  	       
   return {  
       mask:function(msg){  
           init();  
           $mask.show();  
           $maskMsg.html(msg||defMsg).show();  
       },
       unmask:function(){  
    	   if($mask)
    	   {
               $mask.hide();  
               $maskMsg.hide();  
    	   }
       }  
   }      
}()); 
   function doSomething(msg){ 
       MaskUtil.mask(msg);  
   } 
   
$(function() {
	InitCd();
/*	window.onresize=function(){
		menuInit();
	}; 

   if(document.createEvent) { 
	  var event = document.createEvent ("HTMLEvents"); 
	  event.initEvent("resize", true, true); 
	  window.dispatchEvent(event); 
   }*//*else if(document.createEventObject){ 
	 window.fireEvent("onresize"); 
   }*/
	
/*	//首页按钮
	$("#home").click(function(){
		window.location.reload();
	});
	
	$("#baiduhome").click(function(){
		openPage("http://cn.bing.com/");
	});*/
	
});

//菜单初始化
/*function menuInit(){
	//getElementById() 方法可返回对拥有指定 ID 的第一个对象的引用
	var menu_ul = document.getElementById("menu_ul");
	//getElementsByTagName() 方法可返回带有指定标签名的对象的集合
	var menu_ul_li = menu_ul.getElementsByTagName("li").length;
	//计算页面的宽度
	if (window.innerWidth){
		winWidth = window.innerWidth;
	}else if ((document.body) && (document.body.clientWidth)){
		winWidth = document.body.clientWidth;
	}
	//四舍五入取整数
	var midth = Math.round(winWidth/85);
	var ceil_average = Math.ceil(menu_ul_li/midth);
	if(ceil_average > 1){
		for(var i=0;i<menu_ul_li;i++){
			if(i+1 < midth){
				menu_ul.getElementsByTagName("li")[i].style.display='block';
			}else{
				menu_ul.getElementsByTagName("li")[i].style.display='none';
			}
		}
		document.getElementById("li_arrow").style.display='block';
	}else{
		document.getElementById("li_arrow").style.display='none';
	}
		
	var innerHtml = "";
	var Click = 0;
	imageSrc = "./bigbin/TryDiv/images/close_arrow.png";
	if(Click==0){
		var imageSrc = "./bigbin/TryDiv/images/open_arrow.png";
	}
	innerHtml += "<a onclick='fileImage_click(this)'";
	innerHtml += "style=background-image:url('"+imageSrc+"')";
	innerHtml += "></a>";
	$("#li_arrow").empty().html(innerHtml);	
}*/

/**
 * 箭头点击事件
 * @param obj
 */
/*function fileImage_click(obj){	
	//获取某个id里面的background-image的值: document.getElementById('configImage').style.backgroundImage;
	if(obj.style.backgroundImage.indexOf("open_arrow.png") > -1){
		obj.style.backgroundImage= 'url("./bigbin/TryDiv/images/close_arrow.png")';
	}else {
		obj.style.backgroundImage= 'url("./bigbin/TryDiv/images/open_arrow.png")';
	}
	
	$("#menu_ul li").each(function(index){
		if($(this)[0].style.display == 'none'){
			$(this)[0].style.display='block';
		}else{
			$(this)[0].style.display='none';
			document.getElementById("li_arrow").style.display='block';
		}
	});
}*/


function menu_click(obj,param){	
	openPage(param);
}
//打开页面
function openPage(param)
{
	if(param == "")
	{
		alert("页面URL为空,请检查。")
		return;
	}
	doSomething("页面正在加载中，请稍候...");
	$('#frameContent').attr("src",param);
}

function iframe_onload(obj)
{
	MaskUtil.unmask(); 
}

function Initmenu_ul(data){
	
//	var innerHtml = "<ul id='menu_ul'>";
	var innerHtml = "";
	for(var i=0; i<data.length;i++){
		innerHtml += '<li> <a href="#"';
		innerHtml += 'onclick="menu_click(this,\'' + data[i].jdcs + '\')">';
		innerHtml += data[i].jdzwm;
		innerHtml += '</a></li>';
	}
	/*innerHtml += ' <li><a href="#">'+'onclick="window.location.reload()">'+"首页"+'</a></li>';*/
	innerHtml += '<li><a href="#"'+'onclick="menu_click(this,\'' + "http://cn.bing.com/" + '\')">'+"bing"+'</a></li>';
	innerHtml += '<li><a href="#">'+"日用百货"+'</a></li>';
	innerHtml += '<li><a href="#">'+"google"+'</a></li>';
	innerHtml += '<li><a href="#">'+"1"+'</a></li>';
	innerHtml += '<li><a href="#">'+"2"+'</a></li>';
	innerHtml += '<li><a href="#">'+"3"+'</a></li>';
	innerHtml += '<li><a href="#">'+"4"+'</a></li>';
	innerHtml += '<li><a href="#">'+"5"+'</a></li>';
	innerHtml += '<li><a href="#">'+"6"+'</a></li>';
	innerHtml += '<li><a href="#">'+"7"+'</a></li>';
	innerHtml += '<li><a href="#">'+"8"+'</a></li>';
	innerHtml += '<li><a href="#">'+"9"+'</a></li>';
	innerHtml += '<li><a href="#">'+"0"+'</a></li>';
	innerHtml += '<li><a href="#">'+"10"+'</a></li>';
	innerHtml += '<li><a href="#">'+"11"+'</a></li>';
	innerHtml += '<li><a href="#">'+"12"+'</a></li>';
	innerHtml += '<li><a href="#">'+"13"+'</a></li>';
	innerHtml += '<li><a href="#">'+"14"+'</a></li>';
	innerHtml += '<li><a href="#">'+"15"+'</a></li>';
	innerHtml += '<li><a href="#">'+"16"+'</a></li>';
	innerHtml += '<li><a href="#">'+"17"+'</a></li>';
	innerHtml += '<li><a href="#">'+"18"+'</a></li>';
	innerHtml += '<li><a href="#">'+"19"+'</a></li>'; 
	innerHtml += '<li id="li_arrow"></li>';
//	innerHtml += "</ul>";
	$("#menu_ul").empty().html(innerHtml);
}

/**
 * 菜单初始化
 */
function InitCd(){
	$.ajax({
		type : "POST",
		url : "./initcd.do",
		data : {},
		dataType : "json",
		success : function(info) {
			if(!!info.msg){
				alert(info.msg);
			}else if(info.success){
				//alert(JSON.stringify(info));
				Initmenu_ul(info.data);	
			}
		}
	});
}


