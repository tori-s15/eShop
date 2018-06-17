<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ユーザ登録画面</title>
		<link rel="stylesheet" href="./css/MainThema.css">
	</head>
	<body>
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form name="UserEntryForm" method="post" action="./UserMasterServlet">
						<h2>新規ユーザの登録</h2>
						ユーザID:<input type="text" name="userid" value=""><br/>
						ユーザ名:<input type="text" name="username" value=""><br/>
						パスワード:<input type="password" name="password" value=""><br/>
						電話番号:<input type="text" name="tel" value=""><br/>
						住所:<input type="text" name="address" value=""><br/>
						<input type="submit" value="登録">
						<input type="button" onclick="location.href = './ItemListServlet'" value="戻る">
					</form>
				</div>
			</div>
		</div>

	</body>
</html>