$(function() {
	$('#fullpage').fullpage({
		'verticalCentered' : false,
		'css3' : true,
		'anchors' : [],
		'navigation' : false,
		'loopBottom' : false,
		'navigationPosition' : 'right',
		'navigationTooltips' : [],
	});

	/**
	 * tab 切换
	 */
	$('#myTabs a').click(function (e) {
		  e.preventDefault();
		  if(!$(this).parent().hasClass("disabled")){
			  $(this).tab('show');  
		  }
	});
	// disabled标记的内容不允许点击
	$(".disabled").bind('click mousedown', function(e) {
		e.preventDefault();
		return false;
	});
	
	/**
	 * tooltip 显示
	 */
	$(".disabled").tooltip({
		title : "这个是不能点击的哟！",
		placement : "bottom"
	});
	
	$(".img1").tooltip({
		title : "img-rounded",
		placement : "left"
	});
	$(".img2").tooltip({
		title : "img-circle",
		placement : "right"
	});
	$(".img3").tooltip({
		title : "img-thumbnail",
		placement : "bottom"
	});
});