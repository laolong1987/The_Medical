<%@ include file="../head.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>I-Warehouse Management System</title>
<link rel="stylesheet" type="text/css" id="mylink" />
<script type="text/javascript">
			var savePwdWindow = null;
            var tab = null;
            var accordion = null;
            var tree = null;
            var tabItems = [];
            $(function ()
            {
                //布局
                $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',space:4, onHeightChanged: f_heightChanged });

                var height = $(".l-layout-center").height();

                //Tab
                $("#framecenter").ligerTab({
                    height: height,
                    showSwitchInTab : true,
                    showSwitch: true,
                    onAfterAddTabItem: function (tabdata)
                    {
                        tabItems.push(tabdata);
                        saveTabStatus();
                    },
                    onAfterRemoveTabItem: function (tabid)
                    { 
                        for (var i = 0; i < tabItems.length; i++)
                        {
                            var o = tabItems[i];
                            if (o.tabid == tabid)
                            {
                                tabItems.splice(i, 1);
                                saveTabStatus();
                                break;
                            }
                        }
                    },
                    onReload: function (tabdata)
                    {
                        var tabid = tabdata.tabid;
                        addFrameSkinLink(tabid);
                    }
                });

                //面板
                $("#accordion1").ligerAccordion({ height: height - 24, speed: null });

                $(".l-link").hover(function ()
                {
                    $(this).addClass("l-link-over");
                }, function ()
                {
                    $(this).removeClass("l-link-over");
                });               

                tab = liger.get("framecenter");
                accordion = liger.get("accordion1");
                $("#pageloading").hide();

                css_init();
                pages_init();              
                iniMenu();
                <c:if test="${not empty param.tabid}">
                	util_addFramecenterTab("${param.tabid}","${empty param.params ? '' : param.params}");
                </c:if>
            });
            
            function iniMenu(){
            	<c:forEach items="${menus}" varStatus="i" var="menu">
	            	var tree = $("#tree${i.index}").ligerTree({
						nodeWidth : 120, 																
						idFieldName : 'id',
						parentIDFieldName : 'pid',
						isExpand : true,
						checkbox : false,
						slide : false,
						onClick : onClick
					});
					tree.clear();
					tree.loadData(null,"${ctx}/common/findSecondMenus", "id=${menu.id}");
					// 点击鼠标时更改顶部menu式样
					$("#acc${i.index}").mouseup(function(){
						setTopMenuHover('topMenu${i.index}', 'acc${i.index}');						
					});   
				</c:forEach>
            }
            
            function onClick(node) {	
				f_addTab(node.data.id , node.data.text, node.data.value);									
			}
            
            function f_heightChanged(options)
            {  
                if (tab)
                    tab.addHeight(options.diff);
                if (accordion && options.middleHeight - 24 > 0)
                    accordion.setHeight(options.middleHeight - 24);
            }
            function f_addTab(tabid, text, url)
            {
                tab.addTabItem({                  
                    text: text,
                    tabid:tabid,
                    url: url,
                    reload : true,
                    callback: function ()
                    {                        
                        addFrameSkinLink(tabid); 
                    }
                });
            }            
            function showCodeView(src)
            {
                $.ligerDialog.open({
                    title : '源码预览',
                    url: 'dotnetdemos/codeView.aspx?src=' + src,
                    width: $(window).width() *0.9,
                    height: $(window).height() * 0.9
                });

            }
            function addFrameSkinLink(tabid)
            {
                var prevHref = getLinkPrevHref(tabid) || "";                
                var skin = getQueryString("skin");                
                if (!skin) return;
                skin = skin.toLowerCase();
                attachLinkToFrame(tabid, prevHref + skin_links[skin]);
            }
            var skin_links = {
                "aqua": "./resources/lib/ligerUI/skins/Aqua/css/ligerui-all.css",
                "gray": "./resources/lib/ligerUI/skins/Gray/css/all.css",
                "silvery": "./resources/lib/ligerUI/skins/Silvery/css/style.css",
                "gray2014": "./resources/lib/ligerUI/skins/Gray2014/css/all.css"
            };           
            
            function pages_init()
            {
                var tabJson = $.cookie('liger-home-tab'); 
                if (tabJson)
                { 
                    var tabitems = JSON2.parse(tabJson);
                    for (var i = 0; tabitems && tabitems[i];i++)
                    { 
                        f_addTab(tabitems[i].tabid, tabitems[i].text, tabitems[i].url);
                    } 
                }
            }
            function saveTabStatus()
            { 
                /* $.cookie('liger-home-tab', JSON2.stringify(tabItems)); */
            }
            function css_init()
            {
                var css = $("#mylink").get(0), skin = getQueryString("skin");                
                $("#skinSelect").val(skin);
                $("#skinSelect").change(function ()
                {                 
                    if (this.value)
                    {
                        location.href = "first.jsp?skin=" + this.value;                        
                    } else
                    {
                        location.href = "index.htm";
                    }
                });
               
                if (!css || !skin) return;
                skin = skin.toLowerCase();
                $('body').addClass("body-" + skin);      
                $(css).attr("href", skin_links[skin]);                 
            }
            function getQueryString(name)
            {
                var now_url = document.location.search.slice(1), q_array = now_url.split('&');
                for (var i = 0; i < q_array.length; i++)
                {
                    var v_array = q_array[i].split('=');
                    if (v_array[0] == name)
                    {
                        return v_array[1];
                    }
                }
                return false;
            }
            function attachLinkToFrame(iframeId, filename)
            { 
                if(!window.frames[iframeId]) return;
                var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
                var fileref = window.frames[iframeId].document.createElement("link");
                if (!fileref) return;
                fileref.setAttribute("rel", "stylesheet");
                fileref.setAttribute("type", "text/css");
                fileref.setAttribute("href", filename);
                head.appendChild(fileref);
            }
            function getLinkPrevHref(iframeId)
            {
                if (!window.frames[iframeId] || !window.frames[iframeId].document) return;
                var head = window.frames[iframeId].document.getElementsByTagName('head').item(0);
                var links = $("link:first", head);
                for (var i = 0; links[i]; i++)
                {
                    var href = $(links[i]).attr("href");
                    if (href && href.toLowerCase().indexOf("ligerui") > 0)
                    {
                        return href.substring(0, href.toLowerCase().indexOf("resources"));
                    }
                }
            }
            
            function logout(){            
            	$.ajax({
					type : "POST",					
					url : "${ctx}/logout",					
					success : function() {
						location.href = '${ctx}/logon.jsp';
					}
				});
            }
            
            function menuClick(menuid,id){
            	setTopMenuHover(menuid, id);
            	$("#" + id).trigger("click");
            }
            
            function setTopMenuHover(menuid, id){
            	$(".topMenu a").each(function(i,cb){
            		$(cb).attr("class",'');
            	});
            	if($("#" +id + " .l-accordion-toggle").hasClass("l-accordion-toggle-close")){
            		$("#" + menuid).attr("class","hover");
            	}
            }
            
            //修改密码
             
           function closePwdWindow(){
				savePwdWindow.hide();
			}
			
			function openPwd(data){
				showPwdWindow();
			}
			function savePwd() {	
			    with(document.all){
   				if(password.value == ""){
					$.ligerDialog.waitting("密码不能为空,请输入密码");
					setTimeout(function() {
						$.ligerDialog.closeWaitting();
					}, 1000);
   				}else if(password.value!=confirmPwd.value){
   					$.ligerDialog.waitting("两次密码不一致，请重新输入");
					setTimeout(function() {
						$.ligerDialog.closeWaitting();
					}, 1000);
	 				password.value = "";
	 				confirmPwd.value = "";
  				 }else{
  				 	 var employeeId = $("#employeeId").val("${employee.id}");
	  				 var params = $("#pwdForm").serializeJson();
	  				 
						$.ajax({
						type : "POST",
						url : "${ctx}/org/savepassword",
						data : JSON.stringify(params),
						contentType : "application/json; charset=utf-8",
						dataType : "text",
						success : function(result) {
							if (result == 'success') {
								savePwdWindow.hide();
								$.ligerDialog.waitting('操作成功');
								setTimeout(function() {
									$.ligerDialog.closeWaitting();
								}, 500);
							} else {
								$.ligerDialog.warn(result);
							}
						}
					});
  				
  				 }
   				
   				}		 
			}
			
			function showPwdWindow() {
				if (savePwdWindow == null) {
					savePwdWindow = $.ligerDialog.open({
						target : $("#pwd"),
						width : 300,
						height : 'auto',
						top : 240,
						title : '修改密码',
						isResize : true,
						buttons: [ { text: '确定', onclick: savePwd },{ text:'取消', onclick: closePwdWindow } ] 
					});
				}
				$("#pwdForm")[0].reset();
				savePwdWindow.show();
			}
     </script>
<style type="text/css">
/* 头部信息-------------------------左 */
.top {
	background: #f3f3f3 url(${ctx}/images/r_m_bg.gif) repeat-x
		left bottom;
	height: 109px;
	width: 100%;
	position: relative;
}

.top .left {
	float: left;
	width: 216px;
}

.top .left span {
	display: block;
	text-align: right;
}

.top .left p {
	display: block;
	background: url(${ctx}/images/l_m_bg.gif) repeat-x;
	height: 32px;
	width: 185px;
	padding-left: 30px;
	border-right: #ddd 1px solid;
}

.top .left p img {
	margin: 5px 5px 0 0;
	float: left;
}

.top .left p font {
	font: 16px/32px Microsoft Yahei;
	color: white;
}

/* 头部信息-------------------------右 */
.top .right {
	float: left;
	width: 50%;
	background: ;
	height: 109px;
	padding-left: 15px;
}

.top .right .weizhi {
	color: #fff;
	font-size: 14px;
	float: left;
	margin-top: 77px;
	line-height: 34px;
	position: absolute;
	left: 20px;
}

.top .topmenu {
	background: url(${ctx}/images/t_m_center.gif) repeat-x;
	height: 19px;
	position: absolute;
	left: 500px;
	top: 58px;
}

.top .topmenu span {
	background: url(${ctx}/images/t_m_left.gif) no-repeat;
	width: 28px;
	float: left;
	height: 19px;
}

.top .topmenu ul {
	float: left;
	height: 19px;
	margin-top: 10px;
	padding: 0 20px 0 10px;
}

.top .topmenu ul li {
	float: left;
	width: 95px;
	height: 28px;
	text-align: center;
	margin-right: 5px;
}

.top .topmenu ul li:hover {
	background: url(${ctx}/images/t_m_bg.png) no-repeat;
}

.top .topmenu ul .hover {
	background: url(${ctx}/images/t_m_bg.png) no-repeat;
}

.top .topmenu ul li a {
	font: bold 14px/28px Microsoft Yahei;
	color: #fff;
	text-decoration: none;
	display: block;
	height: 28px;
}

.top .topmenu p {
	background: url(${ctx}/images/t_m_right.gif) no-repeat;
	width: 28px;
	float: left;
	height: 19px;
}

.top .loginbox {
	position: absolute;
	right: 10px;
	top: 50px;
	color: #000;
}

.top .loginbox a:link,.top .loginbox a:visited {
	text-decoration: underline;
	color: #000;
}

.top .loginbox a:hover {
	color: red;
}

.top .name {
	color: #4a4a4a;
	font: 20px Microsoft Yahei;
	position: absolute;
	left: 230px;
	top: 25px;
}

body,html {
	height: 100%;
}

body {
	padding: 0px;
	margin: 0;
	overflow: hidden;
}

.l-link {
	display: block;
	height: 26px;
	line-height: 26px;
	padding-left: 10px;
	text-decoration: underline;
	color: #333;
}

.l-link2 {
	text-decoration: underline;
	color: white;
	margin-left: 2px;
	margin-right: 2px;
}

.l-layout-top {
	background: #102A49;
	color: White;
}

.l-layout-bottom {
	background: #E5EDEF;
	text-align: center;
}

#pageloading {
	position: absolute;
	left: 0px;
	top: 0px;
	background: white url("./resources/lib/images/loading.gif") no-repeat
		center;
	width: 100%;
	height: 100%;
	z-index: 99999;
}

.l-link {
	display: block;
	line-height: 22px;
	height: 22px;
	padding-left: 16px;
	border: 1px solid white;
	margin: 4px;
}

.l-link-over {
	background: #FFEEAC;
	border: 1px solid #DB9F00;
}

.l-winbar {
	background: #2B5A76;
	height: 30px;
	position: absolute;
	left: 0px;
	bottom: 0px;
	width: 100%;
	z-index: 99999;
}

.space {
	color: #E7E7E7;
}
/* 顶部 */
.l-topmenu {
	margin: 0;
	padding: 0;
	height: 31px;
	line-height: 31px;
	background: url('./resources/lib/images/top.jpg') repeat-x bottom;
	position: relative;
	border-top: 1px solid #1D438B;
}

.l-topmenu-logo {
	color: #E7E7E7;
	padding-left: 35px;
	line-height: 26px;
	background: url('./resources/lib/images/topicon.gif') no-repeat 10px 5px;
}

.l-topmenu-welcome {
	position: absolute;
	height: 24px;
	line-height: 24px;
	right: 30px;
	top: 2px;
	color: #070A0C;
}

.l-topmenu-welcome a {
	color: #E7E7E7;
	text-decoration: underline
}

.body-gray2014 #framecenter {
	margin-top: 3px;
}

.viewsourcelink {
	background: #B3D9F7;
	display: block;
	position: absolute;
	right: 10px;
	top: 3px;
	padding: 6px 4px;
	color: #333;
	text-decoration: underline;
}

.viewsourcelink-over {
	background: #81C0F2;
}

.l-topmenu-welcome label {
	color: white;
}

#skinSelect {
	margin-right: 6px;
}
</style>
</head>
<body style="padding:0px;background:#EAEEF5;min-width:${fn:length(menus)*100+666}px;overflow: auto">
	<div id="pageloading"></div>
	<!-- <div id="topmenu" class="l-topmenu">
		<div class="l-topmenu-logo">WelCome</div>
		<div class="l-topmenu-welcome">
			<label> 皮肤切换：</label> <select id="skinSelect">
				<option value="aqua">默认</option>
				<option value="silvery">Silvery</option>
				<option value="gray">Gray</option>
				<option value="gray2014">Gray2014</option>
			</select>
			<a href="javascript:logout();">注销</a>
		</div>		
	</div> -->
	<div class="top">
		<div class="left">
			<span><img style="margin-top: 1px;" src="${ctx}/images/logo.gif" width="215" height="76">
			</span>
		</div>
		<div class="right">
			<div class="topmenu" id="topmenuDiv">
				<span></span>
				<ul>
				<!-- 
					<c:forEach items="${menus}" varStatus="i" var="menu">
						<c:choose>
							<c:when test="${i.first}">
								<c:set var="cc" value="hover"></c:set>
							</c:when>
							<c:otherwise>
								<c:set var="cc" value=""></c:set>
							</c:otherwise>
						</c:choose>
						<li><a id="topMenu${i.index}" href="javascript:menuClick('topMenu${i.index}','acc${i.index}');" class="${cc}">${menu.name}</a>
						</li>
					</c:forEach> -->
				</ul>
				<p></p>
			</div>
			<div class="name">医疗管理</div>
			
			<div class="loginbox">
				
				<img src="${ctx}/images/logout.png" onmouseover="this.style.cursor='pointer'" onclick="logout();" title="注销" />&nbsp;
				<img src="${ctx}/images/changepassword.png" onmouseover="this.style.cursor='pointer'" onclick="openPwd();" title="修改密码">
			</div>
			<div style=" color: #4a4a4a;font: 16px Microsoft Yahei;right: 10px;position: absolute;top: 15px;">你好,${employee.name}</div>
			<div class="weizhi">XX System</div>
		</div>
	</div>
	<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; ">
		<div position="left" title="菜单" id="accordion1">
		<li><a  href="javascript:f_addTab('xtgl4','文档管理','/admin/Content/listcontent')">文档管理</a> </li>
		<li><a  href="javascript:f_addTab('xtgl7','上传管理','/admin/Content/listupload')">上传管理</a></li>						
	  <li><a  href="javascript:f_addTab('xtgl9','轮播图管理','/admin/Content/listimg')">轮播图管理</a></li>

		</div>
		<div position="center" id="framecenter">
			<div tabid="home" title="我的主页" style="height:300px">
				<iframe frameborder="0" name="home" id="home" src="views/home.jsp"></iframe>
			</div>
		</div>
	</div>
	<!-- <div style="height:32px; line-height:32px; text-align:center;">
		Copyright © 2011-2014 www.ligerui.com</div>
	<div style="display:none"></div> -->
	
	<!-- 添加修改密码 From -->
	<div id="pwd" style="display: none; text-align: center;">
		<form id="pwdForm">
			<input type='hidden' id="employeeId" name="employeeId"/>
			<table>
				<tr>
					<td>
						<label style="text-align:left;width:80px; float: left;margin-top: 10px;margin-left: 10px;">新密码：</label>
					</td>
					<td>
						<input type="password" name="password" id="password"  class="liger-textbox" />
					</td>
				</tr>
				<tr>
					<td>
						<label style="text-align:left;width:80px; float: left;margin-top: 10px;margin-left: 10px;">确认密码：</label>
					</td>
					<td>
						<input type="password" name="confirmPwd" id="confirmPwd" class="liger-textbox" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>
