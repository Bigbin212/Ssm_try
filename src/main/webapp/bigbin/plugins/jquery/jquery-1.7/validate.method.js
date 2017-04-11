/**    
 * 文件名：validate.method.js     
 * 版本信息： jquery  元素验证信息 需要使用form包围
 *    
 */
/*******************************插件新功能-设置插件validator的默认参数*****************************************/
$.validator.setDefaults({
	errorElement:'div',
	errorPlacement: function(error, element) {
		error.addClass('tooltip tooltip-inner');
		element.after(error);
		var pos = $.extend({}, element.position(), {
			width: element.outerWidth(),
			height: element.outerHeight()
		  });
		  actualWidth = error.outerWidth();
		  actualHeight = error.outerHeight();
		error.css({
				display:'block',
				opacity:'0.8',
				top: pos.top - actualHeight,
				left: pos.left + pos.width / 2 - actualWidth / 2
			});
	},
	onfocusout: function( element ) {
		$(element).valid();
	},
	onkeyup: function(element) {
		$(element).valid();
	},
	focusInvalid: true
});

jQuery.extend(jQuery.validator.messages, {
        required: "字段不能为空!",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: $.validator.format("字段长度最多是 {0}!"),
		minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: $.validator.format("请输入一个最大为 {0} 的值"),
		min: $.validator.format("请输入一个最小为 {0} 的值")
});


/**
 * 防止用户名重复
 */
$.validator.addMethod("nameCheck", function(value, element,params) {
	var result = false;
	
	if(value == params){
		return true;
	}
	
	$.ajax({
	      url : "registerCheck",// 请求的URL  
	      type: "post",
	      data : {  
	           username : value
	       },
	       async : false, // 改异步为同步  
	       dataType : 'json',  
	       success : function(records) {  
	    	   result = records; 
	       }  
	}); 	
	 
	 if (result) {  
	     return false;  
	  } else {  
	     return true; 
	  }  
},$.validator.format("该名称已存在！"));


/**
 * 防止邮箱重复注册
 */
$.validator.addMethod("emailCheck", function(value, element,params) {
	var result = false;  
	
	if(value == params){
		return true;
	}
	$.ajax({  
	      url : "registerEmail",// 请求的URL  
	      type: "post",
	      data : {  
	           email : value
	       },
	       async : false, // 改异步为同步  
	       dataType : 'json',  
	       success : function(records) {  
	    	   result = records; 
	       }  
	}); 	
	 
	 if (result) {  
	     return false;  
	  } else {  
	     return true;  
	  }  
},$.validator.format("该邮箱账号已被注册使用!"));


/**
 * 正整数验证
 */
$.validator.addMethod("numberCheck", function(value, element) {
	  return this.optional(element) || /^[0-9]*[1-9][0-9]*$/.test(value);  
},$.validator.format("只能输入正整数!"));

/**
 * 经度
 */
$.validator.addMethod("jdCheck", function(value, element) {
	  return this.optional(element) || /^\d+(\.\d{1,6})?$/.test(value) && value>=0 && value<=180;   
},$.validator.format("经度有效范围0-180（6位小数）!"));

/**
 * 维度
 */
$.validator.addMethod("wdCheck", function(value, element) {
	  return this.optional(element) || /^\d+(\.\d{1,6})?$/.test(value) && value>=0 && value<=90;     
},$.validator.format("纬度有效范围0-90（6位小数）!"));

/**
 * 手机号码
 */
$.validator.addMethod("phoneNumber",function(value, element){
	return this.optional(element) || /^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/.test(value);
},$.validator.format("请输入有效的联系方式！"));

$.validator.addMethod("nameValidate",function(value,element){
	return this.optional(element)||/^[\u4E00-\u9FA5a-zA-Z0-9_-]+$/.test(value);
},$.validator.format("名称只能由中文,字母和数字以及下划线组成!"));