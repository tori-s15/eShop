package dao;

import java.util.List;

import model.ItemOrder;

public interface OrderTableDao {

	/**
	 * 注文IDで注文情報を取得
	 * @param orderid
	 * @return ItemOrder
	 */
	abstract ItemOrder selectByOrderId(long orderid);

	/**
	 * ユーザーIDで注文情報を取得
	 * @param userid
	 * @return List<ItemOrder>
	 */
	abstract List<ItemOrder> selectByUserId(String userid);


	/**
	 * ユーザーIDで注文情報を取得（カート格納分のみ）
	 * @param userid
	 * @return ItemOrder
	 */
	abstract ItemOrder selectByUserIdInitOnly(String userid);

	/**
	 * 注文情報を登録
	 * @param ItemOrder
	 * @return orderid
	 */
	abstract long insert(ItemOrder order);

	/**
	 * 注文情報を更新
	 * @param ItemOrder
	 * @return なし
	 */
	abstract void update(ItemOrder order);

	/**
	 * 注文情報をマスタから削除
	 * @param orderid
	 * @return なし
	 */
	abstract void delete(long orderid);


}
