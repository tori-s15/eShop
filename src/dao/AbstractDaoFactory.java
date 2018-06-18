package dao;

public abstract class AbstractDaoFactory {

	public abstract UserMasterDao createUserMasterDao();

	public abstract ItemMasterDao createItemMasterDao();

	public abstract OrderTableDao createOrderTableDao();

	public abstract OrderDetailTableDao createOrderDetailTableDao();
}
