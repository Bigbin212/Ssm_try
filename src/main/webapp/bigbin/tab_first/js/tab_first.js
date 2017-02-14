$(document).ready(function() {
	
	$('#billy_scroller').tab({
		slidePause : 5000,
		nextLink : $('#next'),
		prevLink : $('#prev'),
	});

	$('#tabber').tab({
		slidePause : 5000,
		indicators : $('ul#tabber_tabs'),
		customIndicators : true,
		autoAnimate : false,
		noAnimation : true
	});

});