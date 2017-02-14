   /**  
* 使用方法:  
* 开启:MaskUtil.mask();  
* 关闭:MaskUtil.unmask();  
*   
* MaskUtil.mask('其它提示文字...');  
*/  
   // MaskUtil Start  
   var MaskUtil = (function(){  
         
       var $mask,$maskMsg;  
         
       var defMsg = '正在处理，请稍待。。。';  
     
   function init(){  
       if(!$mask){  
           $mask = $("<div></div>")  
       .css({  
         'position' : 'absolute'  
         ,'left' : '0'  
         ,'top' : '0'  
         ,'width' : '100%'  
         ,'height' : '100%'  
         ,'opacity' : '0.3'  
         ,'filter' : 'alpha(opacity=30)'  
         ,'display' : 'none'  
         ,'background-color': 'grey'
         ,'z-index':'9999'
       })  
       .appendTo("body");  
   }  
   if(!$maskMsg){  
       $maskMsg = $("<div></div>")  
           .css({  
             'position': 'absolute'  
             ,'top': '50%'  
             ,'margin-top': '-20px'  
             ,'padding': '5px 50px 5px 20px'  
             ,'width': 'auto'  
             ,'border-width': '0'  
             ,'border-style': 'solid'  
             ,'display': 'none'  
             ,'background-color': 'grey'  
             ,'font-size':'14px'
             ,'z-index':'9999'  
           })  
           .appendTo("body");  
   }  
     
   $mask.css({width:"100%",height:$(document).height()});  
             
           var scrollTop = $(document.body).scrollTop();  
             
           $maskMsg.css({  
               left:( $(document.body).outerWidth(true) - 190 ) / 2  
               ,top:( ($(window).height() - 45) / 2 ) + scrollTop  
           });   
                     
       }  
         
       return {  
           mask:function(msg){  
               init();  
               $mask.show();  
               $maskMsg.html(msg||defMsg).show();  
           }  
           ,unmask:function(){  
               $mask.hide();  
               $maskMsg.hide();  
           }  
       }  
         
   }());  
   // MaskUtil End     
   function doSomething(msg){ 
       MaskUtil.mask(msg);  
       /*
       setTimeout(function(){  
       // 模拟操作时间,3秒后关闭  
       MaskUtil.unmask();  
       },3000);  
       */
   }
