package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.postgresql.PostgreSqlDaoFactory;
import dao.postgresql.PostgreSqlUserMasterDao;
import model.UserMaster;

/**
 * Servlet implementation class UserMaseterServlet
 */
@WebServlet("/UserMaseterServlet")
public class UserMasterServlet extends HttpServlet {

	private Logger logger = LogManager.getLogger();

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMasterServlet() {
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

		String view = null;

		try {

			logger.trace("start");

			// 文字コードをutf-8に変換
			request.setCharacterEncoding("utf-8");

			// ユーザマスタの生成
			UserMaster user = new UserMaster();

			// パラメータの取得
			user.setUserid(request.getParameter("userid"));		// ユーザID
			user.setUsername(request.getParameter("username"));	// ユーザ名
			user.setPassword(request.getParameter("password"));	// パスワード
			user.setTel(request.getParameter("tel"));			// 電話番号
			user.setAddress(request.getParameter("address"));	// 住所

			logger.debug("ユーザID={}",user.getUserid());
			logger.debug("ユーザ名={}",user.getUsername());
			logger.debug("パスワード={}",user.getPassword());
			logger.debug("電話番号={}",user.getTel());
			logger.debug("住所={}",user.getAddress());

			// dao生成
			PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
			PostgreSqlUserMasterDao dao = (PostgreSqlUserMasterDao) daofactory.createUserMasterDao();

			// ユーザマスタの登録
			dao.insert(user);

			// ユーザIDのセット
			request.setAttribute("userid", user.getUserid());

			// 遷移先をユーザ登録完了画面にセット
			view = "/WEB-INF/view/UserEntryFinishView.jsp";

		} catch(Exception e) {
			// ログ出力
			logger.error("SYSTEM ERROR",e);

			// 遷移先をエラー画面にセット
			view = "/WEB-INF/view/ErrorMessage.jsp";

		} finally {
			// 画面遷移
		    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		    dispatcher.forward(request, response);

		    logger.trace("end");
		}

	}

}
