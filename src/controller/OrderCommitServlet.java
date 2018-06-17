package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.postgresql.PostgreSqlDaoFactory;
import dao.postgresql.PostgreSqlOrderTableDao;
import model.ItemOrder;

/**
 * Servlet implementation class ShopMenuServlet
 */
@WebServlet("/OrderCommitServlet")
public class OrderCommitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderCommitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("[実行]：OrderCommitServlet");

		// 文字コードをutf-8に変換
		request.setCharacterEncoding("utf-8");


		// パラメータの取得
		long orderid = (long)Integer.parseInt(request.getParameter("orderid"));	// 注文ID
		int payment = Integer.parseInt(request.getParameter("payment"));		// 支払方法
		String address = (String)request.getParameter("address");				// 発送先

		// dao生成
		PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
		PostgreSqlOrderTableDao orderdao = (PostgreSqlOrderTableDao) daofactory.createOrderTableDao();

		System.out.println("[DAO生成]：OrderCommitServlet");

		// 注文IDをキーに注文情報を検索
		ItemOrder order = orderdao.selectByOrderId(orderid);

		System.out.println("[注文情報検索]：OrderCommitServlet");
		System.out.println("[注文情報検索]：OrderID:" + order.getOrderid());
		System.out.println("[注文情報検索]：UserID:" + order.getUserid());
		System.out.println("[注文情報検索]：Status:" + order.getStatus());

		// 支払方法・発送先をセット
		order.setPayment(payment);
		order.setAddress(address);

		// ステータスを注文済みにセット
		order.setStatus(ItemOrder.STATUS_ORDER);

		// 注文情報を更新
		orderdao.update(order);

		System.out.println("[ステータス更新]：OrderCommitServlet");

		// 注文完了画面へ遷移
	    String view = "/WEB-INF/view/OrderFinishView.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(view);

	    dispatcher.forward(request, response);
	}

}
