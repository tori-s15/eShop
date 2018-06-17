package dao;

import model.UserMaster;

public interface UserMasterDao {

	/**
	 * ユーザIDでユーザマスタを取得
	 * @param userid
	 * @return UserMaster
	 */
	abstract UserMaster selectByUserId(String userid);

	/**
	 * ユーザをマスタに登録
	 * @param UserMaster
	 * @return なし
	 */
	abstract void insert(UserMaster usermaster);

	/**
	 * ユーザマスタを更新
	 * @param UserMaster
	 * @return なし
	 */
	abstract void update(UserMaster usermaster);

	/**
	 * ユーザマスタを削除
	 * @param userid
	 * @return なし
	 */
	abstract void delete(String userid);
}
