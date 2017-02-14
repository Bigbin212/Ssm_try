$(document).ready(function() {
	showImage();//显示要展示的图片
	$('.slideshow').each( function() {
		  var $slideshow = $(this);
		  $slideshow.imagesLoaded( function() {
			$slideshow.skidder({
			  slideClass    : '.slide',
			  animationType : 'css',
			  scaleSlides   : true,
			  maxWidth      : 1300,
			  maxHeight     : 500,
			  paging        : true,
			  autoPaging    : true,
			  pagingWrapper : ".skidder-pager",
			  pagingElement : ".skidder-pager-dot",
			  swiping       : true,
			  leftaligned   : false,
			  cycle         : true,
			  jumpback      : false,
			  speed         : 400,
			  autoplay      : false,
			  autoplayResume: false,
			  interval      : 4000,
			  transition    : "slide",
			  afterSliding  : function() {},
			  afterInit     : function() {}
			});
		  });
	});
	$(window).smartresize(function(){
		  $('.slideshow').skidder('resize');
	});
});
function showImage(){
	var divHtml = [];
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/11.jpg"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/12.jpg"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/13.jpg"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/14.jpg"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/15.jpg"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/16.png"></div>');
	divHtml.push('<div class="slide"><img src="bigbin/carousel/images/17.png"></div>');
	
	$('.slideshow').empty().append(divHtml.join(''));
}
