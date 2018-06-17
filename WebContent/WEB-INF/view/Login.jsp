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
			function execRequest(action, method, data) {
			    var form = document.createElement("form");
			    form.setAttribute("action", action); // 投げたいURLを書く。
			    form.setAttribute("method", method); // POSTリクエストもしくはGETリクエストを書く。
			    form.style.display = "none"; // 画面に表示しないことを指定する
			    document.body.appendChild(form);
			    if (data !== undefined) {
			        var input = document.createElement('input');
			        input.setAttribute('type', 'hidden');
			        input.setAttribute('name', 'mode');
			        input.setAttribute('value', data);
			        form.appendChild(input);
			    }
			    form.submit();
			}
		-->
		</script>
	</head>
	<body>
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form method="post" action="./LoginServlet">
						<h2>ログイン</h2>
						<h3>ユーザIDとパスワードを入力してください。</h3>
						<% if ( judge.equals("NG") ) { %>
							<h3><font color="red">ユーザIDかパスワードが間違っています。</font></h3>
						<% } %>
						ユーザID:<input type="text" name="userid" value=""><br/>
						パスワード:<input type="password" name="password" value=""><br/>
						<input type="button" value="ログイン" onclick="execRequest('./LoginServlet','post','chk')">
						<input type="button" value="新規ユーザの登録" onclick="execRequest('./LoginServlet','post','new')">
						<input type="button" onclick="location.href = './ItemListServlet'" value="戻る">
					</form>
				</div>
			</div>
		</div>

	</body>
</html>