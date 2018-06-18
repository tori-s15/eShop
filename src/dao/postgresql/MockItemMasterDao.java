package dao.postgresql;

import java.util.ArrayList;
import java.util.List;

import dao.ItemMasterDao;
import model.ItemMaster;

public class MockItemMasterDao extends ItemMasterDao {

	@Override
	public ItemMaster selectById(String id) {
		ItemMaster item = new ItemMaster();
		item.setItemid(id);
		item.setItemname("Java入門");
		item.setPrice(1000);
		item.setStocks(250);
		item.setDescription("Javaの入門書決定版！");

		return item;
	}

	@Override
	public List<ItemMaster> selectByName(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ItemMaster> selectAll() {
		List<ItemMaster> itemlist = new ArrayList<ItemMaster>();

		ItemMaster item = new ItemMaster();
		item.setItemid("0001");
		item.setItemname("Java入門");
		item.setPrice(1000);
		item.setStocks(250);
		item.setDescription("Javaの入門書決定版！");
		itemlist.add(item);

		item = new ItemMaster();
		item.setItemid("0002");
		item.setItemname("ロスジェネの逆襲");
		item.setPrice(1500);
		item.setStocks(10);
		item.setDescription("半沢直樹シリーズの第三弾。東京セントラル証券に左遷された半沢の逆襲が始まる！");
		itemlist.add(item);

		return itemlist;
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
