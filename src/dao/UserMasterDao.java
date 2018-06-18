package dao;

import model.UserMaster;

public abstract class UserMasterDao {

	/**
	 * ユーザIDでユーザマスタを取得
	 * @param userid
	 * @return UserMaster
	 */
	public abstract UserMaster selectByUserId(String userid);

	/**
	 * ユーザをマスタに登録
	 * @param UserMaster
	 * @return なし
	 */
	public abstract void insert(UserMaster usermaster);

	/**
	 * ユーザマスタを更新
	 * @param UserMaster
	 * @return なし
	 */
	public abstract void update(UserMaster usermaster);

	/**
	 * ユーザマスタを削除
	 * @param userid
	 * @return なし
	 */
	public abstract void delete(String userid);
}
