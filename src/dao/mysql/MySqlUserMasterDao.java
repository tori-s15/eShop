package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserMasterDao;
import model.UserMaster;

public class MySqlUserMasterDao extends UserMasterDao {

	/** ログ出力 */
	private Logger logger = LogManager.getLogger();

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public MySqlUserMasterDao(DataSource source) {
		this.source = source;
	}

	/**
	 * ユーザマスタをユーザIDをキーに検索
	 * @param id
	 * @return UserMaster
	 */
	@Override
	public UserMaster selectByUserId(String userid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		UserMaster usermaster = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" USER_MASTER ");
			sql.append("where ");
			sql.append(" USER_ID=? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setString(index++, userid);

			rs = statement.executeQuery();

			while(rs.next()) {
				usermaster = new UserMaster();
				usermaster.setUserid(rs.getString("USER_ID"));
				usermaster.setPassword(rs.getString("PASSWORD"));
				usermaster.setUsername(rs.getString("USER_NAME"));
			}

		} catch (SQLException e) {
			logger.error("DB ERROR",e);
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return usermaster;
	}

	/**
	 * ユーザマスタに登録
	 * @param UserMaster
	 * @return なし
	 */
	@Override
	public void insert(UserMaster usermaster) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer insertsql = new StringBuffer();
			insertsql.append("insert ");
			insertsql.append(" into ");
			insertsql.append(" USER_MASTER ");
			insertsql.append(" ( ");
			insertsql.append("  USER_ID, PASSWORD, USER_NAME, TEL, ADDRESS ");
			insertsql.append(" ) ");
			insertsql.append(" values ");
			insertsql.append(" (?, ?, ?, ?, ? ) ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(insertsql.toString());
			int index = 1;
			statement.setString(index++, usermaster.getUserid());
			statement.setString(index++, usermaster.getPassword());
			statement.setString(index++, usermaster.getUsername());
			statement.setString(index++, usermaster.getTel());
			statement.setString(index++, usermaster.getAddress());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.debug("ユーザID={}", usermaster.getUserid());
			logger.debug("登録件数={}件", i);

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			logger.error("DB ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("DB ERROR",e1);
			}
		} finally {
			close(statement);
			close(conn);
		}
	}

	/**
	 * ユーザマスタを更新
	 * @param UserMaster
	 * @return なし
	 */
	@Override
	public void update(UserMaster usermaster) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Update文の定義
			StringBuffer updatesql = new StringBuffer();
			updatesql.append("update ");
			updatesql.append(" USER_MASTER ");
			updatesql.append(" set ");
			updatesql.append("  PASSWORD = ? ,");
			updatesql.append("  USER_NAME = ? ,");
			updatesql.append("  TEL = ? ,");
			updatesql.append("  ADDRESS = ? ");
			updatesql.append(" where ");
			updatesql.append("  USER_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(updatesql.toString());
			int index = 1;
			statement.setString(index++, usermaster.getPassword());
			statement.setString(index++, usermaster.getUsername());
			statement.setString(index++, usermaster.getTel());
			statement.setString(index++, usermaster.getAddress());
			statement.setString(index++, usermaster.getUserid());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.debug("ユーザID={}", usermaster.getUserid());
			logger.debug("登録件数={}件", i);

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			logger.error("DB ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("DB ERROR",e1);
			}
		} finally {
			close(statement);
			close(conn);
		}
	}

	/**
	 * ユーザマスタから削除
	 * @param userid
	 * @return なし
	 */
	@Override
	public void delete(String userid) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer deletesql = new StringBuffer();
			deletesql.append("delete ");
			deletesql.append(" USER_MASTER ");
			deletesql.append("where ");
			deletesql.append(" USER_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(deletesql.toString());
			int index = 1;
			statement.setString(index++, userid);

			// SQL文実行
			int i = statement.executeUpdate();

			logger.trace("削除件数={}件", i);

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			logger.error("DB ERROR",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("DB ERROR",e1);
			}
		} finally {
			close(statement);
			close(conn);
		}
	}

	/**
	 * コネクションをクローズ
	 * @param conn
	 */
	private void close(Connection conn) {
		if ( conn != null  ) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("DB ERROR",e);
			}
		}
	}

	/**
	 * ステートメントをクローズ
	 * @param statement
	 */
	private void close(PreparedStatement statement) {
		if ( statement != null ) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("DB ERROR",e);
			}
		}
	}

	/**
	 * 結果セットをクローズ
	 * @param rs
	 */
	private void close(ResultSet rs) {
		if ( rs != null ) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("DB ERROR",e);
			}
		}
	}

}
