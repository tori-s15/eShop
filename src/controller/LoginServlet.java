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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private Logger logger = LogManager.getLogger();

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String userid = null;		// ユーザID
		String password = null;		// パスワード

		try {

			logger.trace("start");

			// 文字コードをutf-8に変換
			request.setCharacterEncoding("utf-8");

			// パラメータの取得
			userid = (String)request.getParameter("userid");		// ユーザID
			password = (String)request.getParameter("password");	// パスワード

			if ( userid == null || userid.equals("") ) {
				// 遷移先をログイン画面にセット
				view = "/WEB-INF/view/Login.jsp";

			} else {

				// dao生成
				PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
				PostgreSqlUserMasterDao dao = (PostgreSqlUserMasterDao) daofactory.createUserMasterDao();

				UserMaster user = dao.selectByUserId(userid);
				if ( (user == null) || !(user.getPassword().equals(password)) ) {

					// 遷移先をログイン画面にセット
					view = "/WEB-INF/view/UserEntryView.jsp";

					// パラメータに判定「NG」をセット
					request.setAttribute("judge", false);

				} else {
					// 商品一覧サーブレットへ処理を移す
				    view = "/ItemListServlet";

				    // パラメータにユーザマスタをセット
					request.setAttribute("usermaster", user);
				}
			}
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
