package dao;

import java.util.List;

import model.ItemMaster;

public interface ItemMasterDao {

	/**
	 * IDで商品マスタを取得
	 * @param id
	 * @return ItemMaster
	 */
	abstract ItemMaster selectById(String id);

	/**
	 * 商品名で商品マスタを取得
	 * @param name
	 * @return List<ItemMaster>
	 */
	abstract List<ItemMaster> selectByName(String name);

	/**
	 * 全商品を取得
	 * @param id
	 * @return List<ItemMaster>
	 */
	abstract List<ItemMaster> selectAll();

	/**
	 * 商品をマスタに登録
	 * @param ItemMaster
	 * @return なし
	 */
	abstract void insert(ItemMaster itemmaster);

	/**
	 * 商品マスタを更新
	 * @param ItemMaster
	 * @return なし
	 */
	abstract void update(ItemMaster itemmaster);

	/**
	 * 商品をマスタから削除
	 * @param id
	 * @return なし
	 */
	abstract void delete(String id);

}
