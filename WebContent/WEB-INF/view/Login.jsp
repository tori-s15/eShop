<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String judge = (String) request.getAttribute("judge");
	if (judge == null) judge = "OK";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ログイン</title>
		<link rel="stylesheet" href="./css/MainThema.css">
		<script type="text/javascript">
		<!--
			function modeSubmit(p_mode) {
				var form = document.forms.LoginForm;
				form.mode.value = p_mode;
			    form.submit();
			}
		-->
		</script>
	</head>
	<body>
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form name="LoginForm" method="post" action="./LoginServlet">
						<h2>ログイン</h2>
						<h3>ユーザIDとパスワードを入力してください。</h3>
						<% if ( judge.equals("NG") ) { %>
							<h3><font color="red">ユーザIDかパスワードが間違っています。</font></h3>
						<% } %>
						ユーザID:<input type="text" name="userid" value=""><br/>
						パスワード:<input type="password" name="password" value=""><br/>
						<input type="hidden" name="mode" value="">
						<input type="button" value="ログイン" onclick="modeSubmit('chk')">
						<input type="button" value="新規ユーザの登録" onclick="modeSubmit('new')">
						<input type="button" onclick="location.href = './ItemListServlet'" value="戻る">
					</form>
				</div>
			</div>
		</div>

	</body>
</html>