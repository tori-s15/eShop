<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ItemMaster"%>
<%@ page import="java.util.List" %>
<%
	@SuppressWarnings("unchecked")
	List<ItemMaster> itemlist = (List<ItemMaster>) request.getAttribute("itemlist");

	String userid = (String) request.getAttribute("userid");
	if (userid == null) userid = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>デモアプリ（ネット書店）</title>
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
		<input type="button" value="ログイン" onclick="execRequest('./LoginServlet','post','login')">
		<input type="hidden" name="userid" value="<%= userid %>">
		<div id="contents">
			<div class="container">
				<% for(ItemMaster item  : itemlist) { %>
				<div class="grid g3">
					<form method="post" action="./ItemDetailServlet">
						<h2><%= item.getItemname() %></h2>
						<h3>\<%= item.getPrice() %></h3>
						<input type="hidden" name="itemid" value="<%= item.getItemid() %>">
						<input type="submit" value="詳細">
					</form>
				</div>
				<% } %>
			</div>
		</div>
	</body>
</html>