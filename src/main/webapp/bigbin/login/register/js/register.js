/**
 * @description 注册页面
 * @author Bigbin
 */
var deviceFormValidate = undefined;
$(document).ready(function() {
	//显示遮罩
  	$('body').showLoading();
	resize();
	$(window).resize(function(){
		resize();
	});
	
	//初始化验证框
	initValidateEvent() ;
	initCom();
	span_class();
	$('#submitBtn').click(function(){
		submit();
	});
});

//验证框实现
function initValidateEvent() {
	//验证输入对象
	deviceFormValidate = $(".register_form").validate({
		rules: {
			username:{
				required: true,
				nameValidate: true,
				nameCheck:true
			},
			password:{
				required: true
			},
			spassword:{
				required: true,
				equalTo: "#password"
			},
			phone :{
				required :false,
				phoneNumber :true
			},
			email:{
				required : true,
				emailCheck:true
			/*	remote : {
					type : "POST",
					url :"registerEmail.do"+ "?r=" + new Date(),
					dataType: "json",
					async:false,
					data : {
						email :function(){ return $('#email').val();}
					},
					dataFilter: function (data) { //判断控制器返回的内容
                        if (data == "true") {
                            return false;
                        }else {
                            return true;
                        }
                    }
				}*/
			}
		},
		messages:{
			username:{
				required: '请输入用户名!'
			},
			password:{
				required: '请输入密码!'
			},
			spassword:{
				required: '请再次输入密码!',
				equalTo: "两次密码输入不一致"
			},
			email:{
				required : '请输入注册邮箱!'
			}
		}
	});
}

/**
 * 初始化组件
 */
function initCom(){
	$(".register_form")[0].reset();
	/*注册成功时的弹出框*/
	$("#successTip").dialog({
		autoOpen: false,
		draggable: true,
		title: '提示',
		height: 180,
		width: 350,
		modal: true,
		resizable: false
	});
	$('body').hideLoading();
}

/**
 * 注册的信息
 */
function formDate(){
	var form = {};
	form.username = $('#username').val();
	form.password = $('#password').val();
	//form.password = hex_md5($('#password').val());//MD5加密
	form.phone = $('#phone').val();
	form.email = $('#email').val();
	return form;
}

/**
 * 页面自适应
 */
function resize(){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	$('.right_half').width(450);
	$('.right_half').height(winHeight);
	$('.left_half').width(winWidth - $('.right_half').outerWidth(true));
}
/**
 * 提交注册信息并校验
 * @returns {Boolean}
 */
function submit(){
	// 验证
	var checkResult = $(".register_form").valid();
	var param = formDate();
	if (!checkResult) {
		return false;
	}else{
		$.ajax({
			type : "POST",// 以POST方式提交数据。
			url : "registerindex",
			dataType : "json",
			data : param,
			error:function(){
				jAlert( "系统异常！请联系管理员！",'提示');
			},
			success : function(data) {
				if (data.success == "1") {
					setCookie("username",param.username); 
					setCookie("password",param.password);
					$("#tipSuccess").html(data.msg+"点击确定后跳转到登录页面！");
					$("#successTip").dialog("open").dialog("option","buttons",{
						"确定": function(){
							$(this).dialog("close");
							window.location = "./login";
						},
						"取消": function(){
							$(this).dialog("close");
						}
					});
				
				} else {
					jAlert( data.msg,'提示');
				}
			}
		});
	}
}

/**
 * 数据验证成功后将span的样式做修改
 */
function span_class(){
	var n = $(".register_form_table").find("span").length;
    for(var i = 0;i<n;i++){
    	$(".register_form_table td").find("input").each = function(param){
    		$("input").eq(param).keyup(function(e){
	    			if(param == 3){
	    				if($(this).valid()){
							$("span[name='vText']").eq(param).removeClass("").addClass("span_success");
						}else{
							$("span[name='vText']").eq(param).removeClass("span_success").addClass("")
						}
	    			}else{
	    				if($(this).valid()){
							$("span[name='vText']").eq(param).text("");
							$("span[name='vText']").eq(param).removeClass("vText").addClass("span_success");
						}else{
							$("span[name='vText']").eq(param).text("*");
							$("span[name='vText']").eq(param).removeClass("span_success").addClass("vText")
						}
	    			}
				});
			}(i);
    	}
}