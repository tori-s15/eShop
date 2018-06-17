package dao.postgresql;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.DaoFactory;
import dao.ItemMasterDao;
import dao.OrderDetailTableDao;
import dao.OrderTableDao;
import dao.UserMasterDao;

public class PostgreSqlDaoFactory extends DaoFactory {

	/**
	 * UserMasterDaoの生成
	 * @return PostgreSqlUserMasterDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public UserMasterDao createUserMasterDao() {
		return new PostgreSqlUserMasterDao(getDataSource());
	}

	/**
	 * ItemMasterDaoの生成
	 * @return PostgreSqlItemMasterDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public ItemMasterDao createItemMasterDao() {
		return new PostgreSqlItemMasterDao(getDataSource());
	}

	/**
	 * OrderTableDaoの生成
	 * @return PostgreSqlOrderTableDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public OrderTableDao createOrderTableDao() {
		return new PostgreSqlOrderTableDao(getDataSource());
	}

	/**
	 * OrderDetailTableDaoの生成
	 * @return PostgreSqlOrderDetailTableDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public OrderDetailTableDao createOrderDetailTableDao() {
		return new PostgreSqlOrderDetailTableDao(getDataSource());
	}


	/**
	 * DataSourceの取得
	 * @return DataSource
	 */
	private DataSource getDataSource() {
		// Postgresqlへ接続（データプール）
        InitialContext ctx = null;
		DataSource ds = null;
        try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");

        } catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

        return ds;

	}


}
