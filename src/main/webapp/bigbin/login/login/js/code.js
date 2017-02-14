var code ; //在全局 定义验证码    
function createCode(){     
	code = "";    
	var codeLength = 4;//验证码的长度    
	var checkCode = document.getElementById("checkCode");
	var random_family = parseInt(5*Math.random());   
	checkCode.value = "";    
	var selectChar = new Array(
				  1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f','g','h','j','k','l',
				 'm','n','p','q','r','s','t','u','v','w','x','y','z','A','B','C',
				 'D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U',
				 'V','W','X','Y','Z'
			 );    
	    
	for(var i=0;i<codeLength;i++) {    
	   var charIndex = Math.floor(Math.random()*60);    
	   code +=selectChar[charIndex];    
	}    
	if(code.length != codeLength){    
	  createCode();    
	}    
	checkCode.value = code;
	$("#checkCode").css('font-family',font_family(random_family));
}    
    
   
/**
 * 验证验证码输入的是否正确
 * @returns {Boolean}
 */
function validate () {    
	var inputCode = document.getElementById("verification").value.toUpperCase();
	var codeToUp = code.toUpperCase();
	if(!!inputCode){
		if(inputCode != codeToUp){
			//alert("验证码输入错误！");
			$.messager.alert('提示', "验证码输入错误！",'warning');   
			createCode();
			return false;
		}else{
			return true;
		}		
	}else{
		//alert("请输入验证码！");   
		$.messager.alert('提示', "请输入验证码！",'info');
		return false;    
	}    
}   

function font_family(n){
	var x = '';
	switch (n)
	{
		case 0:
		  x='标楷体';
		  break;
		case 1:
		  x='楷体';
		  break;
		case 2:
		  x='楷体 _GB2312';
		  break;
		case 3:
		  x='幼圆';
		  break;
		case 4:
		  x='Arial';
		  break;
		case 5:
		  x='华文琥珀';
		  break;
		 default:
		   x='Arial';
	}
	return x;
}

