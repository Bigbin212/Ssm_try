/**
 * 用户管理
 * @author Bigbin
 */
//操作内容,way可取值[modify/add]
var oprJson = {way:null};
var formValidate = undefined;		//表单的验证对象
$(document).ready(function(){
	layout();
	window.onresize=layout;
	// 初始化组件
	initComponent();
	//事件初始化
	initElementEvent();
	//初始化分页控件
	queryVehicleData(0);
});


/**布局*/
function layout(){
	//减去2是边框
	var width  = $(window).width();
	var height  =  $(window).height();
	
	$('.main').width(width-2);
	$('.main').height(height-2);
	$('#main-content').height(height-155);

}

/**
 * 初始化事件
 */
function initElementEvent(){

	//检索查询按钮
	$('#searchBtn').on('click',function(){
		queryVehicleData(0);
	});
	//查询数据重置
	$('#resetBtn').on("click",function(){
		$('#username').val('');
		$('#email').val('');
	});
	
	// 调整每页显示数量后，直接查询
	$('#pageSizeSelect').on('change', function(evt, params) {
		queryVehicleData(0);
	});
	
/*	//详情
	$('#main-content ul').on('click','.detail-span',function(event){
		oprJson.way = "modify";
		
		var obj = $(this).closest('li');
		obj.click();
		//根据用户的编号加载详情
		initYhInfo(obj.attr('id'));
		$('#yhxqDialog').dialog("option","title", "用户详情");
		$( "#yhxqDialog" ).dialog('open');
		//停止冒泡
		event.stopPropagation();
	});*/
	
	//选中某个li时效果显示
	$('#main-content').on('click','li',function(e){		
		$('.detel-span').removeClass('select-delete');
		$(this).find('.detel-span').addClass('select-delete');
		$('.detail-span').removeClass('select-detail');
		$(this).find('.detail-span').addClass('select-detail');
		
		$('.flagDiv').removeClass("icon-success");
		$(this).find('.flagDiv').addClass("icon-success");
		$('.chooseDiv').removeClass("divSelected");
		$(this).find('.chooseDiv').addClass("divSelected");
		
		
	});
}


/**
 * 分页查询
 * @param page_index
 * @param jq
 */
function queryVehicleData(page_index,jq) {
	
	var params = getQueryParams();
	params.start = page_index*params.pageSize;
	$('#main-content ul').empty();
	//遮罩
	$('body').showLoading();
	
	$.ajax({
		type: "post",
		url:'queryDate' + '?r=' + new Date().getTime(),
		dataType: "json",
		data:params,
		success: function(response){
			$('body').hideLoading();
			if(response.msg){
				jAlert(response.msg);
				return;
			}
			if(!response.data || response.data.length<=0){
				$.sticky('没有搜到数据,试试修改查询条件!');
				return;
			}
			//插入并显示所有的用户概略图
			insertAndshowData(response.data);
		},
		error:function(xmlHttpRequest, textStatue, errorThrown){
			$('body').hideLoading();
			if(xmlHttpRequest.status && xmlHttpRequest.status=='12029'){
				jAlert("网络不通，或者平台已关闭!",'提示信息');
			}else if(xmlHttpRequest.status && xmlHttpRequest.status=='200'){
				jAlert("登陆超时");
			}else{
				jAlert("系统异常，请联系管理员");
			}
		}
	});
	//读取第一页时，查询总数
	if(page_index==0 && !jq){
		$('.pagination .pagination-total-Num').html('');
		$('#pageDiv').hideLoading();
		$('#pageDiv').showLoading({'loadcontent':'正在查询总数','loadingtotal':true});
		$.ajax({
			type: "post",
			url:'queryDate' + '?r=' + new Date().getTime(),
			dataType: "json",
			data:params,
			success: function(response){
				$('#pageDiv').hideLoading();
				$("#pagination").pagination(response.total, {
					 current_page:page_index,
					 items_per_page:params.pageSize,
					 callback:queryVehicleData,
					 num_display_entries:4,
					 enabledSticky:true,
					 displayTotal:true,
					 totalCount:response.total
				 });
			},
			error:function(xmlHttpRequest, textStatue, errorThrown){
				$('#pageDiv').hideLoading();
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
 * 初始化组件
 */
function initComponent(){
	 $('#pageSizeSelect').chosen({
			width : '100px',
			inherit_select_classes:true,
			disable_search : true
	 });
	//分页栏
	 $("#pagination").pagination(0, {
		 items_per_page:$('#pageSizeSelect').val(),
		 callback:queryVehicleData,
		 enabledSticky:true,
		 displayTotal:false
	 });
	 
	 $( "#yhxqDialog" ).dialog({  
			autoOpen: false,
			draggable: true,
			height: 300,
			width:500,
			modal: true,
			resizable: false,
			open: function(){
				$(this).scrollTop(0);
			},
			buttons: {
				"确定":function(){
					// 验证
					var checkResult = $("#yhgl_form").valid();
					if (!checkResult) {
						return;
					}else{
						save(oprJson.way);
					}
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
	 
}

/**
 * 查询信息
 */

function getQueryParams(){
	var params = {};
	
	var pageSize = $('#pageSizeSelect').val();
	var username = $('#username').val();
	var email =  $('#email').val();
	
	params.start = 0;
	params.pageSize = pageSize;
	params.username = username;
	params.email = email;
	return params;
}

/**
 * 插入并显示数据
 * @param data
 */
function insertAndshowData(data){
	
	var $ul = $('#main-content ul').empty();
	$.each(data, function(index){
		
		var li = $("<li class='vehicle-data-li'>");
		$ul.append(li);
		
		var topDiv = $("<div class='select-shadow'>");
		
		var flagDiv = $("<span class='flagDiv'></span>"); //显示被选中的li
		topDiv.append(flagDiv);
		
		var userName = $("<span class='device-name-span' title='用户名:"+this.username+"'>");
		userName.text(this.username);
		topDiv.append(userName);
		
		var detail_span = $("<span class='detail-span' title='详情'>&nbsp;</span>");
		topDiv.append(detail_span);
		var detel_span = $("<span class='detel-span' title='删除'>&nbsp;</span>");
		topDiv.append(detel_span);

		li.append(topDiv);

		var chooseDiv = $("<div class='chooseDiv' style='height:3px;width: 100%;'></div>"); //被选中的额外样式
		li.append(chooseDiv);
		
		var bottomDiv = $("<div class='time-div' title='注册时间"+zcsjFormat(this.zcsj)+"'>");
		bottomDiv.text(zcsjFormat(this.zcsj));
		li.append(bottomDiv);
		
		li.data("data", this);
	});
	
	//默认选中第一个
	$("#main-content ul li").eq(0).click();	
	
	//详情
	$('#main-content ul').on('click','.detail-span',function(event){
		oprJson.way = "modify";
		
		var obj = $(this).closest('li');
		obj.click();
		//加载用户详情
		initDeviceForm(obj.data("data"));
		$('#yhxqDialog').dialog("option","title", "用户详情");
		$( "#yhxqDialog" ).dialog('open');
		//停止冒泡
		event.stopPropagation();
	});
	
	//删除
	$('#main-content ul').on('click','.detel-span',function(event){
		oprJson.way = "delete";
		
		var obj = $(this).closest('li');
		obj.click();
		console.log(obj.data("data").xlh);
		//停止冒泡
		event.stopPropagation();
	});
}

/**
 * 点击修改的时候表单的数据
 * @param data
 */
function initDeviceForm(data){
	var nameAbleVal = {
			username:'',
			email:''
	};
	$('#tBxlh').val(data.xlh);
	$('#tBusername').val(data.username);
	$('#tBpassword').val(data.password);
	$('#tBphone').val(data.phone);
	$('#tBemail').val(data.email);
	$('#tBzcsj').val(zcsjFormat(data.zcsj));
	
	if (formValidate) {
		formValidate.destroy();
	}
	
	
	nameAbleVal.username = data.username;
	nameAbleVal.email = data.email;
	Valid(nameAbleVal);
}

/**
 * 校验信息(jquery validate)
 * @param nameAbleVal
 */
function Valid(nameAbleVal){
	//验证输入对象
	formValidate = $("#yhgl_form").validate({
		onfocusout: function(element) { $(element).valid(); },
		onkeyup: function(element) { $(element).valid(); },
		focusInvalid: true,
		errorPlacement : function(error, element) {
			error.css({
				'font-family' : 'Microsoft Yahei',
				'color' : 'red',
				'overflow' : 'hidden',
				'margin-left' : '10px',
				'position' : 'absolute',
				'width' : 'auto',
				'left' : element.position().left + element.outerWidth(true),
				'top' : element.position().top + 7
				});
			element.after(error);
		},
		rules: {
			tBusername:{
				required: true,
				nameValidate: true,
				nameCheck:nameAbleVal.username
			},
			tBpassword:{
				required: true
			},
			tBphone :{
				required :false,
				phoneNumber :true
			},
			tBemail:{
				required : true,
				emailCheck:nameAbleVal.email
			}
		},
		messages:{
			tBusername:{
				required: '请输入用户名!',
				nameCheck:'赶紧换一个名称吧!'
			},
			tBpassword:{
				required: '请输入密码!'
			},
			tBemail:{
				required : '请输入注册邮箱!',
				emailCheck:'偷偷的告诉你，这个被注册了!'
			}
		}
	});
}
/**
 * 保存用户信息
 */
function save(type){
		
	var saveParam = {};
	saveParam.xlh = $('#tBxlh').val();
	saveParam.username = $('#tBusername').val();
	saveParam.password = $('#tBpassword').val();
	saveParam.phone =  $('#tBphone').val();
	saveParam.email = $('#tBemail').val();
	
	$.ajax({
		type : "POST",// 以POST方式提交数据。
		url : "./yhxxUpdate",
		data : saveParam,
		dataType : "json",
		success : function(data) {
			oprJson = {way:null};//操作状态初始化
		}
	});
	
}

function zcsjFormat(value){
	 if(value==null){
		  return "";
	  }else{
		  return new Date(value.time).format("yyyy-MM-dd hh:mm:ss");
	  }
}