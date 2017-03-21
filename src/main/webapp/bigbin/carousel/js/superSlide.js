/**
 * 图片轮播的公共方法js
 * @param imgZoomSlide
*/


$(document).ready(function() {
	layout();
	a();
	
/*	$(".slideBox").slide({
		mainCell : ".bd ul",
		autoPlay : false
	});	*/
});


function layout(){
	var winWidth  = $(window).width()-5;
	var winHeight  =  $(window).height()-5;
	var $topDiv = $('#topDiv');
	var $centerDiv = $('#centerDiv');
	$topDiv.height(80);
	$topDiv.width(winWidth);
	$centerDiv.height(winHeight - $topDiv.outerHeight(true));
	$centerDiv.width(winWidth);
}

function a(){
	var imgArray = [];
		imgArray.push("bigbin/carousel/images/11.jpg");
		imgArray.push("bigbin/carousel/images/12.jpg");
		imgArray.push("bigbin/carousel/images/13.jpg");
		imgArray.push("bigbin/carousel/images/14.jpg");
		imgArray.push("bigbin/carousel/images/15.jpg");
		imgArray.push("bigbin/carousel/images/16.png");
		imgArray.push("bigbin/carousel/images/17.png");
	
		$("#image").imgZoomSlide({
			width: $("#image").parent().innerWidth(),
			height: $("#image").parent().innerHeight(),
			mainCell : ".bd ul",
			autoPlay : false,
			imgUrls : imgArray
		});
}