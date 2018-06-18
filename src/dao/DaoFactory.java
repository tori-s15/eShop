package dao;

import dao.mysql.MySqlDaoFactory;
import dao.postgresql.PostgreSqlDaoFactory;

public class DaoFactory {

	public final static int MYSQL = 1;
	public final static int POSTGRESQL = 2;

	public AbstractDaoFactory create(int dbMode) {
		AbstractDaoFactory daofactory = null;

		if ( dbMode == MYSQL ) {
			daofactory = new MySqlDaoFactory();

		} else if( dbMode == POSTGRESQL ){
			daofactory = new PostgreSqlDaoFactory();

		}

		return daofactory;
	}

}
