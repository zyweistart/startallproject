var appImgSrcExpand = "images/b2owhite.gif";
var appImgSrcExpanded = "images/b2xwhite.gif";
function createButton(name, title, img, url, target) {
	this.obj = null; // button layer object
	this.objf = null; // button layer object
	this.objm = null; // button layer object
	this.name = name; // button layer object
	this.title = title; // button layer object
	this.img = img; // button layer ID
	this.url = url; // caption layer object
	this.target = target; // button field layer object
	this.btns = new Array();
	this.sta = new Array(); // button status (internal)
	this.addButton = b_addButton; // button caption
	this.clear = b_clear; // reset all buttons
	this.v = this.name + "var"; // global var of 'this'
	eval(this.v + "=this");
	return this;
}

function b_showButton(nr) {
	var i, l, o;
	l = this.btns.length;
	var pHei = this.objf.style.height.substring(0, this.objf.style.height.length - 2);
	var bl = this.btns[nr].objf.style.visibility == 'hidden';
	if (bl) {
		this.btns[nr].objf.style.visibility = 'visible';
		this.btns[nr].objm.src = appImgSrcExpanded;
	} else {
		this.btns[nr].objf.style.visibility = 'hidden';
		this.btns[nr].objm.src = appImgSrcExpand;
	}
	for (i = 0; i < l; i++) {
		if (i > nr) {
			var objTop = this.btns[i].obj.style.top.substring(0, this.btns[i].obj.style.top.length - 2);
			if (this.btns[i].objf != null) {
				var objfTop = this.btns[i].objf.style.top.substring(0, this.btns[i].objf.style.top.length - 2);
			}
			if (bl) {
				this.btns[i].obj.style.top = parseInt(objTop) + this.btns[nr].btns.length * 20;
				if (this.btns[i].objf != null) {
					this.btns[i].objf.style.top = parseInt(objfTop) + this.btns[nr].btns.length * 20;
				}
				this.objf.style.height = parseInt(pHei) + this.btns[nr].btns.length * 20;
			} else {
				this.btns[i].obj.style.top = parseInt(objTop) - this.btns[nr].btns.length * 20;
				if (this.btns[i].objf != null) {
					this.btns[i].objf.style.top = parseInt(objfTop) - this.btns[nr].btns.length * 20;
				}
				this.objf.style.height = parseInt(pHei) - this.btns[nr].btns.length * 20;
			}
		}
	}
	this.testScroll();
}

function b_showPanel_ns4(nr) {
	var i, l;
	document.cookie = nr;
	l = this.panels.length;
	for (i = 0; i < l; i++) {
		if (i > nr) {
			this.panels[i].obj.top = this.height - ((l - i) * 20) - 1;
		} else {
			this.panels[i].obj.top = i * 20;
		}
	}
}

function toggleAppMenuDisplay(menu, img) {
	if (menu.style.display == 'none') {
		menu.style.display = 'block';
	} else {
		menu.style.display = 'none';
	}
}
function b_addButton(button) {
	button.name = this.name + '_button' + this.btns.length;
	this.btns[this.btns.length] = button;
	this.sta[this.sta.length] = 0;
	return this;
}

function b_clear() {
	var i;
	for (i = 0; i < this.sta.length; i++) {
		if (this.sta[i] != 0) this.mOut(i);
	}
}
function b_mOver_ns4(nr) {
	this.clear();
	l = this.obj.layers[0].layers[nr].document;
	l.open();
	l.write("<Center>");
	l.write("<SPAN class='imgbout'>");
	l.write("<A href='#' onmouseOut='" + this.v + ".mOut(" + nr + ")' ");
	l.write("onMousedown='" + this.v + ".mDown(" + nr + ")'><img src='" + this.img[nr]);
	l.write("' border=0></A></SPAN><Font size=2 face=Arial color=white>");
	l.write(this.lbl[nr] + "</FONT><BR><BR>");
	l.close();
	this.sta[nr] = 1;
}

function b_mOut_ns4(nr) {
	l = this.obj.layers[0].layers[nr].document;
	l.open();
	l.write("<Center>");
	l.write("<SPAN class='imgnob'>");
	l.write("<A href='#' onmouseOver='" + this.v + ".mOver(" + nr + ")' ");
	l.write("onmouseOut='" + this.v + ".mOut(" + nr + ")'><img src='" + this.img[nr]);
	l.write("' border=0></A></SPAN><Font size=2 Face=Arial color=white>");
	l.write(this.lbl[nr] + "</FONT><BR><BR>");
	l.close();
	this.sta[nr] = 0;
}

function b_mDown_ns4(nr) {
	l = this.obj.layers[0].layers[nr].document;
	l.open();
	l.write("<Center>");
	l.write("<SPAN class='imgbin'>");
	l.write("<A href='#' onmouseOver='" + this.v + ".mOver(" + nr + ")' ");
	l.write("onmouseOut='" + this.v + ".mOut(" + nr + ")' onMouseup='" + this.act[nr]);
	l.write(";" + this.v + ".mOver(" + nr + ")'><img src='" + this.img[nr]);
	l.write("' border=0></A></SPAN><Font size=2 Face=Arial color=white>");
	l.write(this.lbl[nr] + "</FONT><BR><BR>");
	l.close();
	this.sta[nr] = 1;
}

function b_testScroll_ns4() {
	var i, j, k;
	i = this.obj.clip.bottom;
	j = this.obj.layers[0].clip.bottom;
	k = parseInt(this.obj.layers[0].top);
	if (k == 38) this.obj.layers[2].visibility = 'hide';
	else this.obj.layers[2].visibility = 'show';
	if ((j + k) < i) {
		this.obj.layers[3].visibility = 'hide';
	} else this.obj.layers[3].visibility = 'show';
}
function b_up_ns4(nr) {
	this.ftop = this.ftop - 5;
	this.obj.layers[0].top = this.ftop;
	nr--;
	if (nr > 0) setTimeout(this.v + '.up(' + nr + ');', 10);
	else this.testScroll();
}
function b_down_ns4(nr) {
	this.ftop = this.ftop + 5;
	if (this.ftop >= 38) {
		this.ftop = 38;
		nr = 0;
	}
	this.obj.layers[0].top = this.ftop;
	nr--;
	if (nr > 0) setTimeout(this.v + '.down(' + nr + ');', 10);
	else this.testScroll();
}

function b_mOver_op5(nr) {
	var obj0 = getObj(this.name + '_b' + nr + '0');
	var obj1 = getObj(this.name + '_b' + nr + '1');
	var obj2 = getObj(this.name + '_b' + nr + '2');
	this.clear();
	obj1.style.visibility = "VISIBLE";
	obj0.style.visibility = "HIDDEN";
	obj2.style.visibility = "HIDDEN";
	this.sta[nr] = 1;
}

function b_mOut_op5(nr) {
	var obj0 = getObj(this.name + '_b' + nr + '0');
	var obj1 = getObj(this.name + '_b' + nr + '1');
	var obj2 = getObj(this.name + '_b' + nr + '2');
	obj2.style.visibility = "visible";
	obj0.style.visibility = "hidden";
	obj1.style.visibility = "hidden";
	this.sta[nr] = 1;
}

function b_mDown_op5(nr) {
	var obj0 = getObj(this.name + '_b' + nr + '0');
	var obj1 = getObj(this.name + '_b' + nr + '1');
	var obj2 = getObj(this.name + '_b' + nr + '2');
	obj0.style.visibility = "visible";
	obj1.style.visibility = "hidden";
	obj2.style.visibility = "hidden";
	this.sta[nr] = 1;
}

function b_testScroll() {

	if (bt.op5) {
		var i = parseInt(this.obj.style.pixelHeight);
		var j = parseInt(this.objf.style.pixelHeight);
	} else {
		var i = parseInt(this.obj.style.height);
		var j = parseInt(this.objf.style.height);
	}
	var k = parseInt(this.objf.style.top);

	if (k == 28) this.objm1.style.visibility = 'hidden';
	else this.objm1.style.visibility = 'visible';

	if ((j + k) < i) {
		this.objm2.style.visibility = 'hidden';
	} else this.objm2.style.visibility = 'visible';
}

function b_up(nr) {
	this.ftop = this.ftop - 5;
	this.objf.style.top = this.ftop;
	nr--;
	if (nr > 0) setTimeout(this.v + '.up(' + nr + ');', 10);
	else this.testScroll();
}

function b_down(nr) {
	this.ftop = this.ftop + 5;
	if (this.ftop >= 28) {
		this.ftop = 28;
		nr = 0;
	}
	this.objf.style.top = this.ftop;
	nr--;
	if (nr > 0) setTimeout(this.v + '.down(' + nr + ');', 10);
	else this.testScroll();
}
function createPanel(name, caption) {
	this.name = name; // panel layer ID
	this.obj = null; // panel layer object
	this.objc = null; // caption layer object
	this.objf = null; // panel field layer object
	this.objm1 = null; // scroll button up
	this.objm2 = null; // scroll button down
	this.ftop = 0; // kong zhi cai dan
	this.caption = caption;
	this.btns = new Array(); // panel caption
	this.sta = new Array(); // button status (internal)
	this.addButton = b_addButton; // add one button to panel
	this.clear = b_clear;
	if (bt.ns4) this.showButton = b_showButton_ns4; // make a panel visible
	else this.showButton = b_showButton; // reset all buttons
	if (bt.ns4) { // functions for ns4
		this.mOver = b_mOver_ns4; // handles mouseOver event
		this.mOut = b_mOut_ns4; // handles mouseOut & mouseUp event
		this.mDown = b_mDown_ns4; // handles mouseDown event
		this.testScroll = b_testScroll_ns4; // test if scroll buttons visible
		this.up = b_up_ns4; // scroll panel buttons up
		this.down = b_down_ns4; // scroll panel buttons down
	}
	if (bt.op5) { // functions for op5
		this.mOver = b_mOver_op5; // handles mouseOver event
		this.mOut = b_mOut_op5; // handles mouseOut & mouseUp event
		this.mDown = b_mDown_op5; // handles mouseDown event
	}
	if (!bt.ns4) { // functions for all browsers but ns4
		this.testScroll = b_testScroll; // test if scroll buttons should be
		this.up = b_up; // scroll panel buttons up
		this.down = b_down; // scroll panel buttons down
	}
	this.v = this.name + "var";
	eval(this.v + "=this");
	return this;
}

function b_addPanel(panel) {
	panel.name = this.name + '_panel' + this.panels.length;
	this.panels[this.panels.length] = panel;
}
function b_writeStyle() {
	document.write('<STYLE TYPE="text/css">');
	document.write('.button {');
	document.write('text-align:center; ');
	document.write('cursor:hand; ');
	document.write('border-left:0px solid #6489A0;');
	document.write('border-top:0px solid #dfdfdf;');
	document.write('border-right:0px solid #6489A0;');
	document.write('border-bottom:0px solid #494949;');
	document.write('background:url(images/cyan/013.gif) transparent no-repeat left top;');
	document.write('}');
	document.write('.button Table tr td {');
	document.write('font-size:14px;letter-spacing:1px;font-weight:bold;color:cadetblue;FILTER:dropshadow(color=#ffffff, offx=1, offy=1, positive=1);');
	document.write('vertical-align:middle;');
	document.write('}');
	document.write('.noLine {text-decoration:none;}');
	document.write('.imgB {color:white; font-family:arial; font-size:10pt;}');
	if (bt.op5) {
		document.write('.imgbin {border-width:3; border-style:inset; ');
		document.write('border-color:white;}');
	} else {
		document.write('.imgbin {border-width:3; border-style:inset; ');
		document.write('border-color:silver;}');
	}
	if (bt.op5) {
		document.write('.imgbout {border-width:3; border-style:outset; ');
		document.write('border-color:white;}');
	} else {
		document.write('.imgbout {border-width:3; border-style:outset; ');
		document.write('border-color:silver;}');
	}
	document.write('.imgnob {border-width:3; border-style:solid; ');
	document.write('</STYLE>');
}
function b_draw() {
	var i, j, t = 0, h, c = 0;
	this.writeStyle();
	if (bt.ns5 || bt.op5) c = 6;
	if (bt.ns4) {
		document.write('<layer bgcolor=' + this.bgcolor + ' name=' + this.name + ' left=');
		document.write(this.xpos + ' top=' + this.ypos + ' width=' + this.width);
		document.write(' clip="0,0,' + this.width + ',' + this.height + '">');
		for (i = 0; i < this.panels.length; i++) {
			document.write('<Layer name=' + this.name + '_panel' + i + ' width=' + this.width);
			document.write(' top=' + i * 20 + ' bgcolor=' + this.bgcolor);
			document.write(' clip="0,0,' + this.width + ',');
			document.write(this.height - (this.panels.length - 1) * 20 + '">');
			document.write('<Layer top=38 width=' + this.width + '>');
			mtop = 0;
			for (j = 0; j < this.panels[i].img.length; j++) {
				document.write('<Layer top=' + mtop + ' width=' + this.width);
				document.write('><Center><SPAN class=imgnob>');
				document.write("<A href='#' onmouseOut='" + this.panels[i].v);
				document.write(".rst(" + j + ")' onmouseOver='" + this.panels[i].v);
				document.write(".mOver(" + j + ")'><img src='" + this.panels[i].img[j]);
				document.write("' border=0></A></SPAN>");
				document.write("<Font size=2 face=arial color=white>");
				document.write(this.panels[i].lbl[j] + "</FONT><BR><BR>");
				document.write('</Layer>');
				mtop = mtop + this.buttonspace;
			}
			document.write('</Layer>');
			document.write('<Layer top=0 width=' + this.width + ' clip="0,0,');
			document.write(this.width + ',20" bgcolor=silver class=button ');
			document.write('onmouseOver="' + this.panels[i].v + '.clear();">');
			document.write('<A class=noLine href="javascript:' + this.v + '.showPanel(');
			document.write(i + ');" onmouseOver="' + this.panels[i].v + '.clear();">');
			document.write('<Font Color=black class=noLine>' + this.panels[i].caption);
			document.write('</Font></A></Layer>');
			document.write('<Layer visibility=hide top=40 left=' + (this.width - 20));
			document.write('><A href="#" onClick="' + this.panels[i].v + '.down(16);" ');
			document.write('onmouseOver="' + this.panels[i].v + '.clear();"><img ');
			document.write('width=16 height=16 src=arrowup.gif border=0>');
			document.write('</A></LAYER><Layer top=');
			document.write((this.height - (this.panels.length) * 20) + '<Layer top=');
			document.write((this.height - (this.panels.length) * 20) + ' left=');
			document.write((this.width - 20) + '><A href="#" onClick="');
			document.write(this.panels[i].v + '.up(16);" onmouseOver="');
			document.write(this.panels[i].v + '.clear();"><img width=16 height=16 ');
			document.write('src=arrowdown.gif border=0></A></LAYER>');
			document.write('</LAYER>');
		}
		document.write('</LAYER>');
	} else {
		document.write('<DIV id=' + this.name + ' Style="position:absolute; left:');
		document.write(this.xpos + '; top:' + this.ypos + '; width:' + this.width);
		document.write('; height:' + this.height + '; background-color:' + this.bgcolor);
		document.write('; clip:rect(0,' + this.width + ',' + this.height + ',0)">');
		h = this.height - ((this.panels.length - 1) * 20);
		for (i = 0; i < this.panels.length; i++) {
			document.write('<DIV id=' + this.name + '_panel' + i);
			document.write(' Style="position:absolute; left:0; top:' + t);
			document.write('; width:' + this.width + '; height:' + h + '; clip:rect(0px, ');
			document.write(this.width + 'px, ' + h + 'px, 0px); background-color:');
			document.write(this.bgcolor + ';">');
			t = t + 20;
			document.write('<div id=' + this.name + '_panel' + i);
			document.write('_f Style="position:absolute; left:0; top:28; width:');
			document.write(this.width + '; height:');
			document.write((this.panels[i].btns.length * this.buttonspace));
			document.write('; background-color:' + this.bgcolor + ';">');
			mtop = 0;
			for (j = 0; j < this.panels[i].btns.length; j++) {
				if (!bt.op5) {
					if (this.panels[i].btns[j].btns != undefined && this.panels[i].btns[j].btns.length > 0) {
						document.write('<DIV id=' + this.name + '_panel' + i + '_b' + j + ' class=imgB ');
						document.write('Style="position:absolute; left:0; width:' + this.width);
						document.write('; top:' + mtop + '; text-align:left;">');
						document.write('<table width="95%" border="0">');
						document.write('<tr><td width="15%" align="right">');
						document.write('<img onclick="javascript:' + this.panels[i].v + '.showButton(' + j + ');" style="cursor:hand" id="' + this.name + '_panel' + i + '_b' + j + '_m" src='
								+ this.panels[i].btns[j].img + ' border=0>');
						document.write('</td>');
						document.write('<td width="85%">');
						document
								.write('<span class="unSelected" style="cursor:hand;" onclick="javascript:' + this.panels[i].v + '.showButton(' + j + ');">' + this.panels[i].btns[j].title + '</span>');
						document.write('</td></tr></table></DIV>');
						document.write('<div id=' + this.name + '_panel' + i + '_b' + j);
						document.write('_f Style="visibility:hidden; position:absolute; left:5; top:' + (mtop + 20) + '; width:');
						document.write(this.width + '; height:');
						document.write((this.panels[i].btns[j].btns.length * this.buttonspace));
						document.write('; background-color:' + this.bgcolor + ';">');
						sbtop = 0;
						for ( var k = 0; k < this.panels[i].btns[j].btns.length; k++) {
							document.write('<DIV id=' + this.name + '_panel' + i + '_b' + j + ' class=imgB ');
							document.write('Style="position:absolute; left:0; width:' + this.width);
							document.write('; top:' + sbtop + '; text-align:left;">');
							document.write('<table width="95%" border="0">');
							document.write('<tr><td width="15%" align="right">');
							document.write('<img src=' + this.panels[i].btns[j].btns[k].img + ' border=0>');
							document.write('</td>');
							document.write('<td width="85%">');
							document.write('<a href="' + this.panels[i].btns[j].btns[k].url + '" target="' + this.panels[i].btns[j].btns[k].target
									+ '"><span class="unSelected" onclick="remindState(this);">' + this.panels[i].btns[j].btns[k].title + '</span></a>');
							document.write('</a></td></tr></table></DIV>');
							sbtop = sbtop + this.buttonspace;
						}
						document.write('</DIV>');
					} else {
						document.write('<DIV id=' + this.name + '_panel' + i + '_b' + j + ' class=imgB ');
						document.write('Style="position:absolute; left:0; width:' + this.width);
						document.write('; top:' + mtop + '; text-align:left;">');
						document.write('<table width="95%" border="0">');
						document.write('<tr><td width="15%" align="right">');
						document.write('<img src=' + this.panels[i].btns[j].img + ' border=0>');
						document.write('</td>');
						document.write('<td width="85%">');
						document.write('<a href="' + this.panels[i].btns[j].url + '" target="' + this.panels[i].btns[j].target + '"><span class="unSelected" onclick="remindState(this);">'
								+ this.panels[i].btns[j].title + '</span></a>');
						document.write('</a></td></tr></table></DIV>');
					}
				}
				mtop = mtop + this.buttonspace;
			}
			document.write('</DIV>');
			if (!bt.op5) {
				document.write('<DIV id=' + this.name + '_panel' + i + '_c class=button ');
				document.write('onClick="javascript:' + this.v + '.showPanel(' + i);
				document.write(');" style="position:absolute; left:0; top:0; width:');
				document.write((this.width - c) + '; height:' + (20 - c) + ';"><table><tr><td ');
				document.write('onClick="' + this.v + '.showPanel(' + i + ');this.blur();');
				document.write('return false;" class=noLine> ');
				document.write('' + this.panels[i].caption);
				document.write('</td></tr></table></DIV>');
			}
			document.write('<DIV id=' + this.name + '_panel' + i);
			document.write('_m1 style="position:absolute; top:32; left:');
			document.write((this.width - 20) + ';"><A href="#" onClick="');
			document.write(this.panels[i].v + '.down(8);this.blur();return false;" ');
			document.write('onmouseOver="' + this.panels[i].v + '.clear();">');
			document.write('<img src=images/icon.minus.gif border=0>');
			document.write('</A></DIV>');
			document.write('<DIV id=' + this.name + '_panel' + i);
			document.write('_m2 style="position:absolute;  top:');
			document.write((this.height - (this.panels.length) * 20) + '; left:');
			document.write((this.width - 20) + ';"><A href="#" onClick="');
			document.write(this.panels[i].v + '.up(8);this.blur();return false" ');
			document.write('onmouseOver="' + this.panels[i].v + '.clear();">');
			document.write('<img src=images/icon.plus.gif border=0>');
			document.write('</A></DIV>');
			document.write('</DIV>');
		}
		if (bt.op5) {
			for (i = 0; i < this.panels.length; i++) {
				document.write('<DIV id=' + this.name + '_panel' + i);
				document.write('_c class=button onmouseOver="' + this.panels[i].v);
				document.write('.clear();" onClick="' + this.v + '.showPanel(' + i);
				document.write(');" style="position:absolute; left:0; top:0; width:');
				document.write((this.width - c) + '; height:' + (20 - c) + ';">');
				document.write('<A href="#" ');
				document.write('onClick="' + this.v + '.showPanel(' + i + ');this.blur();');
				document.write('return false;" class=noLine><FONT color=black ');
				document.write('class=noLine">' + this.panels[i].caption);
				document.write('</FONT></A></DIV>');
			}
		}
		document.write('</DIV>');
	}
	for (i = 0; i < this.panels.length; i++) {
		this.panels[i].obj = getObj(this.name + '_panel' + i);
		for (j = 0; j < this.panels[i].btns.length; j++) {
			this.panels[i].btns[j].obj = getObj(this.name + '_panel' + i + '_b' + j);
			this.panels[i].btns[j].objf = getObj(this.name + '_panel' + i + '_b' + j + '_f');
			this.panels[i].btns[j].objm = getObj(this.name + '_panel' + i + '_b' + j + '_m');
		}
		if (!bt.ns4) {
			this.panels[i].objc = getObj(this.name + '_panel' + i + '_c');
			this.panels[i].objf = getObj(this.name + '_panel' + i + '_f');
			this.panels[i].objm1 = getObj(this.name + '_panel' + i + '_m1');
			this.panels[i].objm2 = getObj(this.name + '_panel' + i + '_m2');
		}
		this.panels[i].testScroll();
	}
	if (bt.op5) {
		if (document.location.search == '') {
			this.showPanel(0);
		} else {
			this.showPanel(document.location.search.substr(1, 1));
		}
	} else {
		if (document.cookie) this.showPanel(0);
		else this.showPanel(0);
	}
}
function b_showPanel_ns4(nr) {
	var i, l;
	document.cookie = nr;
	l = this.panels.length;
	for (i = 0; i < l; i++) {
		if (i > nr) {
			this.panels[i].obj.top = this.height - ((l - i) * 20) - 1;
		} else {
			this.panels[i].obj.top = i * 20;
		}
	}
}
function b_showPanel(nr) {
	var i, l, o;
	document.cookie = nr;
	this.aktPanel = nr;
	l = this.panels.length;
	for (i = 0; i < l; i++) {
		if (i > nr) {
			this.panels[i].obj.style.top = this.height - ((l - i) * 20);
			if (bt.op5) {
				this.panels[i].objf.style.visibility = 'hidden';
				this.panels[i].objc.style.top = this.height - ((l - i) * 20);
			}
		} else {
			this.panels[i].obj.style.top = i * 20;
			if (bt.op5) {
				this.panels[i].objf.style.visibility = 'visible';
				this.panels[i].objc.style.top = i * 20;
			}
		}
	}
}

function b_resize(x, y, width, height) {
	var o, i, j, h;
	var c = (bt.ns5) ? 6 : 0;
	if (bt.ns4) location.reload();
	else {
		if (bt.op5 && (width != this.width)) if (location.href.indexOf('?') != -1) location.href = location.href.replace(/\?./, "?" + this.aktPanel);
		else location.href = location.href + '?' + this.aktPanel;
		else {
			this.xpos = x;
			this.yPos = y;
			this.width = width;
			this.height = height;
			o = getObj(this.name);
			o.style.left = x;
			o.style.top = y;
			o.style.width = width;
			o.style.height = height;
			o.style.clip = 'rect(0px ' + this.width + 'px ' + this.height + 'px 0px)';
			if (this.height > ((this.panels.length) * 20 + 32 * 2)) {
				h = this.height - ((this.panels.length - 1) * 20);
			} else {
				return true;
			}
			for (i = 0; i < this.panels.length; i++) {
				o = getObj(this.name + '_panel' + i + '_c');
				o.style.width = (this.width - c);
				if (!bt.op5) for (j = 0; j < this.panels[i].btns.length; j++) {
					o = getObj(this.name + '_panel' + i + '_b' + j);
					o.style.width = this.width;
				}
				this.panels[i].objm1.style.left = (this.width - 20);
				this.panels[i].objm2.style.top = (this.height - (this.panels.length) * 20);
				this.panels[i].objm2.style.left = (this.width - 20);
				this.panels[i].objf.style.width = this.width;
				this.panels[i].obj.style.width = this.width;
				this.panels[i].obj.style.height = h;
				this.panels[i].obj.style.pixelHeight = h;
				this.panels[i].obj.style.clip = 'rect(0px ' + this.width + 'px ' + h + 'px 0px)';
				this.panels[i].testScroll();
			}
		}
		this.showPanel(this.aktPanel);
	}
}

function createOutlookBar(name, x, y, width, height, bgcolor, pagecolor) {
	this.aktPanel = 0;
	this.name = name;
	this.xpos = x;
	this.ypos = y;
	this.width = width;
	this.height = height;
	this.bgcolor = "#D7E7EE";
	this.pagecolor = pagecolor;
	this.buttonspace = 20;
	this.panels = new Array();
	this.addPanel = b_addPanel;
	this.writeStyle = b_writeStyle;
	this.draw = b_draw;
	if (bt.ns4) this.showPanel = b_showPanel_ns4;
	else this.showPanel = b_showPanel;
	this.resize = b_resize;
	this.v = name + "var";
	eval(this.v + "=this");
	return this;
}