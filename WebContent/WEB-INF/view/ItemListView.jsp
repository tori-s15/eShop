<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ItemMaster"%>
<%@ page import="java.util.List" %>
<%
	@SuppressWarnings("unchecked")
	List<ItemMaster> itemlist = (List<ItemMaster>) request.getAttribute("itemlist");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="./css/MainThema.css">

		<title>デモアプリ（ネット書店）</title>
	</head>
	<body>
		<input type="button" onclick="location.href = '/WEB-INF/view/Login.jsp'" value="ログイン">

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