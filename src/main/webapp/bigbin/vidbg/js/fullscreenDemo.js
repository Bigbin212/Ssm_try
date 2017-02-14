$(document).ready(function() {
	/**
	 * 假使是全屏
	 */
	$('body').vidbg({
			'mp4' : 'bigbin/vidbg/media/mp4_video.mp4',
			'webm' : 'bigbin/vidbg/media/webm_video.webm',
			'poster' : 'bigbin/vidbg/media/fallback.jpg',
		}, {
			muted : true,
			loop : true,
			overlay : true,
	});

});