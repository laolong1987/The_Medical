<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="../head.jsp"%>
<html>
<head>
<title>I-Warehouse Management System</title>
<style type="text/css">
.overlay {
	-webkit-box-align: center;
	-webkit-box-pack: center;
	-webkit-transition: 0.25s opacity;
	-moz-transition: 0.25s opacity;
	transition: 0.25s opacity;
	filter: progid:DXImageTransform.Microsoft.gradient(enabled=         'true',
		startColorstr=         '#4C999999', endColorstr=         '#07999999' );
	background: -webkit-radial-gradient(rgba(127, 127, 127, 0.5),
		rgba(127, 127, 127, 0.5) 35%, rgba(0, 0, 0, 0.7) );
	background: -moz-radial-gradient(rgba(127, 127, 127, 0.5),
		rgba(127, 127, 127, 0.5) 35%, rgba(0, 0, 0, 0.7) );
	bottom: 0;
	display: block;
	display: -webkit-box;
	display: -moz-box;
	display: box;
	left: 0;
	position: fixed;
	*position: absolute;
	*width: 100%;
	*height: 100%;
	right: 0;
	top: 0;
	z-index: 1000;
}

.overlay .ie {
	background: #fff;
	filter: alpha(opacity =                 0);
	opacity: 0;
	position: absolute;
	position: fixed;
	z-index: -1;
	width: 100%;
	height: 100%;
}

.transparent {
	opacity: 0;
}

.support_bg_add,.language_box {
	-webkit-box-shadow: 0px 5px 80px #505050;
	-moz-box-shadow: 0px 5px 80px #505050;
	box-shadow: 0px 5px 80px #505050;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
}

html,body {
	background: #ddd url(${ctx}/images/login.gif);
	background-position: center;
	background-repeat: no-repeat;
	background-attachment: fixed;
}

html {
	height: 100%
}

.login-submit-button {
	margin-left: 59px;
	display: block;
	margin: 0;
	padding: 0;
	position: absolute;
	top: 124px;
	right: 124px;
}

.login-submit-button button {
	width: 85px;
	height: 23px;
	background: url('${ctx}/images/btn-login.png') no-repeat;
	border: none;
}

.login-submit-button a {
	margin-left: 15px
}

.login {
	width: 799px;
	height: 495px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -400px;
	margin-top: -297px;
}

.login-article {
	position: absolute;
	top: 180px;
	right: 106px
}

.login-article ul {
	width: 268px;
	overflow: hidden;
	position: absolute;
	right: 3px;
	top: 48px;
}

.login-article li {
	height: 35px;
	padding-top: 1px
}

.login-article label {
	display: inline-block;
	width: 58px
}

li.username input,li.password input {
	border: #ccc 1px solid;
	background: #fff;
	height: 24px;
	padding: 4px;
	font-family: Arial, Helvetica, sans-serif
}

li.username input {
	width: 180px;
}

.message {
	width: 240px;
	position: absolute;
	top: 0;
	right: 30px;
	color: #666666;
	display: none;
}

.message p {
	border: 1px #ff8080 solid;
	padding: 5px 5px 5px 5px;
	background: #fff2f2;
}
</style>
<script type="text/javascript">
	$(function(){
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
				logon();
		     }
		}
	})
	function logon() {
		var param = $("#logonForm").serializeJson();
		if('${param.tabid}'){
			param.tabid = '${param.tabid}';
			param.params = '${param.params}';
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/logon",
			data : JSON.stringify(param),
			contentType : "application/json; charset=utf-8",
			dataType : "JSON",
			success : function(result) {
				if (result == 'success') {
					$(".message").hide();
					$("#msg").html('');
					$("#hrefForm").submit();
				}else {
					$(".message").show();
					$("#msg").html(result);
				}
			}
		});
	}	
</script>
</head>
<body>
	<section class="login">
		<article class="login-article">
			<form id="logonForm" method="post">
				<article class="message">
					<p class="error" id="msg"></p>
				</article>
				<ul>
					<li class="username"><label for="username">&nbsp;</label> <input
						type="text" name="username" id="username"></li>
					<li class="username"><label for="password">&nbsp;</label> <input
						type="password" name="password" id="password"></li>
					<!-- <li class="savelogin"><label>&nbsp;<input id="autologon" name="autologon" type="checkbox" value="1">自动登录</label></li> -->
				</ul>
				<aside class="login-submit-button" id="submit-button">
					<button type="button" id="loginBtn" onclick="logon();"></button>
					<!-- <span style="line-height: 24px;height: 24px;"> <a id="supportBtn" href="###">支持中心</a> </span> -->
				</aside>
			</form>
		</article>
	</section>
	<form id="hrefForm" accept-charset="UTF-8" action="first.jsp" method="post">
	  <input id="tabid" name="tabid" type="hidden" value="${param.tabid}" />
	  <input id="params" name="params" type="hidden" value="${param.params}" />
	</form>
</body>
</html>