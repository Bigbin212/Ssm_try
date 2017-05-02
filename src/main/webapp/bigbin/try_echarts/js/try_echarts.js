/**
 * 测试
 */
$(function() {
	show();
	//setTimeout('my_refresh()',1000);//1秒刷新一次
	//setTimeout('show()',1000);
	setInterval('show()', 2000);
	
});

function show(){
	//窗口宽度
	var  winWidth = $(window).width();
	//窗口高度
	var  winHeight = $(window).height();
	//为这个echarts_view设置自适应窗口的大小
	$("#echarts_view").css({
		'width':winWidth,
		'height':winHeight
	});
	//初始化
	var myChart = echarts.init(document.getElementById('echarts_view'));         
	var heatData = [];
	for (var i = 0; i < 200; ++i) {
	    heatData.push([
	        100 + Math.random() * 20,
	        24 + Math.random() * 16,
	        Math.random()
	    ]);
	}
	for (var j = 0; j < 10; ++j) {
	    var x = 100 + Math.random() * 16;
	    var y = 24 + Math.random() * 12;
	    var cnt = 30 * Math.random();
	    for (var i = 0; i < cnt; ++i) {
	        heatData.push([
	            x + Math.random() * 2,
	            y + Math.random() * 2,
	            Math.random()
	        ]);
	    }
	}

	var option = {
	    backgroundColor: '#1b1b1b',
	    title : {
	        text: '热力图结合地图',
	        x:'center',
	        textStyle: {
	            color: 'white'
	        }
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: '{b}'
	    },
	    toolbox: {
	        show : true,
	        orient : 'vertical',
	        x: 'right',
	        y: 'center',
	        feature : {
	            mark : {show: false},
	            dataView : {show: true, readOnly: false},
	            restore : {show: false},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : false,
	    series : [
	        {
	            name: '',
	            type: 'map',
	            mapType: 'china',
	            roam: false,
	            hoverable: true, //鼠标放置显示区域
	           // selectedMode : 'multiple',
	            data:[],
	            heatmap: {
	                minAlpha: 0.1,
	                data: heatData
	            },
	            itemStyle: {
	                normal: {
	                    borderColor:'rgba(100,149,237,0.6)',
	                    borderWidth:0.5,
	                    areaStyle: {
	                        color: '#1b1b1b'
	                    }
	                }
	            }
	        }
	    ]
	};
		                    
    // 为echarts对象加载数据 
    myChart.setOption(option,true); 
    
    // 图表清空-------------------
    //myChart.clear();

    // 图表释放-------------------
    //myChart.dispose();
}


function my_refresh()
{
	/**
	 * 动态刷新页面
	 */
   window.location.reload();
}