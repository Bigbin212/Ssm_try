var _curTabIndex = 0;
$(document).ready(function() {
	
	resize();
	$(".tab a").click(function(){
		$(this).addClass('on').siblings().removeClass('on');
		_curTabIndex = $(this).index();
		$('.content-li').hide();		
		$('#tabContent'+_curTabIndex+'').show();
	});
});


function resize(){
	//窗口宽度
	var  winWidth = $(window).width()-3;
	//窗口高度
	var  winHeight = $(window).height()-3;
	
	$('.tab').width(winWidth);
	$('.tab').height(35);
	$('.content').width(winWidth);
	$('.content').height(winHeight - $('.tab').outerHeight(true));
	
	$('.content-li').width(winWidth);
	$('.content-li').height($('.content').height());
	
}