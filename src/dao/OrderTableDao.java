package dao;

import java.util.List;

import model.ItemOrder;

public abstract class OrderTableDao {

	/**
	 * 注文IDで注文情報を取得
	 * @param orderid
	 * @return ItemOrder
	 */
	public abstract ItemOrder selectByOrderId(long orderid);

	/**
	 * ユーザーIDで注文情報を取得
	 * @param userid
	 * @return List<ItemOrder>
	 */
	public abstract List<ItemOrder> selectByUserId(String userid);


	/**
	 * ユーザーIDで注文情報を取得（カート格納分のみ）
	 * @param userid
	 * @return ItemOrder
	 */
	public abstract ItemOrder selectByUserIdInitOnly(String userid);

	/**
	 * 注文情報を登録
	 * @param ItemOrder
	 * @return orderid
	 */
	public abstract long insert(ItemOrder order);

	/**
	 * 注文情報を更新
	 * @param ItemOrder
	 * @return なし
	 */
	public abstract void update(ItemOrder order);

	/**
	 * 注文情報をマスタから削除
	 * @param orderid
	 * @return なし
	 */
	public abstract void delete(long orderid);


}
