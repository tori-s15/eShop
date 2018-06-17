package dao;

import java.util.List;

import model.ItemOrderDetail;;

public interface OrderDetailTableDao {

	/**
	 * 注文IDで注文情報明細を取得
	 * @param orderid
	 * @return List<ItemOrderDetail>
	 */
	abstract List<ItemOrderDetail> selectByOrderId(long orderid);

	/**
	 * 注文明細を登録
	 * @param ItemOrderDetail
	 * @return なし
	 */
	abstract void insert(ItemOrderDetail order);

	/**
	 * 注文明細を更新
	 * @param ItemOrderDetail
	 * @return なし
	 */
	abstract void update(ItemOrderDetail order);

	/**
	 * 注文明細をテーブルから削除
	 * @param orderid,no
	 * @return なし
	 */
	abstract void delete(long orderid, int no);


}
