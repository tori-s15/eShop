package dao.mysql;

import java.util.List;

import javax.sql.DataSource;

import dao.OrderTableDao;
import model.ItemOrder;

public class MySqlOrderTableDao implements OrderTableDao {

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public MySqlOrderTableDao(DataSource source) {
		this.source = source;
	}

	@Override
	public ItemOrder selectByOrderId(long orderid) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ItemOrder> selectByUserId(String userid) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public long insert(ItemOrder order) {
		return 0;
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(ItemOrder order) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(long orderid) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public ItemOrder selectByUserIdInitOnly(String userid) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
