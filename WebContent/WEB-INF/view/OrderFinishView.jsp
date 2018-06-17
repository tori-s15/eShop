<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userid = (String) request.getAttribute("userid");
	if (userid == null) userid = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="./css/MainThema.css">
		<title>注文完了</title>
	</head>
	<body>
		レジ
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form method="post" action="./ItemListServlet">
						<h2>注文を完了しました。</h2>
						<input type="hidden" name="userid" value="<%= userid %>">
						<input type="submit" value="戻る">
					</form>
				</div>
			</div>
		</div>
	</body>
</html>