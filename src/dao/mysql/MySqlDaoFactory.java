package dao.mysql;

import javax.sql.DataSource;

import dao.DaoFactory;
import dao.ItemMasterDao;
import dao.OrderDetailTableDao;
import dao.OrderTableDao;
import dao.UserMasterDao;

public class MySqlDaoFactory extends DaoFactory {

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
		return null;

	}


}
