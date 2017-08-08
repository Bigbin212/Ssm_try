/**
 * 测试
 */
$(function() {
	
		var defaultEchartMapOption = {
			    tooltip : {
			        trigger: ''
			    },
				series: [{
		            type: 'map',
		            map: '江苏',
			        roam: false, //是否支持缩放拖动
		            selectedMode : 'single', // 显示图例颜色标识（系列标识的小圆点），存在legend时生效
		            showLegendSymbol:true,
		            itemStyle:{
		                normal:{
		                	label:{show:true}
		                },
		                emphasis:{
		                	label:{show:true}
		                }
		            }
		        }]
		};
	
		echartMapInit();
		
		function echartMapInit(){
			var echartMap = echarts.init($('#chartMap')[0]);
			var curOption = $.extend({},defaultEchartMapOption,true);
			echartMap.setOption(curOption);
		}
});