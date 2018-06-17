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

import dao.ItemMasterDao;
import model.ItemMaster;

public class PostgreSqlItemMasterDao implements ItemMasterDao {

	/** ログ出力 */
	private Logger logger = LogManager.getLogger();

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public PostgreSqlItemMasterDao(DataSource source) {
		this.source = source;
	}

	/**
	 * 商品マスタを商品IDをキーに検索
	 * @param id
	 * @return ItemMaster
	 */
	@Override
	public ItemMaster selectById(String id) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ItemMaster item = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ITEM_MASTER ");
			sql.append("where ");
			sql.append(" ITEM_ID = ? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setString(index++, id);

			rs = statement.executeQuery();

			while(rs.next()) {
				item = new ItemMaster();
				item.setItemid(rs.getString("ITEM_ID"));
				item.setItemname(rs.getString("ITEM_NAME"));
				item.setPrice(rs.getLong("PRICE"));
				item.setStocks(rs.getLong("STOCKS"));
				item.setDescription(rs.getString("DESCRIPTION"));
			}

		} catch (SQLException e) {
			logger.error("DB ERROR",e);
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return item;
	}

	/**
	 * 商品マスタを商品名をキーにLike検索
	 * @param name
	 * @return List<ItemMaster>
	 */
	@Override
	public List<ItemMaster> selectByName(String name) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<ItemMaster> itemlist = new ArrayList<ItemMaster>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ITEM_MASTER ");
			sql.append("where ");
			sql.append(" ITEM_NAME LIKE ? ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			int index = 1;
			statement.setString(index++, name);

			rs = statement.executeQuery();

			while(rs.next()) {
				ItemMaster item = new ItemMaster();
				item.setItemid(rs.getString("ITEM_ID"));
				item.setItemname(rs.getString("ITEM_NAME"));
				item.setPrice(rs.getLong("PRICE"));
				item.setStocks(rs.getLong("STOCKS"));
				item.setDescription(rs.getString("DESCRIPTION"));
				itemlist.add(item);
			}

		} catch (SQLException e) {
			logger.error("DB ERROR",e);
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}

		return itemlist;
	}


	/**
	 * 商品マスタを全件取得
	 * @param なし
	 * @return List<ItemMaster>
	 */
	@Override
	public List<ItemMaster> selectAll() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<ItemMaster> itemlist = new ArrayList<ItemMaster>();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(" * ");
			sql.append("from ");
			sql.append(" ITEM_MASTER ");

			conn = source.getConnection();
			statement = conn.prepareStatement(sql.toString());

			rs = statement.executeQuery();

			while(rs.next()) {
				ItemMaster item = new ItemMaster();
				item.setItemid(rs.getString("ITEM_ID"));
				item.setItemname(rs.getString("ITEM_NAME"));
				item.setPrice(rs.getLong("PRICE"));
				item.setStocks(rs.getLong("STOCKS"));
				item.setDescription(rs.getString("DESCRIPTION"));
				itemlist.add(item);
			}

		} catch (SQLException e) {
			logger.error("DB ERROR",e);
		} finally {
			close(rs);
			close(statement);
			close(conn);
		}
		return itemlist;
	}


	/**
	 * 商品マスタに追加
	 * @param ItemMaster
	 * @return なし
	 */
	@Override
	public void insert(ItemMaster itemmaster) {
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
			insertsql.append(" ITEM_MASTER ");
			insertsql.append(" ( ");
			insertsql.append("  ITEM_ID, ITEM_NAME, PRICE, STOCKS, DESCRIPTION ");
			insertsql.append(" ) ");
			insertsql.append(" values ");
			insertsql.append(" (?, ?, ?, ?, ? ) ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(insertsql.toString());
			int index = 1;
			statement.setString(index++, itemmaster.getItemid());
			statement.setString(index++, itemmaster.getItemname());
			statement.setLong(index++, itemmaster.getPrice());
			statement.setLong(index++, itemmaster.getStocks());
			statement.setString(index++, itemmaster.getDescription());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.trace("商品ID", itemmaster.getItemid());
			logger.trace("登録件数", i + "件");

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
	 * 商品マスタを更新
	 * @param ItemMaster
	 * @return なし
	 */
	@Override
	public void update(ItemMaster itemmaster) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Update文の定義
			StringBuffer updatesql = new StringBuffer();
			updatesql.append("update ");
			updatesql.append(" ITEM_MASTER ");
			updatesql.append(" set ");
			updatesql.append("  ITEM_NAME = ? ,");
			updatesql.append("  PRICE = ? ,");
			updatesql.append("  STOCKS = ? ,");
			updatesql.append("  DESCRIPTION = ? ");
			updatesql.append(" where ");
			updatesql.append("  ITEM_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(updatesql.toString());
			int index = 1;
			statement.setString(index++, itemmaster.getItemname());
			statement.setLong(index++, itemmaster.getPrice());
			statement.setLong(index++, itemmaster.getStocks());
			statement.setString(index++, itemmaster.getDescription());
			statement.setString(index++, itemmaster.getItemid());

			// SQL文実行
			int i = statement.executeUpdate();

			logger.trace("更新件数", i + "件");

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
	 * 商品マスタから削除
	 * @param id
	 * @return なし
	 */
	@Override
	public void delete(String id) {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			// DB接続
			conn = source.getConnection();
			conn.setAutoCommit(false);

			// Insert文の定義
			StringBuffer deletesql = new StringBuffer();
			deletesql.append("delete ");
			deletesql.append(" ITEM_MASTER ");
			deletesql.append("where ");
			deletesql.append(" ITEM_ID = ? ");

			// 実行するSQL文とパラメータの指定
			statement = conn.prepareStatement(deletesql.toString());
			int index = 1;
			statement.setString(index++, id);

			// SQL文実行
			int i = statement.executeUpdate();

			logger.trace("削除件数", i + "件");

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
