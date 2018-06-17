package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import dao.UserMasterDao;
import model.UserMaster;

public class PostgreSqlUserMasterDao implements UserMasterDao {

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public PostgreSqlUserMasterDao(DataSource source) {
		this.source = source;
	}

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
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return usermaster;
	}

	@Override
	public void insert(UserMaster usermaster) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(UserMaster usermaster) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(String userid) {
		// TODO 自動生成されたメソッド・スタブ

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

				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}

}
