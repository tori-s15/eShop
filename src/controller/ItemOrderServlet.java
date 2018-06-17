package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.postgresql.PostgreSqlDaoFactory;
import dao.postgresql.PostgreSqlItemMasterDao;
import dao.postgresql.PostgreSqlOrderDetailTableDao;
import dao.postgresql.PostgreSqlOrderTableDao;
import model.ItemMaster;
import model.ItemOrder;
import model.ItemOrderDetail;

/**
 * Servlet implementation class ItemOrderServlet
 */
@WebServlet("/ItemOrderServlet")
public class ItemOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 文字コードをutf-8に変換
		request.setCharacterEncoding("utf-8");

		// パラメータの取得
		long orderid = 0;														// 注文ID
		String itemid = (String)request.getParameter("itemid");					// 商品ID
		long number = (long)Integer.parseInt(request.getParameter("number"));	// 数量
		long price = (long)Integer.parseInt(request.getParameter("price"));		// 単価
		long amount = number * price;											// 金額 （数量　×　単価）

		// dao生成
		PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
		PostgreSqlOrderTableDao orderdao = (PostgreSqlOrderTableDao) daofactory.createOrderTableDao();
		PostgreSqlOrderDetailTableDao detaildao = (PostgreSqlOrderDetailTableDao) daofactory.createOrderDetailTableDao();
		PostgreSqlItemMasterDao itemdao = (PostgreSqlItemMasterDao) daofactory.createItemMasterDao();

		// ユーザIDをキーにカートを検索
		ItemOrder order = orderdao.selectByUserIdInitOnly("TEST");

		// カートに保存しているものがない場合
		if ( order == null ) {

			// 注文をカートに格納
			order = new ItemOrder();
			order.setUserid("TEST");					// ユーザIDをセット
			order.setStatus(ItemOrder.STATUS_INIT);		// カート格納状態をセット
			order.setTotalamount(amount);				// 合計金額に金額をセット

			// カートを保存して注文IDを取得
			orderid = orderdao.insert(order);

			// 注文IDをセット
			order.setOrderid(orderid);

		// カートに保存しているものがある場合
		} else {
			// 合計金額を加算
			order.setTotalamount(order.getTotalamount() + amount );

			// 合計金額をカートに反映
			orderdao.update(order);

			// 注文IDを取得
			orderid = order.getOrderid();
		}

		// 注文明細へ格納
		ItemOrderDetail detail = new ItemOrderDetail();
		detail.setOrderid( orderid );
		detail.setItemid( itemid );
		detail.setNumber( number );
		detail.setAmount( amount );

		// 注文明細テーブルにInsert
		detaildao.insert(detail);

		// 商品マスタから在庫数を減算
		ItemMaster itemmaster = itemdao.selectById( itemid );
		itemmaster.setStocks(itemmaster.getStocks() - number);

		// 商品マスタを更新
		itemdao.update(itemmaster);

		// 当該ユーザの注文一覧でステータスが「カート」のものをパラメータにセット
		request.setAttribute("order", order);


		// レジ画面へ遷移
	    String view = "/WEB-INF/view/ItemOrderView.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(view);

	    dispatcher.forward(request, response);

	}

}
