package dao.mysql;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DaoFactory;
import dao.ItemMasterDao;
import dao.OrderDetailTableDao;
import dao.OrderTableDao;
import dao.UserMasterDao;

public class MySqlDaoFactory extends DaoFactory {

	/** ログ出力 */
	private Logger logger = LogManager.getLogger();

	/**
	 * UserMasterDaoの生成
	 * @return MySqlUserMasterDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public UserMasterDao createUserMasterDao() {
		return new MySqlUserMasterDao(getDataSource());
	}

	/**
	 * ItemMasterDaoの生成
	 * @return MySqlItemMasterDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public ItemMasterDao createItemMasterDao() {
		return new MySqlItemMasterDao(getDataSource());
	}

	/**
	 * OrderTableDaoの生成
	 * @return MySqlOrderTableDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public OrderTableDao createOrderTableDao() {
		return new MySqlOrderTableDao(getDataSource());
	}

	/**
	 * OrderDetailTableDaoの生成
	 * @return MySqlOrderDetailTableDao
	 * @see dao.DaoFactory#createUserMasterDao()
	 */
	@Override
	public OrderDetailTableDao createOrderDetailTableDao() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


	/**
	 * DataSourceの取得
	 * @return DataSource
	 */
	private DataSource getDataSource() {
		// MySqlへ接続（データプール）
        InitialContext ctx = null;
		DataSource ds = null;
        try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mysql");

        } catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			logger.error("DB ERROR",e);
		}
        return ds;
	}


}
