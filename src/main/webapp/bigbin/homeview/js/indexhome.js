var SEPARATION = 100, AMOUNTX = 100, AMOUNTY = 70;
var container;
var camera, scene, renderer;

var particles, particle, count = 0;

var mouseX = 85, mouseY = -342;

var windowHalfX = window.innerWidth / 2;
var windowHalfY = window.innerHeight / 2;

$(function() {
	init();
	//初始化尺寸
	resize();
	$(window).resize(function(){
		resize();
	});
	initComplement();
	animate();	
});


/*初始化*/
function init() {

	container = document.createElement('div');
	document.body.appendChild(container);

	camera = new THREE.PerspectiveCamera(120, window.innerWidth/ window.innerHeight, 1, 10000);
	camera.position.z = 1000;

	scene = new THREE.Scene();

	particles = new Array();

	var PI2 = Math.PI * 2;
	var material = new THREE.ParticleCanvasMaterial({

		color : 0xe1e1e1,
		program : function(context) {

			context.beginPath();
			context.arc(0, 0, .6, 0, PI2, true);
			context.fill();

		}

	});

	var i = 0;

	for (var ix = 0; ix < AMOUNTX; ix++) {

		for (var iy = 0; iy < AMOUNTY; iy++) {

			particle = particles[i++] = new THREE.Particle(material);
			particle.position.x = ix * SEPARATION - ((AMOUNTX * SEPARATION) / 2);
			particle.position.z = iy * SEPARATION - ((AMOUNTY * SEPARATION) / 2);
			scene.add(particle);

		}

	}

	renderer = new THREE.CanvasRenderer();
	renderer.setSize(window.innerWidth, window.innerHeight);
	container.appendChild(renderer.domElement);

	document.addEventListener('mousemove', onDocumentMouseMove, false);
	document.addEventListener('touchstart', onDocumentTouchStart, false);
	document.addEventListener('touchmove', onDocumentTouchMove, false);

	window.addEventListener('resize', onWindowResize, false);
}

//页面初始化
function onWindowResize() {

	windowHalfX = window.innerWidth / 2;
	windowHalfY = window.innerHeight / 2;

	camera.aspect = window.innerWidth / window.innerHeight;
	camera.updateProjectionMatrix();

	renderer.setSize(window.innerWidth, window.innerHeight);

}

//鼠标移动定位
function onDocumentMouseMove(event) {

	mouseX = event.clientX - windowHalfX;
	mouseY = event.clientY - windowHalfY;

}

function onDocumentTouchStart(event) {

	if (event.touches.length === 1) {

		event.preventDefault();

		mouseX = event.touches[0].pageX - windowHalfX;
		mouseY = event.touches[0].pageY - windowHalfY;

	}

}

function onDocumentTouchMove(event) {

	if (event.touches.length === 1) {

		event.preventDefault();

		mouseX = event.touches[0].pageX - windowHalfX;
		mouseY = event.touches[0].pageY - windowHalfY;

	}

}

function animate() {

	requestAnimationFrame(animate);

	render();

}

function render() {

	camera.position.x += (mouseX - camera.position.x) * .05;
	camera.position.y += (-mouseY - camera.position.y) * .05;
	camera.lookAt(scene.position);

	var i = 0;

	for (var ix = 0; ix < AMOUNTX; ix++) {

		for (var iy = 0; iy < AMOUNTY; iy++) {

			particle = particles[i++];
			particle.position.y = (Math.sin((ix + count) * 0.3) * 50) + (Math.sin((iy + count) * 0.5) * 50);
			particle.scale.x = particle.scale.y = (Math.sin((ix + count) * 0.3) + 1)* 2 + (Math.sin((iy + count) * 0.5) + 1) * 2;

		}

	}

	renderer.render(scene, camera);

	count += 0.1;

}

function resize(){
	var mainWith =  $('body').width()-2;
	var mainHeight = $('body').height() - $('.header-div').outerHeight(true);
	
	$('.main').width(mainWith);
	$('.main').height(mainHeight);
	
	$('.main-top').width(mainWith);
	$('.main-top').height(mainHeight/2);
	$('.iframe-div').width(mainWith/2);
	$('.iframe-div').height($('.main-top').height());
	
	$('.top-right').width($('.main-top').width() - $('.iframe-div').outerWidth());
	$('.top-right').height($('.main-top').height());
}

function initComplement(){
	$('.iframe-div').css({
		"display" : "block"
	});
	$('.iframe-style').attr("src",'./html5video.do');

	//显示当前登录的用户名
	showUserName();
	
	$.ajax({
		type : "POST",// 以POST方式提交数据。
		url : "getUserImage.do",
		dataType : "json",
		async : false,// 设置同步
		success : function(data) {
			if(data.url){
				var userImage = [];
				userImage.push('<i class="user-photo" style="background-image:url('+data.url+');"></i>');
				$(".logo-span").append(userImage);
			}
		},
		error : function() {
			jAlert('莎啦啦卡!', "提示");
		}
	});
	
	$(".header-info").hover(
			function(){
				$("#arrow").removeClass("arrow-up-white").addClass("arrow-down-white");
				$(".user-message ").addClass("mouseon");
			},
			function(){
				$("#arrow").removeClass("arrow-down-white").addClass("arrow-up-white");
				$(".user-message ").removeClass("mouseon");
			}
		)
}

/**
 * 显示当前登录的用户名
 * @description 如果为空就返回登录界面
 */
function showUserName(){
	$.ajax({
		url : './showUsername.do'+ '?r=' + new Date().getTime(),
		type : 'post',
		dataType : 'json',		
		cache : false,
		async: true,
		data :'',
		success : function(msg) {
			if(msg.username == ""){
				window.location = "./login.do";
			}else{
				$('.user-name').html(msg.username);
			}
		}
	});
}