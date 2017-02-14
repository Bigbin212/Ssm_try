//JS操作cookies方法!
//写cookies
function setCookie(name,value,Days){
  var days = !Days? "1":Days;
  var exp = new Date();
  exp.setTime(exp.getTime() + days*24*60*60*1000);
  document.cookie = name + "="+ base64encode(escape(value)) + ";expires=" + exp.toGMTString();
}

//读取cookies
function getCookie(name){
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

  if(arr=document.cookie.match(reg)){
	  return (unescape(base64decode(arr[2])));
  }else{
      return null;
  }
}

//删除cookies
function delCookie(name){
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(name);
  if(cval!=null)
      document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}