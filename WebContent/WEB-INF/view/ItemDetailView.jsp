<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ItemMaster"%>
<%
	ItemMaster item = (ItemMaster) request.getAttribute("item");
	String userid = (String) request.getAttribute("userid");
	if (userid == null) userid = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="./css/MainThema.css">
		<title>商品詳細</title>
	</head>
	<body>
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form method="post" action="./ItemOrderServlet">
						<h2><%= item.getItemid() %></h2>
						<h2><%= item.getItemname() %></h2>
						<h2>金額：\<%= item.getPrice() %></h2>
						<h2>在庫数：<%= item.getStocks() %></h2>
						<h2><%= item.getDescription() %></h2>
						注文数:<input type="text" name="number" value="">
						<input type="hidden" name="itemid" value="<%= item.getItemid() %>">
						<input type="hidden" name="price" value="<%= item.getPrice() %>">
						<input type="hidden" name="userid" value="<%= userid %>">
						<input type="submit" value="レジへ進む">
					</form>
				</div>
			</div>
		</div>
		<input type="button" onclick="location.href = './ItemListServlet'" value="戻る">
	</body>
</html>