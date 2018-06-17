package dao;

public abstract class DaoFactory {

	/**
	 * UserMasterのDAOを生成
	 * @param なし
	 * @return なし
	 */
	abstract public UserMasterDao createUserMasterDao();

	/**
	 * ItemMasterのDAOを生成
	 * @param なし
	 * @return なし
	 */
	abstract public ItemMasterDao createItemMasterDao();

	/**
	 * OrderテーブルのDAOを生成
	 * @param なし
	 * @return なし
	 */
	abstract public OrderTableDao createOrderTableDao();

	/**
	 * OrderDetailテーブルのDAOを生成
	 * @param なし
	 * @return なし
	 */
	abstract public OrderDetailTableDao createOrderDetailTableDao();
}
