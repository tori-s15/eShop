package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.AbstractDaoFactory;
import dao.DaoFactory;
import dao.ItemMasterDao;
import model.ItemMaster;;

/**
 * Servlet implementation class ItemListServlet
 */
@WebServlet("/ItemListServlet")
public class ItemListServlet extends HttpServlet {

	private Logger logger = LogManager.getLogger();

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.trace("start");

		// 文字コードをutf-8に変換
		request.setCharacterEncoding("utf-8");

		// パラメータの取得
		String userid = request.getParameter("userid");		// ユーザID

		// dao生成
		DaoFactory factory = new DaoFactory();
		AbstractDaoFactory daofactory = factory.create(DaoFactory.POSTGRESQL);
		ItemMasterDao dao = daofactory.createItemMasterDao();

//		PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
//		PostgreSqlItemMasterDao dao = (PostgreSqlItemMasterDao) daofactory.createItemMasterDao();

		// dao生成（モック）
		//MockItemMasterDao dao = new MockItemMasterDao();

		// 検索処理（全件検索）
		List <ItemMaster> itemlist = dao.selectAll();

		// 検索結果のセット
		request.setAttribute("itemlist", itemlist);

		// ユーザIDのセット
		request.setAttribute("userid", userid);

		// 結果表示画面へ遷移
	    String view = "/WEB-INF/view/ItemListView.jsp";
	    RequestDispatcher dispatcher = request.getRequestDispatcher(view);

	    dispatcher.forward(request, response);

		logger.trace("end");

	}

}
