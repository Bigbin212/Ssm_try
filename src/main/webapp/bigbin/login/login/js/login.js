/**
 * @description 登录页面
 * @author bigbin
 */

var deviceFormValidate = undefined;
$(document).ready(function() {
	//初始化验证框
	initValidateEvent() ;
	//初始化执行一些函数
	init();
	
	if(getCookie("checked")){
		$("#checkbox_check").addClass("checked");
		$('#username').val(getCookie("username"));
		$('#password').val(getCookie("password"));
	}else{
		$("#checkbox_check").removeClass("checked");
		$('#username').val(getCookie("username"));
		$('#password').val(getCookie("password"));
	}
});
function init(){
	//防止火狐下会出现残留的input数据
	$("#loginForm")[0].reset();
	//初始化的时候默认选择的是用户名文本框
	document.getElementById("username").focus();
	//创建验证码
	//createCode();
	
	// 得到焦点
	$("#password").focus(function() {
		$("#left_hand").animate({
			left : "150",
			top : " -38"
		}, {
			step : function() {
				if (parseInt($("#left_hand").css("left")) > 140) {
					$("#left_hand").attr("class", "left_hand");
				}
			}
		}, 2000);
		$("#right_hand").animate({
			right : "-64",
			top : "-38px"
		}, {
			step : function() {
				if (parseInt($("#right_hand").css("right")) > -70) {
					$("#right_hand").attr("class", "right_hand");
				}
			}
		}, 2000);
	});
	// 失去焦点
	$("#password").blur(function() {
		$("#left_hand").attr("class", "initial_left_hand");
		$("#left_hand").attr("style", "left:100px;top:-12px;");
		$("#right_hand").attr("class", "initial_right_hand");
		$("#right_hand").attr("style", "right:-112px;top:-12px");
	});
	
	$("#username").keyup(function(e) {
		login_error(false);
	    // 回车键事件  
	    if(e.which == 13) {  
	    	login();
	       }  
	 });
	
	$("#password").keyup(function(e) { 
		login_error(false);
	    // 回车键事件  
	    if(e.which == 13) {  
	    	login();
	       }  
	 });
	
	// 忘记密码
	$("#forgetpass").attr("disabled","disabled");
	// 注册
	$("#register").on('click', function() {
		resetDeviceForm();
	});
	// 登录按钮点击事件
	$("#login").on('click', function() {
		login();
	});
	//点击看不清楚时的事件
	/*$("#see").on('click',function(){
		createCode();
	})
*/
	
	login_error(false);
	/*$("input[type='checkbox']").click(function() {
		checkbox_check();
	});*/
	
	$("#checkbox_check").click(function(){
		if(!$(this).hasClass("checked")){
			$(this).addClass("checked");
			$("#tsy").addClass("login_error");
			$("#tsy").html("请勿在公共电脑上勾选此选项！");
			return;
		}
		$(this).removeClass("checked");
		$("#tsy").removeClass("login_error");
		$("#tsy").html("");
	});
	
}

//验证框实现
function initValidateEvent() {
	//验证输入对象
	deviceFormValidate = $("#loginForm").validate({
		rules: {
			username:{
				required: true
			},
			password:{
				required: true
			}
		},
		messages:{
			username:{
				required: '请输入用户名或者邮箱!'
			},
			password:{
				required: '请输入密码!'
			}
		}
	});
}



/**
 *登录 
 */
function login() {
	var checkResult = $("#loginForm").valid();
	var form = formData();
	
	if(!checkResult){
		return false;
	}else{
		$.ajax({
			type : "post",// 以POST方式提交数据。
			url : "login_check.do",
			cache : false,
			async : false,
			data : form,
			dataType : "json",
			success : function(result) {
				if(result.success == "0"){
					login_error(true);
					$("#login_error").html(result.msg);
				}else{
					if($("#checkbox_check").hasClass("checked")){
						setCookie("checked",true); 
						setCookie("username",form.username); 
						setCookie("password",form.password);
					}else{
						delCookie("checked");
						delCookie("username");
						delCookie("password");
					}
					if(form.username == "admin" && form.password == "admin"){
						window.location = "./yhgl.do";
					}else{
						window.location = "./mainview.do";
					}
					
				}
			},
			error: function(xmlHttpRequest, textStatue, errorThrown){
				if(xmlHttpRequest.status && xmlHttpRequest.status=='12029'){
					jAlert("网络不通，或者平台已关闭!",'提示信息');
				}else if(xmlHttpRequest.status && xmlHttpRequest.status=='200'){
					jAlert("登陆超时");
				}else{
					jAlert("系统异常，请联系管理员");
				}
				
			}
		});
	}
}

/**
 * 用户名密码输错时
 * @param flag
 */
function login_error(flag){
	if(flag){
		$("#login_error").removeClass("").addClass("login_error");
	}else{
		$("#login_error").removeClass("login_error").addClass("");
		$("#login_error").html("have a try");
	}
}
/**
 * checkbox是否处于选中状态
 */
/*function checkbox_check(){
	var flag = $("input[type='checkbox']").is(':checked');
	if(flag){
		$("#checkbox_check").removeClass("").addClass("login_error");
		$("#checkbox_check").html("请勿在公共电脑上勾选此选项！");
	}else{
		$("#checkbox_check").removeClass("login_error").addClass("");
		$("#checkbox_check").html("");
	}
}*/

/**
 * 表单数据
 * @returns form
 */
function formData(){
	var form = {};
	form.username = $.trim($('#username').val());
	form.password = $.trim($('#password').val());
	return form;
}

/*
 * 初始化防止缓存
 */
function resetDeviceForm(){
	$('#username').val('');
	$('#password').val('');
	$("#checkbox_check").removeClass("checked");
	//清空输入框
	if(!deviceFormValidate){
		deviceFormValidate = $("#loginForm").validate();
	}
	//重置验证信息
	deviceFormValidate.resetForm();
	window.location = "./register.do";
}

