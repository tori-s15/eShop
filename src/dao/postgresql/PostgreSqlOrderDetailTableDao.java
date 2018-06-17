package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.OrderDetailTableDao;
import model.ItemOrderDetail;

public class PostgreSqlOrderDetailTableDao implements OrderDetailTableDao {

	/** ログ出力 */
	private Logger logger = LogManager.getLogger();

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public PostgreSqlOrderDetailTableDao(DataSource source) {
		this.source = source;
	}

	/**
	 * 注文IDで注文情報明細を取得
	 * @param orderid
	 * @return List<ItemOrderDetail>
	 */
	@Override
	public List<ItemOrderDetail> selectByOrderId(long orderid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<ItemOrderDetail> detaillist = new ArrayList<ItemOrderDetail>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ORDER_DETAIL_TABLE ");
			sql.append("where ");
			sql.append(" ORDER_ID =? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setLong(index++, orderid);

			rs = statement.executeQuery();

			while(rs.next()) {
				ItemOrderDetail detail = new ItemOrderDetail();
				detail.setOrderid(rs.getLong("ORDER_ID"));
				detail.setNo(rs.getInt("NO"));
				detail.setItemid(rs.getString("ITEM_ID"));
				detail.setNumber(rs.getLong("NUMBER"));
				detail.setAmount(rs.getLong("AMOUNT"));
				detaillist.add(detail);
			}
		} catch (SQLException e) {
			logger.error("DB ERROR",e);
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return detaillist;
	}

	/**
	 * 注文明細を登録
	 * @param ItemOrderDetail
	 * @return なし
	 */
	@Override
	public void insert(ItemOrderDetail detail) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Select文の定義
			StringBuffer selectsql = new StringBuffer();
			selectsql.append("select ");
			selectsql.append(" MAX(NO) AS NOW_NO ");
			selectsql.append("from ");
			selectsql.append(" ORDER_DETAIL_TABLE ");
			selectsql.append("where ");
			selectsql.append(" ORDER_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(selectsql.toString());
			int selindex = 1;
			statement.setLong(selindex++, detail.getOrderid());

			// SQL文実行
			rs = statement.executeQuery();

			// Noの最大値を取得（存在しなければ0）
			while(rs.next()) {
				detail.setNo(rs.getInt("NOW_NO"));
			}

			// Noの採番
			detail.setNo(detail.getNo() + 1);

			// ステートメントを閉じる
			close(statement);

			// Insert文の定義
			StringBuffer insertsql = new StringBuffer();
			insertsql.append("insert ");
			insertsql.append(" into ");
			insertsql.append(" ORDER_DETAIL_TABLE ");
			insertsql.append(" ( ");
			insertsql.append("  ORDER_ID, NO, ITEM_ID, NUMBER, AMOUNT ");
			insertsql.append(" ) ");
			insertsql.append(" values ");
			insertsql.append(" (?, ?, ?, ?, ? ) ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(insertsql.toString());
			int insindex = 1;
			statement.setLong(insindex++, detail.getOrderid());
			statement.setInt(insindex++, detail.getNo());
			statement.setString(insindex++, detail.getItemid());
			statement.setLong(insindex++, detail.getNumber());
			statement.setLong(insindex++, detail.getAmount());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.debug("注文ID={}", detail.getOrderid());
			logger.debug("明細番号={}", detail.getNo());
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
	 * 注文明細を更新
	 * @param ItemOrderDetail
	 * @return なし
	 */
	@Override
	public void update(ItemOrderDetail detail) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer updatesql = new StringBuffer();
			updatesql.append("update ");
			updatesql.append(" ORDER_DETAIL_TABLE ");
			updatesql.append(" set ");
			updatesql.append("  ITEM_ID = ? ,");
			updatesql.append("  NUMBER = ? ,");
			updatesql.append("  AMOUNT = ? ");
			updatesql.append(" where ");
			updatesql.append("  ORDER_ID = ? ");
			updatesql.append(" and ");
			updatesql.append("  NO = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(updatesql.toString());
			int index = 1;
			statement.setString(index++, detail.getItemid());
			statement.setLong(index++, detail.getNumber());
			statement.setLong(index++, detail.getAmount());
			statement.setLong(index++, detail.getOrderid());
			statement.setInt(index++, detail.getNo());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.debug("注文ID={}", detail.getOrderid());
			logger.debug("明細番号={}", detail.getNo());
			logger.debug("更新件数={}件", i);

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
	 * 注文明細をテーブルから削除
	 * @param orderid,no
	 * @return なし
	 */
	@Override
	public void delete(long orderid, int no) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer deletesql = new StringBuffer();
			deletesql.append("delete ");
			deletesql.append(" ORDER_DETAIL_TABLE ");
			deletesql.append(" where ");
			deletesql.append("  ORDER_ID = ? ");
			deletesql.append(" and ");
			deletesql.append("  NO = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(deletesql.toString());
			int index = 1;
			statement.setLong(index++, orderid);
			statement.setInt(index++, no);

			// SQL文実行
			int i = statement.executeUpdate();

			logger.debug("削除件数={}件", i);

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
