(function(f, g, c) {
	if (!Object.create) {
		Object.create = function(k) {
			if (arguments.length > 1) {
				throw new Error(
						"Object.create implementation only accepts the first parameter.")
			}
			function j() {
			}
			j.prototype = k;
			return new j()
		}
	}
	if (!Object.keys) {
		Object.keys = function(m) {
			if (m !== Object(m)) {
				throw new TypeError("Object.keys called on a non-object")
			}
			var j = [], l;
			for (l in m) {
				if (Object.prototype.hasOwnProperty.call(m, l)) {
					j.push(l)
				}
			}
			return j
		}
	}
	if (!Date.now) {
		Date.now = function a() {
			return (new Date).valueOf()
		}
	}
	if (!Function.prototype.bind) {
		Function.prototype.bind = function(j) {
			if (typeof this !== "function") {
				throw new TypeError(
						"Function.prototype.bind - what is trying to be bound is not callable")
			}
			var n = Array.prototype.slice.call(arguments, 1), m = this, k = function() {
			}, l = function() {
				return m.apply(this instanceof k && j ? this : j || f, n
						.concat(Array.prototype.slice.call(arguments)))
			};
			k.prototype = this.prototype;
			l.prototype = new k();
			return l
		}
	}
	if (!String.prototype.trim) {
		String.prototype.trim = function() {
			return this.replace(/^\s+|\s+$/g, "")
		}
	}
	if (!Array.isArray) {
		Array.isArray = function(j) {
			return Object.prototype.toString.call(j) === "[object Array]"
		}
	}
	if (typeof Array.prototype.forEach != "function") {
		Array.prototype.forEach = function(m, l) {
			var k, j;
			for (k = 0, j = this.length; k < j; ++k) {
				if (k in this) {
					m.call(l, this[k], k, this)
				}
			}
		}
	}
	if (typeof Array.prototype.map != "function") {
		Array.prototype.map = function(n, m) {
			var j = [];
			if (typeof n === "function") {
				for (var l = 0, o = this.length; l < o; l++) {
					j.push(n.call(m, this[l], l, this))
				}
			}
			return j
		}
	}
	if (typeof Array.prototype.filter != "function") {
		Array.prototype.filter = function(n, m) {
			var j = [];
			if (typeof n === "function") {
				for (var l = 0, o = this.length; l < o; l++) {
					n.call(m, this[l], l, this) && j.push(this[l])
				}
			}
			return j
		}
	}
	if (typeof Array.prototype.some != "function") {
		Array.prototype.some = function(m, l) {
			var o = false;
			if (typeof m === "function") {
				for (var j = 0, n = this.length; j < n; j++) {
					if (o === true) {
						break
					}
					o = !!m.call(l, this[j], j, this)
				}
			}
			return o
		}
	}
	if (typeof Array.prototype.every != "function") {
		Array.prototype.every = function(m, l) {
			var o = true;
			if (typeof m === "function") {
				for (var j = 0, n = this.length; j < n; j++) {
					if (o === false) {
						break
					}
					o = !!m.call(l, this[j], j, this)
				}
			}
			return o
		}
	}
	if (typeof Array.prototype.indexOf != "function") {
		Array.prototype.indexOf = function(m, n) {
			var l = -1;
			n = n * 1 || 0;
			for (var j = 0, o = this.length; j < o; j++) {
				if (j >= n && this[j] === m) {
					l = j;
					break
				}
			}
			return l
		}
	}
	if (typeof Array.prototype.lastIndexOf != "function") {
		Array.prototype.lastIndexOf = function(m, n) {
			var l = -1, o = this.length;
			n = n * 1 || o - 1;
			for (var j = o - 1; j > -1; j -= 1) {
				if (j <= n && this[j] === m) {
					l = j;
					break
				}
			}
			return l
		}
	}
	if (typeof Array.prototype.reduce != "function") {
		Array.prototype.reduce = function(o, j) {
			var n = j, l = 0, m = this.length;
			if (typeof j === "undefined") {
				n = this[0];
				l = 1
			}
			if (typeof o === "function") {
				for (l; l < m; l++) {
					this.hasOwnProperty(l) && (n = o(n, this[l], l, this))
				}
			}
			return n
		}
	}
	if (typeof Array.prototype.reduceRight != "function") {
		Array.prototype.reduceRight = function(o, j) {
			var n = this.length, l = n - 1, m = j;
			if (typeof j === "undefined") {
				m = this[n - 1];
				l--
			}
			if (typeof o === "function") {
				for (l; l > -1; l -= 1) {
					this.hasOwnProperty(l) && (m = o(m, this[l], l, this))
				}
			}
			return m
		}
	}
	var i = {
		querySelector : function(j) {
			return i.querySelectorAll.call(this, j)[0] || null
		},
		querySelectorAll : function(j) {
			return d(Sizzle(j, this))
		},
		getElementsByClassName : function(j) {
			return this.querySelectorAll("." + j.trim().replace(/\s+/, "."))
		},
		addEventListener : function(m, o, j) {
			var l = this, k = "";
			if (m == "input") {
				m = "propertychange"
			}
			if (typeof o != "function") {
				return

				

								

				

			}
			var n = function(p) {
				p = p || f.event || {};
				if (!p.target) {
					p.target = p.srcElement
				}
				if (!p.preventDefault) {
					p.preventDefault = function() {
						p.returnValue = false
					}
				}
				if (m == "propertychange") {
					if (p.propertyName !== "value" || l.r_oldvalue === l.value) {
						return

						

												

						

					}
					l.r_oldvalue = l.value
				}
				return o.call(l, p || {})
			};
			n.initFuncHandle = o;
			l.attachEvent("on" + m, n);
			if (l["event" + m]) {
				l["event" + m].push(n)
			} else {
				l["event" + m] = [ n ]
			}
		},
		dispatchEvent : function(k) {
			var j = k && k.type;
			if (j && this["event" + j]) {
				k.target = this;
				this["event" + j].forEach(function(l) {
					k.timeStamp = Date.now();
					l.call(this, k)
				}.bind(this))
			}
		},
		removeEventListener : function(k, m, j) {
			var l = this["event" + k];
			if (Array.isArray(l)) {
				this["event" + k] = l.filter(function(n) {
					if (n.initFuncHandle === m) {
						this.detachEvent("on" + k, n);
						return false
					}
					return true
				}.bind(this))
			}
		}
	};
	var d = function(j) {
		j.forEach(function(m, k) {
			for ( var l in i) {
				m[l] = i[l].bind(m)
			}
		});
		return j
	};
	g.querySelector = function(j) {
		return g.querySelectorAll(j)[0] || null
	};
	g.querySelectorAll = function(j) {
		var k = Sizzle(j);
		return d(k)
	};
	if (!g.getElementsByClassName) {
		g.getElementsByClassName = function(j) {
			return i.getElementsByClassName.call(g, j)
		}
	}
	if (typeof g.addEventListener == "undefined") {
		[ f, g ].forEach(function(j) {
			j.addEventListener = function(l, m, k) {
				i.addEventListener.call(j, l, m, k)
			};
			j.dispatchEvent = function(k) {
				i.dispatchEvent.call(j, k)
			};
			j.removeEventListener = function() {
				i.removeEventListener
						.call(j, eventType, funcHandle, useCapture)
			}
		})
	}
	if (!g.createEvent) {
		g.createEvent = function(j) {
			var k = {};
			switch (j) {
			case "Event":
			case "Events":
			case "HTMLEvents":
				k = {
					initEvent : function(m, n, l) {
						k.type = m;
						k.canBubble = n || false;
						k.cancelable = l || false;
						delete (k.initEvent)
					},
					bubbles : false,
					cancelBubble : false,
					cancelable : false,
					clipboardData : c,
					currentTarget : null,
					defaultPrevented : false,
					eventPhase : 0,
					returnValue : true,
					srcElement : null,
					target : null,
					timeStamp : Date.now(),
					type : ""
				};
				break;
			case "MouseEvents":
				k = {
					initMouseEvent : function(p, z, q, y, u, s, r, o, m, n, l,
							x, w, t, v) {
						k.type = p;
						k.canBubble = z || false;
						k.cancelable = q || false;
						k.view = y || null;
						k.screenX = s || 0;
						k.screenY = r || 0;
						k.clientX = o || 0;
						k.clientY = m || 0;
						k.ctrlKey = n || false;
						k.altKey = l || false;
						k.shiftKey = x || false;
						k.metaKey = w || false;
						k.button = t || 0;
						k.relatedTarget = v || null;
						delete (k.initMouseEvent)
					},
					altKey : false,
					bubbles : false,
					button : 0,
					cancelBubble : false,
					cancelable : false,
					charCode : 0,
					clientX : 0,
					clientY : 0,
					clipboardData : c,
					ctrlKey : false,
					currentTarget : null,
					dataTransfer : null,
					defaultPrevented : false,
					detail : 0,
					eventPhase : 0,
					fromElement : null,
					keyCode : 0,
					layerX : 0,
					layerY : 0,
					metaKey : false,
					offsetX : 0,
					offsetY : 0,
					pageX : 0,
					pageY : 0,
					relatedTarget : null,
					returnValue : true,
					screenX : 0,
					screenY : 0,
					shiftKey : false,
					srcElement : null,
					target : null,
					timeStamp : Date.now(),
					toElement : null,
					type : "",
					view : null,
					webkitMovementX : 0,
					webkitMovementY : 0,
					which : 0,
					x : 0,
					y : 0
				};
				break;
			case "UIEvents":
				k = {
					initUIEvent : function(n, p, m, l, o) {
						k.type = n;
						k.canBubble = p || false;
						k.cancelable = m || false;
						k.view = l || null;
						k.detail = o || 0;
						delete (k.initUIEvent)
					},
					bubbles : false,
					cancelBubble : false,
					cancelable : false,
					charCode : 0,
					clipboardData : c,
					currentTarget : null,
					defaultPrevented : false,
					detail : 0,
					eventPhase : 0,
					keyCode : 0,
					layerX : 0,
					layerY : 0,
					pageX : 0,
					pageY : 0,
					returnValue : true,
					srcElement : null,
					target : null,
					timeStamp : Date.now(),
					type : "",
					view : null,
					which : 0
				};
				break;
			default:
				throw new TypeError(
						"NotSupportedError: The implementation did not support the requested type of object or operation.")
			}
			return k
		}
	}
	if (!("addEventListener" in g.createElement("div"))) {
		var h = f.location, b = h.href, e = h.hash;
		setInterval(function() {
			var k = h.href, j = h.hash;
			if (j != e && typeof f.onhashchange === "function") {
				f.onhashchange({
					type : "hashchange",
					oldURL : b,
					newURL : k
				});
				b = k;
				e = j
			}
		}, 100)
	}
	if (typeof f.getComputedStyle !== "function") {
		f.getComputedStyle = function(m, n) {
			var j = {};
			var k = m.currentStyle || {};
			for ( var l in k) {
				j[l] = k[l]
			}
			j.styleFloat = j.cssFloat;
			j.getPropertyValue = function(p) {
				var o = /(\-([a-z]){1})/g;
				if (p == "float") {
					p = "styleFloat"
				}
				if (o.test(p)) {
					p = p.replace(o, function() {
						return arguments[2].toUpperCase()
					})
				}
				return m.currentStyle[p] ? m.currentStyle[p] : null
			};
			return j
		}
	}
})(window, document);
/*
 * ! Sizzle CSS Selector Engine v@VERSION http://sizzlejs.com/
 * 
 * Copyright 2013 jQuery Foundation, Inc. and other contributors Released under
 * the MIT license http://jquery.org/license
 * 
 * Date: @DATE
 */
(function(aw) {
	var E, az, l, u, N, Q, ac, aD, O, ad, af, I, v, ao, aj, ax, k, L, aq = "sizzle"
			+ -(new Date()), P = aw.document, aA = 0, ak = 0, d = G(), ap = G(), M = G(), K = function(
			i, e) {
		if (i === e) {
			ad = true
		}
		return 0
	}, av = typeof undefined, W = 1 << 31, U = ({}).hasOwnProperty, at = [], au = at.pop, S = at.push, b = at.push, t = at.slice, j = at.indexOf
			|| function(aF) {
				var aE = 0, e = this.length;
				for (; aE < e; aE++) {
					if (this[aE] === aF) {
						return aE
					}
				}
				return -1
			}, c = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped", w = "[\\x20\\t\\r\\n\\f]", a = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+", R = a
			.replace("w", "w#"), am = "\\[" + w + "*(" + a + ")" + w
			+ "*(?:([*^$|!~]?=)" + w + "*(?:(['\"])((?:\\\\.|[^\\\\])*?)\\3|("
			+ R + ")|)|)" + w + "*\\]", r = ":("
			+ a
			+ ")(?:\\(((['\"])((?:\\\\.|[^\\\\])*?)\\3|((?:\\\\.|[^\\\\()[\\]]|"
			+ am.replace(3, 8) + ")*)|.*)\\)|)", y = new RegExp("^" + w
			+ "+|((?:^|[^\\\\])(?:\\\\.)*)" + w + "+$", "g"), B = new RegExp(
			"^" + w + "*," + w + "*"), H = new RegExp("^" + w + "*([>+~]|" + w
			+ ")" + w + "*"), A = new RegExp("=" + w + "*([^\\]'\"]*?)" + w
			+ "*\\]", "g"), Y = new RegExp(r), aa = new RegExp("^" + R + "$"), ai = {
		ID : new RegExp("^#(" + a + ")"),
		CLASS : new RegExp("^\\.(" + a + ")"),
		TAG : new RegExp("^(" + a.replace("w", "w*") + ")"),
		ATTR : new RegExp("^" + am),
		PSEUDO : new RegExp("^" + r),
		CHILD : new RegExp(
				"^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + w
						+ "*(even|odd|(([+-]|)(\\d*)n|)" + w + "*(?:([+-]|)"
						+ w + "*(\\d+)|))" + w + "*\\)|)", "i"),
		bool : new RegExp("^(?:" + c + ")$", "i"),
		needsContext : new RegExp("^" + w
				+ "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + w
				+ "*((?:-\\d)?\\d*)" + w + "*\\)|)(?=[^-]|$)", "i")
	}, h = /^(?:input|select|textarea|button)$/i, s = /^h\d$/i, V = /^[^{]+\{\s*\[native \w/, X = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, ah = /[+~]/, T = /'|\\/g, z = new RegExp(
			"\\\\([\\da-f]{1,6}" + w + "?|(" + w + ")|.)", "ig"), al = function(
			e, aF, i) {
		var aE = "0x" + aF - 65536;
		return aE !== aE || i ? aF : aE < 0 ? String.fromCharCode(aE + 65536)
				: String.fromCharCode(aE >> 10 | 55296, aE & 1023 | 56320)
	};
	try {
		b.apply((at = t.call(P.childNodes)), P.childNodes);
		at[P.childNodes.length].nodeType
	} catch (J) {
		b = {
			apply : at.length ? function(i, e) {
				S.apply(i, t.call(e))
			} : function(aG, aF) {
				var e = aG.length, aE = 0;
				while ((aG[e++] = aF[aE++])) {
				}
				aG.length = e - 1
			}
		}
	}
	function C(aL, aE, aP, aR) {
		var aQ, aI, aJ, aN, aO, aH, aG, e, aF, aM;
		if ((aE ? aE.ownerDocument || aE : P) !== I) {
			af(aE)
		}
		aE = aE || I;
		aP = aP || [];
		if (!aL || typeof aL !== "string") {
			return aP
		}
		if ((aN = aE.nodeType) !== 1 && aN !== 9) {
			return []
		}
		if (ao && !aR) {
			if ((aQ = X.exec(aL))) {
				if ((aJ = aQ[1])) {
					if (aN === 9) {
						aI = aE.getElementById(aJ);
						if (aI && aI.parentNode) {
							if (aI.id === aJ) {
								aP.push(aI);
								return aP
							}
						} else {
							return aP
						}
					} else {
						if (aE.ownerDocument
								&& (aI = aE.ownerDocument.getElementById(aJ))
								&& L(aE, aI) && aI.id === aJ) {
							aP.push(aI);
							return aP
						}
					}
				} else {
					if (aQ[2]) {
						b.apply(aP, aE.getElementsByTagName(aL));
						return aP
					} else {
						if ((aJ = aQ[3]) && az.getElementsByClassName
								&& aE.getElementsByClassName) {
							b.apply(aP, aE.getElementsByClassName(aJ));
							return aP
						}
					}
				}
			}
			if (az.qsa && (!aj || !aj.test(aL))) {
				e = aG = aq;
				aF = aE;
				aM = aN === 9 && aL;
				if (aN === 1 && aE.nodeName.toLowerCase() !== "object") {
					aH = o(aL);
					if ((aG = aE.getAttribute("id"))) {
						e = aG.replace(T, "\\$&")
					} else {
						aE.setAttribute("id", e)
					}
					e = "[id='" + e + "'] ";
					aO = aH.length;
					while (aO--) {
						aH[aO] = e + p(aH[aO])
					}
					aF = ah.test(aL) && Z(aE.parentNode) || aE;
					aM = aH.join(",")
				}
				if (aM) {
					try {
						b.apply(aP, aF.querySelectorAll(aM));
						return aP
					} catch (aK) {
					} finally {
						if (!aG) {
							aE.removeAttribute("id")
						}
					}
				}
			}
		}
		return ay(aL.replace(y, "$1"), aE, aP, aR)
	}
	function G() {
		var i = [];
		function e(aE, aF) {
			if (i.push(aE + " ") > u.cacheLength) {
				delete e[i.shift()]
			}
			return (e[aE + " "] = aF)
		}
		return e
	}
	function q(e) {
		e[aq] = true;
		return e
	}
	function m(i) {
		var aF = I.createElement("div");
		try {
			return !!i(aF)
		} catch (aE) {
			return false
		} finally {
			if (aF.parentNode) {
				aF.parentNode.removeChild(aF)
			}
			aF = null
		}
	}
	function aB(aE, aG) {
		var e = aE.split("|"), aF = aE.length;
		while (aF--) {
			u.attrHandle[e[aF]] = aG
		}
	}
	function f(i, e) {
		var aF = e && i, aE = aF && i.nodeType === 1 && e.nodeType === 1
				&& (~e.sourceIndex || W) - (~i.sourceIndex || W);
		if (aE) {
			return aE
		}
		if (aF) {
			while ((aF = aF.nextSibling)) {
				if (aF === e) {
					return -1
				}
			}
		}
		return i ? 1 : -1
	}
	function D(e) {
		return function(aE) {
			var i = aE.nodeName.toLowerCase();
			return i === "input" && aE.type === e
		}
	}
	function g(e) {
		return function(aE) {
			var i = aE.nodeName.toLowerCase();
			return (i === "input" || i === "button") && aE.type === e
		}
	}
	function an(e) {
		return q(function(i) {
			i = +i;
			return q(function(aE, aI) {
				var aG, aF = e([], aE.length, i), aH = aF.length;
				while (aH--) {
					if (aE[(aG = aF[aH])]) {
						aE[aG] = !(aI[aG] = aE[aG])
					}
				}
			})
		})
	}
	function Z(e) {
		return e && typeof e.getElementsByTagName !== av && e
	}
	az = C.support = {};
	Q = C.isXML = function(e) {
		var i = e && (e.ownerDocument || e).documentElement;
		return i ? i.nodeName !== "HTML" : false
	};
	af = C.setDocument = function(aE) {
		var e, aF = aE ? aE.ownerDocument || aE : P, i = aF.defaultView;
		if (aF === I || aF.nodeType !== 9 || !aF.documentElement) {
			return I
		}
		I = aF;
		v = aF.documentElement;
		ao = !Q(aF);
		if (i && i !== i.top) {
			if (i.addEventListener) {
				i.addEventListener("unload", function() {
					af()
				}, false)
			} else {
				if (i.attachEvent) {
					i.attachEvent("onunload", function() {
						af()
					})
				}
			}
		}
		az.attributes = m(function(aG) {
			aG.className = "i";
			return !aG.getAttribute("className")
		});
		az.getElementsByTagName = m(function(aG) {
			aG.appendChild(aF.createComment(""));
			return !aG.getElementsByTagName("*").length
		});
		az.getElementsByClassName = V.test(aF.getElementsByClassName)
				&& m(function(aG) {
					aG.innerHTML = "<div class='a'></div><div class='a i'></div>";
					aG.firstChild.className = "i";
					return aG.getElementsByClassName("i").length === 2
				});
		az.getById = m(function(aG) {
			v.appendChild(aG).id = aq;
			return !aF.getElementsByName || !aF.getElementsByName(aq).length
		});
		if (az.getById) {
			u.find.ID = function(aI, aH) {
				if (typeof aH.getElementById !== av && ao) {
					var aG = aH.getElementById(aI);
					return aG && aG.parentNode ? [ aG ] : []
				}
			};
			u.filter.ID = function(aH) {
				var aG = aH.replace(z, al);
				return function(aI) {
					return aI.getAttribute("id") === aG
				}
			}
		} else {
			delete u.find.ID;
			u.filter.ID = function(aH) {
				var aG = aH.replace(z, al);
				return function(aJ) {
					var aI = typeof aJ.getAttributeNode !== av
							&& aJ.getAttributeNode("id");
					return aI && aI.value === aG
				}
			}
		}
		u.find.TAG = az.getElementsByTagName ? function(aG, aH) {
			if (typeof aH.getElementsByTagName !== av) {
				return aH.getElementsByTagName(aG)
			}
		} : function(aG, aK) {
			var aL, aJ = [], aI = 0, aH = aK.getElementsByTagName(aG);
			if (aG === "*") {
				while ((aL = aH[aI++])) {
					if (aL.nodeType === 1) {
						aJ.push(aL)
					}
				}
				return aJ
			}
			return aH
		};
		u.find.CLASS = az.getElementsByClassName && function(aH, aG) {
			if (typeof aG.getElementsByClassName !== av && ao) {
				return aG.getElementsByClassName(aH)
			}
		};
		ax = [];
		aj = [];
		if ((az.qsa = V.test(aF.querySelectorAll))) {
			m(function(aG) {
				aG.innerHTML = "<select t=''><option selected=''></option></select>";
				if (aG.querySelectorAll("[t^='']").length) {
					aj.push("[*^$]=" + w + "*(?:''|\"\")")
				}
				if (!aG.querySelectorAll("[selected]").length) {
					aj.push("\\[" + w + "*(?:value|" + c + ")")
				}
				if (!aG.querySelectorAll(":checked").length) {
					aj.push(":checked")
				}
			});
			m(function(aH) {
				var aG = aF.createElement("input");
				aG.setAttribute("type", "hidden");
				aH.appendChild(aG).setAttribute("name", "D");
				if (aH.querySelectorAll("[name=d]").length) {
					aj.push("name" + w + "*[*^$|!~]?=")
				}
				if (!aH.querySelectorAll(":enabled").length) {
					aj.push(":enabled", ":disabled")
				}
				aH.querySelectorAll("*,:x");
				aj.push(",.*:")
			})
		}
		if ((az.matchesSelector = V.test((k = v.webkitMatchesSelector
				|| v.mozMatchesSelector || v.oMatchesSelector
				|| v.msMatchesSelector)))) {
			m(function(aG) {
				az.disconnectedMatch = k.call(aG, "div");
				k.call(aG, "[s!='']:x");
				ax.push("!=", r)
			})
		}
		aj = aj.length && new RegExp(aj.join("|"));
		ax = ax.length && new RegExp(ax.join("|"));
		e = V.test(v.compareDocumentPosition);
		L = e || V.test(v.contains) ? function(aH, aG) {
			var aJ = aH.nodeType === 9 ? aH.documentElement : aH, aI = aG
					&& aG.parentNode;
			return aH === aI
					|| !!(aI && aI.nodeType === 1 && (aJ.contains ? aJ
							.contains(aI) : aH.compareDocumentPosition
							&& aH.compareDocumentPosition(aI) & 16))
		} : function(aH, aG) {
			if (aG) {
				while ((aG = aG.parentNode)) {
					if (aG === aH) {
						return true
					}
				}
			}
			return false
		};
		K = e ? function(aH, aG) {
			if (aH === aG) {
				ad = true;
				return 0
			}
			var aI = !aH.compareDocumentPosition - !aG.compareDocumentPosition;
			if (aI) {
				return aI
			}
			aI = (aH.ownerDocument || aH) === (aG.ownerDocument || aG) ? aH
					.compareDocumentPosition(aG) : 1;
			if (aI
					& 1
					|| (!az.sortDetached && aG.compareDocumentPosition(aH) === aI)) {
				if (aH === aF || aH.ownerDocument === P && L(P, aH)) {
					return -1
				}
				if (aG === aF || aG.ownerDocument === P && L(P, aG)) {
					return 1
				}
				return O ? (j.call(O, aH) - j.call(O, aG)) : 0
			}
			return aI & 4 ? -1 : 1
		}
				: function(aH, aG) {
					if (aH === aG) {
						ad = true;
						return 0
					}
					var aN, aK = 0, aM = aH.parentNode, aJ = aG.parentNode, aI = [ aH ], aL = [ aG ];
					if (!aM || !aJ) {
						return aH === aF ? -1 : aG === aF ? 1 : aM ? -1
								: aJ ? 1 : O ? (j.call(O, aH) - j.call(O, aG))
										: 0
					} else {
						if (aM === aJ) {
							return f(aH, aG)
						}
					}
					aN = aH;
					while ((aN = aN.parentNode)) {
						aI.unshift(aN)
					}
					aN = aG;
					while ((aN = aN.parentNode)) {
						aL.unshift(aN)
					}
					while (aI[aK] === aL[aK]) {
						aK++
					}
					return aK ? f(aI[aK], aL[aK]) : aI[aK] === P ? -1
							: aL[aK] === P ? 1 : 0
				};
		return aF
	};
	C.matches = function(i, e) {
		return C(i, null, null, e)
	};
	C.matchesSelector = function(aE, aG) {
		if ((aE.ownerDocument || aE) !== I) {
			af(aE)
		}
		aG = aG.replace(A, "='$1']");
		if (az.matchesSelector && ao && (!ax || !ax.test(aG))
				&& (!aj || !aj.test(aG))) {
			try {
				var i = k.call(aE, aG);
				if (i || az.disconnectedMatch || aE.document
						&& aE.document.nodeType !== 11) {
					return i
				}
			} catch (aF) {
			}
		}
		return C(aG, I, null, [ aE ]).length > 0
	};
	C.contains = function(e, i) {
		if ((e.ownerDocument || e) !== I) {
			af(e)
		}
		return L(e, i)
	};
	C.attr = function(aE, e) {
		if ((aE.ownerDocument || aE) !== I) {
			af(aE)
		}
		var i = u.attrHandle[e.toLowerCase()], aF = i
				&& U.call(u.attrHandle, e.toLowerCase()) ? i(aE, e, !ao)
				: undefined;
		return aF !== undefined ? aF : az.attributes || !ao ? aE
				.getAttribute(e) : (aF = aE.getAttributeNode(e))
				&& aF.specified ? aF.value : null
	};
	C.error = function(e) {
		throw new Error("Syntax error, unrecognized expression: " + e)
	};
	C.uniqueSort = function(aF) {
		var aG, aH = [], e = 0, aE = 0;
		ad = !az.detectDuplicates;
		O = !az.sortStable && aF.slice(0);
		aF.sort(K);
		if (ad) {
			while ((aG = aF[aE++])) {
				if (aG === aF[aE]) {
					e = aH.push(aE)
				}
			}
			while (e--) {
				aF.splice(aH[e], 1)
			}
		}
		O = null;
		return aF
	};
	N = C.getText = function(aH) {
		var aG, aE = "", aF = 0, e = aH.nodeType;
		if (!e) {
			while ((aG = aH[aF++])) {
				aE += N(aG)
			}
		} else {
			if (e === 1 || e === 9 || e === 11) {
				if (typeof aH.textContent === "string") {
					return aH.textContent
				} else {
					for (aH = aH.firstChild; aH; aH = aH.nextSibling) {
						aE += N(aH)
					}
				}
			} else {
				if (e === 3 || e === 4) {
					return aH.nodeValue
				}
			}
		}
		return aE
	};
	u = C.selectors = {
		cacheLength : 50,
		createPseudo : q,
		match : ai,
		attrHandle : {},
		find : {},
		relative : {
			">" : {
				dir : "parentNode",
				first : true
			},
			" " : {
				dir : "parentNode"
			},
			"+" : {
				dir : "previousSibling",
				first : true
			},
			"~" : {
				dir : "previousSibling"
			}
		},
		preFilter : {
			ATTR : function(e) {
				e[1] = e[1].replace(z, al);
				e[3] = (e[4] || e[5] || "").replace(z, al);
				if (e[2] === "~=") {
					e[3] = " " + e[3] + " "
				}
				return e.slice(0, 4)
			},
			CHILD : function(e) {
				e[1] = e[1].toLowerCase();
				if (e[1].slice(0, 3) === "nth") {
					if (!e[3]) {
						C.error(e[0])
					}
					e[4] = +(e[4] ? e[5] + (e[6] || 1)
							: 2 * (e[3] === "even" || e[3] === "odd"));
					e[5] = +((e[7] + e[8]) || e[3] === "odd")
				} else {
					if (e[3]) {
						C.error(e[0])
					}
				}
				return e
			},
			PSEUDO : function(i) {
				var e, aE = !i[5] && i[2];
				if (ai.CHILD.test(i[0])) {
					return null
				}
				if (i[3] && i[4] !== undefined) {
					i[2] = i[4]
				} else {
					if (aE && Y.test(aE) && (e = o(aE, true))
							&& (e = aE.indexOf(")", aE.length - e) - aE.length)) {
						i[0] = i[0].slice(0, e);
						i[2] = aE.slice(0, e)
					}
				}
				return i.slice(0, 3)
			}
		},
		filter : {
			TAG : function(i) {
				var e = i.replace(z, al).toLowerCase();
				return i === "*" ? function() {
					return true
				} : function(aE) {
					return aE.nodeName && aE.nodeName.toLowerCase() === e
				}
			},
			CLASS : function(e) {
				var i = d[e + " "];
				return i
						|| (i = new RegExp("(^|" + w + ")" + e + "(" + w
								+ "|$)"))
						&& d(e, function(aE) {
							return i.test(typeof aE.className === "string"
									&& aE.className
									|| typeof aE.getAttribute !== av
									&& aE.getAttribute("class") || "")
						})
			},
			ATTR : function(aE, i, e) {
				return function(aG) {
					var aF = C.attr(aG, aE);
					if (aF == null) {
						return i === "!="
					}
					if (!i) {
						return true
					}
					aF += "";
					return i === "=" ? aF === e
							: i === "!=" ? aF !== e
									: i === "^=" ? e && aF.indexOf(e) === 0
											: i === "*=" ? e
													&& aF.indexOf(e) > -1
													: i === "$=" ? e
															&& aF
																	.slice(-e.length) === e
															: i === "~=" ? (" "
																	+ aF + " ")
																	.indexOf(e) > -1
																	: i === "|=" ? aF === e
																			|| aF
																					.slice(
																							0,
																							e.length + 1) === e
																					+ "-"
																			: false
				}
			},
			CHILD : function(i, aG, aF, aH, aE) {
				var aJ = i.slice(0, 3) !== "nth", e = i.slice(-4) !== "last", aI = aG === "of-type";
				return aH === 1 && aE === 0 ? function(aK) {
					return !!aK.parentNode
				} : function(aQ, aO, aT) {
					var aK, aW, aR, aV, aS, aN, aP = aJ !== e ? "nextSibling"
							: "previousSibling", aU = aQ.parentNode, aM = aI
							&& aQ.nodeName.toLowerCase(), aL = !aT && !aI;
					if (aU) {
						if (aJ) {
							while (aP) {
								aR = aQ;
								while ((aR = aR[aP])) {
									if (aI ? aR.nodeName.toLowerCase() === aM
											: aR.nodeType === 1) {
										return false
									}
								}
								aN = aP = i === "only" && !aN && "nextSibling"
							}
							return true
						}
						aN = [ e ? aU.firstChild : aU.lastChild ];
						if (e && aL) {
							aW = aU[aq] || (aU[aq] = {});
							aK = aW[i] || [];
							aS = aK[0] === aA && aK[1];
							aV = aK[0] === aA && aK[2];
							aR = aS && aU.childNodes[aS];
							while ((aR = ++aS && aR && aR[aP] || (aV = aS = 0)
									|| aN.pop())) {
								if (aR.nodeType === 1 && ++aV && aR === aQ) {
									aW[i] = [ aA, aS, aV ];
									break
								}
							}
						} else {
							if (aL && (aK = (aQ[aq] || (aQ[aq] = {}))[i])
									&& aK[0] === aA) {
								aV = aK[1]
							} else {
								while ((aR = ++aS && aR && aR[aP]
										|| (aV = aS = 0) || aN.pop())) {
									if ((aI ? aR.nodeName.toLowerCase() === aM
											: aR.nodeType === 1)
											&& ++aV) {
										if (aL) {
											(aR[aq] || (aR[aq] = {}))[i] = [
													aA, aV ]
										}
										if (aR === aQ) {
											break
										}
									}
								}
							}
						}
						aV -= aE;
						return aV === aH || (aV % aH === 0 && aV / aH >= 0)
					}
				}
			},
			PSEUDO : function(aF, aE) {
				var e, i = u.pseudos[aF] || u.setFilters[aF.toLowerCase()]
						|| C.error("unsupported pseudo: " + aF);
				if (i[aq]) {
					return i(aE)
				}
				if (i.length > 1) {
					e = [ aF, aF, "", aE ];
					return u.setFilters.hasOwnProperty(aF.toLowerCase()) ? q(function(
							aI, aK) {
						var aH, aG = i(aI, aE), aJ = aG.length;
						while (aJ--) {
							aH = j.call(aI, aG[aJ]);
							aI[aH] = !(aK[aH] = aG[aJ])
						}
					})
							: function(aG) {
								return i(aG, 0, e)
							}
				}
				return i
			}
		},
		pseudos : {
			not : q(function(e) {
				var i = [], aE = [], aF = ac(e.replace(y, "$1"));
				return aF[aq] ? q(function(aH, aM, aK, aI) {
					var aL, aG = aF(aH, null, aI, []), aJ = aH.length;
					while (aJ--) {
						if ((aL = aG[aJ])) {
							aH[aJ] = !(aM[aJ] = aL)
						}
					}
				}) : function(aI, aH, aG) {
					i[0] = aI;
					aF(i, null, aG, aE);
					return !aE.pop()
				}
			}),
			has : q(function(e) {
				return function(i) {
					return C(e, i).length > 0
				}
			}),
			contains : q(function(e) {
				return function(i) {
					return (i.textContent || i.innerText || N(i)).indexOf(e) > -1
				}
			}),
			lang : q(function(e) {
				if (!aa.test(e || "")) {
					C.error("unsupported lang: " + e)
				}
				e = e.replace(z, al).toLowerCase();
				return function(aE) {
					var i;
					do {
						if ((i = ao ? aE.lang : aE.getAttribute("xml:lang")
								|| aE.getAttribute("lang"))) {
							i = i.toLowerCase();
							return i === e || i.indexOf(e + "-") === 0
						}
					} while ((aE = aE.parentNode) && aE.nodeType === 1);
					return false
				}
			}),
			target : function(e) {
				var i = aw.location && aw.location.hash;
				return i && i.slice(1) === e.id
			},
			root : function(e) {
				return e === v
			},
			focus : function(e) {
				return e === I.activeElement && (!I.hasFocus || I.hasFocus())
						&& !!(e.type || e.href || ~e.tabIndex)
			},
			enabled : function(e) {
				return e.disabled === false
			},
			disabled : function(e) {
				return e.disabled === true
			},
			checked : function(e) {
				var i = e.nodeName.toLowerCase();
				return (i === "input" && !!e.checked)
						|| (i === "option" && !!e.selected)
			},
			selected : function(e) {
				if (e.parentNode) {
					e.parentNode.selectedIndex
				}
				return e.selected === true
			},
			empty : function(e) {
				for (e = e.firstChild; e; e = e.nextSibling) {
					if (e.nodeType < 6) {
						return false
					}
				}
				return true
			},
			parent : function(e) {
				return !u.pseudos.empty(e)
			},
			header : function(e) {
				return s.test(e.nodeName)
			},
			input : function(e) {
				return h.test(e.nodeName)
			},
			button : function(i) {
				var e = i.nodeName.toLowerCase();
				return e === "input" && i.type === "button" || e === "button"
			},
			text : function(i) {
				var e;
				return i.nodeName.toLowerCase() === "input"
						&& i.type === "text"
						&& ((e = i.getAttribute("type")) == null || e
								.toLowerCase() === "text")
			},
			first : an(function() {
				return [ 0 ]
			}),
			last : an(function(e, i) {
				return [ i - 1 ]
			}),
			eq : an(function(e, aE, i) {
				return [ i < 0 ? i + aE : i ]
			}),
			even : an(function(e, aF) {
				var aE = 0;
				for (; aE < aF; aE += 2) {
					e.push(aE)
				}
				return e
			}),
			odd : an(function(e, aF) {
				var aE = 1;
				for (; aE < aF; aE += 2) {
					e.push(aE)
				}
				return e
			}),
			lt : an(function(e, aG, aF) {
				var aE = aF < 0 ? aF + aG : aF;
				for (; --aE >= 0;) {
					e.push(aE)
				}
				return e
			}),
			gt : an(function(e, aG, aF) {
				var aE = aF < 0 ? aF + aG : aF;
				for (; ++aE < aG;) {
					e.push(aE)
				}
				return e
			})
		}
	};
	u.pseudos.nth = u.pseudos.eq;
	for (E in {
		radio : true,
		checkbox : true,
		file : true,
		password : true,
		image : true
	}) {
		u.pseudos[E] = D(E)
	}
	for (E in {
		submit : true,
		reset : true
	}) {
		u.pseudos[E] = g(E)
	}
	function ab() {
	}
	ab.prototype = u.filters = u.pseudos;
	u.setFilters = new ab();
	function o(aG, aL) {
		var i, aH, aJ, aK, aI, aE, e, aF = ap[aG + " "];
		if (aF) {
			return aL ? 0 : aF.slice(0)
		}
		aI = aG;
		aE = [];
		e = u.preFilter;
		while (aI) {
			if (!i || (aH = B.exec(aI))) {
				if (aH) {
					aI = aI.slice(aH[0].length) || aI
				}
				aE.push((aJ = []))
			}
			i = false;
			if ((aH = H.exec(aI))) {
				i = aH.shift();
				aJ.push({
					value : i,
					type : aH[0].replace(y, " ")
				});
				aI = aI.slice(i.length)
			}
			for (aK in u.filter) {
				if ((aH = ai[aK].exec(aI)) && (!e[aK] || (aH = e[aK](aH)))) {
					i = aH.shift();
					aJ.push({
						value : i,
						type : aK,
						matches : aH
					});
					aI = aI.slice(i.length)
				}
			}
			if (!i) {
				break
			}
		}
		return aL ? aI.length : aI ? C.error(aG) : ap(aG, aE).slice(0)
	}
	function p(aG) {
		var aF = 0, aE = aG.length, e = "";
		for (; aF < aE; aF++) {
			e += aG[aF].value
		}
		return e
	}
	function x(aG, aE, aF) {
		var e = aE.dir, aH = aF && e === "parentNode", i = ak++;
		return aE.first ? function(aK, aJ, aI) {
			while ((aK = aK[e])) {
				if (aK.nodeType === 1 || aH) {
					return aG(aK, aJ, aI)
				}
			}
		} : function(aM, aK, aJ) {
			var aO, aI, aL, aN = aA + " " + i;
			if (aJ) {
				while ((aM = aM[e])) {
					if (aM.nodeType === 1 || aH) {
						if (aG(aM, aK, aJ)) {
							return true
						}
					}
				}
			} else {
				while ((aM = aM[e])) {
					if (aM.nodeType === 1 || aH) {
						aL = aM[aq] || (aM[aq] = {});
						if ((aI = aL[e]) && aI[0] === aN) {
							if ((aO = aI[1]) === true || aO === l) {
								return aO === true
							}
						} else {
							aI = aL[e] = [ aN ];
							aI[1] = aG(aM, aK, aJ) || l;
							if (aI[1] === true) {
								return true
							}
						}
					}
				}
			}
		}
	}
	function aC(e) {
		return e.length > 1 ? function(aH, aG, aE) {
			var aF = e.length;
			while (aF--) {
				if (!e[aF](aH, aG, aE)) {
					return false
				}
			}
			return true
		} : e[0]
	}
	function ag(e, aE, aF, aG, aJ) {
		var aH, aM = [], aI = 0, aK = e.length, aL = aE != null;
		for (; aI < aK; aI++) {
			if ((aH = e[aI])) {
				if (!aF || aF(aH, aG, aJ)) {
					aM.push(aH);
					if (aL) {
						aE.push(aI)
					}
				}
			}
		}
		return aM
	}
	function n(aE, i, aG, aF, aH, e) {
		if (aF && !aF[aq]) {
			aF = n(aF)
		}
		if (aH && !aH[aq]) {
			aH = n(aH, e)
		}
		return q(function(aS, aP, aK, aR) {
			var aU, aQ, aM, aL = [], aT = [], aJ = aP.length, aI = aS
					|| F(i || "*", aK.nodeType ? [ aK ] : aK, []), aN = aE
					&& (aS || !i) ? ag(aI, aL, aE, aK, aR) : aI, aO = aG ? aH
					|| (aS ? aE : aJ || aF) ? [] : aP : aN;
			if (aG) {
				aG(aN, aO, aK, aR)
			}
			if (aF) {
				aU = ag(aO, aT);
				aF(aU, [], aK, aR);
				aQ = aU.length;
				while (aQ--) {
					if ((aM = aU[aQ])) {
						aO[aT[aQ]] = !(aN[aT[aQ]] = aM)
					}
				}
			}
			if (aS) {
				if (aH || aE) {
					if (aH) {
						aU = [];
						aQ = aO.length;
						while (aQ--) {
							if ((aM = aO[aQ])) {
								aU.push((aN[aQ] = aM))
							}
						}
						aH(null, (aO = []), aU, aR)
					}
					aQ = aO.length;
					while (aQ--) {
						if ((aM = aO[aQ])
								&& (aU = aH ? j.call(aS, aM) : aL[aQ]) > -1) {
							aS[aU] = !(aP[aU] = aM)
						}
					}
				}
			} else {
				aO = ag(aO === aP ? aO.splice(aJ, aO.length) : aO);
				if (aH) {
					aH(null, aP, aO, aR)
				} else {
					b.apply(aP, aO)
				}
			}
		})
	}
	function ar(aJ) {
		var aE, aH, aF, aI = aJ.length, aM = u.relative[aJ[0].type], aN = aM
				|| u.relative[" "], aG = aM ? 1 : 0, aK = x(function(i) {
			return i === aE
		}, aN, true), aL = x(function(i) {
			return j.call(aE, i) > -1
		}, aN, true), e = [ function(aP, aO, i) {
			return (!aM && (i || aO !== aD))
					|| ((aE = aO).nodeType ? aK(aP, aO, i) : aL(aP, aO, i))
		} ];
		for (; aG < aI; aG++) {
			if ((aH = u.relative[aJ[aG].type])) {
				e = [ x(aC(e), aH) ]
			} else {
				aH = u.filter[aJ[aG].type].apply(null, aJ[aG].matches);
				if (aH[aq]) {
					aF = ++aG;
					for (; aF < aI; aF++) {
						if (u.relative[aJ[aF].type]) {
							break
						}
					}
					return n(aG > 1 && aC(e), aG > 1
							&& p(aJ.slice(0, aG - 1).concat({
								value : aJ[aG - 2].type === " " ? "*" : ""
							})).replace(y, "$1"), aH, aG < aF
							&& ar(aJ.slice(aG, aF)), aF < aI
							&& ar((aJ = aJ.slice(aF))), aF < aI && p(aJ))
				}
				e.push(aH)
			}
		}
		return aC(e)
	}
	function ae(aF, aE) {
		var aH = 0, e = aE.length > 0, aG = aF.length > 0, i = function(aR, aL,
				aQ, aP, aU) {
			var aM, aN, aS, aW = 0, aO = "0", aI = aR && [], aX = [], aV = aD, aK = aR
					|| aG && u.find.TAG("*", aU), aJ = (aA += aV == null ? 1
					: Math.random() || 0.1), aT = aK.length;
			if (aU) {
				aD = aL !== I && aL;
				l = aH
			}
			for (; aO !== aT && (aM = aK[aO]) != null; aO++) {
				if (aG && aM) {
					aN = 0;
					while ((aS = aF[aN++])) {
						if (aS(aM, aL, aQ)) {
							aP.push(aM);
							break
						}
					}
					if (aU) {
						aA = aJ;
						l = ++aH
					}
				}
				if (e) {
					if ((aM = !aS && aM)) {
						aW--
					}
					if (aR) {
						aI.push(aM)
					}
				}
			}
			aW += aO;
			if (e && aO !== aW) {
				aN = 0;
				while ((aS = aE[aN++])) {
					aS(aI, aX, aL, aQ)
				}
				if (aR) {
					if (aW > 0) {
						while (aO--) {
							if (!(aI[aO] || aX[aO])) {
								aX[aO] = au.call(aP)
							}
						}
					}
					aX = ag(aX)
				}
				b.apply(aP, aX);
				if (aU && !aR && aX.length > 0 && (aW + aE.length) > 1) {
					C.uniqueSort(aP)
				}
			}
			if (aU) {
				aA = aJ;
				aD = aV
			}
			return aI
		};
		return e ? q(i) : i
	}
	ac = C.compile = function(e, aI) {
		var aF, aE = [], aH = [], aG = M[e + " "];
		if (!aG) {
			if (!aI) {
				aI = o(e)
			}
			aF = aI.length;
			while (aF--) {
				aG = ar(aI[aF]);
				if (aG[aq]) {
					aE.push(aG)
				} else {
					aH.push(aG)
				}
			}
			aG = M(e, ae(aH, aE))
		}
		return aG
	};
	function F(aE, aH, aG) {
		var aF = 0, e = aH.length;
		for (; aF < e; aF++) {
			C(aE, aH[aF], aG)
		}
		return aG
	}
	function ay(aF, e, aG, aJ) {
		var aH, aL, aE, aM, aK, aI = o(aF);
		if (!aJ) {
			if (aI.length === 1) {
				aL = aI[0] = aI[0].slice(0);
				if (aL.length > 2 && (aE = aL[0]).type === "ID" && az.getById
						&& e.nodeType === 9 && ao && u.relative[aL[1].type]) {
					e = (u.find.ID(aE.matches[0].replace(z, al), e) || [])[0];
					if (!e) {
						return aG
					}
					aF = aF.slice(aL.shift().value.length)
				}
				aH = ai.needsContext.test(aF) ? 0 : aL.length;
				while (aH--) {
					aE = aL[aH];
					if (u.relative[(aM = aE.type)]) {
						break
					}
					if ((aK = u.find[aM])) {
						if ((aJ = aK(aE.matches[0].replace(z, al), ah
								.test(aL[0].type)
								&& Z(e.parentNode) || e))) {
							aL.splice(aH, 1);
							aF = aJ.length && p(aL);
							if (!aF) {
								b.apply(aG, aJ);
								return aG
							}
							break
						}
					}
				}
			}
		}
		ac(aF, aI)(aJ, e, !ao, aG, ah.test(aF) && Z(e.parentNode) || e);
		return aG
	}
	az.sortStable = aq.split("").sort(K).join("") === aq;
	az.detectDuplicates = !!ad;
	af();
	az.sortDetached = m(function(e) {
		return e.compareDocumentPosition(I.createElement("div")) & 1
	});
	if (!m(function(e) {
		e.innerHTML = "<a href='#'></a>";
		return e.firstChild.getAttribute("href") === "#"
	})) {
		aB("type|href|height|width", function(i, e, aE) {
			if (!aE) {
				return i.getAttribute(e, e.toLowerCase() === "type" ? 1 : 2)
			}
		})
	}
	if (!az.attributes || !m(function(e) {
		e.innerHTML = "<input/>";
		e.firstChild.setAttribute("value", "");
		return e.firstChild.getAttribute("value") === ""
	})) {
		aB("value", function(i, e, aE) {
			if (!aE && i.nodeName.toLowerCase() === "input") {
				return i.defaultValue
			}
		})
	}
	if (!m(function(e) {
		return e.getAttribute("disabled") == null
	})) {
		aB(c, function(i, e, aF) {
			var aE;
			if (!aF) {
				return i[e] === true ? e.toLowerCase() : (aE = i
						.getAttributeNode(e))
						&& aE.specified ? aE.value : null
			}
		})
	}
	if (typeof define === "function" && define.amd) {
		define(function() {
			return C
		})
	} else {
		if (typeof module !== "undefined" && module.exports) {
			module.exports = C
		} else {
			aw.Sizzle = C
		}
	}
})(window);
if (typeof JSON !== "object") {
	JSON = {}
}
(function() {
	function f(n) {
		return n < 10 ? "0" + n : n
	}
	if (typeof Date.prototype.toJSON !== "function") {
		Date.prototype.toJSON = function() {
			return isFinite(this.valueOf()) ? this.getUTCFullYear() + "-"
					+ f(this.getUTCMonth() + 1) + "-" + f(this.getUTCDate())
					+ "T" + f(this.getUTCHours()) + ":"
					+ f(this.getUTCMinutes()) + ":" + f(this.getUTCSeconds())
					+ "Z" : null
		};
		String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function() {
			return this.valueOf()
		}
	}
	var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g, gap, indent, meta = {
		"\b" : "\\b",
		"\t" : "\\t",
		"\n" : "\\n",
		"\f" : "\\f",
		"\r" : "\\r",
		'"' : '\\"',
		"\\" : "\\\\"
	}, rep;
	function quote(string) {
		escapable.lastIndex = 0;
		return escapable.test(string) ? '"'
				+ string.replace(escapable, function(a) {
					var c = meta[a];
					return typeof c === "string" ? c : "\\u"
							+ ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
				}) + '"' : '"' + string + '"'
	}
	function str(key, holder) {
		var i, k, v, length, mind = gap, partial, value = holder[key];
		if (value && typeof value === "object"
				&& typeof value.toJSON === "function") {
			value = value.toJSON(key)
		}
		if (typeof rep === "function") {
			value = rep.call(holder, key, value)
		}
		switch (typeof value) {
		case "string":
			return quote(value);
		case "number":
			return isFinite(value) ? String(value) : "null";
		case "boolean":
		case "null":
			return String(value);
		case "object":
			if (!value) {
				return "null"
			}
			gap += indent;
			partial = [];
			if (Object.prototype.toString.apply(value) === "[object Array]") {
				length = value.length;
				for (i = 0; i < length; i += 1) {
					partial[i] = str(i, value) || "null"
				}
				v = partial.length === 0 ? "[]" : gap ? "[\n" + gap
						+ partial.join(",\n" + gap) + "\n" + mind + "]" : "["
						+ partial.join(",") + "]";
				gap = mind;
				return v
			}
			if (rep && typeof rep === "object") {
				length = rep.length;
				for (i = 0; i < length; i += 1) {
					if (typeof rep[i] === "string") {
						k = rep[i];
						v = str(k, value);
						if (v) {
							partial.push(quote(k) + (gap ? ": " : ":") + v)
						}
					}
				}
			} else {
				for (k in value) {
					if (Object.prototype.hasOwnProperty.call(value, k)) {
						v = str(k, value);
						if (v) {
							partial.push(quote(k) + (gap ? ": " : ":") + v)
						}
					}
				}
			}
			v = partial.length === 0 ? "{}" : gap ? "{\n" + gap
					+ partial.join(",\n" + gap) + "\n" + mind + "}" : "{"
					+ partial.join(",") + "}";
			gap = mind;
			return v
		}
	}
	if (typeof JSON.stringify !== "function") {
		JSON.stringify = function(value, replacer, space) {
			var i;
			gap = "";
			indent = "";
			if (typeof space === "number") {
				for (i = 0; i < space; i += 1) {
					indent += " "
				}
			} else {
				if (typeof space === "string") {
					indent = space
				}
			}
			rep = replacer;
			if (replacer
					&& typeof replacer !== "function"
					&& (typeof replacer !== "object" || typeof replacer.length !== "number")) {
				throw new Error("JSON.stringify")
			}
			return str("", {
				"" : value
			})
		}
	}
	if (typeof JSON.parse !== "function") {
		JSON.parse = function(text, reviver) {
			var j;
			function walk(holder, key) {
				var k, v, value = holder[key];
				if (value && typeof value === "object") {
					for (k in value) {
						if (Object.prototype.hasOwnProperty.call(value, k)) {
							v = walk(value, k);
							if (v !== undefined) {
								value[k] = v
							} else {
								delete value[k]
							}
						}
					}
				}
				return reviver.call(holder, key, value)
			}
			text = String(text);
			cx.lastIndex = 0;
			if (cx.test(text)) {
				text = text.replace(cx, function(a) {
					return "\\u"
							+ ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
				})
			}
			if (/^[\],:{}\s]*$/
					.test(text
							.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@")
							.replace(
									/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
									"]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) {
				j = eval("(" + text + ")");
				return typeof reviver === "function" ? walk({
					"" : j
				}, "") : j
			}
			throw new SyntaxError("JSON.parse")
		}
	}
}());