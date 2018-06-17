package dao.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dao.OrderTableDao;
import model.ItemOrder;

public class PostgreSqlOrderTableDao implements OrderTableDao {

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public PostgreSqlOrderTableDao(DataSource source) {
		this.source = source;
	}

	/**
	 * 注文テーブルを注文IDをキーに検索
	 * @param id
	 * @return ItemOrder
	 */
	@Override
	public ItemOrder selectByOrderId(long orderid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ItemOrder order = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ORDER_TABLE ");
			sql.append("where ");
			sql.append(" ORDER_ID =? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setLong(index++, orderid);

			rs = statement.executeQuery();

			while(rs.next()) {
				order = new ItemOrder();
				order.setOrderid(rs.getLong("ORDER_ID"));
				order.setUserid(rs.getString("USER_ID"));
				order.setTotalamount(rs.getLong("TOTAL_AMOUNT"));
				order.setPayment(rs.getInt("PAYMENT"));
				order.setAddress(rs.getString("ADDRESS"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return order;
	}

	/**
	 * 注文テーブルをユーザIDをキーに検索
	 * @param id
	 * @return List<ItemOrder>
	 */
	@Override
	public List<ItemOrder> selectByUserId(String userid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<ItemOrder> orderlist = new ArrayList<ItemOrder>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ORDER_TABLE ");
			sql.append("where ");
			sql.append(" USER_ID =? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setString(index++, userid);

			rs = statement.executeQuery();

			while(rs.next()) {
				ItemOrder order = new ItemOrder();
				order.setOrderid(rs.getLong("ORDER_ID"));
				order.setUserid(rs.getString("USER_ID"));
				order.setTotalamount(rs.getLong("TOTAL_AMOUNT"));
				order.setPayment(rs.getInt("PAYMENT"));
				order.setAddress(rs.getString("ADDRESS"));
				orderlist.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return orderlist;
	}

	/**
	 * ユーザーIDで注文情報を取得（カート格納分のみ）
	 * @param userid
	 * @return ItemOrder
	 */
	@Override
	public ItemOrder selectByUserIdInitOnly(String userid) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ItemOrder order =  null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ORDER_TABLE ");
			sql.append("where ");
			sql.append(" STATUS = " + ItemOrder.STATUS_INIT);
			sql.append(" and ");
			sql.append(" USER_ID =? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setString(index++, userid);

			rs = statement.executeQuery();

			while(rs.next()) {
				order = new ItemOrder();
				order.setOrderid(rs.getLong("ORDER_ID"));
				order.setUserid(rs.getString("USER_ID"));
				order.setTotalamount(rs.getLong("TOTAL_AMOUNT"));
				order.setPayment(rs.getInt("PAYMENT"));
				order.setAddress(rs.getString("ADDRESS"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return order;
	}

	/**
	 * 注文テーブルにレコードを追加して、採番した注文IDを返す
	 * @param ItemOrder
	 * @return orderid
	 */
	@Override
	public long insert(ItemOrder order)  {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int orderid = 0;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer insertsql = new StringBuffer();
			insertsql.append("insert ");
			insertsql.append(" into ");
			insertsql.append(" ORDER_TABLE ");
			insertsql.append(" ( ");
			insertsql.append("  USER_ID, TOTAL_AMOUNT, PAYMENT, ADDRESS, STATUS ");
			insertsql.append(" ) ");
			insertsql.append(" values ");
			insertsql.append(" (?, ?, ?, ?, ? ) ");
			insertsql.append(" returning ORDER_ID ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(insertsql.toString());
			int index = 1;
			statement.setString(index++, order.getUserid());
			statement.setLong(index++, order.getTotalamount());
			statement.setInt(index++, order.getPayment());
			statement.setString(index++, order.getAddress());
			statement.setInt(index++, ItemOrder.STATUS_INIT);

			// SQL文実行
			rs = statement.executeQuery();

			// 注文ID取得
			while(rs.next()) {
				orderid = rs.getInt("ORDER_ID");
			}

			System.out.println("登録[ORDER_TABLE]：注文ID：" + orderid);

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return orderid;

	}

	/**
	 * 注文テーブルを更新
	 * @param ItemOrder
	 * @return なし
	 */
	@Override
	public void update(ItemOrder order) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Update文の定義
			StringBuffer updatesql = new StringBuffer();
			updatesql.append("update ");
			updatesql.append(" ORDER_TABLE ");
			updatesql.append(" set ");
			updatesql.append("  TOTAL_AMOUNT = ? ,");
			updatesql.append("  PAYMENT = ? ,");
			updatesql.append("  ADDRESS = ? ,");
			updatesql.append("  STATUS = ? ");
			updatesql.append(" where ");
			updatesql.append("  ORDER_ID = ? ");
			updatesql.append(" and ");
			updatesql.append("  USER_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(updatesql.toString());
			int index = 1;
			statement.setLong(index++, order.getTotalamount());
			statement.setInt(index++, order.getPayment());
			statement.setString(index++, order.getAddress());
			statement.setInt(index++, order.getStatus());
			statement.setLong(index++, order.getOrderid());
			statement.setString(index++, order.getUserid());

			// SQL文実行
			int i = statement.executeUpdate();

			System.out.println("更新[ORDER_TABLE]：" + i + "件");

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			close(statement);
			close(conn);
		}
	}

	/**
	 * 注文テーブルから削除
	 * @param orderid
	 * @return なし
	 */
	@Override
	public void delete(long orderid) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer deletesql = new StringBuffer();
			deletesql.append("delete ");
			deletesql.append(" ORDER_TABLE ");
			deletesql.append("where ");
			deletesql.append(" ORDER_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(deletesql.toString());
			int index = 1;
			statement.setLong(index++, orderid);

			// SQL文実行
			int i = statement.executeUpdate();

			System.out.println("更新：" + i + "件");

			// コミット
			conn.commit();

		} catch (SQLException  e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
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
