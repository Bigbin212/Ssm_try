
$(function(){
	
	
/*	$("#syt").uploadify({
        'removeCompleted' : false,
        'swf'             : '/uploadify/uploadify.swf',
        'uploader'        : '/uploadify/uploadify.php' 
	 });*/
	ShowMessage();
	
	/**
	 * 示意图的onchange事件
	 */
	Onchange();
})

function Onchange(){
	$(".uploadImg ").on('change','#syt',
			function() {
				if(!(/^.*\.(jpg|gif|png)$/i.test($(this)[0].value))){
					jAlert("头像只能是jpg,gif和png格式!");
					clearFileInput();
					return;
		        }else if (window.File && window.FileReader && window.FileList && window.Blob){ //H5判断上传文件的大小
	                var fsize =$(this)[0].files[0].size;//获取上传文件的大小
	                if(fsize>1048576*5){ //设置上传文件不大于5M
	                	jAlert(fsize +" bites\n不能大于5M啦！！！！", "提示");
	                	clearFileInput();
	                    return;
	                }
		        }
			
				var options = {
					url :  "uploadDevImg.do" + "?r=" + new Date(),
					dataType : 'json',
					success : function(response) {
						clearFileInput();
						if (response.msg) {
							jAlert(response.msg);
							return;
						}
						// 如果有图片
						if (response.url) { //示意图
							initSytImgSrc(response.url);
							autoMainView();
						}
					},
					error : function(response) {
						clearFileInput();
					}

				};

				// 避免跳转
				$("#deviceForm").ajaxSubmit(options);
			}
	);
}

/**
 * 为了避免input  file onchange失效,移除后重新添加
 */
function clearFileInput() {
	// 为了避免onchange失效
	$("#syt").remove();
	var input = '上传 <input type="file" id="syt"  name="syt" class="file" accept="image/jpeg,image/png,image/gif" size="1" />';
	$(".uploadImg").html(input);
}

function initSytImgSrc(url){
	var img = new Image();
	img.src = url + "?r=" + Math.random();
	$('#userimg').attr('src', img.src);
}

function ShowMessage(){
	$.ajax({
		type : "POST",// 以POST方式提交数据。
		url : "getUserImage.do",
		dataType : "json",
		async : false,// 设置同步
		success : function(data) {
			if(data.url){ //示意图
				initSytImgSrc(data.url);
			}
		},
		error : function() {
			jAlert('莎啦啦卡!', "提示");
		}
	});
	$("#successTip").dialog({
		autoOpen: false,
		draggable: true,
		title: '提示',
		height: 155,
		width: 350,
		modal: true,
		resizable: false
	});
}

function autoMainView(){
	$("#tipSuccess").html("头像设置成功！点击确定后跳转到主页面！");
	$("#successTip").dialog("open").dialog("option","buttons",{
		"确定": function(){
			$(this).dialog("close");
			window.location = "./mainview.do";
		},
		"取消": function(){
			$(this).dialog("close");
		}
	});
}