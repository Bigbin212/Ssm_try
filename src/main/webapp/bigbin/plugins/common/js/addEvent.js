/**********************************************
	@function	IE6-9 placeholder与textarea的maxlength修复，并提供值改变时的回调函数供实时捕捉用户输入
				fixed oncontextmenu delete or cut and backspace
	@param		id: 必选参数，元素的id
	@param		fn: 可选参数，实时响应的回调函数
	@return		通过id获取的元素
	@note		原理说明：IE6-9动态生成另一个一样的背景透明的输入框叠加到本输入框下层，通过selectionchange、onpropertychange事件与keyup等事件实现实时捕捉，现代谷歌火狐内核的浏览器直接支持placeholder与maxlength，这里用input事件实现了实时输入捕捉；
				注意点：布局时应在要应用的表单元素父节点上进行相对定位
	@inadequate	不足：影响了页面结构，未实现事件代理，动态元素必须先插入文档之后再执行此函数实现动态添加的元素placeholder与maxlength修复；IE6-9 onpropertychange也能捕捉到js设置输入框值改变的事件，但现代谷歌火狐内核的浏览器捕捉不到
*/
function iChange(id, fn){
	//var obj = id.nodeType == 1 ? id : id.jQuery ? id[0] : document.getElementById(id),
	var obj = id.nodeType == 1 ? id : document.getElementById(id);
	var doc = obj.document || obj.ownerDocument || document,
		win = doc.defaultView || doc.parentWindow || window,
		inputClone = doc.createElement(obj.nodeName == 'INPUT' ? 'INPUT' : 'TEXTAREA'),
		advancedBrowser = 'placeholder' in inputClone,
		oldValAttr = 'oldvalue' + (+new Date());
	if(advancedBrowser){
		obj.addEventListener("input",function(e){
			fn && fn.call(this, e);
		},false);
	}else{
		//IE6-9
		var docElem = doc.documentElement,
			inpCloneStyle = inputClone.style,
			placeholder = obj.getAttributeNode('placeholder'),
			curstyle = obj.currentStyle,
			objstyle = obj.style;
		placeholder = placeholder ? placeholder.nodeValue : '';
		if(placeholder){
			//初始化inputClone
			inpCloneStyle.cssText = objstyle.cssText + ';position:absolute;color:graytext;background-color:transparent;border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent;';
			inputClone.className = obj.className;
			inputClone.disabled = true;
			if(obj.value === '' || obj.defaultValue === ''){
				inputClone.value = placeholder;
			}
			
			
			if(curstyle.position == 'static'){
				objstyle.position = 'relative';
			}else{
				if(typeof curstyle.zIndex == 'number' && curstyle.zIndex > 0){
					inpCloneStyle.zIndex = --curstyle.zIndex;
				}else{
					inpCloneStyle.zIndex = 0;
				}
			}
			inpCloneStyle.left = obj.offsetLeft+'px';
			inpCloneStyle.top = obj.offsetTop+'px';
			obj[oldValAttr] = obj.value;
		}
		var IEwatchChange = function(e){
			var e = win.event || e,
				maxLen = obj.getAttributeNode('maxlength'),
				maxLen = maxLen && parseInt(maxLen.nodeValue);
			
			if(e.type == 'selectionchange' || e.propertyName == "value"){
				var objVal = obj.value;
				if(obj[oldValAttr] !== objVal){
					if(placeholder){
						if(objVal === '') inputClone.value = placeholder;
						else inputClone.value = '';
					}
					//input的maxlength所有浏览器都支持，只有TEXTAREA不支持
					if(maxLen && obj.nodeName == 'TEXTAREA' && objVal.length > maxLen){
						obj.value = objVal.substring(0, maxLen);
					}
					obj[oldValAttr] = obj.value;
					//两次直接改值会造成光标到开头位置的bug，方法一：延迟后调用回调；方法二：回调中使用正则匹配一遍后再执行赋值
					fn && fn.call(obj, e);
					/*setTimeout(function(){
						fn && fn.call(obj, e);
					}, 0);*/
				}
			}
		};
		if(!win.XMLHttpRequest){
			//IE6无法聚焦修复
			obj.attachEvent('onclick',function(){
				obj.focus();
			});
			inputClone.attachEvent('onclick',function(){
				obj.focus();
			});
		}
		//现代型事件绑定
		obj.attachEvent("onfocus", function(){
			//主要解决IE9中oncontextmenu delete or cut and backspace事件在onpropertychange时不响应
			document.attachEvent("onselectionchange", IEwatchChange);
		});
		
		obj.attachEvent("onpropertychange", IEwatchChange);
		
		obj.attachEvent("onblur", function(){
			document.detachEvent("onselectionchange", IEwatchChange);
		});
		
		//事件全部绑定后再插入，尽量减少对dom重复渲染
		if(placeholder){
			objstyle.backgroundColor = 'transparent';
			//IE6,8,9 input在将输入框背景设成透明时无法聚焦bug修复
			objstyle.backgroundImage = 'url(about:blank)';
			obj.parentNode.insertBefore(inputClone, obj);
		}
	}
	return obj;
}