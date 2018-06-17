<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.ItemOrder"%>
<%
	ItemOrder order = (ItemOrder) request.getAttribute("order");
//	ItemOrder order = new ItemOrder();
//	order.setOrderid(1);
//	order.setUserid("TEST");
//	order.setTotalamount(10000);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="./css/MainThema.css">
		<title>会計</title>
	</head>
	<body>
		レジ
		<div id="contents">
			<div class="container">
				<div class="grid g2">
					<form method="post" action="./OrderCommitServlet">
						<h2>合計金額：\<%= order.getTotalamount() %></h2>
						支払方法:<br/>
						現金<input type="radio" name="payment" value="<%= ItemOrder.PAYMENT_CASH %>">
						クレジットカード<input type="radio" name="payment" value="<%= ItemOrder.PAYMENT_CARD %>">
						振り込み<input type="radio" name="payment" value="<%= ItemOrder.PAYMENT_PAY %>"><br/>
						送付先：<input type="text" name="address" value=""><br/>
						<input type="hidden" name="orderid" value="<%= order.getOrderid() %>">
						<input type="submit" value="注文確定">
					</form>
				</div>
			</div>
		</div>

		<input type="button" onclick="location.href = './ItemListServlet'" value="戻る">
	</body>
</html>