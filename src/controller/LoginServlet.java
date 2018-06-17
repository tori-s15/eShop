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
		String mode = null;

		try {

			logger.trace("start");

			// 文字コードをutf-8に変換
			request.setCharacterEncoding("utf-8");

			// パラメータ（処理モード）の取得
			mode = (String)request.getParameter("mode");

			// パラメータ（ユーザID/パスワード）の取得
			userid = (String)request.getParameter("userid");		// ユーザID
			password = (String)request.getParameter("password");	// パスワード

			switch ( mode ) {
				// 新規ユーザ登録
				case "new" :
				    logger.trace("mode:new");

				    // 遷移先をユーザ登録画面にセット
					view = "/WEB-INF/view/UserEntryView.jsp";
					break;

				// ログイン
				case "login" :
				    logger.trace("mode:login");

				    // 遷移先をログイン画面にセット
					view = "/WEB-INF/view/Login.jsp";
					break;

				// 認証処理
				case "chk" :
				    logger.trace("mode:chk");

					// 認証処理（OKであればユーザマスタが戻る）
					UserMaster user = certification(userid, password);

					//認証OKの場合
					if ( user != null ) {
						// 遷移先を商品一覧サーブレットにセット
					    view = "/ItemListServlet";

					    // パラメータにユーザマスタをセット
						request.setAttribute("usermaster", user);

				   // 認証NGの場合
					} else {
						// 遷移先をログイン画面にセット
						view = "/WEB-INF/view/Login.jsp";

						// パラメータに判定「NG」をセット
						request.setAttribute("judge", "NG");

					}
					break;
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


	/**
	 * ログイン認証
	 * @param userid,password
	 * @return UserMaster
	 */
	private UserMaster certification(String userid, String password) {

		UserMaster user = null;

		if ( (userid != null) && !( userid.equals("")) ) {
			// dao生成
			PostgreSqlDaoFactory daofactory = new PostgreSqlDaoFactory();
			PostgreSqlUserMasterDao dao = (PostgreSqlUserMasterDao) daofactory.createUserMasterDao();

			// ユーザIDでユーザマスタを検索
			user = dao.selectByUserId(userid);

			// 認証NG（パスワード間違い）の場合
			if ((user != null) && !(user.getPassword().equals(password)) ) {
				logger.debug("password={}",user.getPassword());

				user = null;
			}
		}
		return user;
	}

}
