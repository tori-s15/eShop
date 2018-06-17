package dao.mysql;

import java.util.List;

import javax.sql.DataSource;

import dao.ItemMasterDao;
import model.ItemMaster;

public class MySqlItemMasterDao implements ItemMasterDao {

	/** コネクション */
	private DataSource source;

	/**
	 * コンストラクタ
	 * @param DataSource
	 */
	public MySqlItemMasterDao(DataSource source) {
		this.source = source;
	}

	@Override
	public ItemMaster selectById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ItemMaster> selectByName(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ItemMaster> selectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void insert(ItemMaster itemmaster) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void update(ItemMaster itemmaster) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void delete(String id) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
